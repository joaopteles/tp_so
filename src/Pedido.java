public class Pedido {

    private int numProdutos;
    private int prazo;

    public Pedido(int n, int p) {
        numProdutos = n;
        prazo = p;
    }

    public int getnumProdutos() {
        return numProdutos;
    }

    public int getPrazo() {
        return prazo;
    }

    public String toString() {
        return numProdutos + " " + prazo;
    }

}
