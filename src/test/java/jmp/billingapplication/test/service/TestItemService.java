package jmp.billingapplication.test.service;

import jmp.billingapplication.entity.Item;
import jmp.billingapplication.repository.ItemRepository;
import jmp.billingapplication.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class TestItemService {

	@Mock
	ItemRepository itemRepository;
	@InjectMocks
	ItemServiceImpl itemServiceImpl;

	@BeforeEach
	public  void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindItems() {
		itemServiceImpl.getItems();
		verify(itemRepository, times(1)).findAll();
	}

	@Test
	public void testDeleteItem() {
		List<Item> itemList=new ArrayList<Item>();
		Item item = new Item(); 
		item.setCategoryName("honey"); item.setItemId(1);
		 item.setItemName("Dabur"); item.setPrice(22.0f); item.setQuantity(3);
		 itemList.add(item);

		when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);
		doNothing().when(itemRepository);
		itemRepository.deleteByItemId(Mockito.anyInt());
		itemServiceImpl.deleteItem(item);
	}

	@Test
	public void testFindItem() {
		itemServiceImpl.findItem("Honey");
		verify(itemRepository, times(1)).findAll();
	}

	@Test
	public void testAddItem() {
   
		List<Item> itemList=new ArrayList<Item>();
		Item item = new Item(); 
		item.setCategoryName("honey"); item.setItemId(1);
		 item.setItemName("Dabur"); item.setPrice(22.0f); item.setQuantity(3);
		 itemList.add(item);
		 List<Item> newItemList=new ArrayList<Item>();
		when(itemRepository.findByCompositeKey(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyFloat(), Mockito.anyFloat())).thenReturn(newItemList);
		when(itemRepository.save(any(Item.class))).thenReturn(item);

		itemServiceImpl.addItem(item);
		}

	@Test
	public void testCheckSearchEmptyList() {

		List<Item> itemList=new ArrayList<Item>();
		Mockito.when(itemRepository.findAll()).thenReturn(itemList);	
		itemServiceImpl.checkSearch("Dabur");
	}
	@Test
	public void testCheckSearch() {

		List<Item> itemList=returnItemList();
		Mockito.when(itemRepository.findAll()).thenReturn(itemList);	
		itemServiceImpl.checkSearch("Dabur");
	}
	@Test
	public void testEditItemForPrice() {
		List<Item> itemList=returnItemList();
		 when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);        
         when(itemRepository.updateItemPrice(Mockito.anyInt(), Mockito.anyFloat())).thenReturn(1);
         
         itemServiceImpl.editItem("1", "Price","4");
	}
	@Test
	public void testEditItemForTax() {
		List<Item> itemList=returnItemList();
		 when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);        
         when(itemRepository.updateItemPrice(Mockito.anyInt(), Mockito.anyFloat())).thenReturn(1);
         
         itemServiceImpl.editItem("1", "Tax","4");
	}
	
	@Test
	public void testEditItemForQuantity() {
		List<Item> itemList=returnItemList();
		 when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);        
         when(itemRepository.updateItemPrice(Mockito.anyInt(), Mockito.anyFloat())).thenReturn(1);
         itemServiceImpl.editItem("1", "Quantity","4");
	}
	
	@Test
	public void testReduceQuantity() {
		List<Item> itemList=returnItemList();
		 when(itemRepository.findItemId(Mockito.anyString())).thenReturn(itemList);      
		 when(itemRepository.findByItemId(Mockito.anyInt())).thenReturn(itemList);  
		  when(itemRepository.updateItemPrice(Mockito.anyInt(), Mockito.anyFloat())).thenReturn(1);
		 itemServiceImpl.reduceQuantity("Dabur", 2);
	}
	private List<Item> returnItemList(){
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);
		return itemList;
	}
}

