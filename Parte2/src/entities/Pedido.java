package entities;

public class Pedido implements Comparable<Pedido>{

    private String cliente; //Informado No arquivo de entrada
    private int numProdutos; //Informado No arquivo de entrada
    private int numProdutosPendentes;
    private int prazo; //Informado No arquivo de entrada
    private int momentoChegadaSegundos; //Informado No arquivo de entrada (em min e passado para segundos)
    private int momentoProduzidoSegundos;

    public Pedido(String cliente, int numProdutos, int prazo, int momentoChegadaSegundos) {
        this.cliente = cliente;
        this.numProdutos = numProdutosPendentes = numProdutos;
        this.prazo = prazo;
        this.momentoChegadaSegundos = momentoChegadaSegundos;
    }

    public String getCliente() {
        return cliente;
    }

    public int getNumProdutos() {
        return numProdutos;
    }

    public int getNumProdutosPendentes() {
        return numProdutosPendentes;
    }

    public void setNumProdutosPendentes(int numProdutosPendentes) {
        this.numProdutosPendentes = numProdutosPendentes;
    }

    public int getPrazo() {
        return prazo;
    }

    public int getMomentoChegadaSegundos() {
        return momentoChegadaSegundos;
    }

    public int getMomentoProduzidoSegundos() {
        return momentoProduzidoSegundos;
    }

    public void setMomentoProduzidoSegundos(int momentoProduzidoSegundos) {
        this.momentoProduzidoSegundos = momentoProduzidoSegundos;
    }

    /**
     * Soma produtos ao pedido atual
     * Desde que os produtos pertençam ao mesmo cliente e tenham o mesmo prazo.
     * @param cliente cliente
     * @param numProdutos a ser somado
     * @param prazo prazo
     */
    public void mesclarPedidoDoMesmoCliente(Pedido mesclarPedido) {

        if(this.cliente == mesclarPedido.getCliente() && this.prazo == mesclarPedido.getPrazo()) {
            this.numProdutos += mesclarPedido.getNumProdutos();
        }
    }

    @Override
    public String toString() {
        return cliente + ";"
                + numProdutos + ";"
                + prazo + ";"
                + momentoChegadaSegundos;
    }

	@Override
	public int compareTo(Pedido o) {
        if(this.prazo == o.getPrazo())
            return 0;
        return  (this.prazo < o.getPrazo()) ? -1:1;
	}

}
