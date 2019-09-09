package categories;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import products.Product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;


    protected Category() {}

    public Category(@NotBlank String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "ID: "+id+", name: "+name;
    }

}
