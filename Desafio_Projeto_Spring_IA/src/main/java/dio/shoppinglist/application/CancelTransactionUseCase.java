package dio.shoppinglist.application;

import dio.shoppinglist.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

 @Service
 public class CancelTransactionUseCase {
     private final TransactionRepository transactionRepository;

     public CancelTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
     }

     @Tool(name = "clear-or-cancel-list", description = "Limpa, cancela ou reinicia a lista de compras atual, apagando todos os produtos adicionados")
     public String execute() {
        // 1. Executa a limpeza total no banco de dados através do repositório
        transactionRepository.removeAll();

        // 2. Retorna a mensagem de confirmação para o usuário/IA
        return "A lista de compras foi cancelada e todos os produtos foram limpos.";
     }
 }

