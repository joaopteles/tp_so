package entities;

public class Pedido {

    private String cliente;
    private int numProdutos;
    private int prazo;

    public Pedido(String cliente, int numProdutos, int prazo) {
        this.cliente = cliente;
        this.numProdutos = numProdutos;
        this.prazo = prazo;
    }

    public String getCliente() {
        return cliente;
    }

    public int getNumProdutos() {
        return numProdutos;
    }

    public int getPrazo() {
        return prazo;
    }

    @Override
    public String toString() {
        return cliente + " " +
                numProdutos + " "
                + prazo;
    }
}
