package pl.epoint.mparr.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
class CategoriesController {

    private final CategoryService categoryService;

    ResponseEntity<List<CategoryDto>> getCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long id) {
        CategoryDto categoryDto = categoryService.findById(id);
        if(categoryDto == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        if(categoryService.categoryExists(categoryDto)) {
            return new ResponseEntity<>(categoryDto, HttpStatus.NOT_FOUND);
        }
        categoryService.save(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @PutMapping
    ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") long id) {
        if(!categoryService.categoryExists(categoryDto)) {
            return new ResponseEntity<>(categoryDto, HttpStatus.NOT_FOUND);
        }
        categoryDto.setId(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity deleteCategory(@PathVariable("id") long id) {
        if(categoryService.findById(id)==null) {
            return ResponseEntity.badRequest().build();
        }
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }



}
