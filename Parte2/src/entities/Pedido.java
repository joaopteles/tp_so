package entities;

import java.util.Objects;

public class Pedido implements Comparable<Pedido>{

    private String cliente; //Informado No arquivo de entrada
    private int numProdutos; //Informado No arquivo de entrada
    private int numProdutosPendentes;
    private int prazoMinuto; //Informado No arquivo de entrada
    private int momentoChegadaMinuto; //Informado No arquivo de entrada (em min e passado para segundos)
    private int momentoProduzidoSegundos;

    public Pedido(String cliente, int numProdutos, int prazoMinuto, int momentoChegadaMinuto) {
        this.cliente = cliente;
        this.numProdutos = numProdutosPendentes = numProdutos;
        this.prazoMinuto = prazoMinuto;
        this.momentoChegadaMinuto = momentoChegadaMinuto;
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

    public int getPrazoMinuto() {
        return prazoMinuto;
    }

    public int getMomentoChegadaMinuto() {
        return momentoChegadaMinuto;
    }

    public int getMomentoProduzidoSegundos() {
        return momentoProduzidoSegundos;
    }

    public void setMomentoProduzidoSegundos(int momentoProduzidoSegundos) {
        this.momentoProduzidoSegundos = momentoProduzidoSegundos;
    }

    public void adicionarProdutos(int quantidade) {
        this.numProdutos += quantidade;
    }

    @Override
    public String toString() {
        return cliente + ";"
                + numProdutos + ";"
                + prazoMinuto + ";"
                + momentoChegadaMinuto;
    }

	@Override
	public int compareTo(Pedido o) {
        if(this.prazoMinuto == o.getPrazoMinuto())
            return 0;
        return  (this.prazoMinuto < o.getPrazoMinuto()) ? -1:1;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return getPrazoMinuto() == pedido.getPrazoMinuto() && getMomentoChegadaMinuto() == pedido.getMomentoChegadaMinuto() && getCliente().equals(pedido.getCliente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCliente(), getPrazoMinuto(), getMomentoChegadaMinuto());
    }
}
