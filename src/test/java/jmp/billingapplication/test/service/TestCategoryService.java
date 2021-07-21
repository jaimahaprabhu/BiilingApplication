package jmp.billingapplication.test.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import jmp.billingapplication.entity.Category;
import jmp.billingapplication.repository.CategoryRepository;
import jmp.billingapplication.service.impl.CategoryServiceImpl;


public class TestCategoryService {

	@Mock
	CategoryRepository categoryRepository;

	@InjectMocks
	CategoryServiceImpl categoryServiceImpl;
 
	private List<Category> categoryList=new ArrayList<Category>();
	
	@BeforeEach
	public  void setup() {

		MockitoAnnotations.initMocks(this);
		
	}
	@Test
	public void testSaveCategory() {

		Category category = new Category();
		categoryServiceImpl.saveCategory(category);
		when(categoryRepository.findCategoryName("honey")).thenReturn(new ArrayList<Category>());
		
		when(categoryRepository.save(any(Category.class))).thenReturn(category);
	}
	
	@Test
	public void testDeleteCategory() {
		Category category = new Category();
		
		 category.setCategoryId(1); category.setCategoryName("honey");
		  categoryList.add(category);
		when(categoryRepository.findCategoryId(1)).thenReturn(categoryList);

		doNothing().when(categoryRepository);
		categoryRepository.deleteById(Mockito.anyInt());
		categoryServiceImpl.deleteCategory(category);
	}
	
	@Test
	public void testEditCategory() {
		Category category = new Category();
		categoryServiceImpl.editCategory(category);
		 category.setCategoryId(1); category.setCategoryName("honey");
		  categoryList.add(category);
		when(categoryRepository.findCategoryName(Mockito.anyString())).thenReturn(categoryList);

		when(categoryRepository.updateCategory(Mockito.anyString(), Mockito.anyInt())).thenReturn(1);
	}
	
	@Test
	public void testGetAll() {
		categoryServiceImpl.getCategories();
		verify(categoryRepository,times(1)).findAll();
	}
	

}


