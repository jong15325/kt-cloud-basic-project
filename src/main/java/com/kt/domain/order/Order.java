package com.kt.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.Local;

import com.kt.common.BaseEntity;
import com.kt.domain.orderproduct.OrderProduct;
import com.kt.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {
	private String receiverName;
	private String receiverAddress;
	private String receiverMobile;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	private LocalDateTime deliveredAt;

	// 연관관계
	// 주문 <-> 회원
	// N : 1 => 다대일
	// ManyToOne
	// FK => 많은 쪽에 생김
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "order")
	private List<OrderProduct> orderProducts = new ArrayList<>();

	//하나의 오더는 여러개의 상품을 가질수있음
	// 1:N
	//하나의 상품은 여러개의 오더를 가질수있음
	// 1:N

	public Order(String receiverName, String receiverAddress, String receiverMobile, OrderStatus status,
			LocalDateTime deliveredAt, User user, List<OrderProduct> orderProducts, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.receiverMobile = receiverMobile;
		this.status = status;
		this.deliveredAt = deliveredAt;
		this.user = user;
		this.orderProducts = orderProducts;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public void updateStatus(OrderStatus status) {
		this.status = status;
	}

	public void update(String receiverName, String receiverAddress, String receiverMobile) {
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.receiverMobile = receiverMobile;
	}
}
