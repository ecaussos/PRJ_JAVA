# 🛒 Sistema de Lista de Compras:

Este é um projeto em **Java** desenvolvido para simular o funcionamento de uma lista de compras de supermercado baseado em linha de comandos. A aplicação permite criar e gerir uma lista de produtos de forma dinâmica, oferecendo operações como adição de itens, remoção de item, cálculo de subtotais e visualização de extratos.

O principal destaque técnico do projeto é a implementação do padrão de desenho (*Design Pattern*) **Singleton**.

---

## 📐 Padrão de Desenho: Singleton

Para gerir a lista de compras, foi implementado o padrão **Singleton** na classe `ListaCompra`.

* **Porquê utilizar?** Garante a existência de **apenas uma única instância** da lista de compra durante a execução do programa. Previne a criação de múltiplas listas de compras isoladas.

* **Como funciona no código?** * O construtor é definido como privado (`private`), impossibilitando o uso do operador `new` fora da classe.
  * O acesso à lista é centralizado no método estático `getInstancia()`, que realiza uma inicialização adiada (*lazy initialization*) apenas quando a lista é solicitado pela primeira vez.

---

## 📂 Estrutura de Classes e Componentes

O projeto possuí uma arquitetura simples, dividida em três componentes essenciais:

Obs.: No código há diversos comentário que facilitam o entendimento da lógica utilizada.

### 1. `Produto.java` (Entidade / Modelo)
Representa a unidade básica de um item de consumo dentro do sistema.
* **Atributos:** Possui campos privados para o `nome` (String), `preco` unitário (double) e `quantidade` (int).
* **Encapsulamento:** Utiliza métodos de leitura (*getters*) para expor as informações de forma segura.

### 2. `ListaCompra.java` (Regras de Negócio & Singleton)
Concentra a lógica de manipulação e armazenados dos dados.
* **`adicionarProduto(Produto produto)`**: adiciona um produto e emite uma mensagem de confirmação.

* **`removerProduto(indice)`**: Realiza uma busca sequencial na lista de compra e efetua a remoção caso o indice coincida com o informado.

* **`calcularValorTotal()`**: Executa a soma ponderada de todos os produtos multiplicando os respetivos preços unitários pelas suas quantidades.

* **`exibirExtrato()`**: Imprime um relatório em formato de tabela textual detalhando as quantidades, nomes, valores unitários, subtotais por item e o valor total.

### 3. `Main.java` (Interface Utilizador & Controlador)
Responsável pelo desenho do menu interativo na consola e capturar as intenções do utilizador.
* **Controle no Input:** Implementa um bloco `try-catch` para capturar a exceção `NumberFormatException`. Ao digitar valores inválidos no menu, o sistema exibe uma mensagem de aviso e continua funcionando em vez de terminar com um erro.
* **Fluxo de Navegação:** Controlado por um ciclo `do-while` e uma estrutura condicional `switch-case` com 5 opções distintas.

---

## 🌟 Funcionalidades Integradas

* **Validações:** O sistema impede ações incorretas, emite um alerta ao tentar remover itens da lista de compra se ela estiver vazia.
* **Cancelamento:** A opção de cancelar limpa imediatamente a memória através do método `clear()` e encerra a operação.
* **Finalização:** Ao concluir a compra com sucesso, o sistema apresenta a lista completo antes de redefinir o estado da aplicação.

---

## 🖥️ Demonstração do Menu na Console

Ao iniciar o programa, o utilizador depara-se com a seguinte interface textual:

```text
============= MENU LISTA DE COMPRA =============
1. Adicionar produto
2. Remover produto
3. Ver valor total / extrato
4. Cancelar compra
5. Finalizar compra
Escolha uma opção:
```

---

## 📂 Estrutura de Diretórios

```text
Desafio_Projeto_Singleton/
│
└── src/
    └── main/
        └── java/
            └── com/
                └── sistema/
                    └── compras/
                        ├── model/
                        │     └── Produto.java
                        ├── service/
                        │     └── ListaCompra.java
                        └── Main.java
```
