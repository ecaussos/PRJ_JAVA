//Declaração do Pacote
package listacompra.service; 
// Importações das Classes
import listacompra.model.Produto;
import java.util.ArrayList;
import java.util.List;

//Declaração da classe pública
public class ListaCompra {
		//Atributo estático de definição do singleton a lista de compra e produto
		private static ListaCompra instancia;
		// Atributo privado que irá armazenar a lista de produtos da compra
		private List<Produto>listaProdutos;
		// Construtor privado que força o acesso a classe pelo método Get
		private ListaCompra() {
			this.listaProdutos = new ArrayList<>(); //inicializa a lista vazia
		}
		// Gerenciamento da instância 
		public static ListaCompra getInstancia() {//Métodos que pode ser chamado de qualquer lugar
			if (instancia == null) { // Verificar se a instância é nula
				instancia = new ListaCompra(); // Criar a instância
			}
			return instancia; // Retorna a instância que ja existe
		}
		// Método para adicionar produto a listaProduto
		public void adicionarProduto(Produto p) {// Recbebe o objeto produto como parâmetro
			this.listaProdutos.add(p); //Insere o objeto na listaProduto
			//Imprime a mensagem de confirmação
			System.out.println("=> " + p.getQuantidade() + "x " + p.getNome() + " adicionado(s) com sucesso!");
		}
		// Métodos para remover produto da lista utilizando o indece
		public boolean removerProduto(int idc) {
				int idcRemove = idc - 1; 
				
				// Valida se o índice informando é maior que zero e se e menor que o tamanho da listaProduto
				if (idcRemove >= 0 && idcRemove < listaProdutos.size()) { // Se Verdadeiro
					Produto removido = listaProdutos.remove(idcRemove); // Remove produto da listaProduto
					System.out.println("=> " + removido.getNome() + " foi removido com sucesso."); // Imprime mensagem
					return true; // Retornar verdadeiro
				}
				return false; // Retornar falso 
		}
		// Metódo calcular valor total
		public double calcularValorTotal() {
			double total = 0; // inicializar a varíavel
			for (Produto p : listaProdutos) { //Percorre todos os produtos dentro listaProduto 
				total += p.getPreco() * p.getQuantidade(); //Soma ao todal o preço do produto vezes a quantidade
			}
			return total; // Retorna o total ao percorrer toda a listaProduto
		}
		// Metódo para exibir o Extrato da ListaCompra
		public void exibirExtrato() {
			if (listaProdutos.isEmpty()) { // Validar se a lista de produão não é vazia
				System.out.println("\nA lista de compra está vazio."); // Se for fazia imprime a mensagem
				return; // Retorna
			}
			// Montagem do extrato
			System.out.println("\n---------- ITENS NA LISTA DE COMPRAR -----------"); // Cabeçalho
			for (int i = 0; i < listaProdutos.size(); i++) { // Percorre a listaProduto
				Produto p = listaProdutos.get(i); // pega os dados do produto na posição i
				//Imprime a mensagem na tela a cada loop com os dados: 
				System.out.printf("[%d] - %dx %s (Unitário: R$ %.2f) | Subtotal: R$ %.2f\n", 
					(i + 1), // indice
					 p.getQuantidade(), //quantidade
					 p.getNome(), // nome
					 p.getPreco(), // preço
					 (p.getPreco() * p.getQuantidade()) // Calcula preço x quantidade
					);
			}
			// Imprime o rodape chamando o metódo que calcula o valor total
			System.out.printf("VALOR TOTAL ATUAL: R$ %.2f\n", calcularValorTotal());
			System.out.println("\n------------------------------------------------"); // Imprime
		}
		//Metódo para limpar a ListaCompra
		public void limparLista() {
			this.listaProdutos.clear(); //limpa listaProduto com parâmetro clear
		}
		//Método que verica se a lista está vazia
		public boolean estaVazio() {
			return this.listaProdutos.isEmpty(); //Retorna true se a listaProduto estiver vazia, ou false se não
		}
}
