package com.belerweb.oss4springmvc;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.belerweb.oss4springmvc.OssService;
import com.belerweb.oss4springmvc.bean.OssUploadResult;

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
	public String upload(@RequestParam(TestConstant.DEFAULT_FILE_PARAMETER_NAME) MultipartFile file) throws IOException {
		OssUploadResult result = ossService.upload(file);
		if (result.isSuccess()) {
			return "Success, the file url is ：" + result.getSuccessFiles().get(0).getUrl();
		}

		return "Failed";
	}
}
