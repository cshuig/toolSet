package com.cshuig.tool.zip;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/** 
 * ZIP压缩文件操作工具类 
 * 支持密码 
 * 依赖zip4j开源项目(http://www.lingala.net/zip4j/) 
 * 版本1.3.* 
 * @author cshuig
 * @since  2013-12-16 
 */ 
public class CompressUtil {
	
	/**
	 * 压缩指定文件或文件夹 --到-->当前目录
	 * @param src
	 * @return
	 */
	public static String zip(String src){
		return zip(src,null);
	}
	
	/**
	 * 使用给定 '密码' 压缩指定 文件或文件夹  --到-->当前目录
	 * @param src			要压缩的文件 	--目前只支持传入带文件夹,默认不会吧文件夹压缩进去 如： C:\\DIR\\file.xxx,
	 * @param password		压缩使用的密码
	 * @return
	 */
	public static String zip(String src, String password){
		return zip(src,null,password);
	}
	
	/**
	 * 使用给定 '密码' 压缩指定 文件或文件夹 到指定的路径
	 * @param src			要压缩的文件	--目前只支持传入带文件夹,默认不会吧文件夹压缩进去 如： C:\\DIR\\file.xxx
	 * @param dest			压缩文件 存放路径
	 * @param password		压缩使用的密码
	 * @return
	 */
	public static String zip(String src, String dest,String password){
		return zip(src,dest,false,password);
	}
	
	/**
	 * <p>
	 * 		dest:可传最终压缩文件存放的绝对路径，也可以存放目录，也可以传null或者""
	 * 			1、null或者""		：	则将压缩文件存放在当前目录，即跟源文件同目录，压缩文件名取源文件,以.zip后缀
	 * 			2、如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,否则视为文件名. 
	 * </p>
	 * 使用给定 '密码' 压缩指定 '文件或文件夹' --到-->指定文件
	 * @param src			要压缩的文件	--目前只支持传入带文件夹,是否要把文件夹也压缩进去'isCreateDir'，根据 如： C:\\DIR\\file.xxx
	 * @param dest			压缩文件 存放路径
	 * @param isCreateDir	是否在压缩文件里创建目录,仅在压缩文件为目录时有效
	 * @param password		压缩使用的密码
	 */
	public static String zip(String src, String dest, boolean isCreateDir, String password){
		File srcFile = new File(src);
		dest = buildDestinationZipFilePath(srcFile, dest);
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);	//压缩方式
		zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM);	//压缩级别
		if(!StringUtils.isEmpty(password)){
			zipParameters.setEncryptFiles(true);
			zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);	//加密方式
			zipParameters.setPassword(password.toCharArray());
		}
		ZipFile zipFile;
		try {
			//在dest目录，创建一个zip文件
			zipFile = new ZipFile(dest);
			if(srcFile.isDirectory()){
				//如果不创建目录的话，将直接给定目录下的文件压缩到压缩文件，即没有目录结构
				if(!isCreateDir){
					File[] subFiles = srcFile.listFiles();
					ArrayList<File> temp = new ArrayList<File>();
					Collections.addAll(temp, subFiles);
					//将目录下的所有文件，添加到zip文件中
					zipFile.addFiles(temp, zipParameters);
					return dest;
				}
				//将文件夹添加到zip文件中
				zipFile.addFolder(srcFile, zipParameters);
			}else{
				zipFile.addFolder(srcFile, zipParameters);
			}
			return dest;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 构建压缩文件存放的路径，如果不存在则默认创建一个
	 * 传入的可能是 文件名 或 文件目录，也可能不传
	 * 此方法用以转换最终压缩文件的存放路径
	 * @param srcFile
	 * @param destParam
	 * @return
	 */
	private static String buildDestinationZipFilePath(File srcFile, String destParam){
		if(StringUtils.isEmpty(destParam)){
			if(srcFile.isDirectory()){
				destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
			}else{
				String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				//构建存放路径
				destParam = srcFile.getParent() + File.separator + fileName + ".zip";
			}
		}else{
			createDestDirectoryIfNecessary(destParam);	// 在指定路径不存在的情况下将其创建出来
			if(destParam.endsWith(File.separator)){
				String fileName = "";
				if(srcFile.isDirectory()){
					fileName = srcFile.getName();
				}else{
					fileName = srcFile.getName().substring(0,srcFile.getName().lastIndexOf("."));
				}
				destParam += fileName + ".zip";
			}
		}
		return destParam;
	}
	
	/**
	 * 在必要的情况下创建文件存放目录，比如指定的存放路径并没有被创建
	 * @param destParam		指定的存放路径,有可能该路径并没有被创建 
	 */
	private static void createDestDirectoryIfNecessary(String destParam){
		File destDir = null;
		if(destParam.endsWith(File.separator)){
			destDir = new File(destParam);
		}else{
			destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {  
            destDir.mkdirs();  
        } 
	}
	
	/**
	 * 根据指定的解压密码,解压文件到当前目录
	 * @param zipPath		指定的zip压缩文件路径
	 * @param password		解压密码
	 * @return
	 * @throws net.lingala.zip4j.exception.ZipException
	 */
	public static File[] unzip(String zipPath, String password) throws ZipException{
		File zipFile = new File(zipPath);
		File parentDir = zipFile.getParentFile();
		return unzip(zipFile,parentDir.getAbsolutePath(),password);
	}
	/**
	 * 
	 * @param zipPath		指定的zip压缩文件路径
	 * @param dest
	 * @param password
	 * @return
	 * @throws net.lingala.zip4j.exception.ZipException
	 */
	public static File[] unzip(String zipPath, String dest, String password) throws ZipException{
		File zipFile = new File(zipPath);
		return unzip(zipFile,dest, password);
	}
	
	/**
	 * 根据指定的解压密码 解压指定的文件  --到-->指定的目录
	 * 如果指定目录不存在，则可以自动创建，不合法的路径将抛出异常
	 * @param zipFile		指定的zip压缩文件
	 * @param dest			解压后的目录
	 * @param password		解压密码
	 * @return
	 * @throws net.lingala.zip4j.exception.ZipException
	 */
	public static File[] unzip(File zipFile, String dest, String password) throws ZipException{
		ZipFile zFile = new ZipFile(zipFile);
		zFile.setFileNameCharset("UTF-8");
		if(!zFile.isValidZipFile()){
			throw new ZipException("目标压缩文件不合法，可能已损坏！");
		}
		File destDir = new File(dest);
		if(destDir.isDirectory() && !destDir.exists()){
			destDir.mkdir();
		}
		if(zFile.isEncrypted()){
			zFile.setPassword(password.toCharArray());
		}
		zFile.extractAll(dest);
		List<FileHeader> headerList = zFile.getFileHeaders();  
        List<File> extractedFileList = new ArrayList<File>();  
        for(FileHeader fileHeader : headerList) {  
            if (!fileHeader.isDirectory()) {  
                extractedFileList.add(new File(destDir,fileHeader.getFileName()));  
            }  
        }  
        File [] extractedFiles = new File[extractedFileList.size()];  
        extractedFileList.toArray(extractedFiles);
        System.out.println("文件【"+zipFile.getName()+"】被解压到:" + dest);
        return extractedFiles;  
	}
}
