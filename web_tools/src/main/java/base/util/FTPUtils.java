package base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.entity.FtpServerConfig;

/**
 * 
 * @author xing.zeng 2016-03-31
 */
public class FTPUtils {
	private static Logger log = LoggerFactory.getLogger(FTPUtils.class);
	private final static String FTP_FILE_SUFFIX = ".TMP";
	private static int reconnectionNum = 3;
	/**
	 * ftp上传文件
	 * 
	 * @param sc
	 * @param remoteDir
	 * @param nameFun
	 * @param uplocadFiles
	 * @return
	 * @throws Exception
	 */
	public static int uploadFiles(FtpServerConfig sc, String remoteDir,
			File... uplocadFiles) throws Exception {
		int result = 0;
		FTPClient ftpClient = null;

		try {
			// 初始化FTPClient
			ftpClient = getFTPClient(sc);

			for (File curr : uplocadFiles) {
				log.info("Start upload file " + curr.getAbsolutePath());
				// 执行上传操作
				boolean tmp = upload(ftpClient, remoteDir, curr);

				if (!tmp) {
					log.error("Upload file failed：" + curr.getAbsolutePath());
				} else {
					result++;
				}
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (ftpClient != null) {
				close(ftpClient);
			}
		}
	}
	
	/**
	 * ftp上传单个文件
	 * @param sc
	 * @param remoteDir
	 * @param uploadFile
	 * @return
	 * @throws Exception
	 */
	public static int uploadFile(FtpServerConfig sc, String remoteDir, File uploadFile) throws Exception {
		int result = 0;
		FTPClient ftpClient = null;
		
		if(StringUtils.isEmpty(remoteDir) || uploadFile == null || !uploadFile.isFile()){
			log.error(">>>>>>>> params error.remoteDir=" + remoteDir + ";uploadFile=" + uploadFile);
			return result;
		}
		
		try {
			// 初始化FTPClient
			ftpClient = getFTPClient(sc);

			log.info("======== Start upload file " + uploadFile.getAbsolutePath());
			// 执行上传操作
			boolean tmp = upload(ftpClient, remoteDir, uploadFile);

			if (!tmp) {
				log.error(">>>>>>>> Upload file failed：" + uploadFile.getAbsolutePath());
			} else {
				result++;
			}
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (ftpClient != null) {
				close(ftpClient);
			}
		}
	}
	
	/**
	 * 批量上传文件
	 * @param sc
	 * @param remoteDir
	 * @param uploadFileList
	 * @return
	 * @throws Exception
	 */
	public static int uploadFileList(FtpServerConfig sc, String remoteDir, List<File> uploadFileList) throws Exception {
		int result = 0;
		FTPClient ftpClient = null;
		
		if(StringUtils.isEmpty(remoteDir) || uploadFileList == null || uploadFileList.size() < 1){
			log.error(">>>>>>>> params error.remoteDir=" + remoteDir + ";uploadFile=" + uploadFileList);
			return result;
		}
		
		try{
			// 初始化FTPClient
			ftpClient = getFTPClient(sc);
			
			for(File uploadFile : uploadFileList){
				if(uploadFile.isFile()){
					log.info("======== Start upload file " + uploadFile.getAbsolutePath());
					// 执行上传操作
					boolean tmp = upload(ftpClient, remoteDir, uploadFile);
					
					if (!tmp) {
						log.error("Upload file failed：" + uploadFile.getAbsolutePath());
					} else {
						result++;
					}
				}else{
					log.error(">>>>>>>> upload file error,wrong file:" + uploadFile.getAbsolutePath());
				}
			}
			
			return result;
		}catch(Exception e){
			throw e;
		}finally {
			if (ftpClient != null) {
				close(ftpClient);
			}
		}
	}

	/**
	 * 下载ftp文件并备份远程文件
	 * @param sc
	 * @param remoteDir
	 * @param localDir
	 * @param remoteBakDir
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,String>> downFilesAndBackup(FtpServerConfig sc, String remoteDir, String localDir, String remoteBakDir, FTPFileFilter filter)throws Exception {
		// ftp服务器连接
		FTPClient ftpClient = getFTPClient(sc);
		if(null == ftpClient){
			for(int i = 0; i < reconnectionNum; i++){
				ftpClient = getFTPClient(sc);
				if(null != ftpClient){
					break;
				}
			}
			if(null == ftpClient){
				return null;
			}
		}
		
		try{
			String[] remoteDirs = remoteDir.split(",");//远程文件路径
			String[] localDirs = localDir.split(",");//共享磁盘路径
			String[] remoteRDirs = remoteBakDir.split(",");//远程备份文件路径
		
			if (remoteDirs.length != localDirs.length && remoteDirs.length != remoteRDirs.length) {
				throw new Exception("remoteDirs&&localDirs&&remoteRDirs params error.");
			}
			
			List<Map<String,String>> fullInfoList = new ArrayList<Map<String,String>>();
			Map<String,String> fileParams = null;
			
			for (int i = 0; i < remoteDirs.length; i++) {
				log.info("======== remote path is :" + remoteDirs[i]);
				String BakDir = remoteRDirs[i] + DateUtils.formatDatetime(new Date()) + "/";
				fileParams = new HashMap<String,String>();
				
				ftpClient.changeWorkingDirectory(remoteDirs[i]);//转移到FTP服务器目录  
				
		        FTPFile[] files = ftpClient.listFiles(remoteDirs[i], filter);
		        
				for(FTPFile ftpFile : files){
					String fileName = ftpFile.getName();
					log.info("======== Start download file :" + ftpFile.getName());
					boolean tmp = downloadFile(ftpClient, fileName, localDir, FTP.BINARY_FILE_TYPE);
					log.info("======== Finish download file result:" + tmp);
					
					if(tmp){// 文件下载成功
						fileParams.put("localDir", localDirs[i]);
						fileParams.put("fileName", fileName);
						
						fullInfoList.add(fileParams);
						// 将文件重命名到备份目录
						ftpRename(ftpClient, remoteDirs[i] + fileName, BakDir + fileName);
						
					}
				}
			}
			
			return fullInfoList;
		}catch(Exception e){
			throw e;
		}finally{
			if (ftpClient != null) {
				close(ftpClient);
			}
		}
	}
	
	/**
	 * 初始化ftp配置
	 * 
	 * @param sc
	 * @return
	 * @throws Exception
	 */
	public static FTPClient getFTPClient(FtpServerConfig sc) throws Exception {
		int reply;
		FTPClient ftpClient = new FTPClient();
		/*FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		ftpClient.configure(conf);*/
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.setConnectTimeout(3000);
		ftpClient.setDataTimeout(120000);
		// 连接
		ftpClient.connect(sc.getServer(), sc.getPort());

		reply = ftpClient.getReplyCode();
		/** Connect failed */
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			throw new Exception("The host " + sc.getServer() + ":"
					+ sc.getPort() + " refused connection!");
		} else {
			log.info("Success connect to " + sc.getServer());
		}
		// 登录
		ftpClient.login(sc.getUsername(), sc.getPassword());

		reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			throw new Exception("Login failed!" + sc);
		}
		
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		// *被动传输模式*
		ftpClient.enterLocalPassiveMode();
		// 切换工作目录
		if (!StringUtils.isEmpty(sc.getLocation())) {
			ftpClient.changeWorkingDirectory(sc.getLocation());
		}

