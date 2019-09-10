package products;

import categories.Category;
import categories.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
class ProductsController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<Product> products = productService.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") long id){
        Optional<Product> optionalProduct = productService.findById(id);
        if(!optionalProduct.isPresent())
            return ResponseEntity.badRequest().build();
        ProductDto productDto = convertToDto(optionalProduct.get());
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) throws Exception {
        if(productService.findAll().stream().anyMatch(product -> product.getId().equals(productDto.getId())))
            return ResponseEntity.badRequest().build();
        Optional<Category> optionalCategory = categoryService.findById(productDto.getCategory().getId());
        if(!optionalCategory.isPresent())
            return ResponseEntity.badRequest().build();
        productService.save(convertToEntity(productDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDto.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") long id)
            throws Exception{
        Optional<Product> optionalProduct = productService.findById(id);
        if(!optionalProduct.isPresent())
            return ResponseEntity.badRequest().build();
        Optional<Category> optionalCategory = categoryService.findById(productDto.getCategory().getId());
        if(!optionalCategory.isPresent())
            return ResponseEntity.badRequest().build();
        productDto.setId(id);
        productService.save(convertToEntity(productDto));
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id) throws Exception{
        if(!productService.findById(id).isPresent())
            return ResponseEntity.badRequest().build();
        productService.deleteById(id);
        return  ResponseEntity.ok().build();
    }

    private ProductDto convertToDto(Product product){
        return modelMapper.map(product, ProductDto.class);
    }

    private Product convertToEntity(ProductDto productDto){
        return modelMapper.map(productDto, Product.class);
    }

}
