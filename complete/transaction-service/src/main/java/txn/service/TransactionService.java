package txn.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import dto.Cart;
import dto.Item;
import dto.TxnType;
import txn.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
@Log4j2
public class TransactionService {

    @Value("${inventory.purchase}")
    private String purchaseAPI;

    @Value("${inventory.refund}")
    private String refundAPI;

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Cart transactItems(TxnType txnType, Map<String, String> items, int txnId) {
        final Cart cart = Cart.builder().
                txnType(txnType).
                txnDate(new Date()).
                items(new ArrayList<>()).
                totalCost(0D).
                build();

        if(txnType == TxnType.refund && transactionRepository.getTxnById(txnId) == null) {
            cart.setId(txnId);
            cart.setDesc("Invalid txn id for refund");
            log.info("final " + txnType + " cart: " + cart);
            return cart;
        }

        items.forEach((itemId,qty) -> {
            try {
                String url = (txnType == TxnType.purchase ? purchaseAPI : refundAPI) + itemId + "/" + qty;
                RestTemplate restTemplate = new RestTemplate();
                Item item = restTemplate.getForObject(url, Item.class);
                log.info(txnType + " allowed for: " + item);
                if(item == null) {
                    throw new Exception("Item is null from inventory service");
                }
                cart.addItem(item);
                cart.setTotalCost(cart.getTotalCost() + (item.getPrice() * item.getQuantity()));
                cart.setDesc(txnType + " successful");
            } catch (Exception e) {
                log.error("exception calling inventory service", e);
            }
        });
        Cart updated;
        if(txnType == TxnType.purchase) {
            updated = transactionRepository.insert(cart);
        } else {
            cart.setId(txnId);
            updated = transactionRepository.update(cart);
        }
        log.info("final " + txnType + " cart: " + updated);
        return updated;
    }
}
