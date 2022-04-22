package entities;

public class Fila<T>{
    protected Pedido[] pedidos; 
	protected int tamanho;

	public Fila(int tamanho) {
		this.tamanho = tamanho;
	}

	public Fila() {
		this(50000);
	}
	
	public void enfileira(Pedido elemento) {
		this.adiciona(elemento);
	}
	
	public Pedido desenfileira() {
		if (this.estaVazia()) {
			return null;
		}
		
		Pedido pedidoRemovido = this.pedidos[0];
		
		this.remove(0);
		
		return pedidoRemovido;
	}
	
	public boolean estaVazia() {
		return this.tamanho == 0;
	}

    /*
    * Adiciona um pedido no final da lista
    * Retorna o pedido caso o mesmo tenha sido adicionado com sucesso e false caso contrário
    */
	protected boolean adiciona(Pedido elemento) {
		if (this.tamanho < this.pedidos.length) {
			this.pedidos[this.tamanho] = elemento;
			this.tamanho++;
			return true;
		} 
		return false;
	}

    /*
    * Adiciona um pedido a partir de uma posição específica
    * Retorna o pedido caso o mesmo tenha sido adicionado com sucesso e false caso contrário
    */
	protected boolean adiciona(int posicao, Pedido pedido) {
		if (posicao < 0 || posicao > tamanho) {
			throw new IllegalArgumentException("Posição inválida!");
		}

		//mover todos os pedidos
		for (int i=this.tamanho-1; i>=posicao; i--) {
			this.pedidos[i+1] = this.pedidos[i];
		}
		this.pedidos[posicao] = pedido;
		this.tamanho++;

		return true;
	}
	
	protected void remove(int posicao) {
		if (!(posicao >= 0 && posicao < tamanho)) {
			throw new IllegalArgumentException("Posição inválida!");
		}
		for (int i=posicao; i<tamanho-1; i++) {
			pedidos[i] = pedidos[i+1];
		}
		tamanho--;
	}

	public int getTamanho() {
		return this.tamanho;
	}

	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder("Pedidos na lista:\n");
		str.append("- ");
		
		for (int i=0; i<this.tamanho-1; i++) {
			str.append(this.pedidos[i]);
			str.append(";\n- ");
		}
		
		if (this.tamanho>0) {
			str.append(this.pedidos[this.tamanho-1]);
		}
		
		str.append("--------------------");
		str.append("\nFim da lista\n");
		
		return str.toString();
	}
}
