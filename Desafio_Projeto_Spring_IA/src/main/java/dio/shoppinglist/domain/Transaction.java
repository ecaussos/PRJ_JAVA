package dio.shoppinglist.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {
    private TransactionId id;
    private String description;
    private Category category;
    private long amount;
    private double price;


    public Transaction(String description, Category category, long amount, double price) {
        this.id = new TransactionId();
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.price = price;

    }
}
