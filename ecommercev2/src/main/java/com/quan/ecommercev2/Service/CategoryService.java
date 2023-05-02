package com.quan.ecommercev2.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quan.ecommercev2.Model.Category;
import com.quan.ecommercev2.Repo.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllProductsCategories() {

        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);

    }

    public Category updateCategory(Long id, Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setName(category.getName());
            return categoryRepository.save(existingCategory);
        } else {
            throw new RuntimeException("No Such Category Found");
        }
    }

    public void deleteCategoryById(Long categoryId) {

        categoryRepository.deleteById(categoryId);

    }
}
