package com.esst.ts.utils;

import com.esst.ts.model.User;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;

public class WriteLogUtils {
    /**
     * 写到文件
     */
    public static String writeToResource(String contents) throws IOException {
        File checkFile = new File(getResourceBasePath() + "/userlog.txt");
        if (!checkFile.exists()) {
            checkFile.createNewFile();// 创建目标文件
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(checkFile)));
        writer.write(contents.toString());
        writer.flush();
        writer.close();
        return  getResourceBasePath() + "/userlog.txt";
    }

    /**
     * 获取项目根路径
     *
     * @return
     */
    private static String getResourceBasePath() {
        // 获取跟目录
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // nothing to do
        }
        if (path == null || !path.exists()) {
            path = new File("");
        }
        String pathStr = path.getAbsolutePath();
        // 如果是在eclipse中运行，则和target同级目录,如果是jar部署到服务器，则默认和jar包同级
        pathStr = pathStr.replace("\\target\\classes", "");
        return pathStr;
    }
}
