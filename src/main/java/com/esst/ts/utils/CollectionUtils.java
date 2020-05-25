package com.esst.ts.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.util.*;

/**
 *  集合工具类
 *  SHY
 */
public class CollectionUtils {
	/**
	 * 转换Collection所有元素(通过toString())为String,
	 * 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
	 */
	public static String convertToString(final Collection collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Collection collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * 取得Collection的第一个元素，如果collection为空返回null.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 获取Collection的最后一个元素 ，如果collection为空返回null.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		// 当类型为List时，直接取得最后一个元素 。
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		// 其他类型通过iterator滚动到最后一个元素.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 返回a+b的新List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}

	/**
	 * 警示优先排序
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> sortByPriority(List<Map<String, Object>> list) {
		List<Map<String, Object>> result = null;
		if (!CollectionUtils.isEmpty(list)) {
			result = new ArrayList<>();
			for (Map<String, Object> ele : list) {
				if(ele.containsKey("priority")){
					Boolean priority = (Boolean) ele.get("priority");
					if(priority){
						result.add(ele);
						list.remove(ele);
						break;
					}
				}
			}
			result.addAll(list);
			return result;
		}
		return list;
	}

	public static List<Map<String,Object>> sortBySimilarity(List<Map<String,Object>> jsonValues){
		Collections.sort(jsonValues, new Comparator<Map<String,Object>>() {
			@Override
			public int compare(Map<String,Object> a, Map<String,Object> b) {
				Double valA = 0D;
				Double valB = 0D;
				try {
					valA = Double.parseDouble(a.get("similarity").toString());
					valB =  Double.parseDouble(b.get("similarity").toString());
				} catch (JSONException e) {
					// do something
				}
				return -(valA.compareTo(valB));
			}
		});
		return jsonValues;
	}

	public static List<Map<String,Object>> getTop3(List<Map<String,Object>> result) {
		if(!CollectionUtils.isEmpty(result)){
			for (int i =0 ;i < result.size();i ++){
				if(i>=3){
					result.remove(i);
					i--;
				}
			}
			return result;
		}
		return null;
	}

	public static List<Map<String,Object>> resultSort(List<Map<String,Object>> result) {
		if(!CollectionUtils.isEmpty(result)){
			List<Map<String,Object>> jsonValues = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < result.size(); i++) {
				jsonValues.add(result.get(i));
			}
			jsonValues = CollectionUtils.sortBySimilarity(jsonValues);
			return jsonValues;
		}
		return null;
	}

	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<>(16);
		map.put("faceId",1);
		map.put("similarity",0.9);
		map.put("priority",false);
		Map<String,Object> map1 = new HashMap<>(16);
		map1.put("faceId",1);
		map1.put("similarity",0.87);
		map1.put("priority",true);
		Map<String,Object> map2 = new HashMap<>(16);
		map2.put("faceId",1);
		map2.put("similarity",0.85);
		map2.put("priority",false);
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(map);
		list.add(map1);
		list.add(map2);
		System.out.println(JSON.toJSON(list));
		List<Map<String, Object>> list1 = sortByPriority(list);
		System.out.println(JSON.toJSON(list1));
	}
}
