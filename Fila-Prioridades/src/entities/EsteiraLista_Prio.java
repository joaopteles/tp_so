package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class EsteiraLista_Prio extends EsteiraBase{
    //#region CONSTANTES
    public static final double PACOTE_VOL_MAX = 5000;
    public static final double PACOTE_TEMPO_MEDIO = 5;
    public static final double TEMPO_TRANSICAO = 0.5;
    //#endregion

    //#region ATRIBUTOS
    private Pedido[] pedidos;
    private int horaFinal;
    private int minutoFinal;
    private int segundoFinal;
    private double segundosDecorridos;
    private int pedidoNumero;
    private int pacoteNumero;
    List<PacoteProduzido> listaTempoProduzido = new ArrayList<>();
    //#endregion

    //#region CONSTRUTOR
    public EsteiraLista_Prio(Pedido[] pedidos){
        super(pedidos);
    }
    //#endregion

    //#region GETTER e SETT
    public int getHoraFinal() {
        atualizaTempoTotal();
        return this.horaFinal;
    }

    public int getMinutoFinal() {
        atualizaTempoTotal();
        return this.minutoFinal;
    }

    public int getSegundoFinal() {
        atualizaTempoTotal();
        return this.segundoFinal;
    }

    public String getTempoDecorrido(){
        StringBuilder string = new StringBuilder();

        if(getHoraFinal() < 10){
            string.append("0" + getHoraFinal());
        } else {
            string.append(getHoraFinal());
        }

        if(getMinutoFinal() < 10){
            string.append(":0" + getMinutoFinal());
        } else {
            string.append(":" + getMinutoFinal());
        }

        if(getSegundoFinal() < 10){
            string.append(":0" + getSegundoFinal());
        } else {
            string.append(":" + getSegundoFinal());
        }

        return string.toString();
    }

    public int getSegundosDecorridos() {
        return (int) Math.ceil(this.segundosDecorridos);
    }

    //#endregion

    //#region MÉTODOS
    public void atualizaTempoTotal() {
        int horaInicio = 8;
        //SegundoTotal > é a quantidade de segundos arredondado para cima se houver fração
        int segundos = (int) Math.ceil(this.segundosDecorridos);
        int minutos = segundos / 60;
        int horas = segundos / 60 / 60;

        this.segundoFinal = segundos % 60;
        this.minutoFinal = minutos % 60;
        this.horaFinal = horaInicio + horas;
    }

    public void ligarEsteira(){
        sortPedidosPrazo();
        for (int i = 0; i < this.pedidos.length; i++) {
            double volumePedido = this.pedidos[i].getNumProdutos() * 250;
            int quantidadePacotes = (int) Math.ceil(volumePedido / PACOTE_VOL_MAX);
            this.pedidoNumero++;
            for(int y = 0; y < quantidadePacotes; y++){
                double tempoGastoNoPacote = realizaPacote(y) + TEMPO_TRANSICAO;
                this.segundosDecorridos += tempoGastoNoPacote;
                this.pacoteNumero++;
                PacoteProduzido pacoteProduzido = new PacoteProduzido(pacoteNumero, segundosDecorridos, pedidos[i].getCliente(),pedidoNumero, pedidos[i].getPrazo());
                this.listaTempoProduzido.add(pacoteProduzido);
            }
        }
    }

    public int pedidosAtendidosAteHorario(int hora, int min){
        int total = 0;
        int tamanho = this.listaTempoProduzido.size();
        int segundos = (min * 60) + (((hora - 8) * 60) * 60);
        Collections.sort(this.listaTempoProduzido);
        for (int i = 0; i < tamanho; i++) {
            if(this.listaTempoProduzido.get(i).getMomentoProduzidoSegundos() < segundos) {
                total++;
            } else {
                tamanho = this.listaTempoProduzido.size();
            }
        }
        return total;
    }

    private void sortPedidosPrazo(){
        int quant = this.pedidos.length;
        for (int i = 0; i < quant; i++) {
            for (int j = 0; j < quant - i - 1; j++)
                // prazo zero: sem prazo
                if (pedidos[j].getPrazo() == 0 || (pedidos[j + 1].getPrazo() != 0 && pedidos[j].getPrazo() > pedidos[j + 1].getPrazo())) {
                    Pedido temp = pedidos[j];
                    pedidos[j] = pedidos[j + 1];
                    pedidos[j + 1] = temp;
                }
        }
    }

    public int realizaPacote(int pos) {
        int tempoDecorrido = 0;
        int qntPrazoIgual = 1;
        boolean done = false;
        
        for(int i = pos; i < this.pedidos.length; i++) {
            if(this.pedidos[i].compareTo(this.pedidos[i+1]) == 0) {
                qntPrazoIgual++;
            } else {
                continue;
            }
        }

        double fracaoTempo = PACOTE_TEMPO_MEDIO / qntPrazoIgual; 
        
        while(!done) {
            tempoDecorrido += fracaoTempo;

            for(int i = 0; i < qntPrazoIgual; i++) {
                if(this.pedidos[i].getPrazo() - tempoDecorrido < 0) {
                    for(int j = 0; j < qntPrazoIgual; j++) {
                        this.pedidos[j].setPrazo(this.pedidos[j].getPrazo() - tempoDecorrido);
                    }
                    done = true;
                }
            }
        }

        return tempoDecorrido;
    } 

    @Override
    public String relatorio(){

        String string =
                "\n##### RELATORIO LISTA DE PRIORIDADES #####\n" +
                        "Total de pedidos: " + listaTempoProduzido.size() + "\n" +
                        "Tempo total: " + ((double) (getSegundosDecorridos() / 60)) + " minutos \n" +
                        "Hora inicio: 08:00\nHora Fim: " + getTempoDecorrido() + "\n" +
                        "Tempo medio para empacotar cada pedido: " + ((int) getSegundosDecorridos() / listaTempoProduzido.size()) + " segundos \n" +
                        "Pedidos produzidos ate 12H: " + pedidosAtendidosAteHorario(12,00) + "\n";
        return string;
    }
    
    //#endregion
}
