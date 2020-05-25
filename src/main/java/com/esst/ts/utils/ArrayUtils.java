package com.esst.ts.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * 集合处理
 * SHY
 */
public class ArrayUtils {
	private static final Logger log = LoggerFactory.getLogger("");
	/**
	 * 求两个数组的差集
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static String[] substract(String[] arr1, String[] arr2) {
		LinkedList<String> list = new LinkedList<String>();
		for (String str : arr1) {
			if (!list.contains(str)) {
				list.add(str);
			}
		}
		for (String str : arr2) {
			if (list.contains(str)) {
				list.remove(str);
			}
		}
		String[] result = {};
		return list.toArray(result);
	}

	/**
	 * 去除数组中的空值
	 * 
	 * @param strArray
	 * @return
	 */
	public static String[] removeArrayEmptyTextBackNewArray(String[] strArray) {
		List<String> strList = Arrays.asList(strArray);
		List<String> strListNew = new ArrayList<>();
		for (int i = 0; i < strList.size(); i++) {
			if (strList.get(i) != null && !strList.get(i).equals("")) {
				strListNew.add(strList.get(i));
			}
		}
		String[] strNewArray = strListNew.toArray(new String[strListNew.size()]);
		return strNewArray;
	}

	/**
	 * 检查字符串中是否包含参数
	 * @param arrayStr
	 * @param checkParam
	 * @return
	 */
	public static boolean checkParamIsInStr(String arrayStr,String checkParam){
        String[] split = arrayStr.split(",");
        log.info("split:" + JSON.toJSONString(split));
        return Arrays.asList(split).contains(checkParam.toString());
	}

	/**
	 * 检查字符串中是否包含参数 arrayStr为空是跳过
	 * @param arrayStr
	 * @param checkParam
	 * @return
	 */
	public static boolean checkParamIsInStr2(String arrayStr,String checkParam){
		if (StringUtils.isNotEmpty(arrayStr)) {
			String[] split = arrayStr.split(",");
			log.info("split:" + JSON.toJSONString(split));
			return Arrays.asList(split).contains(checkParam.toString());
		}
		return true;
	}
}
