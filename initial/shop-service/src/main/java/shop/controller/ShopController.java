package shop.controller;

import org.springframework.web.bind.annotation.*;
import shop.dto.Cart;
import shop.dto.TxnType;
import shop.service.ShopService;

import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping(path="/purchase", produces = "application/json")
    public Cart purchaseCart(@RequestParam Map<String, String> items) {
        boolean allowed = shopService.canTransactItems(TxnType.purchase, items);
        if(!allowed) {
            return Cart.builder().
                    txnType(TxnType.purchase).
                    desc("at least one item is low on stock").
                    build();
        }
        return shopService.transactItems(TxnType.purchase, items, -1);
    }

    @GetMapping(path="/refund/{id}", produces = "application/json")
    public Cart refundCart(@PathVariable int id, @RequestParam Map<String, String> items) {
        boolean allowed = shopService.canTransactItems(TxnType.refund, items);
        if(!allowed) {
            return Cart.builder().
                    txnType(TxnType.refund).
                    desc("at least one item is ineligible for return").
                    build();
        }
        return shopService.transactItems(TxnType.refund, items, id);
    }
}
