package com.esst.ts.utils;

import java.io.IOException;
import java.net.InetAddress;

public class IpUtils {
    /**
     * 获取服务器IP地址
     */
    public static String getServerIp() throws IOException {
        String ip="";
        if(InetAddress.getLocalHost().getHostAddress().length()>0){
            ip = InetAddress.getLocalHost().getHostAddress();
        }
        return ip;
    }
}
