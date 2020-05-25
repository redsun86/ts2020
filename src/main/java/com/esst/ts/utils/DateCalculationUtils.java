package com.esst.ts.utils;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间计算工具类
 * SHY
 */
public class DateCalculationUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * @Title: getDatesBetweenTwoDate @Description: TODO(获取两个日期内的所有时间) @param
	 * startTime 开始日期 @param endTime 结束日期 @return @throws Exception
	 * List<String> @throws
	 */

	private static List<String> getDatesBetweenTwoDate(String startTime, String endTime) throws Exception {
		Date beginDate = sdf.parse(startTime);
		Date endDate = sdf.parse(endTime);
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);
		// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		// 把结束时间加入集合
		lDate.add(endDate);
		List<String> list = new ArrayList<>();
		for (Date date : lDate) {
			list.add(sdf.format(date));
		}
		return list;
	}


	private static List<String> getDatesBeforeToday(String endTime, int dateTimeLength) throws Exception {

		Date endDate = sdf.parse(endTime);
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(endDate);
		// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(endDate);
		for (int i = 0; i < dateTimeLength; i++) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
			lDate.add(cal.getTime());
		}
		List<String> list = new ArrayList<>();
		for (int i = lDate.size() - 1; i >= 0; i--) {
			list.add(sdf.format(lDate.get(i)));

		}
		return list;
	}

	private static List<String> getDatesBeforeToday(Date endTime, int dateTimeLength) throws Exception {
		String endDate = sdf.format(endTime);
		return getDatesBeforeToday(endDate, dateTimeLength);
	}

	private static List<String> getDatesAfterToday(String startTime, int dateTimeLength) throws Exception {
		Date endDate = sdf.parse(startTime);
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(endDate);
		// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(endDate);
		for (int i = 0; i < dateTimeLength; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(cal.getTime());
		}
		List<String> list = new ArrayList<>();
		for (Date date : lDate) {
			list.add(sdf.format(date));
		}
		return list;
	}

	public static List<String> getTimeList(String startTime, String endTime, int dateTimeLength) {
		List<String> list = new ArrayList<String>();
		try {
			if (StringUtils.isEmpty(endTime) && StringUtils.isEmpty(startTime)) {
				Date date = new Date();
				list = getDatesBeforeToday(date, dateTimeLength);
			} else if (StringUtils.isEmpty(startTime)) {
				list = getDatesBeforeToday(endTime, dateTimeLength);
			} else if (StringUtils.isEmpty(endTime)) {
				Date nowDate = new Date();
				list = getDatesBetweenTwoDate(startTime, sdf.format(nowDate));
				if (list.size() > dateTimeLength) {
					list = getDatesAfterToday(startTime, dateTimeLength);
				}
			} else {
				list = getDatesBetweenTwoDate(startTime, endTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(JSON.toJSON(getTimeList("2019-03-11", "", 7)));
		System.out.println(JSON.toJSON(getTimeList("2019-06-20", "", 7)));
	}
}
