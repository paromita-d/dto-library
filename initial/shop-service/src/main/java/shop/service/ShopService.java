package shop.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.dto.Cart;
import shop.dto.Stock;

import java.util.Map;

@Service
@Log4j2
public class ShopService {

    @Value("${inventory.endpoint.purchase}")
    private String purchaseAPI;

    @Value("${inventory.endpoint.return}")
    private String returnAPI;
    public Cart purchaseItems(Map<String, String> items) {
        Cart cart = new Cart();
        items.forEach((itemId,qty) -> {
            try {
                String url = purchaseAPI + itemId + "/" + qty;
                RestTemplate restTemplate = new RestTemplate();
                Stock stock = restTemplate.getForObject(url, Stock.class);
                log.info("purchase allowed for: " + stock);
                cart.addItem(stock);
            } catch (Exception e) {
                log.error("exception calling inventory service", e);
            }
        });
        log.info("final purchase cart: " + cart);
        return cart;
    }

    public Cart returnItems(Map<Integer, Integer> items) {
        Cart cart = new Cart();
        items.forEach((itemId,qty) -> {
            try {
                String url = returnAPI + itemId + "/" + qty;
                RestTemplate restTemplate = new RestTemplate();
                Stock stock = restTemplate.getForObject(url, Stock.class);
                log.info("return allowed for: " + stock);
                cart.addItem(stock);
            } catch (Exception e) {
                log.error("exception calling inventory service", e);
            }
        });
        log.info("final return cart: " + cart);
        return cart;
    }
}
