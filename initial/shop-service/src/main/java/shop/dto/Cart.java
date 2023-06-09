package shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private int id;
    private Date txnDate;
    private TxnType txnType;
    private Integer customerId;
    private List<Item> items = new ArrayList<>();
    private Double totalCost = 0D;
    private String desc;

    public List<Item> addItem(Item item) {
        items.add(item);
        return items;
    }
}
