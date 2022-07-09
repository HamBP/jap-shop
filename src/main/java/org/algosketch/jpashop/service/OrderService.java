package org.algosketch.jpashop.service;

import lombok.RequiredArgsConstructor;
import org.algosketch.jpashop.domain.Delivery;
import org.algosketch.jpashop.domain.Member;
import org.algosketch.jpashop.domain.Order;
import org.algosketch.jpashop.domain.OrderItem;
import org.algosketch.jpashop.domain.item.Item;
import org.algosketch.jpashop.repository.ItemRepository;
import org.algosketch.jpashop.repository.MemberRepository;
import org.algosketch.jpashop.repository.OrderRepository;
import org.algosketch.jpashop.repository.OrderSearch;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
