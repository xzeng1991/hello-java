package base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**  
 * Copyright: Copyright (c) 2014 Asiainfo
 * 
 * @ClassName: IOUtils.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: yuanpa
 * @date: 2014年9月19日 下午2:51:16 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2014年9月19日     yuanpa           v1.0.0               修改原因
 */
public class IOUtils extends org.apache.commons.io.IOUtils {
	/***
	 * 
	 * @Function: IOUtils::copy
	 * @Description: Copy size of bytes from input to output
	 * @param in
	 * @param out
	 * @param size
	 * @throws IOException
	 * @version: v1.0.0
	 * @author: yuanpa
	 * @date: 2014年9月19日 下午3:00:31 
	 *
	 * Modification History:
	 * Date         Author          Version            Description
	 *-------------------------------------------------------------
	 */
	public static void copy(InputStream in,OutputStream out,long size) throws IOException
	{
		copyLarge(in,out,0,size);
	}
	
	
	public static InputStream bufferInputStream(InputStream input)
	{
		if(input instanceof BufferedInputStream)
			return input;
		return new BufferedInputStream(input);
	}
	
	public static OutputStream bufferOutputStream(OutputStream output)
	{
		if(output instanceof BufferedOutputStream)
			return output;
		return new BufferedOutputStream(output);
	}
	
	
	public static String read(InputStream input) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(8096);
		copy(input, baos);
		return new String(baos.toByteArray());
	}
	
}
