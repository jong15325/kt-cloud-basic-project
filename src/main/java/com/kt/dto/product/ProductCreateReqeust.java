package com.kt.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *packageName    : com.kt.dto
 * fileName       : ProductCreateReqeust
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */
public record ProductCreateReqeust (
	@NotBlank
	String name,
	String description,
	@NotNull // blank는 문자열에만 해당됨
	@Min(value = 0)
	long price,
	@NotNull
	@Min(value = 0)
	long stockQuantity
){
}
