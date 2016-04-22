package base.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 常用文件操作
 * @author xing.zeng
 * create time :2016年4月22日
 */
public class FileUtils extends org.apache.commons.io.FileUtils{
	private static Logger log = LoggerFactory.getLogger(FileUtils.class);
	// 文件缓存2M
	private static int FILE_BUFFER_SIZE = 1024 * 1024 * 2;
	
	/**
	 * 创建文件
	 * add by xzeng 2016年4月22日
	 * @param path
	 * @return
	 */
	public static File createFile(String path) {
		File f = new File(path);
		// 创建父目录
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		return f;
	}
	
	public static File createFile(String filePath, boolean create) throws IOException {
		return createFile(new File(filePath), create);
	}
	
	public static File createFile(File file, boolean create) throws IOException {
		if (create && !file.exists()) {
			makeDirectory(file.getParentFile());
			file.createNewFile();
		}
		return file;
	}
	
	public static File makeDirectory(String dir) throws IOException {
		return makeDirectory(new File(dir));
	}
	
	public static File makeDirectory(File dir) throws IOException {
		forceMkdir(dir);
		return dir;
	}
	
	/**
	 * 列出文件列表
	 * add by xzeng 2016年4月22日
	 * @param srcFile
	 * @param filter
	 * @return
	 */
	public static File[] listFiles(File srcFile, FileFilter filter) {
		if (srcFile == null || !srcFile.exists()){
			return null;
		}
		if (srcFile.isDirectory()){
			return srcFile.listFiles(filter);
		}
		if (filter.accept(srcFile)){
			return new File[] {srcFile};
		}
		return null;
	}
	
	/**
	 * 列出符合正则表达式的文件
	 * add by xzeng 2016年4月22日
	 * @param srcFilePath
	 * @param regex
	 * @return
	 */
	public static File[] listFiles(String srcFilePath, final String regex) {
		return new File(srcFilePath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().matches(regex);
			}
		});
	}
	
	public static void copyClasspathResourceToFS(String resPath, File destFile) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = FileUtils.class.getClassLoader().getResourceAsStream(resPath);
			out = getFileOutputStream(destFile, true);
			IOUtils.copy(in, out);
		} catch (Exception e) {
			throw new RuntimeException("When copy the classpath resource " + resPath + " to file " + destFile
					+ " error!Cause by " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}
	
	public static OutputStream getFileOutputStream(File file, boolean buffer) throws FileNotFoundException, IOException {
		return buffer ? new BufferedOutputStream(new FileOutputStream(createFile(file, true), 8192 < 2))
				: new FileOutputStream(createFile(file, true));

	}
}
