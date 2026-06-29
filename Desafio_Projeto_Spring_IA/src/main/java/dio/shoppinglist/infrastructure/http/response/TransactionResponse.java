package dio.shoppinglist.infrastructure.http.response;

import dio.shoppinglist.application.output.TransactionOutput;

public record TransactionResponse(String id, String description, String category, long amount, double price) {
    public static TransactionResponse from(TransactionOutput output) {
        return new TransactionResponse(
                output.id(),
                output.description(),
                output.category()!= null ? output.category().name() : null,
                output.amount(),
                output.price());
    }
}
