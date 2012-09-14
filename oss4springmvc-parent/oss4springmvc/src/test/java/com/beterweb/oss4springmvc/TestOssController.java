package com.beterweb.oss4springmvc;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

public class TestOssController extends TestBase {

	@Test
	public void testUpload() {
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setCharacterEncoding("UTF-8");
		request.setMethod("POST");
		request.setRequestURI(TestConstant.ALIYUN_OSS_UPLOAD_URI);
		try {
			InputStream input = TestOssController.class.getResourceAsStream("/aliyun-logo.gif");
			request.addFile(new MockMultipartFile(TestConstant.DEFAULT_FILE_PARAMETER_NAME, "aliyun-logo.gif",
					"image/gif", input));
			servlet.service(request, response);
			System.out.println(response.getContentAsString());
			Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("请求失败。");
		}
	}
}
