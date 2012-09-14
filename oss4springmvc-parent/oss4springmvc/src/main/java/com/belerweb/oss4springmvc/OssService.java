package com.belerweb.oss4springmvc;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSErrorCode;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;
import com.belerweb.oss4springmvc.bean.OssFile;
import com.belerweb.oss4springmvc.bean.OssUploadResult;
import com.belerweb.oss4springmvc.builder.DatePathBuilder;
import com.belerweb.oss4springmvc.builder.FileNameBuilder;
import com.belerweb.oss4springmvc.builder.PathBuilder;
import com.belerweb.oss4springmvc.builder.UuidFileNameBuilder;

/**
 * @author Jun
 *
 * OSS 服务
 */
public class OssService implements InitializingBean {

	private String endpoint;
	private String accessId;
	private String accessKey;
	private String bucket;

	private boolean detectContentType = false;

	private PathBuilder pathBuilder;
	private FileNameBuilder fileNameBuilder;

	private OSSClient ossClient;

	public OssUploadResult upload(MultipartFile file) throws IOException {
		return upload(file, createDefaultObjectMetadata(file));
	}

	public OssUploadResult upload(MultipartFile file, ObjectMetadata metadata) throws IOException {
		String fileName = fileNameBuilder.build(file.getOriginalFilename());
		OssFile ossFile = ossUpload(pathBuilder.build() + fileName, file.getInputStream(), metadata);
		ossFile.setName(fileName);
		ossFile.setOriginalName(file.getOriginalFilename());
		return new OssUploadResult(ossFile.geteTag() == null ? Constant.FAILURE : Constant.SUCCESS, ossFile);
	}

	private OssFile ossUpload(String key, InputStream inputStream, ObjectMetadata metadata) {
		OssFile result = null;
		try {
			PutObjectResult putObjectResult = ossClient.putObject(bucket, key, inputStream, metadata);
			result = new OssFile(putObjectResult.getETag());
			result.setUrl(endpoint + Constant.SLASH + bucket + Constant.SLASH + key);
			result.setSize(metadata.getContentLength());
			result.setContentType(metadata.getContentType());
		} catch (OSSException e) {
			result = new OssFile(e.getErrorCode(), e.getMessage());
			e.printStackTrace();
		} catch (ClientException e) {
			result = new OssFile(null, e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	private ObjectMetadata createDefaultObjectMetadata(MultipartFile file) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());

		if (detectContentType) {
			// TODO
		} else {
			metadata.setContentType(file.getContentType());
		}

		return metadata;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public void setPathBuilder(PathBuilder pathBuilder) {
		this.pathBuilder = pathBuilder;
	}

	@Override
	public void afterPropertiesSet() {
		if (endpoint == null) {
			endpoint = Constant.DEFAULT_OSS_ENDPOINT;
		} else if (endpoint.endsWith(Constant.SLASH)) {
			endpoint = endpoint.replaceAll("/*$", Constant.EMPTY);
		}
		Assert.hasText(accessId, "请指定accessId。\n" + Constant.API_KEY_HELP);
		Assert.hasText(accessKey, "请指定accessKey。\n" + Constant.API_KEY_HELP);
		Assert.hasText(bucket, "请指定bucket。\n" + Constant.BUCKET_HELP);

		ossClient = new OSSClient(endpoint, accessId, accessKey);
		try {
			ossClient.getBucketAcl(bucket);
			// TODO 校验ACL
		} catch (OSSException e) {
			// e.printStackTrace();
			String errorCode = e.getErrorCode();
			if (OSSErrorCode.INVALID_ACCESS_KEY_ID.equals(errorCode)) {
				Assert.isTrue(false, "请指定正确的accessId。\n" + Constant.API_KEY_HELP);
			} else if (OSSErrorCode.SIGNATURE_DOES_NOT_MATCH.equals(errorCode)) {
				Assert.isTrue(false, "请指定正确的accessKey。\n" + Constant.API_KEY_HELP);
			} else if (OSSErrorCode.NO_SUCH_BUCKET.equals(errorCode)) {
				Assert.isTrue(false, "请指定一个存在的Bucket。");
			} else {
				Assert.isTrue(false, errorCode);
			}
		} catch (ClientException e) {
			// Ignore
			e.printStackTrace();
		}

		if (pathBuilder == null) {
			pathBuilder = new DatePathBuilder();
		}

		if (fileNameBuilder == null) {
			fileNameBuilder = new UuidFileNameBuilder();
		}
	}
}
