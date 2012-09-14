package com.beterweb.oss4springmvc.builder;

import java.util.UUID;

import com.beterweb.oss4springmvc.Constant;

/**
 * @author Jun
 *
 * 保持原文件名
 */
public class UuidFileNameBuilder implements FileNameBuilder {

	/* (non-Javadoc)
	 * @see com.beterweb.oss4springmvc.builder.FileNameBuilder#build(java.lang.String)
	 */
	@Override
	public String build(String fileName) {
		String suffix = Constant.EMPTY;
		if (fileName.contains(Constant.DOT)) {
			suffix = fileName.substring(fileName.lastIndexOf(Constant.DOT));
		}
		return UUID.randomUUID().toString() + suffix;
	}

}
