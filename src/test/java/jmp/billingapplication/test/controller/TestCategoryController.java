package jmp.billingapplication.test.controller;

import jmp.billingapplication.controller.CategoryController;
import jmp.billingapplication.entity.Category;
import jmp.billingapplication.service.CategoryService;
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

public class TestCategoryController {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/")).andExpect(view().name("index"));
    }

    @Test
    public void testCategoryPage() throws Exception {

        List<Category> categoryList = new ArrayList<Category>();
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("honey");
        categoryList.add(category);

        when(categoryService.getCategories()).thenReturn(categoryList);
        this.mockMvc.perform(get("/categoryPage")).andExpect(model().attributeExists("categoryList")).andExpect(view().name("category"));
    }


    @Test
    public void testHome() throws Exception {

        this.mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("index"));
    }


    @Test
    public void testAddCategory() throws Exception {
        List<Category> categoryList = new ArrayList<Category>();
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("honey");
        categoryList.add(category);
        doNothing().when(categoryService);
        categoryService.saveCategory(category);
        when(categoryService.getCategories()).thenReturn(categoryList);

        this.mockMvc.perform(post("/addcategory")).andExpect(status().isOk())
                .andExpect(model().attributeExists("categoryList")).andExpect(view().name("category"));

    }

    @Test
    public void testEditCategory() throws Exception {
        List<Category> categoryList = new ArrayList<Category>();
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("honey");
        categoryList.add(category);
        doNothing().when(categoryService);
        categoryService.editCategory(category);
        when(categoryService.getCategories()).thenReturn(categoryList);

        this.mockMvc.perform(post("/deletecategory")).andExpect(status().isOk())
                .andExpect(model().attributeExists("categoryList")).andExpect(view().name("category"));

    }

    @Test
    public void testDeleteCategory() throws Exception {
        List<Category> categoryList = new ArrayList<Category>();
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("honey");
        categoryList.add(category);
        doNothing().when(categoryService);
        categoryService.deleteCategory(category);
        when(categoryService.getCategories()).thenReturn(categoryList);

        this.mockMvc.perform(post("/editcategory")).andExpect(status().isOk())
                .andExpect(model().attributeExists("categoryList")).andExpect(view().name("category"));

    }
}