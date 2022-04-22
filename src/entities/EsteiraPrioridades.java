package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EsteiraPrioridades extends EsteiraBase {

    //#region CONSTRUTOR
    public EsteiraPrioridades(Pedido[] pedidos){
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
    @Override
    protected void atualizaTempoTotal() {
        int horaInicio = 8;
        //SegundoTotal > é a quantidade de segundos pedidosedondado para cima se houver fração
        int segundos = (int) Math.ceil(this.segundosDecorridos);
        int minutos = segundos / 60;
        int horas = segundos / 60 / 60;

        this.segundoFinal = segundos % 60;
        this.minutoFinal = minutos % 60;
        this.horaFinal = horaInicio + horas;
    }

    @Override
	public void ligarEsteira() {
		List<Pedido> pedidos = getPedidos();

        Comparator<Pedido> compPrazo = new Comparator<Pedido>() {

            @Override
            public int compare(Pedido o1, Pedido o2) {
                // prazo zero : sem prazo
                if (o1.getPrazo() == 0) {
                    if (o2.getPrazo() == 0) {
                        return 0;
                    }
                    return 1;
                }
                else if (o2.getPrazo() == 0) {
                    return -1;
                }
                return (o1.getPrazo() - o2.getPrazo());
            }

        };
		Collections.sort(pedidos, compPrazo);
        for (int i = 0; i < pedidos.size(); i++) {
            double volumePedido = pedidos.get(i).getNumProdutos() * 250;
            int quantidadePacotes = (int) Math.ceil(volumePedido / PACOTE_VOL_MAX);
            int tempoGastoNoPedido = 0;
            pedidoNumero++;
            for(int y = 0; y < quantidadePacotes; y++){
                double tempoGastoNoPacote = PACOTE_TEMPO_MEDIO + TEMPO_TRANSICAO;
                tempoGastoNoPedido += tempoGastoNoPacote;
                segundosDecorridos += (PACOTE_TEMPO_MEDIO + TEMPO_TRANSICAO);
                pacoteNumero++;
            }
            // 17 h a esteira para de funcionar
            if (segundosDecorridos + tempoGastoNoPedido >= TEMPO_FUNCIONAMENTO) {
                break;
            }
            segundosDecorridos += tempoGastoNoPedido;
            pedidoNumero++;
            listaTempoProduzido.add(pedidos.get(i));
            pedidos.get(i).setMomentoProduzidoSegundos(segundosDecorridos);
        }
	}


    public int pedidosAtendidosAteHorario(int hora, int min){
        int total = 0;
        int tamanho = listaTempoProduzido.size();
        int segundos = (min * 60) + (((hora - 8) * 60) * 60);
        Collections.sort(listaTempoProduzido);
        for (int i = 0; i < tamanho; i++) {
            if(listaTempoProduzido.get(i).getMomentoProduzidoSegundos() < segundos) {
                total++;
            } else {
                tamanho = listaTempoProduzido.size();
            }
        }
        return total;
    }

    @Override
    public String relatorio() {

        String string = "\n##### RELATÓRIO PRIORIDADES #####\n" +
                "Total de pedidos: " + listaTempoProduzido.size() + "\n" +
                "Tempo total: " + ((double) (getSegundosDecorridos() / 60)) + " minutos \n" +
                "Hora início: 08:00\nHora Fim: " + getTempoDecorrido() + "\n" +
                "Tempo médio para empacotar cada pedido: " + ((int) getSegundosDecorridos() / listaTempoProduzido.size())
                + " segundos \n" +
                "Pedidos produzidos até 12H: " + pedidosAtendidosAteHorario(12, 00) + "\n";
        return string;
    }

    //#endregion
}
