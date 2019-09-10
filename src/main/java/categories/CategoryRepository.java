package categories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
interface CategoryRepository extends CrudRepository<Category, Long> {
    @Override
    List<Category> findAll();
}
