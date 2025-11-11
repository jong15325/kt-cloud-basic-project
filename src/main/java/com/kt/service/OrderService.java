package com.kt.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.domain.order.Order;
import com.kt.domain.order.OrderStatus;
import com.kt.domain.orderproduct.OrderProduct;
import com.kt.domain.product.Product;
import com.kt.dto.order.OrderCreateRequest;
import com.kt.dto.order.OrderProductReqeust;
import com.kt.dto.order.OrderUpdateRequest;
import com.kt.repository.order.OrderRepository;
import com.kt.repository.orderproduct.OrderProductRepository;
import com.kt.repository.product.ProductRepository;
import com.kt.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 *packageName    : com.kt.service
 * fileName       : OrderService
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
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderProductRepository orderProductRepository;

	public void create(OrderCreateRequest request) {
		var user = userRepository.findById(request.receiverId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		System.out.println(request.orderProducts().size());

		//재고 확인 및 차감
		List<Long> productIds = request.orderProducts().stream()
			.map(OrderProductReqeust::productId)
			.toList();
		System.out.println(productIds);

		List<Product> products = productRepository.findAllById(productIds);
		System.out.println(Arrays.toString(products.toArray()));

		Map<Long, Product> productMap = new HashMap<>();
		for (var product : products) {
			productMap.put(product.getId(), product);
		}

		var newOrder = new Order(
			user.getName(),
			request.receiverAddress(),
			user.getMobile(),
			OrderStatus.PENDING,
			null,
			user,
			LocalDateTime.now(),
			LocalDateTime.now()
		);

		var order = orderRepository.save(newOrder);/*
		var orderProduct = orderProductRepository.save(new OrderProduct(order, ));*/

		//TODO: 추후 bulk로 처리해도 될 듯(생각 해봐야 할 것은 bulk로 처리 시 영속성 문제와 성능 이슈 없을지 고려)
		/*for (var product : products) {
			orderProductRepository.save(new OrderProduct(order, product, product.getStock()));
		}*/

		List<OrderProduct> orderProducts = request.orderProducts().stream()
			.map(req -> {
				Product product = productMap.get(req.productId());

				return new OrderProduct(order, product, req.quantity());

			})
			.toList();

		// 이런게 있네~
		orderProductRepository.saveAll(orderProducts);

	}

	public void updateStatus(Long id, OrderStatus status) {
		var order = orderRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));

		order.updateStatus(status);

		//todo: 주문 상태에 따른 추가 로직 구현
		/*PENDING("결제대기"),
			COMPLETED("결제완료"),
			CANCELLED("주문취소"),
			SHIPPED("배송중"),
			DELIVERED("배송완료"),
			CONFIRMED("구매확정");*/
	}

	public void update(Long id, OrderUpdateRequest request) {
		var order = orderRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));

		order.update(
			request.receiverName(),
			request.receiverAddress(),
			request.receiverMobile()
		);
	}

	public void delete(Long id) {
		orderRepository.deleteById(id);
	}

}
