package inventory.repository;

import inventory.dto.Stock;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Log4j2
public class StockRepository {

    private Map<Integer, Stock> stockMap = new LinkedHashMap<>();

    public StockRepository() {
        Stock a = new Stock(1, "Shirt", 100, 45.50, "Sonoma");
        Stock b = new Stock(2, "Pant", 150, 24.99, "Cargo");
        Stock c = new Stock(3, "Socks", 200, 9.99, "Hanes");

        stockMap.put(1, a);
        stockMap.put(2, b);
        stockMap.put(3, c);

        log.info("Initialized inventory");
    }

    public List<Stock> getAllStock() {
        return new ArrayList<>(stockMap.values());
    }

    public Stock getStockById(int id) {
        return stockMap.get(id);
    }

    public Stock update(int id, Stock s) {
        return stockMap.put(id, s);
    }
}