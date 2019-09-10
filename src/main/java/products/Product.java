package products;

import categories.Category;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "categories_id", nullable = false)
    private Category category;

    protected Product(){}

    public Product(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "ID: "+id+", name: "+name+", category: "+category.getName();
    }

}
