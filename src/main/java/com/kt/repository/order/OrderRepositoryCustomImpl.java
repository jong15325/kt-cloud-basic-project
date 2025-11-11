package com.kt.repository.order;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.kt.domain.order.QOrder;
import com.kt.domain.orderproduct.QOrderProduct;
import com.kt.domain.product.QProduct;
import com.kt.dto.order.OrderResponse;
import com.kt.dto.order.QOrderResponse_Search;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

/**
 *packageName    : com.kt.repository.order
 * fileName       : OrderRepositoryCustomImpl
 * author         : howee
 * date           : 2025-11-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-11-11        howee       최초 생성
 */

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	private final QOrder order = QOrder.order;
	private final QOrderProduct orderProduct = QOrderProduct.orderProduct;
	private final QProduct product = QProduct.product;

	private BooleanExpression containsProductName(String keyword) {
		// if(Strings.isNotBlank(keyword)) {
		// 	return product.name.containsIgnoreCase(keyword);
		// } else {
		// 	return null;
		// }
		return Strings.isNotBlank(keyword) ? product.name.containsIgnoreCase(keyword) : null;
	}

	@Override
	public Page<OrderResponse.Search> search(String keyword, Pageable pageable) {

		var booleanBuilder = new BooleanBuilder();

		booleanBuilder.and(containsProductName(keyword));
		// booleanBuilder.and()
		// booleanBuilder.or()
		// booleanBuilder안에다가 booleanExpression을 추가해주는 방식으로

		var content = jpaQueryFactory
			.select(new QOrderResponse_Search(
				order.id,
				order.receiverName,
				product.name,
				orderProduct.quantity,
				product.price.multiply(orderProduct.quantity),
				order.status,
				order.createdAt
			))
			.from(order)
			.join(orderProduct).on(orderProduct.order.id.eq(order.id))
			.join(product).on(orderProduct.product.id.eq(product.id))
			.where(booleanBuilder)
			.orderBy(order.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch(); // 이 순간 SELECT 쿼리가 DB로 전송됨

		// 최초에 페이지 접근했을때 -> 전체검색이 되야할까 아니면 특정키워드검색이 자동으로 되야하나 -> 최초 접근은 전체 리스트 반환해야함
		// name like '%null%' (동작 해야하나?) - 동작안해야
		// keyword = null

		var total = (long)jpaQueryFactory.select(order.id)
			.from(order)
			.join(orderProduct).on(orderProduct.order.id.eq(order.id))
			.join(product).on(orderProduct.product.id.eq(product.id))
			.where(booleanBuilder)
			.fetch().size();

		return new PageImpl<>(content, pageable, total); // 인터페이스로 반환 -> 구현체를 바꿔도 호출하는 쪽은 영향 없음
	}
}
