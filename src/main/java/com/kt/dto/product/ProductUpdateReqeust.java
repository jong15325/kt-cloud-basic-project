package com.kt.dto.product;

import com.kt.domain.product.ProductStatus;

/**
 *packageName    : com.kt.dto
 * fileName       : ProductUpdateStatusReqeust
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */
public record ProductUpdateReqeust (
	String name,
	long price,
	long stockQuantity,
	ProductStatus status
){
}
