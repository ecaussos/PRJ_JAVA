package dio.shoppinglist.application;

import dio.shoppinglist.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class FinishTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public FinishTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "finish-shopping-list", description = "Finaliza a lista de compras atual e apresenta o resumo com o valor total.")
    public String execute() {
        // 1. Busca todas as transações atuais para calcular o fechamento
        var transactions = transactionRepository.findAll();

        // 2. Calcula o valor total da compra
        double total = transactions.stream()
                .mapToDouble(t -> t.getAmount() * t.getPrice())
                .sum();

        // 3. Limpa a lista de compras do banco (conforme o contrato atualizado para removeAll)
        transactionRepository.removeAll();

        // 4. Retorna o resumo de fechamento para o usuário/IA
        return String.format("Lista de compras finalizada com sucesso! O valor total fechado foi de R$ %.2f.", total);
    }
}

