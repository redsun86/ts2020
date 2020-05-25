package com.esst.ts.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

@Service
public class FtpJSchUtils {
	private final Logger log = LoggerFactory.getLogger("");
	private static ChannelSftp sftp = null;

	// 账号
	// private static String username = "llvision";
	// // 主机ip
	// private static String host = "123.56.22.39";
	// // 密码
	// private static String password = "llvision@test";
	// // 端口
	// private static int port = 22;
	// ftp服务器地址
	@Value("${hostname}")
	private String host;
	// sftp服务器端口号默认为22
	@Value("${port}")
	private Integer port;
	// ftp登录账号
	@Value("${ftpUserName}")
	private String username;
	// ftp登录密码
	@Value("${ftpPassword}")
	private String password;
	private static Session sshSession = null;

	// 连接sftp服务器
	public ChannelSftp initFtpClient() {
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(config);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			if (channel != null) {
				channel.connect();
				log.info("初始化成功");
			} else {

			}
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			log.info("初始化失败！！！");
			disConnect();
			initFtpClient();
		}
		return sftp;
	}

	
	/**
	 * 
	 * @param uploadFile
	 *            上传文件的路径
	 * @return 服务器上文件名
	 * @throws Exception
	 */
	public String upload(String uploadFile, String directory, String fileName) {
		initFtpClient();
		File file = null;
		SftpATTRS attrs = null;
		try {
			attrs = sftp.stat(directory);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if (attrs == null) {
				sftp.mkdir(directory);
				log.info("创建子目录：" + directory);
			}
			sftp.cd(directory);
			file = new File(uploadFile);
			sftp.put(new FileInputStream(file), fileName);
			log.info("上传文件" + fileName + "到目录" + directory);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return file == null ? null : fileName;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件名
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String downloadFileName, String directory, String saveFile) {
		try {
			sftp.cd(directory);

			File file = new File(saveFile);

			sftp.get(downloadFileName, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param deleteFile
	 *            要删除的文件名字
	 * @param sftp
	 */
	public void delete(String deleteFile, String directory) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory) throws SftpException {
		return sftp.ls(directory);
	}

	/**  
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     * @param basePath  服务器的基础路径 
     * @param directory  上传到该目录  
     * @param sftpFileName  sftp端文件名  
     * @param in   输入流  
     
	 * @throws Exception */
	public String wirteContentToFile(String savePath, String directory, String sftpFileName, String content)
			throws Exception {
		initFtpClient();
		FileUtils.makefile(savePath);
		log.info("创建路径" + savePath);
		FileBase64ConvertUitl.toFile(content, savePath + sftpFileName);
		log.info("生成文件" + savePath + sftpFileName);
		String upload = upload(savePath + sftpFileName, directory, sftpFileName);
		File file = new File(savePath + sftpFileName);
		if (file.exists()) {
			file.delete();
		}
		return upload;
	}

	/**
	 * 关闭server
	 * 
	 * @param args
	 */
	public void disConnect() {
		if(sftp!=null){
			if (sftp.isConnected()) {
				sftp.disconnect();
			}
		}
		if (sshSession != null) {
			if (sshSession.isConnected()) {
				sshSession.disconnect();
			}
		}
	}

	public static void main(String[] args) {
		FtpJSchUtils ftpJSchUtils = new FtpJSchUtils();
		try {
			ftpJSchUtils.wirteContentToFile("/home/llvision", "/record/test1", "test.json", "adminmadadawdada");
			ftpJSchUtils.upload("D://test.png", "/home/llvision/record/test", "test.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}