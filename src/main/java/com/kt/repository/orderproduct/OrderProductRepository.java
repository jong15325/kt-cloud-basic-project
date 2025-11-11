package com.kt.repository.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.domain.orderproduct.OrderProduct;

/**
 *packageName    : com.kt.repository.orderproduct
 * fileName       : OrderProduct
 * author         : howee
 * date           : 2025-11-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-11        howee       최초 생성
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
