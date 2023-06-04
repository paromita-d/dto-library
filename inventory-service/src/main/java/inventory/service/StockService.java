package inventory.service;

import inventory.dto.Stock;
import inventory.repository.StockRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock purchaseItem(int id, int qty) throws Exception {
        Stock s = stockRepository.getStockById(id);
        if(qty <= 0) {
            log.warn("invalid purchase quantity");
            throw new Exception("invalid purchase quantity");
        }
        if(qty > s.getQuantity()) {
            log.warn("not enough in stock");
            throw new Exception("not enough in stock");
        }
        s.setQuantity(s.getQuantity() - qty);
        stockRepository.update(id, s);

        log.info("purchase successful for id: " + id + " qty: " + qty);
        log.info("new inventory count for id: " + id + " qty: " + s.getQuantity());

        return new Stock(s.getId(), s.getName(), qty, s.getPrice(), s.getDesc());
    }

    public Stock returnItem(int id, int qty) throws Exception {
        Stock s = stockRepository.getStockById(id);
        if(s == null) {
            throw new Exception("we dont sell this item");
        }
        if(qty <= 0) {
            throw new Exception("invalid return quantity");
        }
        s.setQuantity(s.getQuantity() + qty);
        stockRepository.update(id, s);

        log.info("return successful for id: " + id + " qty: " + qty);
        log.info("new inventory count for id: " + id + " qty: " + s.getQuantity());

        return new Stock(s.getId(), s.getName(), qty, s.getPrice(), s.getDesc());
    }
}
