package inventory.service;

import inventory.dto.Item;
import inventory.repository.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public boolean canPurchase(int id, int qty) {
        Item s = itemRepository.getItemById(id);
        if(s == null)
            return false;
        return qty <= s.getQuantity();
    }

    public boolean canReturn(int id) {
        return itemRepository.getItemById(id) != null;
    }

    public Item purchaseItem(int id, int qty) throws Exception {
        Item s = itemRepository.getItemById(id);
        if(qty <= 0) {
            log.warn("invalid purchase quantity");
            throw new Exception("invalid purchase quantity");
        }
        if(qty > s.getQuantity()) {
            log.warn("not enough in stock");
            throw new Exception("not enough in stock");
        }
        s.setQuantity(s.getQuantity() - qty);
        itemRepository.update(id, s);

        log.info("purchase successful for id: " + id + " qty: " + qty);
        log.info("new inventory count for id: " + id + " qty: " + s.getQuantity());

        return new Item(s.getId(), s.getName(), qty, s.getPrice(), s.getDesc());
    }

    public Item returnItem(int id, int qty) throws Exception {
        Item s = itemRepository.getItemById(id);
        if(s == null) {
            throw new Exception("we dont sell this item");
        }
        if(qty <= 0) {
            throw new Exception("invalid return quantity");
        }
        s.setQuantity(s.getQuantity() + qty);
        itemRepository.update(id, s);

        log.info("return successful for id: " + id + " qty: " + qty);
        log.info("new inventory count for id: " + id + " qty: " + s.getQuantity());

        return new Item(s.getId(), s.getName(), qty, s.getPrice(), s.getDesc());
    }
}
