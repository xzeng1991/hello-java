package base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.entity.FtpServerConfig;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 *SFTP操作类
 *@author haijun.zou
 *@date 2015-05-18
 */
public class SFTPUtil {
	private final static Logger log = LoggerFactory.getLogger(SFTPUtil.class);
	static int reconnectionNum = 3;


	/**
	 *得到 ChannelSftp
	 *@param fsc
	 *@return ChannelSftp
	 */
	public static ChannelSftp getChannelSftp(FtpServerConfig fsc){
		ChannelSftp sftp= connect(fsc);
		return sftp;
	}
	/**
	 *下载文件并备份 downloadFilesAndBackByDirect
	 * @param remoteDir ftp上目录
	 * @param localDir 本地目录
	 * @param backPath 备份目录
	 * @param fsc 服务器信息
	 */
	public static void downloadFilesAndBackByDirect(String remoteDir,String localDir,String backPath,FtpServerConfig fsc,String source){
		ChannelSftp sftp = getChannelSftp(fsc);
		if(null == sftp){
			for(int i=0;i<reconnectionNum;i++){//防止连接FTP中间网络有间隙
				sftp = getChannelSftp(fsc);
				if(null != sftp){
					break;
				}
			}
			if(null == sftp){
				return;
			}
		}
		Date date = new Date(System.currentTimeMillis()-10*60*1000);//作业时间延迟十分钟
		try {
			String[] remoteDirs = remoteDir.split(",");//远程文件路径
			String[] localDirs = localDir.split(",");//共享磁盘路径
			String[] remoteRDirs = backPath.split(",");//远程备份文件路径
			if (remoteDirs.length!=localDirs.length && remoteDirs.length!=remoteRDirs.length) {
				throw new Exception("remote file have not this local file");
			}
			
			/*for(int i = 0;i<remoteDirs.length;i++){
				remoteDirs[i] = remoteDirs[i] + "/";
			}
			
			for(int i = 0;i<localDirs.length;i++){
				localDirs[i] = localDirs[i] + DateUtils.SDF_STR_YYYYMMDD.format(date) + "/";
				FileUtils.createMyFile(localDirs[i]);//目录不存在便创建
			}*/
			
			for(int i = 0;i<remoteRDirs.length;i++){
				remoteRDirs[i] = remoteRDirs[i] + DateUtils.formatDatetime(date) + "/";
				try {
					sftp.ls(remoteRDirs[i]);
				} catch (Exception e) {
					try {
						sftp.mkdir(remoteRDirs[i]);
					} catch (Exception e2) {
						log.info("sftpUtil make dirs error {}",e.getMessage());
						e.printStackTrace();
					}
				}
//				FileUtils.createMyFile(remoteRDirs[i]);//目录不存在便创建
			}
			
			for (int i = 0;i<remoteDirs.length;i++) {
				log.info("remote path is : {} ",remoteDirs[i]);
				List<String> list = getFilesByDirect(remoteDirs[i],sftp);
				for (String file : list) {
					log.info("will down file is : {} ",file);
					//下载文件
					download(remoteDirs[i], file, localDirs[i]+File.separator+file, sftp);
					//更改本地文件名称
					boolean flag = renameFile(localDirs[i]+File.separator+file);
					if(!flag){
						File f= new File(localDirs[i]+File.separator+file + ".tmp");
						f.delete();
					}
					//备份远端文件
					try {
						backFile(remoteDirs[i], remoteRDirs[i],file,sftp);
					} catch (Exception e) {//待优化
						try {
							backFile(remoteDirs[i], remoteRDirs[i],file,System.currentTimeMillis()+"",sftp);
						} catch (Exception e2) {
							e.printStackTrace();
						}
					}
					
					
					/*if("RT".equals(source)){//记录下载的文件名称，方便后面数据核对
						InterfaceLog log = new InterfaceLog();
						log.setId(IdentityHelper.identityLong(InterfaceLog.class));
						log.setCutomerId(source);//来源
						log.setFileName(file);//文件名称
						log.setStatus(0);//未处理
						log.setCreateTime(new Timestamp(System.currentTimeMillis()));//创建时间
						log.setUpdateTime(new Timestamp(System.currentTimeMillis()));//更新时间
					}*/
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeSftp(sftp);
		}
	}
	
	/**
	 * 备份目录下以天为目录
	 * @param remoteDir
	 * @param localDir
	 * @param backPath
	 * @param fsc
	 */
	public static List<Map<String,String>> downloadFilesAndBackByDirect(String remoteDir,String localDir,String backPath,String localBak,FtpServerConfig fsc) throws Exception{
		ChannelSftp sftp = getChannelSftp(fsc);
		if(null == sftp){
			for(int i=0;i<reconnectionNum;i++){//防止连接FTP中间网络有间隙
				sftp = getChannelSftp(fsc);
				if(null != sftp){
					break;
				}
			}
			if(null == sftp){
				return null;
			}
		}
		Date date = new Date(System.currentTimeMillis()-10*60*1000);//作业时间延迟十分钟
		List<String> list = new ArrayList<String>();
		List<Map<String,String>> fullInfoList = new ArrayList<Map<String,String>>();
		try {
			String[] remoteDirs = remoteDir.split(",");//远程文件路径
			String[] localDirs = localDir.split(",");//共享磁盘路径
			String[] remoteRDirs = backPath.split(",");//远程备份文件路径
			String[] remoteBaks = localBak.split(",");//远程备份文件路径
			if (remoteDirs.length!=localDirs.length && remoteDirs.length!=remoteRDirs.length) {
				throw new Exception("remote file have not this local file");
			}
			
			for(int i = 0;i<remoteRDirs.length;i++){
				remoteRDirs[i] = remoteRDirs[i]+"/" + DateUtils.formatDatetime(date) + "/";
				try {
					sftp.ls(remoteRDirs[i]);
				} catch (Exception e) {
					try {
						sftp.mkdir(remoteRDirs[i]);
					} catch (Exception e2) {
						log.info("sftpUtil make dirs error {}",e.getMessage());
						e.printStackTrace();
					}
				}
			}
			
			Map<String,String> fileParams = new HashMap<String, String>();
			for (int i = 0;i<remoteDirs.length;i++) {
				log.info("remote path is : {} ",remoteDirs[i]);
				list = getFilesByDirect(remoteDirs[i],sftp);
				for (String file : list) {
					log.info("will down file is : {} ",file);
					//下载文件
					download(remoteDirs[i], file, localDirs[i]+File.separator+file, sftp);
					//更改本地文件名称
					boolean flag = renameFile(localDirs[i]+File.separator+file);
					if(!flag){
						File f= new File(localDirs[i]+File.separator+file + ".tmp");
						f.delete();
					}
					//备份远端文件
					try {
						backFile(remoteDirs[i], remoteRDirs[i],file,sftp);
					} catch (Exception e) {//待优化
						try {
							backFile(remoteDirs[i], remoteRDirs[i],file,System.currentTimeMillis()+"",sftp);
						} catch (Exception e2) {
							e.printStackTrace();
						}
					}
					
					fileParams.put("localDir", localDirs[i]);
					fileParams.put("localBaK", remoteBaks[i]);
					fileParams.put("fileName", file);
					
					fullInfoList.add(fileParams);
				}
			}
			
		}catch (Exception e) {
			throw e;
		}finally{
			closeSftp(sftp);
		}
		return fullInfoList;
	}
	
	/**
	 * 
	 * @param directory
	 * @param uploadFile
	 * @param sftp
	 */
	public static void safeUpload(String directory, String uploadFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			upload(directory, uploadFile, sftp);//上传临时文件
			boolean flag = renameFile(directory+File.separator+uploadFile);
			if(!flag){
				File f= new File(directory+File.separator+uploadFile + ".tmp");
				f.delete();
			}
			sftp.put(new FileInputStream(file), file.getName());//写完文件后修改文件名称
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 目录下所有文件
	 * @param remoteDir 远程目录
	 * @param sftp
	 * @return List<String>
	 */
	public static List<String> getFilesByDirect(String remoteDir,ChannelSftp sftp){
		log.info("remote path is : {} ",remoteDir);
		List<String> list = new ArrayList<String>();
		try {
			list = buildFiles(listFiles(remoteDir, sftp));
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 *下载文件
	 * @param remoteDir 远程目录
	 * @param localDir 本地目录
	 * @param sftp
	 * @return void
	 */
	public static void downloadFilesByDirect(String remoteDir,String localDir,ChannelSftp sftp){
		try {
			log.info("remote path is : {} ",remoteDir);
			List<String> list = getFilesByDirect(remoteDir,sftp);
			for (String file : list) {
				log.info("will down file is : {} ",file);
				download(remoteDir, file, localDir+File.separator+file, sftp);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *备份目录文件
	 *@param remote 远程目录
	 *@param backPath 备份目录
	 *@param file 需要备份的文件
	 *@param sftp
	 * @throws Exception 
	 */
	public static void backFile(String remote,String backPath,String file,ChannelSftp sftp) throws Exception{
		if (remote.endsWith("/")) {
			remote = remote+file;
		}else {
			remote = remote+"/"+file;
		}
		if (backPath.endsWith("/")) {
			backPath=backPath+file;
		}else {
			backPath=backPath+"/"+file;
		}
		log.info("source file is {} and dest file is {}",new String[]{remote,backPath});
		move(remote, backPath,sftp);
		/*try {
			if (remote.endsWith("/")) {
				remote = remote+file;
			}else {
				remote = remote+"/"+file;
			}
			if (backPath.endsWith("/")) {
				backPath=backPath+file;
			}else {
				backPath=backPath+"/"+file;
			}
			LOG.info("source file is {} and dest file is {}",new String[]{remote,backPath});
			SFTPOperate.move(remote, backPath,sftp);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	/**
	 *备份目录文件
	 *@param remote 远程目录
	 *@param backPath 备份目录
	 *@param file 需要备份的文件
	 *@param sftp
	 * @throws Exception 
	 */
	public static void backFile(String remote,String backPath,String file,String suffix,ChannelSftp sftp) throws Exception{
		if (remote.endsWith("/")) {
			remote = remote+file;
		}else {
			remote = remote+"/"+file;
		}
		if (backPath.endsWith("/")) {
			backPath=backPath+file+"."+suffix;
		}else {
			backPath=backPath+"/"+file+"."+suffix;
		}
		log.info("source file is {} and dest file is {}",new String[]{remote,backPath});
		move(remote, backPath,sftp);
		/*try {
			if (remote.endsWith("/")) {
				remote = remote+file;
			}else {
				remote = remote+"/"+file;
			}
			if (backPath.endsWith("/")) {
				backPath=backPath+file;
			}else {
				backPath=backPath+"/"+file;
			}
			LOG.info("source file is {} and dest file is {}",new String[]{remote,backPath});
			SFTPOperate.move(remote, backPath,sftp);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	/**
	 * 更改文件名称
	 * @param ntf
	 * @param destFile
	 * @return boolean
	 */
	public static boolean renameFile (String destFile){
		String sourceFile = destFile;
		File sFile = new File(sourceFile);
		File dFile = new File(destFile);
		log.info("source file is {} and dest file is : {} ",new String[]{sFile.getAbsolutePath(),dFile.getAbsolutePath()});
		return sFile.renameTo(dFile);
	}
	
	/**
	 * 连接sftp服务器
	 * @param sc
	 * @return
	 */
	public static ChannelSftp connect(FtpServerConfig sc) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			Session sshSession = jsch.getSession(sc.getUsername(), sc.getServer(), sc.getPort());
			log.info("Session created.");
			sshSession.setPassword(sc.getPassword());
			Properties sshConfig = new Properties();
			log.info("StrictHostKeyChecking", "no");
			sshConfig.setProperty("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.setTimeout(10000);
			sshSession.connect();
			log.info("Session connected.");
			log.info("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			log.info("Connected to " + sc.getServer() + ".");
		} catch (Exception e) {
			sftp = null;
			e.printStackTrace();
		}
		return sftp;
	}

	/**
	 * 上传文件
	 *
	 * @param directory 上传的目录
	 * @param uploadFile  要上传的文件
	 * @param sftp
	 */
	/*public static boolean upload(String directory, String uploadFile, ChannelSftp sftp) {
		boolean falg = false;
		InputStream is =null;
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			is = new FileInputStream(file);
			sftp.put(is, file.getName()+".tmp");
			sftp.rename(file.getName()+".tmp", file.getName());
			falg = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return falg;
	}*/

	/**
	 * 上传文件
	 *
	 * @param directory 上传的目录
	 * @param uploadFile  要上传的文件
	 * @param sftp
	 */
	public static void upload(String directory, String uploadFile, ChannelSftp sftp) {
		FileInputStream fis = null;
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			fis = new FileInputStream(file);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fis);
		}
	}
	/**
	 * 下载文件
	 *
	 * @param directory 下载目录
	 * @param downloadFile 下载的文件
	 * @param saveFile 存在本地的路径
	 * @param sftp
	 */
	public static void download(String directory, String downloadFile,
								String saveFile, ChannelSftp sftp) {
		FileOutputStream fos = null;
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			fos = new FileOutputStream(file);
			sftp.get(downloadFile, fos);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (fos!=null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * 下载文件
	 *
	 * @param directory 下载目录
	 * @param downloadFile 下载的文件
	 * @param saveFile 存在本地的路径
	 * @param sftp
	 */
	/*public static void download(String directory, String downloadFile,
								String saveFile, ChannelSftp sftp) {
		FileOutputStream fos = null;
		try {
			sftp.cd(directory);
			String tmpFile = saveFile;
			File file = new File(tmpFile);
			fos = new FileOutputStream(file);
			sftp.get(downloadFile, fos);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (fos!=null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}*/

	/**
	 * 删除文件
	 * @param directory 要删除文件所在目录
	 * @param deleteFile  要删除的文件
	 * @param sftp
	 */
	public static void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 *
	 * @param directory  要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public static Vector<LsEntry> listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}
	/**
	 *得到所有的文件
	 *@param ls
	 *@return List<String> 如果没得.返回new ArrayList<String>
	 */
	public static List<String> buildFiles(Vector<LsEntry> ls) throws Exception {
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
	}
	/**
	 *关闭链接
	 * @param sftp
	 * @return void
	 */
	public static void close(ChannelSftp sftp){
		if (sftp != null && sftp.isConnected()) {
			try {
				sftp.getSession().disconnect();
			} catch (JSchException e) {
				e.printStackTrace();
			}
			sftp.disconnect();
			sftp = null;
		}
	}
	/**
	 * 移动文件
	 * @author haijun.zou
	 *
	 */
	public static void move(String source,String dest,ChannelSftp sftp) throws Exception{
		sftp.rename(source, dest);
		/*try {
			sftp.rename(source, dest);
		} catch (SftpException e) {
			
		}*/
	}
	/**
	 *关闭SFTP
	 *@param  sftp
	 *@return void
	 */
	public static void closeSftp(ChannelSftp sftp){
		close(sftp);
	}
	
}
