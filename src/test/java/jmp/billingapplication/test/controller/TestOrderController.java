package jmp.billingapplication.test.controller;

import jmp.billingapplication.controller.OrderController;
import jmp.billingapplication.entity.Item;
import jmp.billingapplication.entity.Orders;
import jmp.billingapplication.service.ItemService;
import jmp.billingapplication.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class TestOrderController {
    @Mock
    ItemService itemService;
    @Mock
    OrderService orderService;
    @InjectMocks
    OrderController orderController;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testViewOrder() throws Exception {

        List<Item> itemList = new ArrayList<Item>();
        Item item = new Item();
        item.setCategoryName("honey");
        item.setItemId(1);
        item.setItemName("Dabur");
        item.setPrice(22.0f);
        item.setQuantity(3);
        itemList.add(item);
        when(itemService.getItems()).thenReturn(itemList);
        this.mockMvc.perform(get("/billing")).andExpect(status().isOk()).andExpect(model().attributeExists("itemList"))
                .andExpect(view().name("search"));
    }

    @Test
    public void testSearchItemCheckSearchTrue() throws Exception {
        List<Item> itemList = new ArrayList<Item>();
        Item item = new Item();
        item.setCategoryName("honey");
        item.setItemId(1);
        item.setItemName("Dabur");
        item.setPrice(22.0f);
        item.setQuantity(3);
        itemList.add(item);
        when(itemService.checkSearch(Mockito.anyString())).thenReturn(true);
        when(itemService.findItem("Dabur")).thenReturn(itemList);

        this.mockMvc.perform(post("/search").param("search", "Dabur")).andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("orderitem")).andExpect(view().name("order"));

    }

    @Test
    public void testSearchItemCheckSearchFalse() throws Exception {
        List<Item> itemList = new ArrayList<Item>();
        Item item = new Item();
        item.setCategoryName("honey");
        item.setItemId(1);
        item.setItemName("Dabur");
        item.setPrice(22.0f);
        item.setQuantity(3);
        itemList.add(item);

        when(itemService.checkSearch(Mockito.any(String.class))).thenReturn(false);
        when(itemService.findItem("Dabur")).thenReturn(itemList);
        this.mockMvc.perform(post("/search").param("search", "da")).andExpect(view().name("searcherror"));
    }

    @Test
    public void testSaveOrderZeroQuantity() throws Exception {

        Orders orders = new Orders();
        orders.setQuantity(0);
        this.mockMvc.perform(post("/saveorder").flashAttr("orders", orders).param("itemName", "Dabur"))
                .andExpect(view().name("error"));
    }

    @Test
    public void testSaveOrderRedundant() throws Exception {
        List<Item> itemList = new ArrayList<Item>();
        Item item = new Item();
        item.setCategoryName("honey");
        item.setItemId(1);
        item.setItemName("Dabur");
        item.setPrice(22.0f);
        item.setQuantity(3);
        itemList.add(item);
        Orders orders = new Orders();
        orders.setQuantity(1);
        when(orderService.saveOrder(orders, "Dabur")).thenReturn(-1);
        this.mockMvc.perform(post("/saveorder").flashAttr("orders", orders).param("itemName", "Dabur"))
                .andExpect(view().name("updateError"));
    }

    @Test
    public void testSaveOrderCheckStock() throws Exception {

        Orders orders = new Orders();
        orders.setQuantity(45);
        when(orderService.saveOrder(orders, "Dabur")).thenReturn(42);
        this.mockMvc.perform(post("/saveorder").flashAttr("orders", orders).param("itemName", "Dabur"))
                .andExpect(view().name("stockerror"));
    }

    @Test
    public void testSaveOrder() throws Exception {
        List<Item> itemList = new ArrayList<Item>();
        Item item = new Item();
        item.setCategoryName("honey");
        item.setItemId(1);
        item.setItemName("Dabur");
        item.setPrice(22.0f);
        item.setQuantity(3);
        itemList.add(item);
        Orders orders = new Orders();
        orders.setQuantity(2);
        when(orderService.saveOrder(orders, "Dabur")).thenReturn(0);
        when(itemService.getItems()).thenReturn(itemList);
        this.mockMvc.perform(post("/saveorder").flashAttr("orders", orders).param("itemName", "Dabur"))
                .andExpect(view().name("search"));
    }
}