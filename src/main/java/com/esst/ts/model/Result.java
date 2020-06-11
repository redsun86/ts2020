package com.esst.ts.model;

import java.io.Serializable;

/**
 * 响应实体
 */
public class Result implements Serializable {
	private static final long serialVersionUID = -7982820491494624333L;

	public static final int SUCCESS = 0;				//请求成功
	public static final int ERROR = 1;					//请求失败（通用错误）
	public static final int PARAMETER_ERROR = 2;		//无效参数
	public static final int TOKEN_ERROR = 3;			//无效token
	public static final int PERMISSION_ERROR = 4;		//权限错误
	public static final int SERVICE_ERROR = 5;			//服务器繁忙
	public static final int USER_NOT_EXIST = 1001;		//该用户不存在
	public static final int PASSWORD_ERROR = 1002;		//用户密码错误

	private int code;
	private String msg;
	private Object data;

	private Result(Object data) {
		setData(data);
	}

	private Result(int code) {
		setCode(code);
	}

	private Result(String msg) {
		setMsg(msg);
	}

	private Result(String msg, Object data) {
		setMsg(msg);
		setData(data);
	}

	private Result(int code, String msg) {
		setCode(code);
		setMsg(msg);
	}

	private Result(int code, String msg,Object data) {
		setCode(code);
		setMsg(msg);
		setData(data);
	}

	public Result() {
	}

	public static Result success(Object data) {
		return new Result(data);
	}

	public static Result success(int code) {
		return new Result(code);
	}

	public static Result success() {
		return new Result();
	}

	public static Result error() {
		return new Result(ERROR);
	}

	public static Result error(String msg) {
		return Result.success(ERROR, msg);
	}

	public static Result success(String msg) {
		return new Result(msg);
	}

	public static Result success(int code, String msg) {
		return new Result(code, msg);
	}

	public static Result success(String msg, Object data) {
		return new Result(msg, data);
	}

	public static Result error(int code, String msg) {
		return new Result(code, msg);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result commonSuccess(Object data){
		return new Result(0,"请求成功",data);
	};

	public Result commonSuccess(){
		return new Result(0,"请求成功");
	};

	public Result commonFail(Integer code,String msg){
		return new Result(code,msg);
	};

	public Result commonFail(){
		return new Result("-1","系统错误");
	};
}
