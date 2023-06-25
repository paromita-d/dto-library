package txn.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import txn.dto.Cart;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Log4j2
public class TransactionRepository {
    private final Map<Integer, Cart> txnMap = new LinkedHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    public TransactionRepository() {
        log.info("Initialized transaction repo");
    }

    public Cart insert(Cart s) {
        s.setId(id.getAndIncrement());
        txnMap.put(s.getId(), s);
        return s;
    }

    public Cart update(Cart s) {
        txnMap.put(s.getId(), s);
        return s;
    }

    public Cart getTxnById(int id) {
        return txnMap.get(id);
    }

    public List<Cart> getAllTransactions() {
        return new ArrayList<>(txnMap.values());
    }

}
