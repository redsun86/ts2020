package com.esst.ts.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

/**
 * base64转换工具
 * SHY
 */
public class FileBase64ConvertUitl {
	/**
	 * 将文件转成base64 字符串
	 * 
	 * @param path 文件路径
	 * @return
	 * @throws Exception
	 */

	public static String encodeBase64File(String path) {
		byte[] buffer =null;
		try {

		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new String(Base64.getEncoder().encode(buffer));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 将文件转成二进制数组
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 * @throws Exception
	 */

	public static byte[] fileToByteArray(String path) {
		byte[] buffer = null;
		try {
			File file = new File(path);
			FileInputStream inputFile = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 将base64字符解码保存文件
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */

	public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
		FileOutputStream out = null;
		try {
			byte[] buffer = Base64.getDecoder().decode(base64Code);
			out = new FileOutputStream(targetPath);
			out.write(buffer);
		} finally {
			out.close();
		}
	}

	/**
	 * 将base64字符保存文本文件
	 * 
	 * @throws Exception
	 */

	public static void toFile(String base64Code, String targetPath) throws Exception {
		byte[] buffer = base64Code.getBytes();
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}

	public static void main(String[] args) {
		try {
			String base64Code = encodeBase64File("D:/测试照片/1524382310158.jpg");
			System.out.println(encodeBase64File("D:/测试照片/1524382310158.jpg"));
			// System.out.println(encodeBase64File("D:/测试照片/生活照.jpg"));
			toFile(base64Code, "E:\\three.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