		return ftpClient;
	}

	/**
	 * 关闭FTP连接
	 * 
	 * @param c
	 * @throws IOException
	 */
	private static void close(FTPClient c) throws IOException {
		// 退出登录
		try {
			c.logout();
		} catch (IOException e) {
			log.error("Exception occurs when logout from " + c.getRemoteAddress());
		}
		// 断开连接
		try {
			if (c.isConnected()) {
				c.disconnect();
			}
		} catch (IOException e) {
			log.error("Exception occurs when disconnect from " + c.getRemoteAddress());
		}
	}

	/**
	 * 往远程目录上传文件 add by xzeng 2016-03-31
	 * 
	 * @param ftpClient
	 * @param remoteDir
	 * @param localFile
	 * @return
	 * @throws Exception
	 */
	private static boolean upload(FTPClient ftpClient, String remoteDir, File localFile) throws Exception {
		InputStream is = null;
		boolean fsClosed = false;

		try {
			// 创建目录
			createDirectorys(ftpClient, remoteDir, false);
			is = new FileInputStream(localFile);
			String remoteFileName = localFile.getName();
			String remotePath = remoteDir + "/" + remoteFileName;
			String remoteTMPPath = remotePath + FTP_FILE_SUFFIX;
			// 写入临时文件
			boolean result = ftpClient.storeFile(remoteTMPPath, is);
			log.info("======== ftp store file result:{} ========",result);
			
			if (is != null) {
				fsClosed = true;
				is.close();
			}
			if(result){
				// 文件重命名
				boolean renameResult = rename(ftpClient, remoteTMPPath, remotePath);
				if (!renameResult) {
					log.error("Rename file name from " + remoteTMPPath + " to " + remotePath + " failed in the " + ftpClient);
				} else {
					String absolutePath = isRelativePath(remotePath) ? getCurrentWorkingDirectory(ftpClient) + remotePath : remotePath;
					log.info("Success upload local  file " + localFile.getAbsolutePath() + " to remote " + ftpClient.getRemoteAddress() + " " + absolutePath);
				}
			}else{
				log.error(">>>>>> ftp store file fail. <<<<<<<<");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (!fsClosed && is != null) {
				is.close();
			}
		}
		return true;
	}

	/**
	 * 创建远程目录 add by xzeng 2016-03-31
	 * 
	 * @param ftpClient
	 * @param path
	 * @param iterate
	 * @return
	 * @throws IOException
	 */
	public static boolean createDirectorys(FTPClient ftpClient, String path, boolean iterate) throws IOException {
		if (path == null || path.trim().length() == 0
				|| path.trim().equals("."))
			return false;
		/** If not iterate just return the result mkdir */
		if (!iterate) {
			return ftpClient.makeDirectory(path);
		}

		/** iterate create the directory */
		boolean relativePath = isRelativePath(path);
		String prefixPath = relativePath ? "" : "/";
		path = relativePath ? path : path.substring(1);
		String[] subPaths = path.split("/");
		boolean r = true;
		for (String p : subPaths) {
			r = ftpClient.makeDirectory(prefixPath + p);
			prefixPath = prefixPath + p + "/";
			log.info("Create directory " + prefixPath + " .Reply string is "
					+ ftpClient.getReplyString());
		}
		return r;
	}
	
	/**
	 * 创建ftp目录
	 * @param ftpClient
	 * @param path
	 * @return
	 */
	public static boolean mkDir(FTPClient ftpClient, String path) {
		if(StringUtils.isEmpty(path)){
			return false;
		}
		boolean relativePath = isRelativePath(path);
		String prefixPath = relativePath ? "" : "/";
		path = relativePath ? path : path.substring(1);
		String[] subPaths = path.split("/");
		boolean r = true;
		try{
			for (String p : subPaths) {
				r = ftpClient.makeDirectory(prefixPath + p);
				prefixPath = prefixPath + p + "/";
				log.info("Create directory " + prefixPath + " .Reply string is " + ftpClient.getReplyString());
			}
			return r;
		}catch(Exception e){
			log.error(">>>>>>> Create directory ftp error :" + e.getMessage());
			return false;
		}
	}

	/**
	 * 该目录是相对路径还是绝对路径
	 * 相对路径true;绝对路径false
	 * @param path
	 * @return
	 */
	public static boolean isRelativePath(String path) {
		if (path.startsWith("/")) {
			return false;
		} else {
			int idx = path.indexOf(':');
			if (idx != -1 && idx >= 1) {
				return false;
			}
			return true;
		}
	}

	/**
	 * 文件重命名
	 * @param ftpClient
	 * @param remotePath
	 * @param destPath
	 * @return
	 * @throws IOException
	 */
	public static boolean rename(FTPClient ftpClient, String remotePath,
			String destPath) throws IOException {
		boolean r = ftpClient.rename(remotePath, destPath);
		//createDirectorys(ftpClient, getParentPath(destPath), true);
		return r;
	}
	
	/**
	 * 文件重命名
	 * @param ftpClient
	 * @param remotePath
	 * @param destPath
	 * @return
	 */
	public static boolean ftpRename(FTPClient ftpClient, String srcName, String destName){
		try{
			createDirectorys(ftpClient, getParentPath(destName), true);
			boolean r = ftpClient.rename(srcName, destName);
			
			if (r) {
				log.info("======== Success rename " + getAbsolutePath(ftpClient, srcName) + " to " + getAbsolutePath(ftpClient, destName));
			} else {
				log.error(">>>>>>>> Failed rename " + getAbsolutePath(ftpClient, srcName) + " to " + getAbsolutePath(ftpClient, destName));
			}
			return r;
		}catch(Exception e){
			log.error(">>>>>>>> ftp rename error:" + e.getMessage());
			return false;
		}
	}

	public static String getParentPath(String path) {
		if (path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		if (isLeafPath(path))
			return ".";
		return StringUtils.substringBeforeLast(path, "/");
	}

	public static boolean isLeafPath(String path) {
		if (path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		return !path.contains("/");
	}

	public static String getAbsolutePath(FTPClient ftpClient, String path)
			throws IOException {
		if (path == null || path.trim().length() == 0)
			path = ".";
		if (isRelativePath(path)) {
			String currWD = getCurrentWorkingDirectory(ftpClient);
			return (currWD.startsWith("/")) ? "/" + path : currWD + "/" + path;
		}
		return path;
	}

	/**
	 * 获取工作目录
	 * @param ftpClient
	 * @return
	 * @throws IOException
	 */
	public static String getCurrentWorkingDirectory(FTPClient ftpClient)
			throws IOException {
		return ftpClient.printWorkingDirectory();
	}

	/**
	 * 判断ftp上的目录是否存在
	 * 
	 * @param ftpClient
	 * @param dirStr
	 * @return
	 * @throws IOException
	 */
	/*private static boolean isDirectoryExists(FTPClient ftpClient, String dirStr)
			throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(dirStr);
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isDirectory()
					&& ftpFile.getName().equalsIgnoreCase(dirStr)) {
				return true;
			}
		}
		return flag;
	}*/
	
	/**
	 * 下载文件
	 * @param ftpClient
	 * @param fileStr
	 * @param localDir
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	private static boolean downloadFile(FTPClient ftpClient, String fileStr, String localDir, int fileType) throws Exception{
		OutputStream os = null;
		File localTMPFile = null;
		File localFile = null;
		boolean fisClosed = false;
		
		try{
			// set file type
			ftpClient.setFileType(fileType);
			localTMPFile = new File(localDir, fileStr + ".TMP");
			localFile = new File(localDir, fileStr);
			
			if (!localTMPFile.getParentFile().exists()) {
				localTMPFile.getParentFile().mkdirs();
			}
			os = new FileOutputStream(localTMPFile);
			
			boolean result = ftpClient.retrieveFile(fileStr, os);
			log.info("======== ftp download file result:{} ========",result);
			
			if (os != null) {
				fisClosed = true;
				os.close();
			}
			if(result){
				/** rename to local file name */
				boolean renameResult = localTMPFile.renameTo(localFile);
			
				if (renameResult) {
					log.info("======== Success rename file :{} to local :{} ", new Object[]{localTMPFile.getAbsolutePath(), localFile.getAbsolutePath()});
					return true;
				} else {
					log.error(">>>>>>> rename the file :{} to:{} failed,pls manual rename!", new Object[]{localTMPFile.getAbsolutePath(), localFile.getAbsolutePath()});
					return false;
				}
			}else{
				log.error(">>>>>>>> ftp download file fail. <<<<<<<<");
				return false;
			}
		}catch(Exception e){
			log.error(">>>>>>>> dowmload file: {} to local:{} ; error:{}", new Object[]{fileStr,localDir,e.getMessage()});
			return false;
		}finally{
			if (!fisClosed && os != null){
				os.close();
			}
			/*if(localTMPFile.exists()){
				localTMPFile.delete();
			}*/
		}
	}
}
