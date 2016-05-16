package base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.entity.FtpServerConfig;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 * sftp 基本操作
 * @author xing.zeng
 * create time :2016年5月12日
 */
public class SFTPUtils {
	private final static Logger log = LoggerFactory.getLogger(SFTPUtils.class);
	
	private final static int reconnectionNum = 3;
	
	/**
	 * 初始化sftp
	 * add by xzeng 2016年5月12日
	 * @param fsc
	 * @return
	 */
	private static ChannelSftp getChannelSftp(FtpServerConfig fsc){
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			Session sshSession = jsch.getSession(fsc.getUsername(), fsc.getServer(), fsc.getPort());
			
			sshSession.setPassword(fsc.getPassword());
			Properties sshConfig = new Properties();
			sshConfig.setProperty("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.setTimeout(10000);
			sshSession.connect();
			
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			
			sftp = (ChannelSftp) channel;
			log.info("======== Connected to {}. ========", fsc.getServer());
		} catch (Exception e) {
			sftp = null;
			log.error(">>>>>>>> SFTP connect error:{} <<<<<<<<", e.getMessage());
		}
		return sftp;
	}
	
	/**
	 * 连接sftp
	 * add by xzeng 2016年5月12日
	 * @param fsc
	 * @return
	 */
	public static ChannelSftp connectSftp(FtpServerConfig fsc){
		ChannelSftp sftp = null;
		for(int i = 0; i < reconnectionNum; i++){//防止连接FTP中间网络有间隙
			sftp = getChannelSftp(fsc);
			if(null != sftp){
				break;
			}
		}
		return sftp;
	}
	
	/**
	 * 上传文件
	 * add by xzeng 2016年5月12日
	 * @param sftp
	 * @param nameTransferFun
	 * @param directory
	 * @param uploadFile
	 * @return
	 */
	public static boolean upload(ChannelSftp sftp, String remoteDir, String uploadFile) {
		FileInputStream fis = null;
		try {
			sftp.cd(remoteDir);
			
			File file = new File(uploadFile);
			fis = new FileInputStream(file);
			// 临时文件
			String tempFile = file.getName() + ".tmp";
			// 上传操作
			sftp.put(fis, tempFile);
			// 重命名
			sftp.rename(tempFile, file.getName());
			return true;
		} catch (Exception e) {
			log.error(">>>>>>>> upload file:{}; error:{}.<<<<<<<<",uploadFile,e.getMessage());
			return false;
		}finally{
			IOUtils.closeQuietly(fis);
		}
	}
	
	/**
	 * 上传并备份文件
	 * add by xzeng 2016年5月13日
	 * @param sftp
	 * @param remoteDir
	 * @param localBackup
	 * @param uploadFile
	 */
	public static boolean uploadAndBackup(ChannelSftp sftp, String remoteDir, String localBackup, String uploadFile){
		boolean result = upload(sftp, remoteDir, uploadFile);
		if(result){
			File file = new File(uploadFile);
			log.info("======= uploadAndBackup:upload file success. ========");
			return renameFile(uploadFile, localBackup + "/" + file.getName());
		}else{
			log.info("======== uploadAndBackup:upload file fail. ========");
			return result;
		}
	}
	
