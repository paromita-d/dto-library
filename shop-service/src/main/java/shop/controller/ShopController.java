package shop.controller;

import org.springframework.web.bind.annotation.*;
import shop.dto.Cart;
import shop.service.ShopService;

import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class ShopController {

    private ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping(path="/purchase", produces = "application/json")
    public Cart purchaseCart(@RequestParam Map<String, String> items) {
        return shopService.purchaseItems(items);
    }

    @GetMapping(path="/return", produces = "application/json")
    public Cart returnCart(@RequestParam Map<Integer, Integer> items) {
        return shopService.returnItems(items);
    }
}
