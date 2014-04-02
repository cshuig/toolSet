package com.cshuig.tool.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * FTP上传下载工具 支持单文件及多文件上传下载。 支持指定文件名上传下载。 FTP服务器配置从FTPConfig.Properties文件读取。
 * 
 * @author Shunqin.Chen
 */
public class FTPUtil {
	private static Logger logger = Logger.getLogger(FTPUtil.class);
	
	/** ftp server Ip地址 */
	private static String FTPSERVER = null;
	
	/** ftp server 端口,ftp默认的端口都是21 */
	private static int FTPPORT = 0;
	
	/** ftp 用户名 */
	private static String FTPUSER = null;
	
	/** ftp 用户密码 */
	private static String FTPPSWD = null;
	
	/** 文件存放的路径 */
	private static String FILEPATH = null;
	
	/**
	 * 获取FTP连接
	 * 
	 * @throws java.io.IOException
	 * @throws java.net.SocketException
	 */
	public static FTPClient getFTPClient() {
		FTPClient ftpClient = null;
		// 通过读取FTPConfig.properties文件获取服务器配置：
		Properties prop = new Properties();
		try {
			prop.load(FTPUtil.class.getClassLoader().getResourceAsStream("FTPConfig.properties"));
		} catch (IOException e) {
			logger.error("无法获取FTP配置文件，与服务器连接失败！");
			// e.printStackTrace();
			return null;
		}
		FTPSERVER = prop.getProperty("FTPSERVER");
		FTPPORT = Integer.parseInt(prop.getProperty("FTPPORT", "0"));
		FTPUSER = prop.getProperty("FTPUSER");
		FTPPSWD = prop.getProperty("FTPPSWD");
		FILEPATH = prop.getProperty("WorkingDirectory");
		// FTP服务器连接及设置部分：
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(FTPSERVER, FTPPORT);// 连接FTP服务器
			ftpClient.login(FTPUSER, FTPPSWD);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
				return null;
			} else {
				logger.info("FTP连接成功。");
			}
			
