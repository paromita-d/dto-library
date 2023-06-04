package inventory.controller;

import inventory.repository.StockRepository;
import inventory.dto.Stock;
import inventory.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class InventoryController {

    private StockRepository stockRepository;
    private StockService stockService;

    public InventoryController(StockRepository stockRepository, StockService stockService) {
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    @GetMapping(path="/all", produces = "application/json")
    public List<Stock> getAllStock() {
        return stockRepository.getAllStock();
    }

    @GetMapping(path="/details/{id}", produces = "application/json")
    public Stock getStock(@PathVariable int id) {
        return stockRepository.getStockById(id);
    }

    @GetMapping(path="/purchase/{id}/{count}", produces = "application/json")
    public Stock purchaseItem(@PathVariable int id, @PathVariable int count) {
        try {
            return stockService.purchaseItem(id, count);
        } catch (Exception e) {
            return Stock.builder().desc(e.getMessage()).build();
        }
    }

    @GetMapping(path="/return/{id}/{count}", produces = "application/json")
    public Stock returnItem(@PathVariable int id, @PathVariable int count) {
        try {
            return stockService.returnItem(id, count);
        } catch (Exception e) {
            return Stock.builder().desc(e.getMessage()).build();
        }
    }
}
