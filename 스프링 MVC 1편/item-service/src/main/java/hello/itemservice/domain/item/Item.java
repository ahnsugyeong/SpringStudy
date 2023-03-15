package hello.itemservice.domain.item;

import lombok.Data;

@Data   // 주요 logic에 @Data 쓰는 것 위험.
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

}
