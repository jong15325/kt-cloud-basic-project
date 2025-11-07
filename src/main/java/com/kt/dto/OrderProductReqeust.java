package com.kt.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 *packageName    : com.kt.dto
 * fileName       : OrderProductReqeust
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */
public record OrderProductReqeust(
	@NotNull
	Long productId,

	@NotNull
	@Min(1)
	Long quantity
) {
}
