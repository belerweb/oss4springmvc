package com.beterweb.oss4springmvc.builder;

/**
 * @author Jun
 *
 * 保持原文件名
 */
public class OriginalFileNameBuilder implements FileNameBuilder {

	/* (non-Javadoc)
	 * @see com.beterweb.oss4springmvc.builder.FileNameBuilder#build(java.lang.String)
	 */
	@Override
	public String build(String fileName) {
		return fileName;
	}

}
