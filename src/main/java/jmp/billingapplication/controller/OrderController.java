package jmp.billingapplication.controller;

import jmp.billingapplication.entity.Item;
import jmp.billingapplication.entity.Orders;
import jmp.billingapplication.service.ItemService;
import jmp.billingapplication.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;

    private List<Item> itemList;
    private ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/billing")
    public ModelAndView viewOrder() {

        itemList = itemService.getItems();
        modelAndView.addObject("itemList", itemList);
        modelAndView.setViewName("search");
        return modelAndView;
    }

    @PostMapping("/saveorder")
    public ModelAndView saveOrder(@ModelAttribute("orders") Orders orders, @RequestParam("itemName") String itemName) {

        if (orders.getQuantity() == 0) {
            System.out.println("im here");
            modelAndView.setViewName("error");

        } else {
            int status = orderService.saveOrder(orders, itemName);
            if (status == 0) {
                itemList = itemService.getItems();
                modelAndView.addObject("itemList", itemList);
                modelAndView.setViewName("search");
            } else if (status == -1) {
                modelAndView.setViewName("updateError");
            } else {
                modelAndView.addObject("stock", status);
                modelAndView.setViewName("stockerror");
            }
        }
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView searchItem(@RequestParam("search") String search) {

        modelAndView.addObject("orders", new Orders());
        boolean searchFlag = itemService.checkSearch(search);
        if (searchFlag) {
            itemList = itemService.findItem(search);
            modelAndView.addObject("orderitem", itemList);
            modelAndView.setViewName("order");
        } else {
            modelAndView.setViewName("searcherror");
        }
        return modelAndView;
    }

}