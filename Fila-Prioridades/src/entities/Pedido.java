package entities;

public class Pedido implements Comparable{

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

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    @Override
    public String toString() {
        return cliente + " " +
                numProdutos + " "
                + prazo;
    }

    @Override
    public int compareTo(Object o) {
        Pedido outro = (Pedido)o;

        if(this.prazo > outro.getPrazo()) {
            return -1;
        } else if(this.prazo < outro.getPrazo()) {
            return 1;
        }
        return 0;
    }
}
