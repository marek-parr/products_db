package pl.epoint.mparr.categories;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.epoint.mparr.products.Product;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category(@NotBlank String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: "+id+", name: "+name;
    }

}
