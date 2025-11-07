package com.kt.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.domain.product.Product;
import com.kt.dto.ProductCreateReqeust;
import com.kt.dto.ProductUpdateReqeust;
import com.kt.dto.UserCreateRequest;
import com.kt.dto.UserUpdatePasswordRequest;
import com.kt.service.ProductService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 *packageName    : com.kt.controller
 * fileName       : ProductController
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */

@Tag(name = "상품", description = "상품 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@ApiResponses(value = {
	@ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
	@ApiResponse(responseCode = "500", description = "서버 에러 - 백엔드에 바로 문의 바랍니다.")
})
public class ProductController {

	private final ProductService productService;

	//생성
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody ProductCreateReqeust request) {
		productService.create(request);
	}

	//수정
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(
		@PathVariable Long id,
		@RequestBody @Valid ProductUpdateReqeust request
	) {
		productService.update(id, request);
	}

	//삭제
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		productService.delete(id);
	}

	//조회(리스트)
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<Product> search(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false) String keyword) {

		return productService.search(PageRequest.of(page - 1, size), keyword);
	}

	//조회(단건)
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Product detail(@PathVariable Long id) {
		return productService.detail(id);
	}

	//상태변경
	@PutMapping("/{id}/update-status")
	@ResponseStatus(HttpStatus.OK)
	public void updateStatus(
		@PathVariable Long id,
		@RequestBody @Valid ProductUpdateReqeust request
	) {
		productService.changeStatus(id, request.status());
	}

	//재고수량변경
	//이렇게하면 restful하지 않음
	/*@PutMapping("/{id}/adjus-stock-quantity")
	@ResponseStatus(HttpStatus.OK)
	public void adjusStockQuantity(
		@PathVariable Long id,
		@RequestBody @Valid ProductUpdateReqeust request,
		@RequestBody String option
	) {
		productService.adjusStockQuantity(id, request.stockQuantity(), option);
	}*/

	//재고수량감소
	@PutMapping("/{id}/decrease-stock")
	@ResponseStatus(HttpStatus.OK)
	public void decreaseStock(
		@PathVariable Long id,
		@RequestBody @Valid ProductUpdateReqeust request
	) {
		productService.decreaseStock(id, request.stockQuantity());
	}


	//재고수량증가
	@PutMapping("/{id}/increase-stock")
	@ResponseStatus(HttpStatus.OK)
	public void increaseStock(
		@PathVariable Long id,
		@RequestBody @Valid ProductUpdateReqeust request
	) {
		productService.increaseStock(id, request.stockQuantity());
	}

}
