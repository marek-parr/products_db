package pl.epoint.mparr.products;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();
}
