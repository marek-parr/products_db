package pl.epoint.mparr.products;

import lombok.Getter;
import lombok.Setter;
import pl.epoint.mparr.categories.CategoryDto;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private CategoryDto category;
}
