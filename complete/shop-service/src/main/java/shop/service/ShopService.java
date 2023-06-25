package shop.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import dto.Cart;
import dto.TxnType;

import java.util.Map;

@Service
@Log4j2
public class ShopService {

    @Value("${inventory.eligibility.purchase}")
    private String canPurchaseAPI;

    @Value("${inventory.eligibility.refund}")
    private String canRefundAPI;

    @Value("${transaction.purchase}")
    private String txnPurchaseAPI;

    @Value("${transaction.refund}")
    private String txnRefundAPI;

    public boolean canTransactItems(TxnType txnType, Map<String, String> items) {
        for(Map.Entry<String, String> entry : items.entrySet()) {
            String itemId = entry.getKey();
            String qty = entry.getValue();
            String url = (txnType == TxnType.purchase ? canPurchaseAPI : canRefundAPI) + itemId + "/" + qty;
            RestTemplate restTemplate = new RestTemplate();
            Boolean allowed = restTemplate.getForObject(url, Boolean.class);
            log.info(txnType + " allowed for " + itemId + ":" + allowed);
            if(allowed != null && !allowed)
                return false;
        }
        return true;
    }

    public Cart transactItems(TxnType txnType, Map<String, String> items, int txnId) {
        StringBuilder strBld = new StringBuilder();
        items.forEach((id, qty) -> strBld.append(id).append("=").append(qty).append("&"));

        String url = (txnType == TxnType.purchase ? txnPurchaseAPI : txnRefundAPI) + txnId + "?" + strBld;
        RestTemplate restTemplate = new RestTemplate();
        Cart cart = restTemplate.getForObject(url, Cart.class);
        log.info("Cart from transaction service: " + cart);
        return cart;
    }
}
