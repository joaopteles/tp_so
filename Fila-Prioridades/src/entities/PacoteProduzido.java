package entities;

public class PacoteProduzido implements Comparable <PacoteProduzido>{
    private int pacoteNumero;
    private double momentoProduzidoSegundos;
    private String cliente;
    private int pedidoNumero;
    private int prazo;


    public PacoteProduzido(int pacoteNumero,
                           double momentoProduzidoSegundos,
                           String cliente,
                           int pedidoNumero,
                           int prazo) {
        this.pacoteNumero = pacoteNumero;
        this.momentoProduzidoSegundos = momentoProduzidoSegundos;
        this.cliente = cliente;
        this.pedidoNumero = pedidoNumero;
        this.prazo = prazo;
    }

    public int getPacoteNumero() {
        return pacoteNumero;
    }

    public double getMomentoProduzidoSegundos() {
        return momentoProduzidoSegundos;
    }

    public String getCliente() {
        return cliente;
    }

    public int getPrazo() {
        return prazo;
    }

    public int getPedidoNumero() {
        return pedidoNumero;
    }

    @Override
    public int compareTo(PacoteProduzido o) {

        if(this.momentoProduzidoSegundos < o.getMomentoProduzidoSegundos()){
            return -1;
        } else if(this.momentoProduzidoSegundos > o.getMomentoProduzidoSegundos()){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Pacote Num= " + pacoteNumero +
                ", Momento= " + momentoProduzidoSegundos;
    }
}