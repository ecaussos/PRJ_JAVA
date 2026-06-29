package dio.shoppinglist.application.input;

import dio.shoppinglist.domain.Category;
import org.springframework.ai.tool.annotation.ToolParam;

public record PersistTransactionInput(@ToolParam(description = "Descrição do produto") String description,
                                      @ToolParam(description = "Categoria do produto") Category category,
                                      @ToolParam(description = "Quantidade de produto") long amount,
                                      @ToolParam(description = "Valor do produto") double price
) {
}
