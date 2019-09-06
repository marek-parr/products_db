package products;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;


    protected Category() {}

    public Category(@NotBlank String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "ID: "+id+", name: "+name;
    }

}
