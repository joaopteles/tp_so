public class FilaPrioridade<T> extends Fila<T>{

    public void enfileira(Pedido pedido) {
        int i;
        for(i = 0; i < this.pedidos.length; i++) {
            if(pedido.compareTo(this.pedidos[i]) < 1) {
                this.adiciona(i, pedido);
            }
        }
        this.adiciona(i, pedido);
    }
}
