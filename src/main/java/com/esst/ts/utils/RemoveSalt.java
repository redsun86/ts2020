package com.esst.ts.utils;

/**
 * 去盐
 * SHY
 */
public class RemoveSalt {

    public static String original(String md5str) {

        int size = md5str.length() / 2;
        char[] a = md5str.substring(size).toCharArray();
        char[] b = md5str.substring(0, size).toCharArray();

        StringBuffer newStr = new StringBuffer();

        for (int i = 0; i < a.length; i++) {
            newStr.append(a[i]);
            if (a.length > b.length && i == a.length - 1) {
                break;
            }
            newStr.append(b[i]);
        }
        return newStr.toString().toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(RemoveSalt.original("E0D34B5AB5E5F0831AC99A9BE6072F8E"));
    }

}
