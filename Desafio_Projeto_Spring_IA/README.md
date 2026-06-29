# 🛒 Shopping List AI & Operations API

Este é um projeto em **Java** desenvolvido para simular o funcionamento de uma lista de compras de supermercado, através da API Spring Boot para gerenciamento de listas de compras de supermercado inteligente. O sistema utiliza o **Spring AI** com o modelo **Gemini** para processar áudios nativamente e executar operações na lista via chamadas de ferramentas (*Function Calling*), além de expor endpoints REST tradicionais.

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.x**
* **Spring AI** (Integração nativa com Gemini)
* **Spring Data JPA** (Persistência de dados)
* **MySql** (Armazenamento em banco relacional)

---

## 🛠️ Arquitetura e Fluxos do Sistema

O projeto segue os princípios da Clean Architecture / Domain-Driven Design (DDD), dividindo-se em camadas de **Domínio** (regras de negócio brutas), **Aplicação** (casos de uso anotados como ferramentas de IA) e **Infraestrutura** (controladores HTTP e repositórios JPA).

Abaixo estão descritos os fluxos lógicos de cada operação mapeada no sistema:

### 1. Criar / Adicionar Item (`PersistTransactionUseCase`)
Adiciona um novo produto ao carrinho informando descrição, categoria, quantidade e preço unitário.
* **Via API REST:** `POST /transactions` recebendo um JSON no corpo da requisição.
* **Via IA:** O Gemini processa o comando (texto/áudio) e invoca a ferramenta `@Tool(name = "persist-transaction")`.
* **Fluxo:** O caso de uso recebe os dados através do `PersistTransactionInput`, instancia a entidade de domínio `Transaction` e a salva no banco de dados através do `TransactionRepository`.

### 2. Remover Item (`RemoveTransactionUseCase`)
Remove um produto específico da lista comparando textualmente o nome fornecido.
* **Ferramenta de IA:** `@Tool(name = "remove-transaction")`
* **Fluxo:** 1. O caso de uso valida se a descrição do produto foi informada.
  2. Busca todos os itens ativos no banco utilizando `transactionRepository.findAll()`.
  3. Filtra a lista em memória ignorando maiúsculas/minúsculas (`equalsIgnoreCase`) para encontrar o item correspondente.
  4. Remove o registro físico do banco se encontrado, ou lança um erro caso o item não exista.

### 3. Calcular Total Atual (`CalculateTransactionUseCase`)
Gera um relatório rápido com a soma do valor total acumulado na lista atual sem limpá-la.
* **Ferramenta de IA:** `@Tool(name = "calculate-total-value")`
* **Fluxo:** O caso de uso busca todos os produtos, realiza o cálculo somando a multiplicação de `(Quantidade × Preço)` de cada item e retorna uma mensagem de texto formatada em Real (R$).

### 4. Cancelar / Limpar Lista (`CancelTransactionUseCase`)
Esvazia completamente o banco de dados, descartando a lista atual de compras.
* **Ferramenta de IA:** `@Tool(name = "clear-or-cancel-list")`
* **Fluxo:** Executa diretamente o método `removeAll()` no repositório, que por sua vez aciona o `deleteAll()` do Spring Data JPA na tabela de entidades, zerando o carrinho.

### 5. Finalizar Lista (`FinishTransactionUseCase`)
Encerra a seção de compras atual apresentando o resumo financeiro final e limpando a base para o próximo uso.
* **Ferramenta de IA:** `@Tool(name = "finish-shopping-list")`
* **Fluxo:**
  1. O caso de uso coleta todos os itens e calcula o valor total geral de forma idêntica ao caso de uso de cálculo.
  2. Executa a limpeza total das tabelas via `transactionRepository.removeAll()`.
  3. Retorna uma mensagem de sucesso com o valor total fechado da compra.

---

## 🎙️ Processamento de Áudio Nativo com Gemini

A API possui um endpoint inteligente capaz de receber arquivos de áudio diretamente do usuário:

* **Endpoint:** `POST /transactions/ai`
* **Content-Type:** `multipart/form-data`

