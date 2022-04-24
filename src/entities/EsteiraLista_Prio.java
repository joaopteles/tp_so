package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class EsteiraLista_Prio extends EsteiraBase{
    //#region CONSTANTES
    public static final double PACOTE_VOL_MAX = 5000;
    public static final double PACOTE_TEMPO_MEDIO = 5;
    public static final double TEMPO_TRANSICAO = 0.5;
    //#endregion

    //#region ATRIBUTOS
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
        List<Pedido> pedidos = getPedidos();

		Collections.sort(pedidos, new Comparator<Pedido>() {
            @Override
            public int compare(Pedido o1, Pedido o2) {
                // prazo zero : sem prazo
                if (o1.getPrazo() == 0) {
                    if (o2.getPrazo() == 0) {
                        return 0;
                    }
                    return 1;
                } else if (o2.getPrazo() == 0) {
                    return -1;
                }
                return (o1.getPrazo() - o2.getPrazo());
            }
        });
        
        for (int i = 0; i < pedidos.size(); i++) {
            double volumePedido = pedidos.get(i).getNumProdutos() * 250;
            int quantidadePacotes = (int) Math.ceil(volumePedido / PACOTE_VOL_MAX);
            int tempoGastoNoPedido = 0;

            this.pacoteNumero++;
            for(int y = 0; y < quantidadePacotes; y++){
                double tempoGastoNoPacote = realizaPacote(i, pedidos) + TEMPO_TRANSICAO;
                tempoGastoNoPedido += tempoGastoNoPacote;
                this.segundosDecorridos += (PACOTE_TEMPO_MEDIO + TEMPO_TRANSICAO);
                this.pacoteNumero++;
            }
            
            // 17 h a esteira para de funcionar
            if (segundosDecorridos + tempoGastoNoPedido >= TEMPO_FUNCIONAMENTO) {
                break;
            }

            this.segundosDecorridos += tempoGastoNoPedido;
            this.pedidoNumero++;
            PacoteProduzido pacote = new PacoteProduzido(pacoteNumero, segundosDecorridos, pedidos.get(i).getCliente(), pedidoNumero, pedidos.get(i).getPrazo());
            listaTempoProduzido.add(pacote);
            pedidos.get(i).setMomentoProduzidoSegundos(segundosDecorridos);
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

    public int realizaPacote(int pos, List<Pedido> pedidos) {
        int tempoGasto = 0;
        int qntPrazoIgual = 1;
        boolean done = false;
        
        for(int i = pos; i < (pedidos.size() - 1); i++) {
            if(pedidos.get(i).compareTo(pedidos.get(i+1)) == 0) {
                qntPrazoIgual++;
            } else {
                break;
            }
        }

        double fracaoTempo = PACOTE_TEMPO_MEDIO / qntPrazoIgual; 
        
        while(!done) {
            tempoGasto += fracaoTempo;
            if (tempoGasto + this.segundosDecorridos < TEMPO_FUNCIONAMENTO) {
                for(int i = 0; i < qntPrazoIgual; i++) {
                    if(pedidos.get(i).getPrazo() - tempoGasto < 0) {
                        for(int j = 0; j < qntPrazoIgual; j++) {
                        }
                        done = true;
                    }
                }
            } else {
                done = true;
            }
        }

        return tempoGasto;
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
