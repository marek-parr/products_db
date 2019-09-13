package pl.epoint.mparr.products;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.epoint.mparr.categories.CategoryService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
class ProductsController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") long id) {
        ProductDto productDto = productService.findById(id);
        if (productDto == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        if (productService.productExists(productDto)) {
            return new ResponseEntity<>(productDto, HttpStatus.NOT_FOUND);
        }
        if(!categoryService.categoryExists(productDto.getCategory())) {
            return new ResponseEntity<>(productDto, HttpStatus.NOT_FOUND);
        }

        productService.save(productDto);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") long id) {
        if (!categoryService.categoryExists(productDto.getCategory())) {
            return new ResponseEntity<>(productDto, HttpStatus.NOT_FOUND);
        }

        if (!productService.productExists(productDto)) {
            return new ResponseEntity<>(productDto, HttpStatus.NOT_FOUND);
        }

        productDto.setId(id);
        productService.save(productDto);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteProduct(@PathVariable("id") long id) {
        if (productService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
