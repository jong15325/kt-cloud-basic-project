package com.kt.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.order.Order;
import com.kt.domain.order.OrderStatus;
import com.kt.domain.orderproduct.OrderProduct;
import com.kt.domain.product.Product;
import com.kt.dto.order.OrderCreateRequest;
import com.kt.dto.order.OrderProductReqeust;
import com.kt.dto.order.OrderUpdateRequest;
import com.kt.repository.OrderRepository;
import com.kt.repository.ProductRepository;
import com.kt.repository.UserRepository;

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

	public void create(OrderCreateRequest request) {
		var user = userRepository.findById(request.receiverId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

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

		List<OrderProduct> opEntity = request.orderProducts().stream()
			.map(req -> {
				Product product = productMap.get(req.productId());

				return new OrderProduct(req.quantity(), null, product);

			})
			.toList();
		System.out.println(Arrays.toString(opEntity.toArray()));

		var newOrder = new Order(
			user.getName(),
			request.receiverAddress(),
			user.getMobile(),
			OrderStatus.PENDING,
			null,
			user,
			opEntity,
			LocalDateTime.now(),
			LocalDateTime.now()
		);

		orderRepository.save(newOrder);

	}

	public void updateStatus(Long id, OrderStatus status) {
		var order = orderRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

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
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

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
