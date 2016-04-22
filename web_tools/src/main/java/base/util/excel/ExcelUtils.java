package base.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {
	private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	
	/**
	 * @MethodName : listToExcel	
	 * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，可自定义工作表大小）
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系 如果需要的是引用对象的属性，则英文属性使用类似于EL表达式的格式
	 *            如：list中存放的都是student对象，student中有collegeName属性，而我们需要学院名称，则可以这样写
	 *            fieldMap.put("collegeName","学院名称")
	 * @param sheetName
	 *            工作表的名称
	 * @param sheetSize
	 *            每个工作表中记录的最大个数
	 * @param out
	 *            导出流
	 * @throws ExcelException
	 */
	public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName, int sheetSize, OutputStream out,Map<String,Map<Object,Object>> replaceValues,HttpServletRequest request,String progressKey) throws ExcelException
	{

		if (sheetSize > 65535 || sheetSize < 1)
		{
			sheetSize = 65535;
		}

		// 创建工作簿并发送到OutputStream指定的地方
		WritableWorkbook wwb = null;
		try
		{
			wwb = Workbook.createWorkbook(out);
			
			if (list.size() == 0 || list == null) { 
				//throw new ExcelException("数据源中没有任何数据"); 
				WritableSheet sheet = wwb.createSheet(sheetName, 0);
				String[] cnFields = new String[fieldMap.size()];
				// 填充数组
				int count = 0;
				for (Entry<String, String> entry : fieldMap.entrySet())
				{
					cnFields[count] = entry.getValue();
					count++;
				}
				// 填充表头
				for (int i = 0; i < cnFields.length; i++)
				{
					Label label = new Label(i, 0, cnFields[i]);
					sheet.addCell(label);
				}
				
				// 设置自动列宽
				setColumnAutoSize(sheet, 5);
				request.getSession().setAttribute(progressKey, 100);
			}
			else
			{
				// 因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
				// 所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
				// 1.计算一共有多少个工作表
				double sheetNum = Math.ceil(list.size() / new Integer(sheetSize).doubleValue());

				// 2.创建相应的工作表，并向其中填充数据
				for (int i = 0; i < sheetNum; i++)
				{
					// 如果只有一个工作表的情况
					if (1 == sheetNum)
					{
						WritableSheet sheet = wwb.createSheet(sheetName, i);
						fillSheet(sheet, list, fieldMap, 0, list.size() - 1,replaceValues, request, progressKey, list.size());

						// 有多个工作表的情况
					}
					else
					{
						WritableSheet sheet = wwb.createSheet(sheetName + (i + 1), i);

						// 获取开始索引和结束索引
						int firstIndex = i * sheetSize;
						int lastIndex = (i + 1) * sheetSize - 1 > list.size() - 1 ? list.size() - 1 : (i + 1) * sheetSize - 1;
						// 填充工作表
						fillSheet(sheet, list, fieldMap, firstIndex, lastIndex,replaceValues, request, progressKey, list.size());
					}
				}
			}

			wwb.write();
			
			if(StringUtils.isNotBlank(progressKey)){
				request.getSession().setAttribute(progressKey,100);
			}
		} 
		catch (Exception e)
		{
			if(StringUtils.isNotBlank(progressKey)){
				request.getSession().setAttribute(progressKey,101);
			}
			
			e.printStackTrace();
			// 如果是ExcelException，则直接抛出
			if (e instanceof ExcelException)
			{
				if(StringUtils.isNotBlank(progressKey)){
					request.getSession().setAttribute(progressKey, "101");
				}
				throw (ExcelException) e;
				// 否则将其它异常包装成ExcelException再抛出
			}
			else
			{
				if(StringUtils.isNotBlank(progressKey)){
					request.getSession().setAttribute(progressKey, "101");
				}
				throw new ExcelException("导出Excel失败");
			}
		}finally{
			try
            {
	            wwb.close();
            } 
			catch (Exception e)
            {
				if(StringUtils.isNotBlank(progressKey)){
					request.getSession().setAttribute(progressKey, "101");
				}
	            e.printStackTrace();
            }
		}

	}

	/**
	 * @MethodName : listToExcel
	 * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，工作表大小为2003支持的最大值）
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param out
	 *            导出流
	 * @throws ExcelException
	 */
	public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName, OutputStream out,Map<String,Map<Object,Object>> replaceValues,HttpServletRequest request, String progressKey) throws ExcelException
	{
		listToExcel(list, fieldMap, sheetName, 65535, out, replaceValues, request, progressKey);

	}

	/**
	 * @MethodName : listToExcel
	 * @Description : 导出Excel（导出到浏览器，可以自定义工作表的大小）
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param sheetSize
	 *            每个工作表中记录的最大个数
	 * @param response
	 *            使用response可以导出到浏览器
	 * @throws ExcelException
	 */
	public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName, int sheetSize, HttpServletResponse response, Map<String,Map<Object,Object>>replaceValues,HttpServletRequest request,String progressKey) throws ExcelException
	{

		OutputStream out = null;
		// 创建工作簿并发送到浏览器
		try
		{
			// 设置默认文件名为当前时间：年月日时分秒
			String fileName = StringUtils.isNotBlank(sheetName)?sheetName : new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
			// 设置response头信息
			response.reset();
			response.setContentType("application/msexcel"); // 改成输出excel文件
			response.setHeader("Content-disposition", "attachment; filename=" + new String(URLEncoder.encode(fileName, "UTF-8")) +".xls");
			out = response.getOutputStream();
			listToExcel(list, fieldMap, sheetName, sheetSize, out, replaceValues, request, progressKey);

		} catch (Exception e)
		{
			if(StringUtils.isNotBlank(progressKey)){
				request.getSession().setAttribute(progressKey,101);
			}
			
			e.printStackTrace();

			// 如果是ExcelException，则直接抛出
			if (e instanceof ExcelException)
			{
				if(StringUtils.isNotBlank(progressKey)){
					request.getSession().setAttribute(progressKey, "101");
				}
				throw (ExcelException) e;

				// 否则将其它异常包装成ExcelException再抛出
			}
			else
			{
				if(StringUtils.isNotBlank(progressKey)){
					request.getSession().setAttribute(progressKey, "101");
				}
				throw new ExcelException("导出Excel失败");
			}
		}finally{
			if(null!=out){
				try
                {
	                out.close();
                } catch (IOException e)
                {
	                e.printStackTrace();
                }
			}
		}
	}

	/**
	 * @MethodName : listToExcel
	 * @Description : 导出Excel（导出到浏览器，工作表的大小是2003支持的最大值）
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            类的英文属性和Excel中的中文列名的对应关系
	 * @param response
	 *            使用response可以导出到浏览器
	 * @throws ExcelException
	 */
	public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldMap, String sheetName, HttpServletResponse response, Map<String,Map<Object,Object>>replaceValues, HttpServletRequest request, String progressKey) throws ExcelException
	{

		listToExcel(list, fieldMap, sheetName, 65535, response, replaceValues, request, progressKey);
	}

	/**
	 * @MethodName : excelToList
	 * @Description : 将Excel转化为List
	 * @param in
	 *            ：承载着Excel的输入流
	 * @param sheetIndex
	 *            ：要导入的工作表序号
	 * @param entityClass
	 *            ：List中对象的类型（Excel中的每一行都要转化为该类型的对象）
	 * @param fieldMap
	 *            ：Excel中的中文列头和类的英文属性的对应关系Map
	 *            如：表中是学院名称, list中存放的都是student对象，student中有collegeName属性，而我们需要collegeName，则可以这样写
	 *            fieldMap.put("学院名称","collegeName")
	 * @param uniqueFields
	 *            ：指定业务主键组合（即复合主键），这些列的组合不能重复
	 * @return ：List
	 * @throws ExcelException
	 */
	public static <T> List<T> excelToList(File file, Class<T> entityClass, LinkedHashMap<String, String> fieldMap, String[] uniqueFields) throws ExcelException
	{

		// 定义要返回的list
		List<T> resultList = new ArrayList<T>();

		try
		{
			InputStream  is = new FileInputStream(file);
			// 根据Excel数据源创建WorkBook
			Workbook wb = Workbook.getWorkbook(is);
			// 获取工作表
			Sheet sheet = wb.getSheet(0);
			
			if (null == sheet) { throw new ExcelException("Excel文件中没有任何数据"); }
			
			// 获取工作表的有效行数
			int realRows = 0;
			for (int i = 0; i < sheet.getRows(); i++)
			{

				int nullCols = 0;
				for (int j = 0; j < sheet.getColumns(); j++)
				{
					Cell currentCell = sheet.getCell(j, i);
					if (currentCell == null || "".equals(currentCell.getContents().toString()))
					{
						nullCols++;
					}
				}

				if (nullCols == sheet.getColumns())
				{
					break;
				}
				else
				{
					realRows++;
				}
			}

			// 如果Excel中没有数据则提示错误
			if (realRows <= 1) { throw new ExcelException("Excel文件中没有任何数据"); }

			Cell[] firstRow = sheet.getRow(0);

			String[] excelFieldNames = new String[firstRow.length];

			// 获取Excel中的列名
			for (int i = 0; i < firstRow.length; i++)
			{
				excelFieldNames[i] = firstRow[i].getContents().toString().trim();
			}

			// 判断需要的字段在Excel中是否都存在
			boolean isExist = true;
			List<String> excelFieldList = Arrays.asList(excelFieldNames);
			for (String cnName : fieldMap.keySet())
			{
				if (!excelFieldList.contains(cnName))
				{
					isExist = false;
					break;
				}
			}

			// 如果有列名不存在，则抛出异常，提示错误
			if (!isExist) { throw new ExcelException("Excel中缺少必要的字段，或字段名称有误"); }

			// 将列名和列号放入Map中,这样通过列名就可以拿到列号
			LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
			if(null!=excelFieldNames){
				for (int i = 0; i < excelFieldNames.length; i++)
				{
					colMap.put(excelFieldNames[i], firstRow[i].getColumn());
				}
			}

			// 判断是否有重复行
			// 1.获取uniqueFields指定的列
			if(null!=uniqueFields){
				Cell[][] uniqueCells = new Cell[uniqueFields.length][];
				for (int i = 0; i < uniqueFields.length; i++)
				{
					int col = colMap.get(uniqueFields[i]);
					uniqueCells[i] = sheet.getColumn(col);
				}
				
				// 2.从指定列中寻找重复行
				for (int i = 1; i < realRows; i++)
				{
					int nullCols = 0;
					for (int j = 0; j < uniqueFields.length; j++)
					{
						String currentContent = uniqueCells[j][i].getContents();
						Cell sameCell = sheet.findCell(currentContent, uniqueCells[j][i].getColumn(), uniqueCells[j][i].getRow() + 1, uniqueCells[j][i].getColumn(), uniqueCells[j][realRows - 1].getRow(), true);
						if (sameCell != null)
						{
							nullCols++;
						}
					}
					
					if (nullCols == uniqueFields.length) { throw new ExcelException("Excel中有重复行，请检查"); }
				}
			}

			// 将sheet转换为list
			for (int i = 1; i < realRows; i++)
			{
				// 新建要转换的对象
				T entity = entityClass.newInstance();

				// 给对象中的字段赋值
				for (Entry<String, String> entry : fieldMap.entrySet())
				{
					// 获取中文字段名
					String cnNormalName = entry.getKey();
					// 获取英文字段名
					String enNormalName = entry.getValue();
					// 根据中文字段名获取列号
					int col = colMap.get(cnNormalName);
					Cell cell = sheet.getCell(col, i);
					// 给对象赋值
					setFieldValueByName(enNormalName, cell, entity);
				}

				resultList.add(entity);
			}
		}catch(BiffException be){
			throw new ExcelException("文件格式有误");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// 如果是ExcelException，则直接抛出
			if (e instanceof ExcelException)
			{
				throw (ExcelException) e;

				// 否则将其它异常包装成ExcelException再抛出
			}
			else
			{
				e.printStackTrace();
				throw new ExcelException("导入Excel失败");
			}
		}
		return resultList;
	}

	/*
	 * <-------------------------辅助的私有方法------------------------------------------
	 * ----->
	 */
	/**
	 * @MethodName : getFieldValueByName
	 * @Description : 根据字段名获取字段值
	 * @param fieldName
	 *            字段名
	 * @param o
	 *            对象
	 * @return 字段值
	 */
	private static Object getFieldValueByName(String fieldName, Object o) throws Exception
	{

		Object value = null;
		Field field = getFieldByName(fieldName, o.getClass());

		if (field != null)
		{
			field.setAccessible(true);
			value = field.get(o);
		}
		else
		{
			throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
		}

		return value;
	}

	/**
	 * @MethodName : getFieldByName
	 * @Description : 根据字段名获取字段
	 * @param fieldName
	 *            字段名
	 * @param clazz
	 *            包含该字段的类
	 * @return 字段
	 */
	private static Field getFieldByName(String fieldName, Class<?> clazz)
	{
		// 拿到本类的所有字段
		Field[] selfFields = clazz.getDeclaredFields();

		// 如果本类中存在该字段，则返回
		for (Field field : selfFields)
		{
			if (field.getName().equals(fieldName)) { return field; }
		}

		// 否则，查看父类中是否存在此字段，如果有则返回
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null && superClazz != Object.class) { return getFieldByName(fieldName, superClazz); }

		// 如果本类和父类都没有，则返回空
		return null;
	}

	/**
	 * @MethodName : getFieldValueByNameSequence
	 * @Description : 根据带路径或不带路径的属性名获取属性值
	 *              即接受简单属性名，如userName等，又接受带路径的属性名，如student.department.name等
	 * 
	 * @param fieldNameSequence
	 *            带路径的属性名或简单属性名
	 * @param o
	 *            对象
	 * @return 属性值
	 * @throws Exception
	 */
	private static Object getFieldValueByNameSequence(String fieldNameSequence, Object o ,Map<Object,Object> replace) throws Exception
	{

		Object value = null;

		// 将fieldNameSequence进行拆分
		String[] attributes = fieldNameSequence.split("\\.");
		if (attributes.length == 1)
		{
			value = getFieldValueByName(fieldNameSequence, o);
		}
		else
		{
			// 根据属性名获取属性对象
			Object fieldObj = getFieldValueByName(attributes[0], o);
			String subFieldNameSequence = fieldNameSequence.substring(fieldNameSequence.indexOf(".") + 1);
			value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj,replace);
		}
		if(value !=null && replace!=null&&replace.size()>0){
//			return replace.get(value);
			Object key= null;
			try{
				Set<Entry<Object,Object>> entrys = replace.entrySet();
				Entry<Object,Object> entry = entrys.iterator().next();
				key = entry.getKey();
				if(key.getClass() == value.getClass()){
					return replace.get(value);
				}
				if(key.getClass() == Integer.class){
					return replace.get(Integer.parseInt(value.toString()));
				}
				if(key.getClass() == Long.class){
					return replace.get(Long.parseLong(value.toString()));
				}
				if(key.getClass() == String.class){
					return replace.get(value.toString());
				}
				if(key.getClass() == Byte.class){
					return replace.get(Byte.parseByte(value.toString()));
				}
			}catch(Exception e){
				log.error("key="+key+"  value="+value);
				try{
					log.error("key class:"+key.getClass().toString());
				}catch (Exception ee){
					log.error("cannot get key CLass");
				}
				try{
					log.error("value class:"+value.getClass().toString());
				}catch (Exception ee){
					log.error("cannot get value CLass");
				}
				e.printStackTrace();
			}
		}
		return value;

	}

	/**
	 * @MethodName : setFieldValueByName
	 * @Description : 根据字段名给对象的字段赋值
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 * @param o
	 *            对象
	 */
	private static void setFieldValueByName(String fieldName, Cell cell, Object o) throws Exception
	{

		Field field = getFieldByName(fieldName, o.getClass());
		if (field != null)
		{
			field.setAccessible(true);
			// 获取字段类型
			Class<?> fieldType = field.getType();

			// 根据字段类型给字段赋值
			if(null == cell || cell.getContents().toString() == null || "".equals(cell.getContents().toString())){
				field.set(o, null);
			}
			else if (String.class == fieldType)
			{
				field.set(o, cell.getContents().trim());
			}/*else if(StringUtils.isBlank(fieldValue.toString())){
				field.set(o, null);
			}*/
			else
				if ((Integer.TYPE == fieldType) || (Integer.class == fieldType))
				{
					field.set(o, Integer.parseInt(cell.getContents().toString().trim()));
				}
				else
					if ((Long.TYPE == fieldType) || (Long.class == fieldType))
					{
						field.set(o, Long.valueOf(cell.getContents().toString().trim()));
					}
					else
						if ((Float.TYPE == fieldType) || (Float.class == fieldType))
						{
							field.set(o, Float.valueOf(cell.getContents().toString().trim()));
						}
						else
							if ((Short.TYPE == fieldType) || (Short.class == fieldType))
							{
								field.set(o, Short.valueOf(cell.getContents().toString().trim()));
							}
							else
								if ((Double.TYPE == fieldType) || (Double.class == fieldType))
								{
									field.set(o, Double.valueOf(cell.getContents().toString().trim()));
								}
								else
									if (Character.TYPE == fieldType)
									{
										if ((cell.getContents().toString().trim() != null) && (cell.getContents().toString().trim().length() > 0))
										{
											field.set(o, Character.valueOf(cell.getContents().toString().trim().charAt(0)));
										}
									}
									else
										if (Date.class == fieldType)
										{
//											TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
											
											try{//excel表格是时间格式
												DateCell dc = (DateCell) cell;
												field.set(o, dc.getDate());
											}catch(ClassCastException e){//excel中是文本格式
												String dataValue = cell.getContents().toString().trim();
												try{
											        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											        field.set(o, f.parse(dataValue));
												}catch(java.text.ParseException t){//excel中是文本格式
													SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
													//field.set(o, new Date2Timestamp().mapFrom(f.parse(dataValue)));
												}
											} catch (Exception e) {
												throw new ExcelException("时间格式错误:"+cell.getContents().toString().trim());
											}
										}else if (Timestamp.class == fieldType)
										{
//											TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
											try{//excel表格是时间格式
												DateCell dc = (DateCell) cell;
												//field.set(o, new Date2Timestamp().mapFrom(dc.getDate()));
											}catch(ClassCastException e){//excel中是文本格式
												String dataValue = cell.getContents().toString().trim();
												try{
													SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
													//field.set(o, new Date2Timestamp().mapFrom(f.parse(dataValue)));
												}catch(Exception t){//excel中是文本格式
													SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
													//field.set(o, new Date2Timestamp().mapFrom(f.parse(dataValue)));
												}
											}catch (Exception e) {
												throw new ExcelException("时间格式错误:"+cell.getContents().toString().trim());
											}
										}
										else
										{
											field.set(o, cell.getContents().toString().trim());
										}
		}
		else
		{
			throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
		}
	}

	/**
	 * @MethodName : setColumnAutoSize
	 * @Description : 设置工作表自动列宽和首行加粗
	 * @param ws
	 */
	private static void setColumnAutoSize(WritableSheet ws, int extraWith)
	{
		// 获取本列的最宽单元格的宽度
		for (int i = 0; i < ws.getColumns(); i++)
		{
			int colWith = 0;
			for (int j = 0; j < ws.getRows(); j++)
			{
				String content = ws.getCell(i, j).getContents().toString();
				int cellWith = content.length();
				if (colWith < cellWith)
				{
					colWith = cellWith;
				}
			}
			// 设置单元格的宽度为最宽宽度+额外宽度
			ws.setColumnView(i, colWith + extraWith);
		}

	}

	/**
	 * @MethodName : fillSheet
	 * @Description : 向工作表中填充数据
	 * @param sheet
	 *            工作表
	 * @param list
	 *            数据源
	 * @param fieldMap
	 *            中英文字段对应关系的Map
	 * @param firstIndex
	 *            开始索引
	 * @param lastIndex
	 *            结束索引
	 */
	private static <T> void fillSheet(WritableSheet sheet, List<T> list, LinkedHashMap<String, String> fieldMap, int firstIndex, int lastIndex, Map<String,Map<Object,Object>> replaceValues, HttpServletRequest request, String progressKey, int totalSize) throws Exception
	{

		try
        {
			// 定义存放英文字段名和中文字段名的数组
			String[] enFields = new String[fieldMap.size()];
			String[] cnFields = new String[fieldMap.size()];
			
			// 填充数组
			int count = 0;
			for (Entry<String, String> entry : fieldMap.entrySet())
			{
				enFields[count] = entry.getKey();
				cnFields[count] = entry.getValue();
				count++;
			}
			// 填充表头
			for (int i = 0; i < cnFields.length; i++)
			{
				Label label = new Label(i, 0, cnFields[i]);
				sheet.addCell(label);
			}
			
			// 填充内容
			int rowNo = 1;
			for (int index = firstIndex; index <= lastIndex; index++)
			{
				// 获取单个对象
				T item = list.get(index);
				for (int i = 0; i < enFields.length; i++)
				{	
					Map<Object,Object> replace = null;
					if(replaceValues != null && replaceValues.size()>0) {
						replace = replaceValues.get(enFields[i]);
					}
					Object objValue = getFieldValueByNameSequence(enFields[i], item, replace);
					
					Class<?> fieldType = objValue == null ? null : objValue.getClass();
					if(replaceValues != null && !replaceValues.containsKey(enFields[i])){
						if((Integer.TYPE == fieldType) || (Integer.class == fieldType) || (Long.TYPE == fieldType) || (Long.class == fieldType))
						{
							//整数
							Long fieldValue = objValue == null ? 0 : Long.valueOf(objValue.toString());
							Number labelNF = new Number(i, rowNo, fieldValue);
							sheet.addCell(labelNF);
						}
						else if((Float.TYPE == fieldType) || (Float.class == fieldType))
						{
							//小数
							Double fieldValue = objValue == null ? 0 : Double.valueOf(objValue.toString());
							Number labelNF = new Number(i, rowNo, fieldValue); 
							sheet.addCell(labelNF);
						}
						else
						{
							String fieldValue = objValue == null ? "" : objValue.toString();
							Label label = new Label(i, rowNo, fieldValue);
							sheet.addCell(label);
						}
					}
					else{
						String fieldValue = objValue == null ? "" : objValue.toString();
						Label label = new Label(i, rowNo, fieldValue);
						sheet.addCell(label);
					}
				}
				
				rowNo++;
				if(StringUtils.isNotBlank(progressKey)){
					double progress = Math.ceil(((double)(firstIndex+index)/(double)totalSize)*10);
					request.getSession().setAttribute(progressKey, progress);
				}
			}
			
			// 设置自动列宽
			setColumnAutoSize(sheet, 5);
	        
        } 
		catch (Exception e)
        {
			e.printStackTrace();
			if(StringUtils.isNotBlank(progressKey)){
				request.getSession().setAttribute(progressKey,101);
			}
        }
	}



	public static void listToExcel(HttpServletResponse response,String sheetName,List<ExcelParam> excelParams,HttpServletRequest request, String progressKey) throws ExcelException {
		if (excelParams != null && excelParams.size() > 0) {
			// 创建工作簿并发送到浏览器
			OutputStream out = null;
			try
			{
				// 设置默认文件名为当前时间：年月日时分秒
//				String fileName = StringUtils.isNotBlank(sheetName)?sheetName : new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();
				// 设置response头信息
				response.reset();
				response.setContentType("application/msexcel"); // 改成输出excel文件
				response.setHeader("Content-disposition", "attachment; filename=" + new String(sheetName.getBytes("UTF-8"),"iso8859-1") +".xls");
				out = response.getOutputStream();
				listToExcel(excelParams, out, request, progressKey);

			} catch (Exception e)
			{
				if(StringUtils.isNotBlank(progressKey)){
					request.getSession().setAttribute(progressKey, "101");
				}
				e.printStackTrace();

				// 如果是ExcelException，则直接抛出
				if (e instanceof ExcelException)
				{
					if(StringUtils.isNotBlank(progressKey)){
						request.getSession().setAttribute(progressKey, "101");
					}
					throw (ExcelException) e;
					// 否则将其它异常包装成ExcelException再抛出
				}
				else
				{
					if(StringUtils.isNotBlank(progressKey)){
						request.getSession().setAttribute(progressKey, "101");
					}
					throw new ExcelException("导出Excel失败");
				}
			}finally{
				if(null!=out){
					try
                    {
	                    out.close();
                    } catch (IOException e)
                    {
	                    e.printStackTrace();
                    }
				}
			}

		}
	}

	private static void listToExcel(List<ExcelParam> excelParams, OutputStream out, HttpServletRequest request, String progressKey) throws ExcelException {
		// 创建工作簿并发送到OutputStream指定的地方
		WritableWorkbook wwb = null;
		try
		{
			wwb = Workbook.createWorkbook(out);

			// 因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条
			// 所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程
			// 1.计算一共有多少个工作表

			int sheetNum = excelParams.size();

			int totalSize=0;
			for (int i = 0; i < sheetNum; i++){
				if(null!=excelParams.get(i)&&null!=excelParams.get(i).getList()){
					totalSize += excelParams.get(i).getList().size();
				}
			}
			
			int	sheetSize = 65535;
			
			int tempSize = 0;
			// 2.创建相应的工作表，并向其中填充数据
			for (int i = 0; i < sheetNum; i++)
			{
				ExcelParam excelParam = excelParams.get(i);
				if(null!=excelParam){
					
					List<?> list = excelParam.getList();
					if(null!=list){
						double sheetChilds = Math.ceil(list.size() / new Integer(sheetSize).doubleValue());
						if (1 == sheetChilds){
							tempSize += list.size();
							WritableSheet sheet = wwb.createSheet(excelParam.getName(), i);
							fillSheet(sheet, excelParams.get(i), request, progressKey, 0,0, tempSize, totalSize);
						}else{
							for (int j = 0; j < sheetChilds; j++)
                            {
								WritableSheet sheet = wwb.createSheet(excelParam.getName()+(j+1), (i+j));
								int firstIndex = j * sheetSize;
								int lastIndex = (j + 1) * sheetSize - 1 > list.size() - 1 ? list.size() - 1 : (j + 1) * sheetSize - 1;
								tempSize += firstIndex;
								ExcelParam ep = new ExcelParam();
								ep.setList(list.subList(firstIndex, lastIndex));
								ep.setMap(excelParam.getMap());
								ep.setName(excelParam.getName());
								fillSheet(sheet, ep, request, progressKey, 0,0, tempSize, totalSize); 
                            }
							
						}
					}
				}
				
			}

			wwb.write();
			
			if(StringUtils.isNotBlank(progressKey)){
				request.getSession().setAttribute(progressKey,100);
			}
		} 
		catch (Exception e)
		{
			if(StringUtils.isNotBlank(progressKey)){
				request.getSession().setAttribute(progressKey,101);
			}
			e.printStackTrace();
			// 如果是ExcelException，则直接抛出
			if (e instanceof ExcelException)
			{
				if(StringUtils.isNotBlank(progressKey)){
					request.getSession().setAttribute(progressKey, "101");
				}
				throw (ExcelException) e;
				// 否则将其它异常包装成ExcelException再抛出
			}
			else
			{
				if(StringUtils.isNotBlank(progressKey)){
					request.getSession().setAttribute(progressKey, "101");
				}
				throw new ExcelException("导出Excel失败");
			}
		}finally{
			try
            {
	            wwb.close();
            } 
			catch (Exception e)
            {
				if(StringUtils.isNotBlank(progressKey)){
					request.getSession().setAttribute(progressKey, "101");
				}
	            e.printStackTrace();
            }
		}
	}

	private static void fillSheet(WritableSheet sheet, ExcelParam ep, HttpServletRequest request, String progressKey, int firstIndex, int lastIndex, int tempSize, int totalSize) throws Exception {
		try{
			// 定义存放英文字段名和中文字段名的数组
			String[] enFields = new String[ep.getMap().size()];
			String[] cnFields = new String[ep.getMap().size()];
			// 填充数组
			int count = 0;
			for (Entry<String, String> entry : ep.getMap().entrySet())
			{
				enFields[count] = entry.getKey();
				cnFields[count] = entry.getValue();
				count++;
			}
			// 填充表头
			for (int i = 0; i < cnFields.length; i++)
			{
				Label label = new Label(i, 0, cnFields[i]);
				sheet.addCell(label);
			}
			
			// 填充内容
			int rowNo = 1;
			
			List<?> item = ep.getList();
			Map<String,Map<Object,Object>> replaceValues = ep.getReplaceValues();
			Map<Object,Object> replace = null;
			Object objValue = null;
			if (item!=null && item.size()>0){
				for (int i = 0; i < item.size(); i++)
				{
					Object o = item.get(i);
					boolean flag = false;
					boolean hideFlag = false;
					for (int j = 0; j < enFields.length; j++)
					{
						String enf = enFields[j];
						Object originalValue = getFieldValueByNameSequence(enf, o ,null);
						if(replaceValues != null && replaceValues.size()>0) {
							replace = replaceValues.get(enf);
							if(null!=replace){
								objValue = getFieldValueByNameSequence(enf, o, replace);
							}else{
								objValue = originalValue;
							}
						}else{
							objValue = originalValue;
						}
						String fieldValue = objValue == null ? "" : objValue.toString();
						String originalField = originalValue == null ? "" : originalValue.toString();
						if ("type".equals(enf) && StringUtils.isNotEmpty(originalField)){
							flag=true;
						}
						if (flag){
							if (("consignee").equalsIgnoreCase(enf)){
								fieldValue = StringUtils.replace(fieldValue,"","");
							}else if (("consigneeMobile").equalsIgnoreCase(enf)){
								//fieldValue = com.feiniu.base.util.StringUtils.replace(fieldValue,3,7);
								try {
									fieldValue = fieldValue;//解密
									fieldValue = StringUtils.replace(fieldValue,"3","7");
								} catch (Exception e) {
									fieldValue = StringUtils.replace(fieldValue,"3","7");//兼容历史数据
								}
								
							}
							
						}
						if ("hideType".equalsIgnoreCase(enf) && StringUtils.isNotEmpty(originalField)){
							hideFlag=true;
						}
						if (hideFlag&&("goodsName").equalsIgnoreCase(enf)){
							fieldValue = StringUtils.replace(fieldValue,"1","");
						}
						
						if(!replaceValues.containsKey(enf)){
							Class<?> fieldType = originalValue == null ? null : originalValue.getClass();
							if((Integer.TYPE == fieldType) || (Integer.class == fieldType) || (Long.TYPE == fieldType) || (Long.class == fieldType))
							{
								//整数
								Long fieldValueL = originalValue == null ? 0 : Long.valueOf(originalValue.toString());
								Number labelNF = new Number(j, rowNo, fieldValueL);
								sheet.addCell(labelNF);
							}
							else if((Float.TYPE == fieldType) || (Float.class == fieldType))
							{
								//小数
								Double fieldValueD = originalValue == null ? 0 : Double.valueOf(originalValue.toString());
								Number labelNF = new Number(j, rowNo, fieldValueD); 
								sheet.addCell(labelNF);
							}
							else
							{
								Label label = new Label(j, rowNo, fieldValue);
								sheet.addCell(label);
							}
						}else{
							Label label = new Label(j, rowNo, fieldValue);
							sheet.addCell(label);
						}
					}
					rowNo++;
					if(StringUtils.isNotBlank(progressKey)){
						double progress = Math.ceil(((double)(i+tempSize)/(double)totalSize)*10);
						request.getSession().setAttribute(progressKey, progress);
					}
				}
			}
			rowNo++;
			// 设置自动列宽
			setColumnAutoSize(sheet, 5);
		}catch(Exception e){
			if(StringUtils.isNotBlank(progressKey)){
				request.getSession().setAttribute(progressKey, 101);
			}
		}
	}

}
