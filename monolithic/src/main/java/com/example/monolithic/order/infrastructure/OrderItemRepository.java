package com.example.monolithic.order.infrastructure;

import com.example.monolithic.order.domain.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  List<OrderItem> findAllByOrderId(Long orderId);
}
