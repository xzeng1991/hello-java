package jdk.common;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * java基本数据
 * @author xzeng
 *
 */
public class BaseElement {
	/* 9 kinds of base type */
	private boolean varBoolean;
	private char varChar;
	private byte varByte;
	private short varShort;
	private int varInt;
	private long varLong;
	private float varFloat;
	private double varDouble;
	// void
	/* end */
	
	/* 9种包装类 */
	private Boolean varBooleanBean;
	private Character varCharBean;
	private Byte varByteBean;
	private Short varShortBean;
	private Integer varIntBean;
	private Long varLongBean;
	private Float varFloatBean;
	private Double varDoubleBean;
	/* end */
	
	private BigInteger varBigInteger;
	private BigDecimal varBigDecimal;
	
	private String str;
	private StringBuilder builder;
	private StringBuffer buffer;
}

