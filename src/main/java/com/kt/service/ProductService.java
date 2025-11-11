package com.kt.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
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

	public void update(Long id, ProductUpdateReqeust request) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		product.update(
			request.name(),
			request.price(),
			request.stockQuantity()
		);
	}

	public void delete(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		productRepository.delete(product);
	}

	public Page<Product> search(Pageable pageable, String keyword) {
		return productRepository.findAllByNameContaining(keyword, pageable);
	}

	public Product detail(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
	}

	public void changeStatus(Long id, ProductStatus status) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

		product.changeStatus(status);
	}


	/*	public void adjusStockQuantity(Long productId, long quantity, String option) {
			var product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

			var productStock = product.getStock();

			if(option.equals("increase")) {
				productStock = productStock + quantity;
			} else {
				productStock = productStock - quantity;

				if (productStock < quantity) {
					productStock = 0;
				}

			}

			product.update(
				product.getName(),
				product.getPrice(),
				productStock
			);
		}*/

	public void decreaseStock(Long productId, long quantity) {
		var product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));


		//TODO :
		// 강사님 tip : 서비스에서 할건지, 도메인에서 할건지, DB에서 할건지 책임을 정하는데 지금의 나라면 도메인에서 할 것
		var productStock = product.getStock();

		if (productStock < quantity) {
			productStock = 0;
		} else {
			productStock = productStock - quantity;
		}

		product.update(
			product.getName(),
			product.getPrice(),
			productStock
		);
	}

	public void increaseStock(Long productId, long quantity) {
		var product = productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

		product.update(
			product.getName(),
			product.getPrice(),
			product.getStock() + quantity
		);
	}
}
