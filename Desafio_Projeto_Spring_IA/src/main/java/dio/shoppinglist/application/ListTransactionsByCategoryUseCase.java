package dio.shoppinglist.application;

import dio.shoppinglist.application.output.TransactionOutput;
import dio.shoppinglist.domain.Category;
import dio.shoppinglist.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionsByCategoryUseCase {
    // Repositório de domínio para comunicação com a camada de persistência de dados
    private final TransactionRepository transactionRepository;


    public ListTransactionsByCategoryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "list-transactions-by-category", description = "Lista transações financeiras por categoria")
    public List<TransactionOutput> execute(@ToolParam(description = "Categoria de uma transação") Category category) {
        return transactionRepository.findAllByCategory(category).stream().map(TransactionOutput::from).toList();
    }
}
