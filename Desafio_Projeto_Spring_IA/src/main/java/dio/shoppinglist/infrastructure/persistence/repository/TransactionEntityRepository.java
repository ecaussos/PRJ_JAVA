package dio.shoppinglist.infrastructure.persistence.repository;

import dio.shoppinglist.domain.Category;
import dio.shoppinglist.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface TransactionEntityRepository extends CrudRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findAllByCategory(Category category);
}
