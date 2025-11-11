package com.kt.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.domain.product.Product;

/**
 *packageName    : com.kt.repository
 * fileName       : ProductRepository
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */

public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * 상품명으로 존재 여부 확인
	 * @param name
	 * @return
	 */
	boolean existsByName(String name);

	/**
	 * 상품명으로 페이징 조회
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<Product> findAllByNameContaining(String name, Pageable pageable);

}
