package jmp.billingapplication.controller;

import jmp.billingapplication.entity.Category;
import jmp.billingapplication.entity.Item;
import jmp.billingapplication.service.CategoryService;
import jmp.billingapplication.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ItemController {

    @Autowired
    ItemService itemService;
    @Autowired
    CategoryService categoryService;

    private ModelAndView modelAndView = new ModelAndView();
    private List<Category> categoryList;
    private List<Item> itemList;

    @GetMapping("/itemPage")
    public ModelAndView viewItem() {

        categoryList = categoryService.getCategories();
        itemList = itemService.getItems();
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.addObject("itemList", itemList);
        modelAndView.addObject("item", new Item());
        modelAndView.setViewName("item");
        return modelAndView;
    }

    @PostMapping("/additem")
    public ModelAndView addItem(@ModelAttribute Item item) {

        if (item.getPrice() == 0 || item.getQuantity() == 0 || item.getTax() == 0) {
            modelAndView.setViewName("itemerror");
        } else {
            itemService.addItem(item);
            categoryList = categoryService.getCategories();
            itemList = itemService.getItems();
            modelAndView.addObject("categoryList", categoryList);
            modelAndView.addObject("itemList", itemList);
            modelAndView.setViewName("item");
        }
        return modelAndView;
    }

    @PostMapping("/edititem")
    public ModelAndView editItem(@RequestParam("itemId") String itemId, @RequestParam("editoption") String editOption,
                                 @RequestParam("valuetoupdate") String valueToUpdate) {


        if (valueToUpdate.isEmpty() || valueToUpdate.matches("[a-zA-Z]+")) {
            modelAndView.setViewName("formatError");
        } else {

            itemService.editItem(itemId, editOption, valueToUpdate);
            categoryList = categoryService.getCategories();
            itemList = itemService.getItems();
            modelAndView.addObject("categoryList", categoryList);
            modelAndView.addObject("itemList", itemList);
            modelAndView.addObject("item", new Item());
            modelAndView.setViewName("item");
        }
        return modelAndView;
    }

    @PostMapping("/deleteitem")
    public ModelAndView deleteItem(@ModelAttribute Item item) {

        itemService.deleteItem(item);
        categoryList = categoryService.getCategories();
        itemList = itemService.getItems();
        modelAndView.addObject("categoryList", categoryList);
        modelAndView.addObject("itemList", itemList);
        modelAndView.setViewName("item");
        return modelAndView;
    }

}