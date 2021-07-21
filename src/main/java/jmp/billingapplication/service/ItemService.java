package jmp.billingapplication.service;

import java.util.List;

import jmp.billingapplication.entity.Item;

public interface ItemService {

	public List<Item> getItems();

	public void addItem(Item item);

	public void editItem(String itemId, String editOption, String valueToUpdate);

	public void deleteItem(Item item);

	public List<Item> findItem(String search);

	public boolean checkSearch(String search);

	public void reduceQuantity(String itemName, int quantity);
}
