oss4springmvc
=============

阿里云开放存储服务(OSS) Spring MVC 插件。当前版本 1.0.0。

# 功能
*   简单配置即可OSS上传
*   上传文件路径规则生成器
*   上传文件文件名规则生成器
*   ...

# 环境需求
*   阿里云开放存储服务器（可写的Bucket）
*   Spring MVC 3+

# 使用步骤
## 步骤一：Maven用户
oss4springmvc已发布到Maven中央资源库，并已包含阿里云Java SDK（2012-7-5），因此只需要添加oss4springmvc依赖即可，代码如下：
	
	<dependency>
		<groupId>com.belerweb</groupId>
		<artifactId>oss4springmvc</artifactId>
		<version>1.0.1</version>
	</dependency>
	
## 步骤一：非Maven用户
下载压缩包，[oss4springmvc-1.0.0.zip](http://repo1.maven.org/maven2/com/belerweb/oss4springmvc/1.0.0/oss4springmvc-1.0.0.zip)
或[oss4springmvc-1.0.0.tar.gz](http://repo1.maven.org/maven2/com/belerweb/oss4springmvc/1.0.0/oss4springmvc-1.0.0.tar.gz)。
解压后将lib目录下jar包包含到项目依赖中。

## 步骤二
在Spring XML配置文件中加入上传文件和OSS服务的bean定义。示例代码如下：

	<!-- SpringMVC 文件上传 -->
	<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<property name="maxUploadSize" value="1000000" />
	</bean>

	<!-- 阿里云OSS 文件上传 -->
	<bean class="com.belerweb.oss4springmvc.OssService">
		<property name="endpoint" value="${oss4springmvc.endpoint}" />
		<property name="accessId" value="${oss4springmvc.accessId}" />
		<property name="accessKey" value="${oss4springmvc.accessKey}" />
		<property name="bucket" value="${oss4springmvc.bucket}" />
	</bean>


## 步骤三
在需要使用OssServer的控制器中注入OssServer，并调用upload方法上传。



# 注意事项
*   将${oss4springmvc.accessId}等变量替换成你的信息，更多资料请参考[如何获取API 密钥](http://help.aliyun.com/manual?spm=0.0.0.88.f86e0d&helpId=786)。
*   将配置文件信息加密可参考[扩展PropertyPlaceholderConfigurer](http://www.linuxso.com/architecture/15985.html)。


# 参考项目
oss4springmvc-test-webapp，下载[oss4springmvc-test-webapp-1.0.0.war](http://repo1.maven.org/maven2/com/belerweb/oss4springmvc-test-webapp/1.0.0/oss4springmvc-test-webapp-1.0.0.war)


更多信息请参考WIKI，欢迎任何人共享WIKI。

