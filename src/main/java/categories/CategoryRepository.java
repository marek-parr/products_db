package categories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
interface CategoryRepository extends CrudRepository<Category, Long> {
}
