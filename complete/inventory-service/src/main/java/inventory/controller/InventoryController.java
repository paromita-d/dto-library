package inventory.controller;

import dto.TxnType;
import inventory.repository.ItemRepository;
import dto.Item;
import inventory.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class InventoryController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    public InventoryController(ItemRepository itemRepository, ItemService itemService) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    @GetMapping(path="/all", produces = "application/json")
    public List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

    @GetMapping(path="/details/{id}", produces = "application/json")
    public Item getItem(@PathVariable int id) {
        return itemRepository.getItemById(id);
    }

    @GetMapping(path="/eligibility/{txnType}/{id}/{count}", produces = "application/json")
    public Boolean canTransactItem(@PathVariable TxnType txnType, @PathVariable int id, @PathVariable int count) {
        if(txnType == TxnType.purchase)
            return itemService.canPurchase(id, count);
        else
            return itemService.canReturn(id);
    }

    @GetMapping(path="/{txnType}/{id}/{count}", produces = "application/json")
    public Item transactItem(@PathVariable TxnType txnType, @PathVariable int id, @PathVariable int count) {
        try {
            if(txnType == TxnType.purchase)
                return itemService.purchaseItem(id, count);
            else
                return itemService.returnItem(id, count);
        } catch (Exception e) {
            return Item.builder().desc(e.getMessage()).build();
        }
    }
}
