//Declaração do Pacote
package listacompra;
// Importações das Classes
import listacompra.model.Produto;
import listacompra.service.ListaCompra;
import java.util.Scanner;

// Metódo Main
public class Main {
    public static void main(String[] args) { //Método Static Void obrigatório para iniciar a execução

        Scanner scanner = new Scanner(System.in); // instância o leitor que monitora a digitação
        ListaCompra compra = ListaCompra.getInstancia(); //Recupera a instância única (Singleton)

        int opcao = 0; //inicia a váriavel option com valor 0
        //Criar o Loop de repetição
        do { 
            // Imprime a lista de opçãoe (Menu)
            System.out.println("\n============= MENU LISTA DE COMPRA =============");
            System.out.println("1. Adicionar produto");
            System.out.println("2. Remover produto");
            System.out.println("3. Ver valor total / Extrato");
            System.out.println("4. Cancelar compra");
            System.out.println("5. Finalizar compra");
            System.out.println("------------------------------------------------");           
            System.out.print("Escolha uma opção: ");
            
            // Valida se foi digitado 123... ou AaCcBb...
            try {
                opcao = Integer.parseInt(scanner.nextLine()); //converte um textos em um número inteiro
            } catch (NumberFormatException e) { // Na conversão se houve uma exeção
                System.out.println("Por favor, digite um número válido."); //Imprime valor invalido
                continue; // Continua o LOOP
            }
            // Switch case para identificação a opção digitada e qual será a ação.
            switch (opcao) { // utiliza a varíavel opção que contém o valor digitado
                case 1: // se o valor for 1 - Adicionar produto
                    System.out.print("Digite o nome do produto: "); //Imprime
                    String nome = scanner.nextLine(); // pegar o valor digitado pelo usuário

                    System.out.print("Digite o valor unitário: R$ "); //Imprime
                    double valor = Double.parseDouble(scanner.nextLine()); // pegar o valor digitado pelo usuário

                    System.out.print("Digite a quantidade: "); //Imprime
                    int qtd = Integer.parseInt(scanner.nextLine()); // pegar o valor digitado pelo usuário

                    // Criar o objeto compra na memoria e chama o metódo para adicionar produto a lista
                    compra.adicionarProduto(new Produto(nome, valor, qtd));
                    break; // Interrompe o Swicht Case

                case 2: // se o valor for 2 - Remover produto
                    // Verificar se a listaProduto está vazia
                    if (compra.estaVazio()) { // Chama o metódo para validar se listaProduto está vazia
                        // Se o retorno for True imprime a mensagem
                        System.out.println("O carrinho já está vazio. Não há o que remover.");
                    } else { // se o retorno for false
                        compra.exibirExtrato(); // chama o metódo e imprime o extrato da listaProduto
                        // Imprime a mensagem
                        System.out.print("Digite o NÚMERO (índice) do produto que deseja remover: ");
                        // Valida se foi digitado 123... ou AaCcBb...
                        try {
                            int idcRemover = Integer.parseInt(scanner.nextLine()); //converte um textos em um número inteiro
                            if (compra.removerProduto(idcRemover)) { //Chama o método para remove produto se retorno for true
                            } else { // Se o retorno for falso
                                // Imprime a mensagem
                                System.out.println("=> Índice inválido! Nenhum produto foi removido.");
                            }
                        } catch (NumberFormatException e) { // Na conversão se houve uma exeção
                            System.out.println("=> Por favor, digite um número de índice válido.");  //Imprime valor invalido
                        }
                    }
                    break; // Interrompe o Swicht Case

                case 3: // se o valor for 3 - Ver valor total / Extrato
                    compra.exibirExtrato(); // Chama o metódo para imprimir extrado
                    break; // Interrompe o Swicht Case

                case 4: // se o valor for 4 - Cancelar compra
                    System.out.println("=> Compra cancelada. Esvaziando o carrinho..."); // Imprime a mnensagem
                    compra.limparLista(); //chama o metódo para limpa a listaProduto
                    opcao = 5; // Fecha o programa após cancelar a compra
                    break; // Interrompe o Swicht Case

                case 5: // se o valor for 5 - Finalizar compra
                    if (compra.estaVazio()) { // Chama o metódo para validar se listaProduto está vazia
                        // Se Retornar True Imprime a mensagem
                        System.out.println("=> Carrinho vazio. Sistema encerrado.");
                    } else { // Se retornar false
                        System.out.println("\n=== COMPRA FINALIZADA COM SUCESSO ==="); // Imprime a mensagem
                        compra.exibirExtrato();  // Chama o metódo para imprimir extrado
                        System.out.println("Obrigado pela preferência!"); // Imprime a mensagem
                        compra.limparLista(); //chama o metódo para limpa a listaProduto
                    }
                    break; // Interrompe o Swicht Case

                default: // Se forma digitado uma opção (número) diferente
                    System.out.println("Opção inválida! Tente novamente."); // Imprime a mensagem
            }

        } while (opcao != 5); // Enquanto a opção digitada for diferente de 5, volta ao inicio do loop

        scanner.close(); // Finaliza o monitoramento do teclado
    }
}
