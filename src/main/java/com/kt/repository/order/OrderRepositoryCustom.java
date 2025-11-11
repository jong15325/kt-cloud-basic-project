package com.kt.repository.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kt.dto.order.OrderResponse;

/**
 *packageName    : com.kt.repository.order
 * fileName       : OrderRepositoryCustom
 * author         : howee
 * date           : 2025-11-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-11        howee       최초 생성
 */
public interface OrderRepositoryCustom {
	Page<OrderResponse.Search> search(String keyword, Pageable pageable);
}
