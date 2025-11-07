package com.kt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.domain.order.Order;
import com.kt.domain.product.Product;

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

public interface OrderRepository extends JpaRepository<Order, Long> {

}
