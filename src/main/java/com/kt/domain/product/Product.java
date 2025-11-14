package com.kt.domain.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kt.common.BaseEntity;
import com.kt.common.ErrorCode;
import com.kt.common.Preconditions;
import com.kt.domain.orderproduct.OrderProduct;

import jakarta.persistence.Column;
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

	@Column(nullable = false)
	boolean isDeleted;

	@OneToMany(mappedBy = "product")
	private List<OrderProduct> orderProducts = new ArrayList<>();

	public Product(String name, long price, long stock, ProductStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.status = status;
		this.isDeleted = false;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	/**
	 * 상품 정보 변경
	 * @param name
	 * @param price
	 * @param stock
	 */
	public void update(String name, long price, long stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	/**
	 * 품절 처리
	 */
	public void soldOut()	{
		this.status = ProductStatus.SOLD_OUT;
	}

	/**
	 * 품절 해제
	 */
	public void active()	{
		this.status = ProductStatus.SOLD_OUT;
	}

	/**
	 * 비활성화 처리
	 */
	public void inActive()	{
		this.status = ProductStatus.INACTIVE;
	}

	/**
	 * 상품 삭제 (소프트)
	 */
	public void delete() {
		this.isDeleted = true;
	}

	public void restore() {
		this.isDeleted = false;
	}

	/**
	 * 재고 수량 감소
	 * @param quantity
	 */
	public long decreaseStock(long quantity) {

		// 양수 검증
		Preconditions.checkPositive(quantity, ErrorCode.INVALID_QUANTITY);

		this.stock = canProvide(quantity) ? this.stock - quantity : 0;

		return this.stock;
	}

	/**
	 * 재고 수량 증가
	 * @param quantity
	 */
	public long increaseStock(long quantity) {

		// 양수 검증
		Preconditions.checkPositive(quantity, ErrorCode.INVALID_QUANTITY);

		return this.stock += quantity;
	}

	/**
	 * 재고 수량 제공 가능 여부
	 * @param quantity
	 * @return
	 */
	public boolean canProvide(long quantity) {
		return this.stock >= quantity;
	}

	/**
	 * 재고	수량 초기화
	 */
	public void resetStock() {
		this.stock = 0;
	}

}
