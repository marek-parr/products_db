package products;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProductRepository extends CrudRepository<Product, Long> {
}
