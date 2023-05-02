package com.quan.ecommercev2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quan.ecommercev2.Model.Category;
import com.quan.ecommercev2.Model.Order;
import com.quan.ecommercev2.Model.Product;
import com.quan.ecommercev2.Model.User;
import com.quan.ecommercev2.Service.CategoryService;
import com.quan.ecommercev2.Service.OrderService;
import com.quan.ecommercev2.Service.ProductService;
import com.quan.ecommercev2.Service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/users/get")

    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<Void> deleteCustomerById(@RequestParam Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("orders/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok().body(orders);
    }

    @PostMapping("products/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product, @RequestParam long categoryId) {
        productService.addProduct(product, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("products/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @RequestParam Long categoryId) {
        Product updatedProduct = productService.updateProduct(product, categoryId);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("products/delete")
    public ResponseEntity<Void> deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/all")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> products = categoryService.getAllProductsCategories();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/category/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/category/update")
    public ResponseEntity<Category> updateCategory(@RequestParam Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/category/delete")
    public ResponseEntity<Void> deleteProductCategory(@RequestParam Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