O `TransactionController` captura o arquivo de áudio enviado, identifica dinamicamente o seu tipo MIME (ex: `audio/mp3`, `audio/wav`) e despacha o arquivo binário diretamente para a API do Gemini utilizando o método `.media()` do `ChatClient`. A IA interpreta a intenção da fala do usuário e decide autonomamente qual das ferramentas (`@Tool`) mapeadas acima deve ser acionada.

## 🖥️ Demonstração

Após iniciar o programa, deve-se utilizar o endpoint para o envio dos arquivo de audio para cada ação:

* **LISTA DE AUDIOS E FUNÇÕES:**
```text
✓ ADICIONAR PRODUTO:
  01-ProdutoCocaCola.mp3
	02-ProdutoArroz.mp3
	03-ProdutoCafe.mp3
	04-ProdutoFeijao.mp3
✓ REMOVER PRODUTO:
	05-RemoverCafe.mp3
✓ VALOR TOTAL:
	06-ValorTotal.mp3
✓ CANCELAR:
	07-Cancelar.mp3
✓ FINALIZAR:
	08-Finalizar.mp3
```
* **ENDPOINT:**
```text
POST http://localhost:8080/transactions/ai HTTP/1.1
Content-Type: multipart/form-data; boundary=boundary
--boundary
Content-Disposition: form-data; name="file"; filename="[ARQUIVO DE AUDIO]"
Content-Type: audio/mpeg
< ./src/test/resources/audio/[ARQUIVO DE AUDIO]
--boundary--
```
* **CURL:**
```text
curl -X POST http://localhost:8080/transactions/ai
  -H "Content-Type: multipart/form-data"
  -F "file=@[ARQUIVO DE AUDIO];type=audio/mpeg"
```
* **PROMPT:**
```text
curl -X POST http://localhost:8080/transactions/ai ^
  -H "Content-Type: multipart/form-data" ^
  -F "file=@[ARQUIVO DE AUDIO];type=audio/mpeg"
```
---

## 📂 Estrutura de Diretórios

```text
desafio_projeto_ia/
	└─ src/
		└─ main/
		│	└─ java/...
		│		└─ shoppingList\
		│			└─ application\
		│			│	└── input\
		│			│	│	└─ Record: PersistTransactionInput.java
		│			│	│	└─ Record:RemoveTransactionInput.java
		│			│	└─ output\
		│			│	│	└─ Record: TransactionOutput.java
		│			│	│─ Class: CalculateTransactionUseCase.java
		│			│	│─ Class: CancelTransactionUseCase.java
		│			│	│─ Class: FinishTransactionUseCase.java
		│			│	│─ Class: ListTransactionsByCategoryUseCase.java
		│			│	│─ Class: PersistTransactionUseCase.java
		│			│	│─ Class: RemoveTransactionUseCase.java
		│			└─ domain\
		│			│	└─ Enum: Category.java
		│			│	└─ Class: Transaction.java
		│			│	└─ Record: TransactionId.java
		│			│	└─ Interface: TransactionRepository.java
		│			└─ infrastructure\
		│			│	└─ http\
		│			│	│	└─ Class: TransactionController.java
		│			│	│	└─ request\
		│			│	│	│	└─ Record: TransactionRequest.java
		│			│	│	└─ response\
		│			│	│		└─ Record: TransactionResponse.java
		│			│	└─ persistence\
		│			│		└─ entity\
		│			│		│	└─ Class: TransactionEntity.java
		│			│		└─ repository\
		│			│			└─ Class: JpaTransactionRepository.java
		│			│			└─ Interface: TransactionRepository.java
		│			└─ Class: BudgetingApplication.java
		│			└─ resources\
		│				└─ application.properties
		└─ test\
			...
			└─ resources\
				└─ audio\
					└─ 01-ProdutoCocaCola.mp3
					└─ 02-ProdutoArroz.mp3
					└─ 03-ProdutoCafe.mp3
					└─ 04-ProdutoFeijao.mp3
					└─ 05-RemoverCafe.mp3
					└─ 06-ValorTotal.mp3
					└─ 07-Cancelar.mp3
					└─ 08-Finalizar.mp3