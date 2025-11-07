package com.kt.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.kt.domain.order.OrderStatus;
import com.kt.domain.orderproduct.OrderProduct;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 *packageName    : com.kt.dto
 * fileName       : OrderCreateRequest
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */
public record OrderCreateRequest (
	@NotNull
	Long receiverId,
	@NotBlank
	String receiverAddress,
	@NotEmpty
	List<OrderProductReqeust> orderProducts
	){
}
