package shop.dto;

import lombok.Data;

import java.util.*;

@Data
public class Cart {

    private List<Stock> items = new ArrayList<>();

    //todo put these in a customer/purchase DTO
    private Date transactionDate;
    private Integer customerId;
    private Integer salesPerson;

    public List<Stock> addItem(Stock stock) {
        items.add(stock);
        return items;
    }
}
