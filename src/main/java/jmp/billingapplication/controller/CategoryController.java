package jmp.billingapplication.controller;

import jmp.billingapplication.entity.Category;
import jmp.billingapplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CategoryController {

	@Autowired
    CategoryService categoryService;
	
	private ModelAndView modelAndView=new ModelAndView();
	private List<Category> categoryList;

	@GetMapping("/categoryPage")
	public ModelAndView viewCat() {

		categoryList = categoryService.getCategories();
		modelAndView.addObject("category", new Category());
		modelAndView.addObject("categoryList", categoryList);
		modelAndView.setViewName("category");
		return modelAndView;
	}

	@GetMapping(value = { "/home","/"})
	public ModelAndView home() {

		modelAndView.setViewName("index");
		return modelAndView;
	}

	@PostMapping("/addcategory")
	public ModelAndView addCategory(@ModelAttribute Category category) {
		
		categoryService.saveCategory(category);
		categoryList = categoryService.getCategories();
		modelAndView.addObject("categoryList", categoryList);
		modelAndView.setViewName("category");
		return modelAndView;

	}

	@PostMapping("/editcategory")
	public ModelAndView editCategory(@ModelAttribute Category category) {

		categoryService.editCategory(category);
		categoryList = categoryService.getCategories();
		modelAndView.addObject("categoryList", categoryList);
		modelAndView.setViewName("category");
		return modelAndView;

	}

	@PostMapping("/deletecategory")
	public ModelAndView deleteCategory(@ModelAttribute Category category) {

		categoryService.deleteCategory(category);
		categoryList = categoryService.getCategories();
		modelAndView.addObject("categoryList", categoryList);
		modelAndView.setViewName("category");
		return modelAndView;

	}

}