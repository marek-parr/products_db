package pl.epoint.mparr.categories;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    CategoryDto findById(Long id) {
        return convertToDto(categoryRepository.findById(id).orElse(null));
    }

    CategoryDto save(CategoryDto categoryDto) {
        categoryRepository.save(convertToEntity(categoryDto));
        return categoryDto;
    }

    void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean categoryExists(CategoryDto categoryDto) {
        return categoryRepository.findAll().stream()
                .anyMatch(category -> category.getId().equals(categoryDto.getId()));
    }

    private CategoryDto convertToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

}
