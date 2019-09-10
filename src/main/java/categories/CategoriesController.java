package categories;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
class CategoriesController {

    private final CategoryService categoryService;

    private final ModelMapper modelMapper;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<Category> categories = categoryService.findAll();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDtos);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long id){
        Optional<Category> optionalCategory = categoryService.findById(id);
        if(!optionalCategory.isPresent())
            return ResponseEntity.badRequest().build();
        CategoryDto categoryDto = convertToDto(optionalCategory.get());
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) throws Exception {
        if(categoryService.findAll().stream().anyMatch(category -> category.getId().equals(categoryDto.getId())))
            return ResponseEntity.badRequest().build();
        categoryService.save(convertToEntity(categoryDto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryDto.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("id") long id)
            throws Exception{
        Optional<Category> optionalCategory = categoryService.findById(id);
        if(!optionalCategory.isPresent())
            return ResponseEntity.badRequest().build();
        categoryDto.setId(id);
        categoryService.save(convertToEntity(categoryDto));
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") long id) throws Exception{
        if(!categoryService.findById(id).isPresent())
            return ResponseEntity.badRequest().build();
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private CategoryDto convertToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category convertToEntity(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }

}
