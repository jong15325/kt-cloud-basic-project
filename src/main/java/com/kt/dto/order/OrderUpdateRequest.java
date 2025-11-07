package com.kt.dto.order;

import com.kt.domain.order.OrderStatus;

import jakarta.validation.constraints.NotBlank;

/**
 *packageName    : com.kt.dto
 * fileName       : OrderUpdateRequest
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */
public record OrderUpdateRequest(
	@NotBlank
	String receiverName,
	@NotBlank
	String receiverAddress,
	@NotBlank
	String receiverMobile,
	OrderStatus status
) {
}
