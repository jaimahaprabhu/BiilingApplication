package jmp.billingapplication.service.impl;

import jmp.billingapplication.entity.Item;
import jmp.billingapplication.repository.ItemRepository;
import jmp.billingapplication.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void addItem(Item item) {

        if (itemRepository
                .findByCompositeKey(item.getCategoryName(), item.getItemName(), item.getPrice(), item.getTax())
                .isEmpty()) {
            itemRepository.save(item);
        }
    }

    public void editItem(String itemId, String editOption, String valueToUpdate) {

        if (!itemRepository.findByItemId(Integer.parseInt(itemId)).isEmpty()) {
            switch (editOption) {
                case "Price":
                    itemRepository.updateItemPrice(Integer.parseInt(itemId), Float.parseFloat(valueToUpdate));
                    break;
                case "Tax":
                    itemRepository.updateItemTax(Integer.parseInt(itemId), Float.parseFloat(valueToUpdate));
                    break;
                case "Quantity":
                    itemRepository.updateItemQuantity(Integer.parseInt(itemId), Integer.parseInt(valueToUpdate));
                    break;
                default:
                    break;
            }
        }
    }

    public void deleteItem(Item item) {

        if (!itemRepository.findByItemId(item.getItemId()).isEmpty()) {
            itemRepository.deleteByItemId(item.getItemId());
        }
    }

    public boolean checkSearch(String search) {
		return !getItemList(search).isEmpty();
    }

    public List<Item> findItem(String search) {
        return getItemList(search);
    }

    public void reduceQuantity(String itemName, int quantity) {

        List<Item> itemList = itemRepository.findItemId(itemName);
        int quantityInInventory = itemList.get(0).getQuantity();
        quantityInInventory -= quantity;
        editItem(String.valueOf(itemList.get(0).getItemId()), "Quantity", String.valueOf(quantityInInventory));

    }

    private List<Item> getItemList(String search) {
        return itemRepository.findAll().stream()
                .filter(index -> index.getItemName().equalsIgnoreCase(search))
                .collect(Collectors.toList());
    }
}