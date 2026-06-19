//Declaração do Pacote
package listacompra.model;

//Declaração da classe pública
public class Produto {
		// Private (Encapsulamento) nenhuma classe pode alterar ou ler
		private String nome;
		private double preco;
		private int quantidade;
		// Construtor e é executado automáticamente através do comando new Produto...
		public Produto(String nome, double preco, int quantidade) {
			//Parâmetros recebidos no momento da criação
			this.nome = nome; // "this" diferenciar atributo das variáveis
			this.preco = preco;
			this.quantidade = quantidade;
		}
		// Métodos públicos de acesso (get) aos produtos
		public String getNome() { return nome; }
		public double getPreco() { return preco; }
		public int getQuantidade() { return quantidade; }

		/* A classe não precisa de escrita (set), tornando os objetos desta classe imutáveis.
		   Se fornecessário editar algum produto o sistem deverá criar um novo objeto para evitar bug de estado de software.
		*/
}