package jmp.billingapplication.test.controller;

import jmp.billingapplication.controller.BillController;
import jmp.billingapplication.entity.BillOrder;
import jmp.billingapplication.entity.Orders;
import jmp.billingapplication.service.BillService;
import jmp.billingapplication.service.ItemService;
import jmp.billingapplication.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestBillController {

    @Mock
    ItemService itemService;
    @Mock
    OrderService orderService;
    @Mock
    BillService billService;
    @InjectMocks
    BillController billController;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(billController).build();
    }

    @Test
    public void testGenerateBill() throws Exception {
        List<BillOrder> billOrderList = new ArrayList<BillOrder>();
        BillOrder billOrder = new BillOrder();
        billOrder.setOrderId(123);
        billOrder.setQuantity(2);
        billOrder.setAmount(44.25f);
        billOrder.setItemName("Dabur");
        billOrder.setPrice(21.50f);
        billOrder.setTax(1.0f);
        billOrderList.add(billOrder);
        when(orderService.returnCurrentOrderId()).thenReturn(123);
        when(billService.generateBill(123)).thenReturn(billOrderList);
        mockMvc.perform(post("/generateBill")).andExpect(status().isOk())
                .andExpect(model().attributeExists("orders")).andExpect(model().attributeExists("bill"))
                .andExpect(view().name("finalbill"));

    }

    @Test
    public void testGenerateFinalBill() throws Exception {
        List<BillOrder> billOrderList = new ArrayList<BillOrder>();
        BillOrder billOrder = new BillOrder();
        billOrder.setOrderId(112345);
        billOrder.setQuantity(2);
        billOrder.setAmount(44.25f);
        billOrder.setItemName("Dabur");
        billOrder.setPrice(21.50f);
        billOrder.setTax(1.0f);
        billOrder.setPurchaseDate("22/02/2020 11.22.33");
        billOrderList.add(billOrder);

        when(billService.saveBill(Mockito.anyInt())).thenReturn(true);
        when(billService.printBill(Mockito.anyInt())).thenReturn(billOrderList);
        mockMvc.perform(get("/generated")).andExpect(model().attributeExists("orderId"))
                .andExpect(model().attributeExists("Date")).andExpect(view().name("generatebill"));

    }

    @Test
    public void testGenerateFinalBillFail() throws Exception {

        when(billService.saveBill(Mockito.anyInt())).thenReturn(false);
        mockMvc.perform(get("/generated")).andExpect(view().name("billError"));

    }

    @Test
    public void testPrintBill() throws Exception {
        List<BillOrder> billOrderList = new ArrayList<BillOrder>();
        BillOrder billOrder = new BillOrder();
        billOrder.setOrderId(112345);
        billOrder.setQuantity(2);
        billOrder.setAmount(44.25f);
        billOrder.setItemName("Dabur");
        billOrder.setPrice(21.50f);
        billOrder.setTax(1.0f);
        billOrder.setPurchaseDate("22/02/2020 11.22.33");
        billOrderList.add(billOrder);
        when(orderService.returnCurrentOrderId()).thenReturn(112345);
        when(billService.generateBill(Mockito.anyInt())).thenReturn(billOrderList);
        when(billService.getTotal()).thenReturn(44.25f);

        mockMvc.perform(post("/print")).andExpect(model().attributeExists("billList"))
                .andExpect(model().attributeExists("count")).andExpect(view().name("printbill"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteOrderEmptyList() throws Exception {

        List<BillOrder> mockList = Mockito.mock(List.class);
        Orders orders = new Orders();
        orders.setQuantity(0);
        doNothing().when(orderService);
        orderService.deleteOrder(orders, "Dabur");
        when(billService.generateBill(Mockito.anyInt())).thenReturn(mockList);
        List<String> billOrderList = Mockito.mock(List.class);
        when(billOrderList.size()).thenReturn(0);
        mockMvc.perform(post("/deleteorder").param("itemName", "Dabur")).andExpect(status().isOk())
                .andExpect(view().name("error"));

    }

    @Test
    public void testDeleteOrder() throws Exception {
        List<BillOrder> billList = new ArrayList<BillOrder>();
        BillOrder billOrder = new BillOrder();
        billOrder.setOrderId(123);
        billOrder.setQuantity(2);
        billOrder.setAmount(44.25f);
        billOrder.setItemName("Dabur");
        billOrder.setPrice(21.50f);
        billOrder.setTax(1.0f);
        billList.add(billOrder);
        Orders orders = new Orders();
        orders.setQuantity(0);
        doNothing().when(orderService);
        orderService.deleteOrder(orders, "Dabur");
        when(billService.generateBill(Mockito.anyInt())).thenReturn(billList);

        mockMvc.perform(post("/deleteorder").param("itemName", "Dabur")).andExpect(model().attributeExists("bill"))
                .andExpect(status().isOk()).andExpect(view().name("finalbill"));

    }

    @Test
    public void testEditOrder() throws Exception {
        List<BillOrder> billList = new ArrayList<BillOrder>();
        BillOrder billOrder = new BillOrder();
        billOrder.setOrderId(123);
        billOrder.setQuantity(2);
        billOrder.setAmount(44.25f);
        billOrder.setItemName("Dabur");
        billOrder.setPrice(21.50f);
        billOrder.setTax(1.0f);
        billList.add(billOrder);
        Orders orders = new Orders();
        orders.setQuantity(0);
        doReturn(1).when(orderService).editOrder("Dabur", orders);
        when(billService.generateBill(Mockito.anyInt())).thenReturn(billList);

        mockMvc.perform(post("/editorder").param("itemName", "Dabur")).andExpect(model().attributeExists("bill"))
                .andExpect(status().isOk()).andExpect(view().name("finalbill"));
    }
}