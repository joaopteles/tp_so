package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EsteiraFCFS extends EsteiraBase{

    //#region CONSTANTES
    public static final double PACOTE_VOL_MAX = 5000;
    public static final double PACOTE_TEMPO_MEDIO = 5;
    public static final double TEMPO_TRANSICAO = 0.5;
    //#endregion

    List<Pedido> listaTempoProduzido = new ArrayList<>();

    //#region CONSTRUTOR
    public EsteiraFCFS(Pedido[] pedidos){
        super(pedidos);
    }
    //#endregion

    //#region GETTER e SETT
    public int getHoraFinal() {
        atualizaTempoTotal();
        return horaFinal;
    }

    public int getMinutoFinal() {
        atualizaTempoTotal();
        return minutoFinal;
    }

    public int getSegundoFinal() {
        atualizaTempoTotal();
        return segundoFinal;
    }

    public String getTempoDecorrido(){
        StringBuilder string = new StringBuilder();

        if(getHoraFinal() < 10){
            string.append("0" + getHoraFinal());
        } else {
            string.append(getHoraFinal());
        }

        if(getMinutoFinal() < 10){
            string.append(":0" + minutoFinal);
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
        return (int) Math.ceil(segundosDecorridos);
    }

    //#endregion

    //#region MÉTODOS
    protected void atualizaTempoTotal() {
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
        for (int i = 0; i < pedidos.length; i++) {
            
            double volumePedido = pedidos[i].getNumProdutos() * 250;
            int quantidadePacotes = (int) Math.ceil(volumePedido / PACOTE_VOL_MAX);
            int tempoGastoNoPedido = 0;
            
            for(int y = 0; y < quantidadePacotes; y++){
                double tempoGastoNoPacote = PACOTE_TEMPO_MEDIO + TEMPO_TRANSICAO;
                tempoGastoNoPedido += tempoGastoNoPacote;
                pacoteNumero++;
            }
            // 17 h a esteira para de funcionar
            if (segundosDecorridos + tempoGastoNoPedido >= TEMPO_FUNCIONAMENTO) {
                break;
            }
            segundosDecorridos += tempoGastoNoPedido;
            pedidoNumero++;
            listaTempoProduzido.add(pedidos[i]);
            pedidos[i].setMomentoProduzidoSegundos(segundosDecorridos);
        }
    }

    public int pedidosAtendidosAteHorario(int hora, int min){
        int total = 0;
        int tamanho = listaTempoProduzido.size();
        int segundos = (min * 60) + (((hora - 8) * 60) * 60);
        for (int i = 0; i < tamanho; i++) {
            if(listaTempoProduzido.get(i).getMomentoProduzidoSegundos() < segundos) {
                total++;
            } else {
                tamanho = listaTempoProduzido.size();
            }
        }
        return total;
    }
    //#endregion
}
