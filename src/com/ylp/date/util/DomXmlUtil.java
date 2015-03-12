package com.ylp.date.util;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * xml工具类
 * 
 * @author Qiaolin Pan
 * 
 */
public class DomXmlUtil {
	/**
	 * 私有构造器
	 */
	private DomXmlUtil() {

	}

	/**
	 * 读取输入流，构建xml内容
	 * 
	 * @param stm
	 * @return dom4j Document 对象
	 * @throws DocumentException
	 */
	public static final Document getDocument(InputStream stm)
			throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(stm);
		return document;
	}
}
