package com.kt.domain.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kt.common.BaseEntity;
import com.kt.domain.orderproduct.OrderProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor // JPA에서는 생성자 파라미터를 알 수 없고 어떤 파라미터를 받는지 모름
// 결국엔 Product product = new Product() 이런식으로 호출하기 때문에 기본생성자가 필요
public class Product extends BaseEntity {
	private String name;
	private long price;
	private long stock;
	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@OneToMany(mappedBy = "product")
	private List<OrderProduct> orderProducts = new ArrayList<>();

	public Product(String name, long price, long stock, ProductStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public void changeStatus(ProductStatus status) {
		this.status = status;
	}

	public void update(String name, long price, long stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}


}
