package com.kt.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.domain.order.Order;

/**
 *packageName    : com.kt.repository
 * fileName       : OrderRepository
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
	// 1. 네이티브쿼리로 작성
	// 2. jqpl로 작성
	// 3. 쿼리메소드로 어찌저찌 작성
	// 4. 조회할때는 동적쿼리를 작성하게해줄 수 있는 querydsl 사용하자
}
