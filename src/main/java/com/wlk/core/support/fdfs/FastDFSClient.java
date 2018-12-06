package com.wlk.core.support.fdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.proto.storage.DownloadFileWriter;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wlk.core.util.string.StringUtil;

/**
 * 工具类
 */
@Component
public class FastDFSClient {
	private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
	@Autowired
	private FastFileStorageClient storageClient;

	/*
	 * @Autowired private NginxInfo nginxInfo;
	 * 
	 * 
	 * @Autowired private FdfsInfo fdfsInfo;
	 */

	@Autowired
	private FdfsConfig fdfsConfig;

	/**
	 * 上传文件 适用于所有文件
	 * 
	 * @param file
	 *            文件对象
	 * @return 文件访问地址
	 * @throws IOException
	 */
	public StorePath uploadFile(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
				FilenameUtils.getExtension(file.getOriginalFilename()), null);
		return storePath;
	}

	/**
	 * 下载文件
	 * 
	 * @param file
	 *            文件对象
	 * @return 文件访问地址
	 * @throws IOException
	 */
	public ResponseEntity<byte[]> downloadFile(String path,String fileName) throws IOException {
		byte[] b = null;
		HttpHeaders h = new HttpHeaders();
		try {
			StorePath storePath = StorePath.praseFromUrl(path);
			DownloadByteArray da = new DownloadByteArray();
			/// DownloadFileWriter d=new DownloadFileWriter("d://ff.zip");
			b = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), da);
	        h.setContentDispositionFormData("attachment",  new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
	        h.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		} catch (FdfsUnsupportStorePathException e) {
			logger.warn(e.getMessage());
		}
		return new ResponseEntity<byte[]> (b,h,HttpStatus.CREATED);
	}

	/**
	 * 将一段字符串生成一个文件上传
	 * 
	 * @param content
	 *            文件路径
	 * @return文件访问地址
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	public StorePath uploadFile(String content) throws FileNotFoundException {
		if (!StringUtils.hasText(content)) {
			throw new NullPointerException();
		}
		File file = new File(content);
		FileInputStream inputStream = new FileInputStream(file);
		String fileName = file.getName();
		// 获取文件后缀名
		String strs = getFileExt(fileName);
		if (!StringUtils.hasText(strs)) {
			throw new NullPointerException();
		}
		StorePath storePath = storageClient.uploadFile(inputStream, file.length(), strs, null);
		return storePath;
	}

	/**
	 * 传图片并同时生成一个缩略图 "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
	 * 
	 * @param file
	 *            文件对象
	 * @return 文件访问地址
	 * @throws IOException
	 */
	public StorePath uploadImageAndCrtThumbImage(MultipartFile file) throws IOException {
		StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
				FilenameUtils.getExtension(file.getOriginalFilename()), null);
		return storePath;
	}

	/**
	 * 传图片并同时生成一个缩略图 "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
	 * 
	 * @param content
	 *            文件路径
	 * @return 文件访问地址
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	public StorePath uploadImageAndCrtThumbImage(String content) throws FileNotFoundException {
		if (!StringUtils.hasText(content)) {
			throw new NullPointerException();
		}
		File file = new File(content);
		FileInputStream inputStream = new FileInputStream(file);
		String fileName = file.getName();
		// 获取文件后缀名
		String strs = getFileExt(fileName);
		if (!StringUtils.hasText(strs)) {
			throw new NullPointerException();
		}
		StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, file.length(), strs, null);
		return storePath;
	}

	/**
	 * 封装文件完整URL地址
	 * 
	 * @param storePath
	 * @return
	 */
	public String getResAccessUrl(StorePath storePath) {
		String fileUrl = fdfsConfig.getResHost() + ":" + fdfsConfig.getStoragePort() + "/" + storePath.getFullPath();
		return fileUrl;
	}

	/**
	 * 装载缩略图名称 "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
	 * 
	 * @param storePath
	 *            文件路径
	 * @return 缩略图名称
	 */
	/*
	 * public String getThumbImage(String storePath){
	 * if(!StringUtils.hasText(storePath)){ throw new NullPointerException(); }
	 * 
	 * //获取文件名称 String pathL = FileUtil.getFileName(storePath) + "_" +
	 * fdfsInfo.getSize(); //获取文件后缀名 String pathR =
	 * FileUtil.getExtName(storePath); return pathL + "." + pathR; }
	 */

	/**
	 * 删除文件
	 * 
	 * @param fileUrl
	 *            文件访问地址
	 * @return
	 * @throws IOException
	 */
	public void deleteFile(String fileUrl) {
		if (StringUtils.isEmpty(fileUrl)) {
			return;
		}
		try {
			StorePath storePath = StorePath.praseFromUrl(fileUrl);
			storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
		} catch (FdfsUnsupportStorePathException e) {
			logger.warn(e.getMessage());
		}
	}

	/**
	 * 获取文件后缀名（不带点）.
	 * 
	 * @return 如："jpg" or "".
	 */
	private static String getFileExt(String fileName) {
		if (StringUtil.isBlank(fileName) || !fileName.contains(".")) {
			return "";
		} else {
			return fileName.substring(fileName.lastIndexOf(".") + 1); // 不带最后的点
		}
	}
}
