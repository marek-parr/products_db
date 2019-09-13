package pl.epoint.mparr.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
class CategoriesController {

    private final CategoryService categoryService;

    @GetMapping
    ResponseEntity<List<CategoryDto>> getCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
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

    @Transactional
    @PutMapping("/{id}")
    ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") long id) {
        if(!categoryService.categoryExists(categoryDto)) {
            return new ResponseEntity<>(categoryDto, HttpStatus.NOT_FOUND);
        }
        categoryDto.setId(id);
        categoryService.save(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteCategory(@PathVariable("id") long id) {
        if(categoryService.findById(id)==null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        categoryService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
