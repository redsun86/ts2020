package com.esst.ts.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.alibaba.fastjson.JSONObject;

/**
 * zip操作工具类
 * @author SHY
 *
 */
public class ZipUtils {


	/**
	 * 解压缩zip包
	 * 
	 * @param zipFilePath
	 *            zip文件的全路径
	 * @param unzipFilePath
	 *            解压后的文件保存的路径
	 * @param includeZipFileName
	 *            解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> unzip(String zipFilePath, String unzipFilePath,
			boolean includeZipFileName) throws Exception {
		Map<String, Map<String, String>> resutl = new HashMap<>();
		if (StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath)) {
			throw new Exception("参数不能为空");
		}
		File zipFile = new File(zipFilePath);
		// 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
		if (includeZipFileName) {
			String fileName = zipFile.getName();
			if (StringUtils.isNotEmpty(fileName)) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			unzipFilePath = unzipFilePath + "/" + fileName;
		}
		// 创建解压缩文件保存的路径
		File unzipFileDir = new File(unzipFilePath);
		if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
			unzipFileDir.mkdirs();
		}
		// 开始解压
		ZipEntry entry = null;
		String entryFilePath = null, entryDirPath = null;
		File entryFile = null, entryDir = null;
		int index = 0, count = 0, bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
		// 循环对压缩包里的每一个文件进行解压
		Map<String, String> excel = new HashMap<>();
		Map<String, String> image = new HashMap<>();
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();
			// 构建压缩包中一个文件解压后保存的文件全路径
			//兼容Windows 和linux
			// 如果发现路径中存在/说明是Windows则将/转成linux中的格式\
			String entryName = entry.getName();
			if (entryName.contains("\\")) {
				entryName = entryName.replace("\\", "/");
			}
			entryFilePath = unzipFilePath + "/" + entryName;
			// 构建解压后保存的文件夹路径
			index = entryFilePath.lastIndexOf("/");
			if (index != -1) {
				entryDirPath = entryFilePath.substring(0, index);
			} else {
				entryDirPath = "";
			}
			entryDir = new File(entryDirPath);
			// 如果文件夹路径不存在，则创建文件夹
			if (!entryDir.exists() || !entryDir.isDirectory()) {
				entryDir.mkdirs();
			}
			// 判断是不是文件夹，如果是文件夹就不执行文件写入
			if (!entry.isDirectory()) {
				// 创建解压文件
				entryFile = new File(entryFilePath);
				if (entryFile.exists()) {
					// 检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
					// SecurityManager securityManager = new SecurityManager();
					// securityManager.checkDelete(entryFilePath);
					// 删除已存在的目标文件
					entryFile.delete();
				}
				// 写入文件
				bos = new BufferedOutputStream(new FileOutputStream(entryFile));
				bis = new BufferedInputStream(zip.getInputStream(entry));
				while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
					bos.write(buffer, 0, count);
				}
				String keyName = "";
				if (entryName.contains("/")) {
					keyName = entryName.split("/")[entryName.split("/").length - 1];
				} else {
					keyName = entryName;
				}
				bos.flush();
				bos.close();
				if (keyName.endsWith("xlsx") || keyName.endsWith("xls")) {
					excel.put("excel", entryFilePath);
				}
				if (keyName.endsWith("jpg") || keyName.endsWith("JPG")||keyName.endsWith("png") || keyName.endsWith("PNG")) {
					image.put(keyName.substring(0, keyName.indexOf(".")), entryName.replace("\\", "/"));
				}
			}
		}
		if (excel.isEmpty() || image.isEmpty()) {
			return resutl;
		}
		resutl.put("excel", excel);
		resutl.put("image", image);
		return resutl;
	}

	/**
	 * 递归压缩文件夹
	 * 
	 * @param srcRootDir
	 *            压缩文件夹根目录的子路径
	 * @param file
	 *            当前递归压缩的文件或目录对象
	 * @param zos
	 *            压缩文件存储对象
	 * @throws Exception
	 */
	private static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception {
		if (file == null) {
			return;
		}

		// 如果是文件，则直接压缩该文件
		if (file.isFile()) {
			int count, bufferLen = 1024;
			byte data[] = new byte[bufferLen];

			// 获取文件相对于压缩文件夹根目录的子路径
			String subPath = file.getAbsolutePath();
			int index = subPath.indexOf(srcRootDir);
			if (index != -1) {
				subPath = subPath.substring(srcRootDir.length() + File.separator.length());
			}
			ZipEntry entry = new ZipEntry(subPath);
			zos.putNextEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			while ((count = bis.read(data, 0, bufferLen)) != -1) {
				zos.write(data, 0, count);
			}
			bis.close();
			zos.closeEntry();
		}
		// 如果是目录，则压缩整个目录
		else {
			// 压缩目录中的文件或子目录
			File[] childFileList = file.listFiles();
			for (int n = 0; n < childFileList.length; n++) {
				childFileList[n].getAbsolutePath().indexOf(file.getAbsolutePath());
				zip(srcRootDir, childFileList[n], zos);
			}
		}
	}

	/**
	 * s 压缩文件
	 * 
	 * @param srcFilePath
	 *            压缩源路径
	 * @param destFilePath
	 *            压缩目的路径
	 */
	public static void compress(String srcFilePath, String destFilePath) {
		//
		File src = new File(srcFilePath);

		if (!src.exists()) {
			throw new RuntimeException(srcFilePath + "不存在");
		}
		File zipFile = new File(destFilePath);

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			String baseDir = "";
			compressbyType(src, zos, baseDir);
			zos.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	/**
	 * 按照原路径的类型就行压缩。文件路径直接把文件压缩，
	 * 
	 * @param src
	 * @param zos
	 * @param baseDir
	 */
	private static void compressbyType(File src, ZipOutputStream zos, String baseDir) {

		if (!src.exists())
			return;
		System.out.println("压缩路径" + baseDir + src.getName());
		// 判断文件是否是文件，如果是文件调用compressFile方法,如果是路径，则调用compressDir方法；
		if (src.isFile()) {
			// src是文件，调用此方法
			compressFile(src, zos, baseDir);

		} else if (src.isDirectory()) {
			// src是文件夹，调用此方法
			compressDir(src, zos, baseDir);

		}

	}

	/**
	 * 压缩文件
	 */
	private static void compressFile(File file, ZipOutputStream zos, String baseDir) {
		if (!file.exists())
			return;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(baseDir + file.getName());
			zos.putNextEntry(entry);
			int count;
			byte[] buf = new byte[1024];
			while ((count = bis.read(buf)) != -1) {
				zos.write(buf, 0, count);
			}
			bis.close();

		} catch (Exception e) {
			// TODO: handle exception

		}
	}

	/**
	 * 压缩文件夹
	 */
	private static void compressDir(File dir, ZipOutputStream zos, String baseDir) {
		if (!dir.exists())
			return;
		File[] files = dir.listFiles();
		if (files.length == 0) {
			try {
				zos.putNextEntry(new ZipEntry(baseDir + dir.getName() + File.separator));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (File file : files) {
			compressbyType(file, zos, "");
		}
	}

	public static void main(String[] args) {
		String zipFilePath = "D:/测试照片/批量上传压缩包/card.zip";
		String num = UUID.randomUUID().toString();
		String unzipFilePath = "D:/测试照片/" + num;
		try {
			Map<String, Map<String, String>> unzip = unzip(zipFilePath, unzipFilePath, false);
			System.out.println(JSONObject.toJSON(unzip));
			Map<String, String> map = unzip.get("image");
			String string = map.get("142625199209264321");
			System.out.println("indexOf" + string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

