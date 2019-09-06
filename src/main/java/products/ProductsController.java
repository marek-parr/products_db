package products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping( "/products")
    public Iterable<Product> getProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") long id){
        return productRepository.findById(id).get();
    }

    @PostMapping("/products")
    public void addProduct(@RequestBody @Valid Product product, HttpServletResponse response) throws Exception{
        productRepository.save(product);
        response.sendRedirect("/products");
    }

    @PutMapping("/products/{id}")
    public void updateProduct(@RequestBody @Valid Product product, @PathVariable("id") long id, HttpServletResponse response)
            throws Exception{
        product.setId(id);
        productRepository.save(product);
        response.sendRedirect("/products");
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") long id, HttpServletResponse response) throws Exception{
        productRepository.deleteById(id);
        response.sendRedirect("/products");
    }

    @GetMapping( "/categories")
    public Iterable<Category> getCategories(){
        return categoryRepository.findAll();
    }

    @GetMapping("/categories/{id}")
    public Category getCategory(@PathVariable("id") long id){
        return categoryRepository.findById(id).get();
    }

    @PostMapping("/categories")
    public void addCategory(@RequestBody @Valid Category category, HttpServletResponse response) throws Exception{
        categoryRepository.save(category);
        response.sendRedirect("/categories");
    }
    @PutMapping("/categories/{id}")
    public void updateCategory(@RequestBody @Valid Category category, @PathVariable("id") long id, HttpServletResponse response)
            throws Exception{
        category.setId(id);
        categoryRepository.save(category);
        response.sendRedirect("/categories");
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable("id") long id, HttpServletResponse response) throws Exception{
        categoryRepository.deleteById(id);
        response.sendRedirect("/categories");
    }

}
