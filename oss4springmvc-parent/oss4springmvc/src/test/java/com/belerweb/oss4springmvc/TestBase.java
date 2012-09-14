package com.belerweb.oss4springmvc;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author Jun
 *
 */
public class TestBase {

	protected static DispatcherServlet servlet;

	/** 
	 * 初始化org.springframework.web.servlet.DispatcherServlet
	 */
	@BeforeClass
	public static void setUp() {
		if (servlet == null) {
			MockServletConfig servletConfig = new MockServletConfig();
			servlet = new DispatcherServlet();
			servlet.setContextConfigLocation("classpath*:springmvc.xml");
			try {
				servlet.init(servletConfig);
			} catch (ServletException e) {
				e.printStackTrace();
				Assert.fail("初始化org.springframework.web.servlet.DispatcherServlet失败。");
			}
		}
	}

}
