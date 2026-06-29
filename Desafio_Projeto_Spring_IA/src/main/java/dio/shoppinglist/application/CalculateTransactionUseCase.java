package dio.shoppinglist.application;

import dio.shoppinglist.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class CalculateTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public CalculateTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "calculate-total-value", description = "Calcula o valor total somando todos os produtos ativos na lista de compras atual")
    public String execute() {
        // 1. Procura todas as transações atualmente guardadas no banco de dados
        var transactions = transactionRepository.findAll();

        // 2. Executa o cálculo somando a multiplicação de (Quantidade * Preço) de cada item
        double total = transactions.stream()
                .mapToDouble(t -> t.getAmount() * t.getPrice())
                .sum();

        // 3. Retorna uma mensagem formatada e amigável para o utilizador/IA
        return String.format("O valor total atual da sua lista de compras é de R$ %.2f", total);
    }
}