			// 配置FTP参数：
			ftpClient.changeWorkingDirectory(FILEPATH);
			ftpClient.setBufferSize(1 * 1024 * 1024); // FTP上传缓冲，如果使用FTP工具上传速度快，但应用上传慢请增加此处大小。
			ftpClient.setControlEncoding("GBK");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); // 登陆后设置文件类型为二进制否则可能导致乱码文件无法打开
		} catch (SocketException e) {
			logger.error("网络连接错误，连接FTP服务器失败！" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("找不到远程FTP服务器工作目录！" + e.getMessage());
			e.printStackTrace();
		}
		return ftpClient;
	}
	
	/**
	 * 获取FTP连接
	 * @param ejml 二级目录	
	 * @return
	 */
	public static FTPClient getFTPClientForPath(String ejml) {
		FTPClient ftpClient = null;
		// 通过读取FTPConfig.properties文件获取服务器配置：
		Properties prop = new Properties();
		try {
			prop.load(FTPUtil.class.getClassLoader().getResourceAsStream("FTPConfig.properties"));
		} catch (IOException e) {
			logger.error("无法获取FTP配置文件，与服务器连接失败！");
			// e.printStackTrace();
			return null;
		}
		FTPSERVER = prop.getProperty("FTPSERVER");
		FTPPORT = Integer.parseInt(prop.getProperty("FTPPORT", "0"));
		FTPUSER = prop.getProperty("FTPUSER");
		FTPPSWD = prop.getProperty("FTPPSWD");
		FILEPATH = prop.getProperty("WorkingDirectory");
		// FTP服务器连接及设置部分：
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(FTPSERVER, FTPPORT);// 连接FTP服务器
			ftpClient.login(FTPUSER, FTPPSWD);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
				return null;
			} else {
				logger.info("FTP连接成功。");
			}
			
			// 配置FTP参数：
			ftpClient.changeWorkingDirectory(FILEPATH);
			//TODO 创建目录
			ftpClient.makeDirectory(ejml);
			ftpClient.changeWorkingDirectory(ejml);
			FILEPATH = FILEPATH +ejml+"\\";
			ftpClient.setBufferSize(1 * 1024 * 1024); // FTP上传缓冲，如果使用FTP工具上传速度快，但应用上传慢请增加此处大小。
			ftpClient.setControlEncoding("GBK");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); // 登陆后设置文件类型为二进制否则可能导致乱码文件无法打开
		} catch (SocketException e) {
			logger.error("网络连接错误，连接FTP服务器失败！" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("找不到远程FTP服务器工作目录！" + e.getMessage());
			e.printStackTrace();
		}
		return ftpClient;
	}
	
	/**
	 * 获取FTP文件路径
	 * 
	 * @return
	 */
	public String getPath() {
		@SuppressWarnings("unused")
		FTPClient ftpClient = null;
		// 通过读取FTPConfig.properties文件获取服务器配置：
		Properties prop = new Properties();
		try {
			prop.load(FTPUtil.class.getClassLoader().getResourceAsStream("FTPConfig.properties"));
		} catch (IOException e) {
			logger.error("无法获取FTP配置文件，与服务器连接失败！");
			// e.printStackTrace();
			return null;
		}
		FTPSERVER = prop.getProperty("FTPSERVER");
		FTPPORT = Integer.parseInt(prop.getProperty("FTPPORT", "0"));
		FTPUSER = prop.getProperty("FTPUSER");
		FTPPSWD = prop.getProperty("FTPPSWD");
		FILEPATH = prop.getProperty("WorkingDirectory");
		return FILEPATH;
	}
	
	/**
	 * FTP文件下载
	 * 
	 * @param localPath
	 *            下载后存储路径
	 * @param remoteFileName
	 *            服务器文件名称，需含后缀名
	 * @param localFileName
	 *            下载后本地保存文件名，需含后缀名
	 * @throws java.io.FileNotFoundException
	 */
	public static boolean ftpDownload(String localPath, String remoteFileName, String localFileName)
			throws FileNotFoundException, IOException {
		FTPClient ftpClient = FTPUtil.getFTPClientForPath(remoteFileName.substring(0, 6));
		FileOutputStream fos = null;
		File f2 = new File(localPath);
		if(!f2.exists()){
			f2.mkdir();
		}
		boolean result = false; // 下载结果
		if (ftpClient == null) { // 如果获取ftpclient失败
			return false;
		} else {
			// 后台打印下载信息
			logger.info("您当前正在下载远程文件：" + remoteFileName + "至" + localPath + "目录。" + "	/n" + "下载后文件名：" + localFileName);
			File f = new File(localPath + localFileName);
			if (f.exists()) {
				fos = new FileOutputStream(f, true);
				ftpClient.setRestartOffset(f.length()); // 设置断点续传
				result = ftpClient.retrieveFile(remoteFileName, fos); // 下载文件
			} else {
				fos = new FileOutputStream(f);
				result = ftpClient.retrieveFile(remoteFileName, fos); // 下载文件
			}
			FTPUtil.destroyFTPClient(null, null, ftpClient, fos); // 关闭文件流文件、FTP客户端
			return result;
		}
	}
	
	/***************************************************************************
	 * FTP文件上传
	 * 
	 * @param localFile
	 *            上传文件，含路径及文件后缀，例：d:/4.txt
	 * @param remoteFileName
	 *            上传后文件名，含后缀名不含路径
	 * @throws java.io.FileNotFoundException
	 *             找不到要上传的文件
	 */
	public static String ftpUpload(String localFile, String remoteFileName) throws FileNotFoundException, IOException {
		FTPClient ftpClient;
		ftpClient = FTPUtil.getFTPClient();
		FileInputStream fis = null;
		if (ftpClient == null) {
			return null;
		} else {
			fis = new FileInputStream(localFile);
			ftpClient.storeFile(remoteFileName, fis); // 储存文件
			logger.info("文件上传成功！");
		}
		FTPUtil.destroyFTPClient(null, fis, ftpClient, null);
		return FILEPATH + remoteFileName;
	}
	
	/***************************************************************************
	 * FTP文件上传
	 * 
	 * @param remoteFileName
	 *            上传后文件名，含后缀名不含路径
	 * @throws java.io.FileNotFoundException
	 *             找不到要上传的文件
	 */
	public static String ftpUploadByInStream(FileInputStream fis, String remoteFileName) throws FileNotFoundException,
			IOException {
		FTPClient ftpClient;
		ftpClient = FTPUtil.getFTPClient();
		if (ftpClient == null) {
			return null;
		} else {
			ftpClient.storeFile(remoteFileName, fis); // 储存文件
			logger.info("文件上传成功！");
		}
		FTPUtil.destroyFTPClient(null, fis, ftpClient, null);
		return FILEPATH + remoteFileName;
	}
	
	/******
	 * FTP文件上传
	 * 这个方法是:在上传原文件名称前面加上：ejml_
	 * @param remoteFileName 上传后文件名，含后缀名不含路径
	 * @param ejml		二级目录
	 * @throws java.io.FileNotFoundException 找不到要上传的文件
	 */
	public static String ftpUploadByInStream(FileInputStream fis, String remoteFileName,String ejml) throws FileNotFoundException,
			IOException {
		FTPClient ftpClient;
		ejml = ejml.substring(0,6);
		ftpClient = FTPUtil.getFTPClientForPath(ejml);
		if (ftpClient == null) {
			return null;
		} else {
			remoteFileName = ejml + "_" + remoteFileName;
			ftpClient.storeFile(remoteFileName, fis); // 储存文件
			logger.info("文件上传成功！");
		}
		FTPUtil.destroyFTPClient(null, fis, ftpClient, null);
		return FILEPATH + remoteFileName;
	}
	
	/*****
	 * 目前是附件从配置中心导入到监管处的时候：使用
	 * FTP文件上传
	 * 这个方法是:不需要在上传原文件名称前面加上：ejml_  
	 * @param remoteFileName 上传后文件名，含后缀名不含路径
	 * @param ejml		二级目录
	 * @throws java.io.FileNotFoundException 找不到要上传的文件
	 */
	public static String ftpUploadByInStreamNotEjml(FileInputStream fis, String remoteFileName,String ejml) throws FileNotFoundException,
			IOException {
		FTPClient ftpClient;
		ftpClient = FTPUtil.getFTPClientForPath(ejml);
		if (ftpClient == null) {
			return null;
		} else {
			ftpClient.storeFile(remoteFileName, fis); // 储存文件
			logger.info("文件上传成功！");
		}
		FTPUtil.destroyFTPClient(null, fis, ftpClient, null);
		return FILEPATH + remoteFileName;
	}
	
	/**
	 * 以文件流方式读取FTP服务器上的文件，并返回文件流
	 * 
	 * @param fileName
	 *            远程服务器文件名，需包含后缀名，不包含路径。
	 * @return ftpReadFile 被读取的文件流
	 */
	public static InputStream readServerFile(String fileName) {
		InputStream fis = null;
		FTPClient ftpClient = null;
		try {
			ftpClient = FTPUtil.getFTPClient();
			if (ftpClient == null) {
				return fis;
			} else {
				fis = ftpClient.retrieveFileStream(fileName); // 不关闭，返回到页面
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			FTPUtil.destroyFTPClient(null, null, ftpClient, null);
		}
		return fis;
	}
	
	/**
	 * 获取ftp一个目录的所有文件
	 * @param path
	 * @throws Exception
	 */
	public static List<FTPFile> listDirFiles(String pathname){
		List<FTPFile> ftpFiles = new ArrayList<FTPFile>();
		FTPFile[] files = null;
		FTPClient ftpClient = null;
		try {
			ftpClient = FTPUtil.getFTPClientForPath(pathname);
			if(ftpClient == null){
				System.out.println("ftpClient is null");
				return null;
			}
			files = ftpClient.listFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			FTPUtil.destroyFTPClient(null, null, ftpClient, null);
		}
		for(FTPFile ftpFile : files){
			ftpFiles.add(ftpFile);
		}
		return ftpFiles;
	}
	
	/**
	 * 关闭文件流，断开连接等资源回收处理，可在finally中使用 如果参数不存在可用null代替
	 */
	private static void destroyFTPClient(InputStream ins, FileInputStream fis, FTPClient ftpClient, OutputStream ops) {
		if (ins != null) {
			try {
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (ops != null) {
			try {
				ops.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
