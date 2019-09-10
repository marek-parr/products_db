package pl.epoint.mparr.products;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    List<ProductDto> findAll(){
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    ProductDto findById(Long id){
        return convertToDto(productRepository.findById(id).orElse(null));
    }

    ProductDto save(ProductDto productDto){
        productRepository.save(convertToEntity(productDto));
        return productDto;
    }

    boolean productExists(ProductDto productDto){
        return productRepository.findAll()
                .stream()
                .anyMatch(product -> product.getId().equals(productDto.getId()));
    }

    void deleteById(Long id){
        productRepository.deleteById(id);
    }

    private ProductDto convertToDto(Product product){
        return modelMapper.map(product, ProductDto.class);
    }

    private Product convertToEntity(ProductDto productDto){
        return modelMapper.map(productDto, Product.class);
    }

}
