package entities;

public class Pedido implements Comparable<Pedido>{

    private String cliente;
    private int numProdutos;
    private int prazo;
    private double momentoProduzidoSegundos;

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

	@Override
	public int compareTo(Pedido o) {
		return this.prazo - o.getPrazo();
	}

    public double getMomentoProduzidoSegundos() {
        return momentoProduzidoSegundos;
    }
    public void setMomentoProduzidoSegundos(double s) {
        momentoProduzidoSegundos = s;
    }
}
