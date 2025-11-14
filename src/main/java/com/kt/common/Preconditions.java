package com.kt.common;

/**
 *packageName    : com.kt.common
 * fileName       : Preconditions
 * author         : howee
 * date           : 2025-11-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-07        howee       최초 생성
 */
public class Preconditions {
	public static void validate(boolean expression, ErrorCode errorCode) {
		if (!expression) {
			throw new CustomException(errorCode);
		}
	}

	/**
	 * 양수 검증
	 * @param value
	 * @param errorCode
	 */
	public static void checkPositive(long value, ErrorCode errorCode) {
		if (value <= 0) {
			throw new CustomException(errorCode);
		}
	}

	/**
	 * 최소값 검증
	 * @param value
	 * @param min
	 * @param errorCode
	 */
	public static void checkMinimum(long value, long min, ErrorCode errorCode) {
		if (value < min) {
			throw new CustomException(errorCode);
		}
	}

	/**
	 * 최대값 검증
	 * @param value
	 * @param max
	 * @param errorCode
	 */
	public static void checkMaximum(long value, long max, ErrorCode errorCode) {
		if (value > max) {
			throw new CustomException(errorCode);
		}
	}
}
