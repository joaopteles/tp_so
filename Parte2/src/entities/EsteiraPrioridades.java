package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class EsteiraPrioridades extends Thread {

    // #region CONSTANTES
    protected static final int PACOTE_VOL_MAX = 5000;
    protected static final int VOL_PRODUTO = 250;
    protected static final double PACOTE_TEMPO_MEDIO = 5;
    protected static final double TEMPO_TRANSICAO = 0.5;
    protected static final double TEMPO_FUNCIONAMENTO = 32400; // 8 h a 17 h em segundos
    // #endregion

    // #region Getters e setters
    public int getPacoteNumero() {
        return pacoteNumero;
    }

    public void setPacoteNumero(int pacoteNumero) {
        this.pacoteNumero = pacoteNumero;
    }

    public List<Pedido> getListaTempoProduzido() {
        return listaTempoProduzido;
    }

    public void setListaTempoProduzido(List<Pedido> listaTempoProduzido) {
        this.listaTempoProduzido = listaTempoProduzido;
    }

    public void setHoraFinal(int horaFinal) {
        this.horaFinal = horaFinal;
    }

    public void setMinutoFinal(int minutoFinal) {
        this.minutoFinal = minutoFinal;
    }

    public void setSegundoFinal(int segundoFinal) {
        this.segundoFinal = segundoFinal;
    }

    public void setSegundosDecorridos(double segundosDecorridos) {
        this.segundosDecorridos = segundosDecorridos;
    }

    public int getPedidosAtendidos() {
        return pedidosAtendidos;
    }
    // #endregion

    // #region ATRIBUTOS
    protected List<Pedido> pedidos;
    protected int horaFinal;
    protected int minutoFinal;
    protected int segundoFinal;
    protected double segundosDecorridos;
    protected int pedidoNumero;
    protected int pacoteNumero;
    List<Pedido> listaTempoProduzido = new ArrayList<>();
    protected Semaphore bloquearLista;
    protected int pedidosAtendidos;
    // #endregion

    // #region CONSTRUTOR
    public EsteiraPrioridades(List<Pedido> pedidos, Semaphore s) {
        this.pedidos = pedidos;
        bloquearLista = s;
        pedidosAtendidos = 0;
    }
    // #endregion

    // #region MÉTODOS

    public String getTempoDecorrido() {
        StringBuilder string = new StringBuilder();

        atualizaTempoTotal();

        if (this.horaFinal < 10) {
            string.append("0" + this.horaFinal);
        } else {
            string.append(this.horaFinal);
        }

        if (this.minutoFinal < 10) {
            string.append(":0" + this.minutoFinal);
        } else {
            string.append(":" + this.minutoFinal);
        }

        if (this.segundoFinal < 10) {
            string.append(":0" + this.segundoFinal);
        } else {
            string.append(":" + this.segundoFinal);
        }

        return string.toString();
    }

    protected void atualizaTempoTotal() {
        int horaInicio = 8;
        int segundos = (int) Math.ceil(this.segundosDecorridos);
        int minutos = segundos / 60;
        int horas = segundos / 60 / 60;

        setSegundoFinal(segundos % 60);
        setMinutoFinal(minutos % 60);
        setHoraFinal(horaInicio + horas);
    }

    public void ligarEsteira() {

        // empacota um pacote do pedido e depois verifica novamente qual é o menor
        // pedido
        while (!pedidos.isEmpty()) {

            int quantEsteira = PACOTE_VOL_MAX / VOL_PRODUTO;
            int tempoGastoNoPedido = 0;

            try {
                bloquearLista.acquire();
                if (!pedidos.isEmpty()) {

                    Pedido pedido = encontrarMenorPrazo((int) segundosDecorridos / 60);
                    pedido.setNumProdutosPendentes(pedido.getNumProdutosPendentes() - quantEsteira);

                    if (pedido.getNumProdutosPendentes() <= 0) {
                        pedidosAtendidos++;
                        pedidos.remove(pedido);
                        listaTempoProduzido.add(pedido);
                        pedido.setMomentoProduzidoSegundos((int) segundosDecorridos);
                    }

                    double tempoGastoNoPacote = PACOTE_TEMPO_MEDIO + TEMPO_TRANSICAO;
                    tempoGastoNoPedido += tempoGastoNoPacote;
                    segundosDecorridos += (PACOTE_TEMPO_MEDIO + TEMPO_TRANSICAO);
                    pacoteNumero++;

                }
                bloquearLista.release();
                // 17 h a esteira para de funcionar
                if (segundosDecorridos + tempoGastoNoPedido >= TEMPO_FUNCIONAMENTO) {
                    break;
                }

            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public int pedidosAtendidosAteHorario(int hora, int min) {
        int total = 0;
        int tamanho = listaTempoProduzido.size();
        int segundos = (min * 60) + (((hora - 8) * 60) * 60);
        Collections.sort(listaTempoProduzido);
        for (int i = 0; i < tamanho; i++) {
            if (listaTempoProduzido.get(i).getMomentoProduzidoSegundos() < segundos) {
                total++;
            } else {
                tamanho = listaTempoProduzido.size();
            }
        }
        return total;
    }

    public int getSegundosDecorridos() {
        return (int) Math.ceil(segundosDecorridos);
    }

    public String relatorio() {

        String string = "##### RELATORIO Prioridades #####\n" +
                "Pedidos atendidos: " + pedidosAtendidos + "\n" +
                "Tempo total: " + (getSegundosDecorridos()) + " segundos \n" +
                "Hora inicio: 08:00\nHora Fim: " + getTempoDecorrido() + "\n" +
                "Pedidos produzidos ate 12H: " + pedidosAtendidosAteHorario(12, 00) + "\n";
        return string;
    }

    public int quantidadePacotesPedido(Pedido p) {
        return p.getNumProdutos() * VOL_PRODUTO / PACOTE_VOL_MAX;
    }

    /**
     * Encontra o menor pedido no tempo atual
     * 
     * @param tempo
     * @return
     */
    public Pedido encontrarMenorPrazo(int tempo) {
        Pedido menor = pedidos.get(0);
        for (Pedido pedido : pedidos) {

            if (pedido.getMomentoChegadaMinuto() <= tempo
                    && pedido.getPrazoMinuto() < menor.getPrazoMinuto()) {
                menor = pedido;
            }

        }
        return menor;
    }

    @Override
    public void run() {
        ligarEsteira();

    }

}
