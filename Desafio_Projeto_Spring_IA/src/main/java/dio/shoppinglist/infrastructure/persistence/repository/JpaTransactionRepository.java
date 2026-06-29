package dio.shoppinglist.infrastructure.persistence.repository;

import dio.shoppinglist.domain.Category;
import dio.shoppinglist.domain.Transaction;
import dio.shoppinglist.domain.TransactionRepository;
import dio.shoppinglist.infrastructure.persistence.entity.TransactionEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository
public class JpaTransactionRepository implements TransactionRepository {
    private final TransactionEntityRepository transactionEntityRepository;

    public JpaTransactionRepository(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = TransactionEntity.from(transaction);
        return transactionEntityRepository.save(entity).toDomain();
    }

    @Override
    @Transactional
    public Transaction remove(Transaction transaction) {
        java.util.UUID uuid = java.util.UUID.fromString(transaction.getId().uuid().toString());
        transactionEntityRepository.deleteById(uuid);
        return transaction;
    }
    @Override
    public void removeAll() {
        transactionEntityRepository.deleteAll();
    }

    @Override
    public List<Transaction> findAllByCategory(Category category) {
        return transactionEntityRepository.findAllByCategory(category)
                .stream()
                .map(TransactionEntity::toDomain)
                .toList();
    }

    @Override
    public List<Transaction> findAll() {
        return StreamSupport.stream(transactionEntityRepository.findAll().spliterator(), false)
                .map(TransactionEntity::toDomain)
                .toList();
    }
}
