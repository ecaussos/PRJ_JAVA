package dio.shoppinglist.domain;

import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

    Transaction remove(Transaction transaction);

    List<Transaction> findAllByCategory(Category category);

    List<Transaction> findAll();

    void removeAll();
}

