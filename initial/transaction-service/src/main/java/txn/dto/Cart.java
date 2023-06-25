package txn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private int id;
    private Date txnDate;
    private TxnType txnType;
    private Integer customerId;
    private List<Item> items;
    private Double totalCost;
    private String desc;

    public List<Item> addItem(Item item) {
        items.add(item);
        return items;
    }
}
