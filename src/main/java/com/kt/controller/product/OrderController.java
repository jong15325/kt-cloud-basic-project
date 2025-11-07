package com.kt.controller.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.dto.order.OrderCreateRequest;
import com.kt.dto.order.OrderUpdateRequest;
import com.kt.service.OrderService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 *packageName    : com.kt.controller
 * fileName       : OrderController
 * author         : howee
 * date           : 2025-11-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-06        howee       최초 생성
 */


@Tag(name = "주문", description = "주문 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@ApiResponses(value = {
	@ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
	@ApiResponse(responseCode = "500", description = "서버 에러 - 백엔드에 바로 문의 바랍니다.")
})
public class OrderController {

	private final OrderService orderService;

	// 주문생성 +
	// 주문생성완료재고차감
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody OrderCreateRequest reqeust) {
		orderService.create(reqeust);
	}

	// 주문상태변경
	@PutMapping("/{id}/update-status")
	@ResponseStatus(HttpStatus.OK)
	public void updateStatus(
		@PathVariable Long id,
		@RequestBody @Valid OrderUpdateRequest request
	) {
		orderService.updateStatus(id, request.status());
	}

	// 배송받는사람정보변경
	@PutMapping("/{id}/update-info")
	@ResponseStatus(HttpStatus.OK)
	public void update(
		@PathVariable Long id,
		@RequestBody @Valid OrderUpdateRequest request
	) {
		orderService.update(id,request);
	}



	// 주문취소
	@DeleteMapping("/{id}/")
	@ResponseStatus(HttpStatus.OK)
	public void delete(
		@PathVariable Long id
	) {
		orderService.delete(id);
	}




}
