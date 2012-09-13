package com.beterweb.oss4springmvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

public class TestOssController extends TestBase {

	@Test
	public void testUpload1() {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", TestConstant.ALIYUN_OSS_UPLOAD_URI);
		MockHttpServletResponse response = new MockHttpServletResponse();
		try {
			servlet.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("请求失败。");
		}

		Assert.assertEquals(response.getStatus(), HttpStatus.METHOD_NOT_ALLOWED.value());
	}

	@Test
	public void testUpload2() {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", TestConstant.ALIYUN_OSS_UPLOAD_URI);
		MockHttpServletResponse response = new MockHttpServletResponse();
		try {
			servlet.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("请求失败。");
		}

		Assert.assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void testUpload3() {
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
		request.setRequestURI(TestConstant.ALIYUN_OSS_UPLOAD_URI);
		request.addParameter(TestConstant.OSS_UPLOAD_TOKEN_PARAMETER, UUID.randomUUID().toString());
		try {
			InputStream input = TestOssController.class.getResourceAsStream("/aliyun-logo.gif");
			request.addFile(new MockMultipartFile(TestConstant.DEFAULT_FILE_PARAMETER_NAME, "aliyun-logo.gif",
					"image/gif", input));
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
		MockHttpServletResponse response = new MockHttpServletResponse();
		try {
			servlet.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("请求失败。");
		}

		Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
	}
}
