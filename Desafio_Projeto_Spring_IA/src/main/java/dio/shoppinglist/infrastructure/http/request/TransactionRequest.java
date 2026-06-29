package dio.shoppinglist.infrastructure.http.request;

import dio.shoppinglist.application.input.PersistTransactionInput;
import dio.shoppinglist.domain.Category;

public record TransactionRequest(String description, Category category, long amount, double price) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description, category, amount, price);
    }
}