	/**
	 * 下载文件
	 * add by xzeng 2016年5月12日
	 * @param sftp
	 * @param nameTransferFun
	 * @param directory
	 * @param remoteFile
	 * @param localFile
	 * @return
	 */
	public static boolean download(ChannelSftp sftp, String directory, String remoteFile, String localFile) {
		FileOutputStream fos = null;
		try {
			sftp.cd(directory);
			File file = new File(localFile);
			fos = new FileOutputStream(file);
			
			sftp.get(remoteFile, fos);
			
			return true;
		} catch (Exception e) {
			log.error(">>>>>>>> download file:{}; error:{}.<<<<<<<<",remoteFile, e.getMessage());
			return false;
		}finally{
			if (fos!=null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					log.error(">>>>>>>> close file error:{}.<<<<<<<<", e.getMessage());
				}
			}
		}
	}
	/**
	 * 下载文件
	 * add by xzeng 2016年5月12日
	 * @param fsc
	 * @param remoteDir
	 * @param localDir
	 * @param remoteBakDir
	 * @param matchPattern
	 * @return
	 */
	public static List<Map<String,String>> downloadAndBackup(FtpServerConfig fsc,String remoteDir,String localDir,String remoteBakDir, String matchPattern){
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		ChannelSftp sftp = connectSftp(fsc);
		if(sftp != null){
			log.info("======== downloadAndBackup:remoteDir:{},localDir:{} ========", new String[]{remoteDir,localDir});
			try{
				Map<String,String> fileParams = new HashMap<String, String>();
				// 获取远程目录下的文件列表
				List<String> list = listFiles(sftp,remoteDir);
				for (String file : list) {
					log.info("======== downloadAndBackup: file:{} to download. ========",file);
					if(StringUtils.isNotEmpty(matchPattern) && !file.matches(matchPattern)){	// 文件过滤
						continue;
					}
					String tmpFile = localDir + file + ".tmp";
					// 下载文件
					boolean downloadFlag = download(sftp, remoteDir, file, tmpFile);
					if(downloadFlag){
						log.info("======== downloadAndBackup: file download success. ========");
						// 重命名文件
						boolean renameFlag = renameFile(tmpFile, localDir + file);
						
						if(!renameFlag){	// 重命名失败，则删除文件
							log.error(">>>>>>>> downloadAndBackup: file rename fail. <<<<<<<<");
							File f= new File(tmpFile);
							f.delete();
							continue;
						}
						
						if(StringUtils.isNotEmpty(remoteBakDir)){	// 备份文件
							log.info("======== downloadAndBackup:remoteBakDir:{} ========",remoteBakDir);
							backupFile(sftp, remoteDir, remoteBakDir, file);
						}else{	// 直接删除文件
							log.info("======== downloadAndBackup:remote file delete. ========");
							delete(sftp, remoteDir, file);
						}
						
						// 将下载成功的文件放回返回数据结构
						fileParams.put("localDir", localDir);
						fileParams.put("fileName", file);
						resultList.add(fileParams);
					}else{
						log.error(">>>>>>>> downloadAndBackup: file download fail. <<<<<<<<");
					}
				}
			}finally{
				close(sftp);
			}
		}
		return resultList;
	}
	
	/**
	 * 
	 * add by xzeng 2016年5月12日
	 * @param sftp
	 * @param directory
	 * @param deleteFile
	 */
	public static boolean delete(ChannelSftp sftp, String directory, String deleteFile) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
			return true;
		} catch (Exception e) {
			log.error(">>>>>>>> delete file:{}; error:{}.<<<<<<<<", deleteFile, e.getMessage());
			return false;
		}
	}
	
	/**
	 * 备份文件
	 * add by xzeng 2016年5月12日
	 * @param sftp
	 * @param srcFile
	 * @param destFile
	 */
	public static boolean backupFile(ChannelSftp sftp, String srcFile, String destFile){
		log.info("======== source File:{},dest File:{}. ========", new String[]{srcFile,destFile});
		try{
			sftp.rename(srcFile, destFile);
			return true;
		}catch(Exception e){
			log.error(">>>>>>>> backup file:{} to file:{}, error:{}.<<<<<<<<", new String[]{srcFile,destFile,e.getMessage()});
			return false;
		}
	}
	
	/**
	 * 备份文件
	 * add by xzeng 2016年5月12日
	 * @param sftp
	 * @param srcDir
	 * @param destDir
	 * @param fileName
	 */
	public static void backupFile(ChannelSftp sftp, String srcDir, String destDir, String fileName){
		Date date = new Date(System.currentTimeMillis());
		String srcFile = srcDir + fileName;
		String bakDir = destDir + DateUtils.formatDate(date) + "/" ;
		
		// 当目录不存在的话，创建目录
        try {
        	sftp.ls(bakDir);
        }catch (Exception e) {
        	try{
        		sftp.mkdir(bakDir);
        	}catch(Exception ex){
        		log.error(">>>>>>>> backupFile: make dir:{},error:{}.<<<<<<<<", destDir, ex.getMessage());
        	}
        }
		try{
			
			sftp.rename(srcFile, bakDir + fileName);
		}catch(Exception e){
			log.error(">>>>>>>> backupFile: src:{} to dest:{}, error:{}.<<<<<<<<", new String[]{srcFile, bakDir + fileName, e.getMessage()});
		}
	}
	
	/**
	 * 文件重命名
	 * add by xzeng 2016年5月12日
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	private static boolean renameFile (String srcFile,String destFile){
		log.info("======== renameFile:source: {} to dest:{}. ========",new String[]{srcFile, destFile});
		File sFile = new File(srcFile);
		File dFile = new File(destFile);
		return sFile.renameTo(dFile);
	}
	
	/**
	 * 获取目录下的文件列表
	 * add by xzeng 2016年5月12日
	 * @param sftp
	 * @param directory
	 * @return
	 */
	public static List<String> listFiles(ChannelSftp sftp, String directory){
		try{
			Vector<LsEntry> ls = sftp.ls(directory);
		
			List<String> list = new ArrayList<String>();
			if (ls != null && ls.size() >= 0) {
				for (int i = 0; i < ls.size(); i++) {
					LsEntry f = (LsEntry) ls.get(i);
					String nm = f.getFilename();
					if (nm.equals(".") || nm.equals(".."))
						continue;
					SftpATTRS attr = f.getAttrs();
					if (!attr.isDir()) {
						list.add(nm);
					}
				}
			}
			return list;
		}catch(Exception e){
			log.error(">>>>>>>> list dir:{}; error:{}.<<<<<<<<", directory, e.getMessage());
			return null;
		}
	}
	
	/**
	 * 关闭sftp连接
	 * add by xzeng 2016年5月12日
	 * @param sftp
	 * @return
	 */
	public static boolean close(ChannelSftp sftp){
		if (sftp != null && sftp.isConnected()) {
			try {
				sftp.getSession().disconnect();
				sftp.disconnect();
				sftp = null;
				return true;
			} catch (JSchException e) {
				log.error(">>>>>>>> close sftp error:{}.<<<<<<<<", e.getMessage());
				return false;
			}
		}else{
			log.info(">>>>>>>> There is no sftp to close.<<<<<<<<");
			return true;
		}
	}
}
