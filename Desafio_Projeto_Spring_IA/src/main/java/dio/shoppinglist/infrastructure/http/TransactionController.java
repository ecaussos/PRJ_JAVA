package dio.shoppinglist.infrastructure.http;

import dio.shoppinglist.application.*;
import dio.shoppinglist.domain.Category;
import dio.shoppinglist.infrastructure.http.request.TransactionRequest;
import dio.shoppinglist.infrastructure.http.response.TransactionResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase;
    private final ChatClient chatClient;

    public TransactionController(PersistTransactionUseCase persistTransactionUseCase,
                                 ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase,
                                 RemoveTransactionUseCase removeTransactionUseCase,
                                 CalculateTransactionUseCase calculateTransactionUseCase,
                                 CancelTransactionUseCase cancelTransactionUseCase,
                                 FinishTransactionUseCase finishTransactionUseCase,
                                 @Value("classpath:prompts/system-message.st") Resource systemPrompt,
                                 ChatClient.Builder chatClientBuilder) throws IOException {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionsByCategoryUseCase = listTransactionsByCategoryUseCase;

        // Injeta todas as ferramentas individuais para o Gemini conseguir chamá-las de forma independente
        this.chatClient = chatClientBuilder
                .defaultSystem(systemPrompt.getContentAsString(Charset.defaultCharset()))
                .defaultTools(
                        persistTransactionUseCase,
                        listTransactionsByCategoryUseCase,
                        removeTransactionUseCase,
                        calculateTransactionUseCase,
                        cancelTransactionUseCase,
                        finishTransactionUseCase
                )
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        var transaction = persistTransactionUseCase.execute(request.toInput());
        return TransactionResponse.from(transaction);
    }

    @GetMapping("/{category}")
    public List<TransactionResponse> readTransactions(@PathVariable Category category) {
        return listTransactionsByCategoryUseCase.execute(category).stream().map(TransactionResponse::from).toList();
    }

    /**
     * O Gemini processa o áudio nativamente. Passamos o arquivo direto no .media() do ChatClient.
     * Como não há mais TTS ativo, alteramos o retorno para String (text/plain).
     */
    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> transcribe(@RequestParam("file") MultipartFile file) {
        // Captura o tipo do áudio dinamicamente (ex: audio/mp3, audio/wav)
        var mimeType = MimeTypeUtils.parseMimeType(file.getContentType());

        var result = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text("Analise este áudio contendo uma descrição de lista de compra e execute a ação correspondente.")
                        .media(mimeType, file.getResource()) // Passa o áudio direto pro Gemini
                )
                .call()
                .content();

        return ResponseEntity.ok(result);
    }
}