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
}
