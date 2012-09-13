package com.beterweb.oss4springmvc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author Jun
 *
 * OSS测试控制器
 */
@Controller
public class OssController {

	@Resource(type = OssService.class)
	private OssService ossService;

	@RequestMapping(method = RequestMethod.POST, value = TestConstant.ALIYUN_OSS_UPLOAD_URI)
	@ResponseBody
	public String upload(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = TestConstant.OSS_UPLOAD_TOKEN_PARAMETER) String token,
			@RequestParam(value = TestConstant.UPLOAD_REQUEST_FILE_PARAMETER_NAME, defaultValue = TestConstant.DEFAULT_FILE_PARAMETER_NAME) String fileParam) {
		if (!(request instanceof MultipartHttpServletRequest)) {
			try {
				response.sendError(HttpStatus.BAD_REQUEST.value());
			} catch (IOException e) {
				// TODO Ignore the exception.
				e.printStackTrace();
			}

			return "Bad request";
		}

		// TODO 校验TOKEN
		System.out.println("TOKEN:" + token);

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(fileParam);

		for (MultipartFile multipartFile : files) {
			if (!multipartFile.isEmpty()) {
				try {
					ossService.upload(multipartFile);
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}

		return "xxx";
	}

}
