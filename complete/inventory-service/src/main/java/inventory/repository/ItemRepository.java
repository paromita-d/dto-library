package inventory.repository;

import dto.Item;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Log4j2
public class ItemRepository {

    private final Map<Integer, Item> itemMap = new LinkedHashMap<>();

    public ItemRepository() {
        Item a = new Item(1, "Shirt", 100, 45.50, "Sonoma");
        Item b = new Item(2, "Pant", 150, 24.99, "Cargo");
        Item c = new Item(3, "Socks", 200, 9.99, "Hanes");

        itemMap.put(1, a);
        itemMap.put(2, b);
        itemMap.put(3, c);

        log.info("Initialized item inventory");
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(itemMap.values());
    }

    public Item getItemById(int id) {
        return itemMap.get(id);
    }

    public Item update(int id, Item s) {
        return itemMap.put(id, s);
    }
}