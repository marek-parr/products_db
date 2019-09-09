package categories;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
class CategoriesController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<Iterable<Category>> getCategories(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") long id){
        Optional<Category> optionalCategory = categoryService.findById(id);
        if(!optionalCategory.isPresent())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(optionalCategory.get());
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> addCategory(@RequestBody @Valid Category category) throws Exception {
        Category savedCategory = categoryService.save(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody @Valid Category category, @PathVariable("id") long id)
            throws Exception{
        Optional<Category> optionalCategory = categoryService.findById(id);
        if(!optionalCategory.isPresent())
            return ResponseEntity.badRequest().build();
        category.setId(id);
        return ResponseEntity.ok(categoryService.save(category));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") long id) throws Exception{
        if(!categoryService.findById(id).isPresent())
            return ResponseEntity.badRequest().build();
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
