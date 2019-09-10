package pl.epoint.mparr.products;

import pl.epoint.mparr.categories.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "categories_id", nullable = false)
    private Category category;

    public Product(String name) {
        this.name = name;
    }

}
