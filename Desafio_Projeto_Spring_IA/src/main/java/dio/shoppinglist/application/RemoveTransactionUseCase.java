package dio.shoppinglist.application;

import dio.shoppinglist.application.input.PersistTransactionInput;
import dio.shoppinglist.application.input.RemoveTransactionInput;
import dio.shoppinglist.application.output.TransactionOutput;
import dio.shoppinglist.domain.Transaction;
import dio.shoppinglist.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class RemoveTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public RemoveTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "remove-transaction", description = "Remove um item existente da lista de compra baseado no nome ou descrição fornecida.")
    public TransactionOutput execute(RemoveTransactionInput input) { // <--- Atualizado aqui
        if (input.description() == null || input.description().isBlank()) {
            throw new IllegalArgumentException("O nome do produto a ser removido não foi fornecido.");
        }

        // 1. Procura o item na lista atual
        var itemParaRemover = transactionRepository.findAll().stream()
                .filter(t -> t.getDescription().equalsIgnoreCase(input.description().trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produto '" + input.description() + "' não encontrado na lista."));

        // 2. Remove o item real encontrado
        var transaction = transactionRepository.remove(itemParaRemover);

        return TransactionOutput.from(transaction);
    }
}