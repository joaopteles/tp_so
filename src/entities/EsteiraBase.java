package entities;

import java.util.ArrayList;
import java.util.List;

public abstract class EsteiraBase {

    // #region CONSTANTES
    protected static final double PACOTE_VOL_MAX = 5000;
    protected static final double PACOTE_TEMPO_MEDIO = 5;
    protected static final double TEMPO_TRANSICAO = 0.5;
    protected static final double TEMPO_FUNCIONAMENTO = 32400; // 8 h a 17 h em segundos
    // #endregion

    // #region ATRIBUTOS
    protected Pedido[] pedidos;
    protected int horaFinal;
    protected int minutoFinal;
    protected int segundoFinal;
    protected double segundosDecorridos;
    protected int pedidoNumero;
    protected int pacoteNumero;
    List<Pedido> listaTempoProduzido = new ArrayList<>();
    // #endregion

    private List<Pedido> retorno;

    public EsteiraBase(Pedido[] pedidos) {
        this.setPedidos(pedidos);
    }

    protected void atualizaTempoTotal() {
        int horaInicio = 8;
        int segundos = (int) Math.ceil(this.segundosDecorridos);
        int minutos = segundos / 60;
        int horas = segundos / 60 / 60;

        this.segundoFinal = segundos % 60;
        this.minutoFinal = minutos % 60;
        this.horaFinal = horaInicio + horas;
    }

    public abstract void ligarEsteira();

    public int pedidosAtendidosAteHorario(int hora, int min) {
        int total = 0;
        int tamanho = listaTempoProduzido.size();
        int segundos = (min * 60) + (((hora - 8) * 60) * 60);
        for (int i = 0; i < tamanho; i++) {
            if (listaTempoProduzido.get(i).getMomentoProduzidoSegundos() < segundos) {
                total++;
            } else {
                tamanho = listaTempoProduzido.size();
            }
        }
        return total;
    }

    public List<Pedido> getPedidos() {
        List<Pedido> retorno = new ArrayList<Pedido>();
        for (Pedido pedido : pedidos) {
            retorno.add(pedido);
        }
        return retorno;
    }

    public void setPedidos(Pedido[] pedidos) {
        this.pedidos = pedidos;
    }

    public abstract String getTempoDecorrido();

    public abstract int getSegundosDecorridos();
}