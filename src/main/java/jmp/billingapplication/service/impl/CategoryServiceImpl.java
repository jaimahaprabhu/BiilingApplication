package jmp.billingapplication.service.impl;

import jmp.billingapplication.entity.Category;
import jmp.billingapplication.repository.CategoryRepository;
import jmp.billingapplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(@RequestBody Category category) {

        if (categoryRepository.findCategoryName(category.getCategoryName()).isEmpty())
            categoryRepository.save(category);
    }

    public void deleteCategory(Category category) {

        if (!categoryRepository.findCategoryId(category.getCategoryId()).isEmpty())
            categoryRepository.deleteById(category.getCategoryId());
    }

    public void editCategory(Category category) {

        if (categoryRepository.findCategoryName(category.getCategoryName()).isEmpty())
            categoryRepository.updateCategory(category.getCategoryName(), category.getCategoryId());
    }
}