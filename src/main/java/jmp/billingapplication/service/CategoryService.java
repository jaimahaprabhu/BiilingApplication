package jmp.billingapplication.service;

import java.util.List;

import jmp.billingapplication.entity.Category;


public interface CategoryService {

	public List<Category> getCategories();
	
	public void deleteCategory(Category category);
	
	public void editCategory(Category category);
	
	public void saveCategory(Category category);
}
