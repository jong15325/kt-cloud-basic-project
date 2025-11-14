package com.kt.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.common.Preconditions;
import com.kt.domain.product.Product;
import com.kt.domain.product.ProductStatus;
import com.kt.dto.product.ProductCreateReqeust;
import com.kt.dto.product.ProductUpdateReqeust;
import com.kt.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

/**
 *packageName    : com.kt.service
 * fileName       : ProductService
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

	/**
	 * 상품 생성
	 * @param request
	 */
	public void create(ProductCreateReqeust request) {
		var newProduct = new Product(
			request.name(),
			request.price(),
			request.stockQuantity(),
			ProductStatus.ACTIVE,
			LocalDateTime.now(),
			LocalDateTime.now()
		);

		productRepository.save(newProduct);
	}

	/**
	 * 상품 수정
	 * @param id
	 * @param request
	 */
	public void update(Long id, ProductUpdateReqeust request) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		product.update(
			request.name(),
			request.price(),
			request.stockQuantity()
		);
	}

	/**
	 * 상품 삭제
	 * @param id
	 */
	public void delete(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		productRepository.delete(product);
	}

	/**
	 * 상품 검색
	 * @param pageable
	 * @param keyword
	 * @return 키워드 및 page, size에 해당하는 상품 리스트 반환
	 */
	public Page<Product> search(Pageable pageable, String keyword) {
		return productRepository.findAllByNameContaining(keyword, pageable);
	}

	/**
	 * 상품 상세
	 * @param id
	 * @return
	 */
	public Product detail(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
	}

	/**
	 * 상품 활성화
	 * @param id
	 */
	public void active(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		product.active();
	}

	/**
	 * 상품 비활성화
	 * @param id
	 */
	public void inActive(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		product.inActive();
	}

	/**
	 * 상품 품절
	 * @param id
	 */
	public void soldOut(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		product.soldOut();
	}

	/**
	 * 상품 삭제(소프트)
	 * @param id
	 */
	public void softDelete(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		product.delete();
	}

	public void restore(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		product.restore();
	}

	/**
	 * 재고 감소
	 * @param productId
	 * @param quantity
	 */
	public void decreaseStock(Long productId, long quantity) {
		var product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		//TODO :
		// 강사님 tip : 서비스에서 할건지, 도메인에서 할건지, DB에서 할건지 책임을 정하는데 지금의 나라면 도메인에서 할 것

		product.decreaseStock(quantity);
	}

	/**
	 * 재고 증가
	 * @param productId
	 * @param quantity
	 */
	public void increaseStock(Long productId, long quantity) {
		var product = productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

		product.increaseStock(quantity);
	}
}
