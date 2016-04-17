package jdk.common;

import java.util.Formatter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestString {
	private final Logger log = LoggerFactory.getLogger(TestString.class);
	private String str = "This is a String, it is used to testing. ";
	private Formatter f = new Formatter(System.out);
	
	@Test
	public void testLength(){
		log.info("======= String size:{}. ========" , str.length());
	}
	
	@Test
	public void testCodePointAt(){
		log.info("======== The code point at 5 is {}. ========", str.codePointAt(5));
	}
	
	@Test
	public void testReplaceAll(){
		String regex = "\\ws";
		String replacement = "XZ";
		log.info("======== String replace by ({}),result: {}",regex,str.replaceAll(regex, replacement));
	}
	
	@Test
	public void testTrim(){
		log.info("======= String size:{}. ========" , str.length());
		log.info("======= String after trim size:{}. ========" , str.trim().length());
	}
	
	@Test
	public void testFormat(){
		log.info("use %s : {}.", String.format("Hi,%s", "王力"));
		log.info("use %c : {}.", String.format("字母a的大写是：%c ", 'A'));
		log.info("use %b : {}.", String.format("3>7的结果是：%b ", 3>7));
		log.info("use %d : {}.", String.format("100的一半是：%d ", 100/2));
		log.info("use %x : {}.", String.format("100的16进制数是：%x", 100));
		log.info("use %o : {}.", String.format("100的8进制数是：%o ", 100));
		log.info("use %f : {}.", String.format("50元的书打8.5折扣是：%f 元", 50*0.85));
		log.info("use %h : {}.", String.format("字母A的散列码是：%h %n", 'A'));
	}
	
	@Test
	public void testFormatter(){
		f.format("I am %s, %d years old.", "zengxing", 25);
	}
}
