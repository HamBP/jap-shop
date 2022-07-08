package org.algosketch.jpashop.repository;

import lombok.Getter;
import lombok.Setter;
import org.algosketch.jpashop.domain.OrderStatus;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
