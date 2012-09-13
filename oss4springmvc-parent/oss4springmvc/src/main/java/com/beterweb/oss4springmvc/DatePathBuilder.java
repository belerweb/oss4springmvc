package com.beterweb.oss4springmvc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jun
 *
 * 按上传日期(例如:2012-09-06)归档/储存文件
 */
public class DatePathBuilder implements PathBuilder {

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

	private SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_PATTERN);

	/* (non-Javadoc)
	 * @see com.beterweb.oss4springmvc.PathBuilder#buildPath()
	 */
	@Override
	public String buildPath(String fileName) {
		return dateFormat.format(new Date()) + Constant.SLASH + fileName;
	}

	public void setPattern(String pattern) {
		if (pattern != null) {
			this.dateFormat = new SimpleDateFormat(pattern);
		}
	}

}
