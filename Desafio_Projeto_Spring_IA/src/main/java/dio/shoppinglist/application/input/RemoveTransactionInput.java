package dio.shoppinglist.application.input;

import org.springframework.ai.tool.annotation.ToolParam;

public record RemoveTransactionInput(
        @ToolParam(description = "Descrição do produto") String description
        ){
}