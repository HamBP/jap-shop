package org.algosketch.jpashop.domain.item;

import lombok.Getter;
import org.algosketch.jpashop.domain.Category;
import org.algosketch.jpashop.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int resStock = this.stockQuantity - quantity;
        if (resStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = resStock;
    }
}
