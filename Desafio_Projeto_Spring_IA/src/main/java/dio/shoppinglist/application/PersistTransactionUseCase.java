package dio.shoppinglist.application;

import dio.shoppinglist.application.input.PersistTransactionInput;
import dio.shoppinglist.application.output.TransactionOutput;
import dio.shoppinglist.domain.Transaction;
import dio.shoppinglist.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "persist-transaction", description = "Persiste uma nova lista de compra")
    public TransactionOutput execute(PersistTransactionInput input) {
        var transaction = transactionRepository.save(
                new Transaction(input.description(), input.category(), input.amount(), input.price()));

        return TransactionOutput.from(transaction);
    }
}
