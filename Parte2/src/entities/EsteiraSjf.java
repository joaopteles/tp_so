package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import util.ListaSem;

public class EsteiraSjf extends Thread {

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
    protected ListaSem<Pedido> listaPedidos;

    // #endregion

    public EsteiraSjf(List<Pedido> pedidos) {
        listaPedidos = new ListaSem<>(pedidos);
        this.setPedidos(pedidos);
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

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

            Pedido pedido = encontrarMinimo((int) segundosDecorridos / 60);

            double volumePedido = pedido.getNumProdutos() * VOL_PRODUTO;
            int quantidadePacotes = (int) Math.ceil(volumePedido / PACOTE_VOL_MAX);
            int quantEsteira = PACOTE_VOL_MAX / VOL_PRODUTO;
            int tempoGastoNoPedido = 0;

            if (pedido.getNumProdutosPendentes() - quantEsteira <= 0) {
                try {
                    listaPedidos.remove(pedido);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                listaTempoProduzido.add(pedido);
                pedido.setMomentoProduzidoSegundos((int) segundosDecorridos);
                System.out.println("Removido: " + pedido.toString());
            }
            
            double tempoGastoNoPacote = PACOTE_TEMPO_MEDIO + TEMPO_TRANSICAO;
            tempoGastoNoPedido += tempoGastoNoPacote;
            segundosDecorridos += (PACOTE_TEMPO_MEDIO + TEMPO_TRANSICAO);
            pacoteNumero++;
            pedido.setNumProdutosPendentes(pedido.getNumProdutosPendentes() - quantEsteira);

            // 17 h a esteira para de funcionar
            if (segundosDecorridos + tempoGastoNoPedido >= TEMPO_FUNCIONAMENTO) {
                break;
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

        String string = "\n##### RELATORIO SJF #####\n" +
                "Total de pedidos: " + listaTempoProduzido.size() + "\n" +
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
    public Pedido encontrarMinimo(int tempo) {
        Pedido menor = pedidos.get(0);
        for (Pedido pedido : pedidos) {

            if (pedido.getMomentoChegadaMinuto() <= tempo
                    && pedido.getNumProdutosPendentes() < menor.getNumProdutosPendentes()) {
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
