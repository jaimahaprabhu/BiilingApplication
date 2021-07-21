package jmp.billingapplication.test.controller;

import jmp.billingapplication.controller.ItemController;
import jmp.billingapplication.entity.Category;
import jmp.billingapplication.entity.Item;
import jmp.billingapplication.service.CategoryService;
import jmp.billingapplication.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class TestItemController {
	@Mock
	ItemService itemService;
	@Mock
	CategoryService categoryService;

	@InjectMocks
	ItemController itemController;

	MockMvc mockMvc;
	
	@BeforeEach
	public  void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc=MockMvcBuilders.standaloneSetup(itemController).build();
	}
	@Test
	public void testViewItem() throws Exception {
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);

		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);

		when(categoryService.getCategories()).thenReturn(categoryList);
		when(itemService.getItems()).thenReturn(itemList);
		this.mockMvc.perform(get("/itemPage")).andExpect(status().isOk()).andExpect(model().attributeExists("categoryList"))
				.andExpect(model().attributeExists("itemList")).andExpect(view().name("item"));
	}

	@Test
	public void testAddItem() throws Exception {
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		item.setTax(1.2f);
		itemList.add(item);
		doNothing().when(itemService);
		itemService.addItem(item);
		when(categoryService.getCategories()).thenReturn(categoryList);
		when(itemService.getItems()).thenReturn(itemList);
		this.mockMvc.perform(post("/additem").flashAttr("item", item)).andExpect(status().isOk())
				.andExpect(model().attributeExists("categoryList")).andExpect(model().attributeExists("itemList"))
				.andExpect(view().name("item"));
	}

	@Test
	public void testAddItemPriceFail() throws Exception {
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(0.0f);
		item.setQuantity(20);
		itemList.add(item);

		this.mockMvc.perform(post("/additem").flashAttr("item", item)).andExpect(status().isOk())
				.andExpect(view().name("itemerror"));
	}
	@Test
	public void testAddItemQuantiyFail() throws Exception {
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(10.0f);
		item.setQuantity(0);
		itemList.add(item);

		this.mockMvc.perform(post("/additem").flashAttr("item", item)).andExpect(status().isOk())
				.andExpect(view().name("itemerror"));
	}
	@Test
	public void testAddItemTaxFail() throws Exception {
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(10.0f);
		item.setTax(0.0f);
		item.setQuantity(10);
		itemList.add(item);

		this.mockMvc.perform(post("/additem").flashAttr("item", item)).andExpect(status().isOk())
				.andExpect(view().name("itemerror"));
	}
	
	@Test
	public void testDeleteItem() throws Exception {
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);
		doNothing().when(itemService);
		itemService.deleteItem(item);
		when(categoryService.getCategories()).thenReturn(categoryList);
		when(itemService.getItems()).thenReturn(itemList);
		this.mockMvc.perform(post("/deleteitem")).andExpect(status().isOk())
				.andExpect(model().attributeExists("categoryList")).andExpect(model().attributeExists("itemList"))
				.andExpect(view().name("item"));
	}

	@Test
	public void testEditItem() throws Exception {
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("honey");
		categoryList.add(category);

		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		item.setCategoryName("honey");
		item.setItemId(1);
		item.setItemName("Dabur");
		item.setPrice(22.0f);
		item.setQuantity(3);
		itemList.add(item);
		doNothing().when(itemService);
		itemService.editItem("1", "Tax", "1.2");

		when(categoryService.getCategories()).thenReturn(categoryList);
		when(itemService.getItems()).thenReturn(itemList);
		mockMvc.perform(post("/edititem").param("valuetoupdate", "1.2").param("itemId", "1").param("editoption", "Tax"))
				.andExpect(model().attributeExists("categoryList")).andExpect(model().attributeExists("itemList"))
				.andExpect(view().name("item"));
	}

	@Test
	public void testEditItemFailIfNull() throws Exception {
		itemService.editItem("1", "Tax", "");
		mockMvc.perform(post("/edititem").param("valuetoupdate", "").param("itemId", "1").param("editoption", "Tax"))
				.andExpect(view().name("formatError"));
	}
	@Test
	public void testEditItemFailIfString() throws Exception {
		itemService.editItem("1", "Tax", "sggr");
		mockMvc.perform(post("/edititem").param("valuetoupdate", "").param("itemId", "1").param("editoption", "Tax"))
				.andExpect(view().name("formatError"));
	}
	
}