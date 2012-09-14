package com.belerweb.oss4springmvc.testwebapp;

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
 */
@Controller
public class OssController {

	@Resource(type = OssService.class)
	private OssService ossService;

	@RequestMapping(method = RequestMethod.POST, value = "/aliyun_oss_upload")
	@ResponseBody
	public OssUploadResult upload(@RequestParam("file") MultipartFile file) throws IOException {
		return ossService.upload(file);
	}

}
