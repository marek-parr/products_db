package products;

import categories.Category;
import categories.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
class ProductsController {

    //t o niżej powinno być final, nie powinniśmy wstrzykiwać komponentów przez pola, a przez konstruktor
    // skorzytaj z adnotacji @RequiredArgsConstructor i ustaw pola finalne
    // pola te powinny być final ponieważ tylko raz są ustawiane.
    // w kontrolerze nie powinno być odwołań bezpośrednio do repozytoriów, logika powinna być zawarta w serwisach
    // utwórz ProductService oraz CategoryService oraz odpowiednie kontrolery.
    // dodatkowo kontrolery nie powinny przyjmować bezpośrednich obiektów bazodawnowych, tylko obiekty DTO
    // https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public ResponseEntity<Iterable<Product>> getProducts(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id){
        Optional<Product> optionalProduct = productService.findById(id);
        if(!optionalProduct.isPresent())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(optionalProduct.get());
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid Product product) throws Exception {
        Product savedProduct = productService.save(product);
        Optional<Category> optionalCategory = categoryService.findById(product.getCategory().getId());
        if(!optionalCategory.isPresent())
            return ResponseEntity.badRequest().build();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/products/{id}")
    //Analogicznie jak wyżej, dodatkowo co w przypadku gdy ktoś wyśle produkt, który nie istnieje, jak robimy update,
    // to powinniśmy sprawdzić czy ten produkt istnieje, update powinien się składać z selecta i update'u
    // wówczas w ramach tej funkcji będą 2 zapytania do bazy, trzeba zadbać o transakcyjność.
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product, @PathVariable("id") long id)
            throws Exception{
        Optional<Product> optionalProduct = productService.findById(id);
        if(!optionalProduct.isPresent())
            return ResponseEntity.badRequest().build();
        product.setId(id);
        return ResponseEntity.ok(productService.save(product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id) throws Exception{
        if(!productService.findById(id).isPresent())
            return ResponseEntity.badRequest().build();
        productService.deleteById(id);
        return  ResponseEntity.ok().build();
    }

}
