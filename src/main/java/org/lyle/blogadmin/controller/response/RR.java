package org.lyle.blogadmin.controller.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一API响应结果格式封装
 */
@Data
@Builder
public class RR implements Serializable {

	@Serial
	private static final long serialVersionUID = 6308315887056661996L;
	/**
	 * response timestamp.
	 */
	private long timestamp;
	private Integer code;
	private String message;
	private Object data;

	// 只返回状态
	public static RR success() {
		return success(null);
	}

	public static RR success(Object data) {
		return RR.builder().data(data)
			.message(RC.SUCCESS.getMessage())
			.code(RC.SUCCESS.getCode())
			.timestamp(System.currentTimeMillis())
			.build();
	}


	public static RR fail(String message) {
		return fail(null, message);
	}

	public static RR fail(RC rc) {
		return fail(null, rc.getMessage());
	}


	public static RR fail(Object data, String message) {
		return RR.builder().data(data)
			.message(message)
			.code(RC.FAIL.getCode())
			.timestamp(System.currentTimeMillis())
			.build();
	}

	public static void main(String[] args) {
		System.out.println(fail(RC.FAIL));
	}

}