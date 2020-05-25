package com.esst.ts.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;


/**
 * FTP连接工具
 * SHY
 */
@Service
public class FtpUtils {
	private final Logger log = LoggerFactory.getLogger(FtpUtils.class);
	// ftp服务器地址
	@Value("${hostname}")
	private String hostname;
	// ftp服务器端口号默认为21
	@Value("${port}")
	private Integer port;
	// ftp登录账号
	@Value("${ftpUserName}")
	private String username;
	// ftp登录密码
	@Value("${ftpPassword}")
	private String password;

	public FTPClient ftpClient = null;

	/**
	 * 初始化ftp服务器
	 */
	public boolean initFtpClient() {
		boolean flag = false;
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
		try {
			ftpClient.connect(hostname, port); // 连接ftp服务器
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				ftpClient.login(username, password); // 登录ftp服务器
			}
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置UNIX环境
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
			ftpClient.configure(conf);
			// 判断是否连接成功
			int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
			} else {
				flag = true;
			}
		} catch (MalformedURLException e) {
			log.info("初始化ftp服务器失败", e);
		} catch (IOException e) {
			log.info("初始化ftp服务器失败", e);
		}
		return flag;
	}

	/**
	 * 上传文件
	 *
	 * @param pathname
	 *            ftp服务保存地址
	 * @param fileName
	 *            上传到ftp的文件名
	 * @param originfilename
	 *            待上传文件的名称（绝对地址） *
	 * @return
	 */
	public boolean uploadFile(String pathname, String fileName, String originfilename) {
		boolean flag = false;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(originfilename));
			if (initFtpClient()) {
				ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
				createDirecroty(pathname);
				ftpClient.makeDirectory(pathname);
				ftpClient.changeWorkingDirectory(pathname);
				ftpClient.storeFile(fileName, inputStream);
				inputStream.close();
				ftpClient.logout();
				flag = true;
			}
		} catch (Exception e) {
			log.info("上传文件到ftp服务器失败", e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					log.info("断开ftp连接失败", e);
				}
			}
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.info("关闭流失败", e);
				}
			}
		}
		return flag;
	}

	/**
	 * 上传文件
	 *
	 * @param pathname
	 *            ftp服务保存地址
	 * @param fileName
	 *            上传到ftp的文件名
	 * @param inputStream
	 *            输入文件流
	 * @return
	 */
	public boolean uploadFile(String pathname, String fileName, InputStream inputStream) {
		boolean flag = false;
		try {
			if (initFtpClient()) {
				ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
				createDirecroty(pathname);
				ftpClient.makeDirectory(pathname);
				ftpClient.changeWorkingDirectory(pathname);
				ftpClient.storeFile(fileName, inputStream);
				inputStream.close();
				ftpClient.logout();
				flag = true;
			}
		} catch (Exception e) {
			log.info("上传文件到ftp服务器失败", e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					log.info("断开ftp连接失败", e);
				}
			}
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.info("关闭流失败", e);
				}
			}
		}
		return flag;
	}

	// 改变目录路径
	public boolean changeWorkingDirectory(String directory) {
		boolean flag = true;
		try {
			flag = ftpClient.changeWorkingDirectory(directory);
		} catch (IOException ioe) {
			log.info("文件目录变更失败", ioe);
		}
		return flag;
	}

	// 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
	public boolean createDirecroty(String remote) throws IOException {
		boolean success = true;
		String directory = remote + "/";
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			String path = "";
			String paths = "";
			while (true) {
				String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
				path = path + "/" + subDirectory;
				if (!existFile(path)) {
					if (makeDirectory(subDirectory)) {
						changeWorkingDirectory(subDirectory);
					} else {
						changeWorkingDirectory(subDirectory);
					}
				} else {
					changeWorkingDirectory(path);
				}
				paths = paths + "/" + subDirectory;
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return success;
	}

	// 判断ftp服务器文件是否存在
	public boolean existFile(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}

	// 创建目录
	public boolean makeDirectory(String dir) {
		boolean flag = true;
		try {
			flag = ftpClient.makeDirectory(dir);
		} catch (Exception e) {
			log.info("ftp创建文件目录失败", e);
		}
		return flag;
	}

	/**
	 * * 下载文件 *
	 *
	 * @param pathname
	 *            FTP服务器文件目录 *
	 * @param newFileName
	 *            文件名称 *
	 * @param localpath
	 *            下载后的文件路径 *
	 * @return
	 */
	public String downloadFile(String pathname, String localpath, String newFileName) {
		String fileName = "";
		OutputStream os = null;
		try {
			if(initFtpClient()){
				// 判断文件是否存在
				boolean existFile = existFile(pathname);
				if (existFile) {
					ftpClient.changeWorkingDirectory(pathname);
					FTPFile[] ftpFiles = ftpClient.listFiles();
					for (FTPFile file : ftpFiles) {
						fileName = file.getName();
						File localFile = new File(localpath + "/" + newFileName);
						os = new FileOutputStream(localFile);

						ftpClient.retrieveFile(file.getName(), os);
						os.close();
					}
				}
				ftpClient.logout();
			}
		} catch (Exception e) {
			log.info("ftp下载文件失败", e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					log.info("断开ftp连接失败", e);
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					log.info("关闭流失败", e);
				}
			}
		}
		return fileName;
	}

	/**
	 * * 删除文件 *
	 *
	 * @param pathname
	 *            FTP服务器保存目录 *
	 * @param filename
	 *            要删除的文件名称 *
	 * @return
	 */
	public boolean deleteFile(String pathname, String filename) {
		boolean flag = false;
		try {
			if (initFtpClient()) {
				// 切换FTP目录
				ftpClient.changeWorkingDirectory(pathname);
				ftpClient.dele(filename);
				ftpClient.logout();
				flag = true;
			}
		} catch (Exception e) {
			log.info("ftp删除文件失败", e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					log.info("断开ftp连接失败", e);
				}
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		FtpUtils ftp = new FtpUtils();
//		ftp.uploadFile("/home/shihongyang/upload/00.00.46.33", "1516952602299.jpg", "D://1516952602299.jpg");
		String downloadFile = ftp.downloadFile("/home/shihongyang/download/0.0.46.35", "E://", "aa.jpg");
		System.out.println("System.out.println(“" + downloadFile);
	}
}