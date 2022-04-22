package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EsteiraFCFS extends EsteiraBase {

    List<Pedido> listaTempoProduzido = new ArrayList<>();

    // #region CONSTRUTOR
    public EsteiraFCFS(Pedido[] pedidos) {
        super(pedidos);
    }
    // #endregion

    // #region GETTER e SETT
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

    public String getTempoDecorrido() {
        StringBuilder string = new StringBuilder();

        if (getHoraFinal() < 10) {
            string.append("0" + getHoraFinal());
        } else {
            string.append(getHoraFinal());
        }

        if (getMinutoFinal() < 10) {
            string.append(":0" + minutoFinal);
        } else {
            string.append(":" + getMinutoFinal());
        }

        if (getSegundoFinal() < 10) {
            string.append(":0" + getSegundoFinal());
        } else {
            string.append(":" + getSegundoFinal());
        }

        return string.toString();
    }

    public int getSegundosDecorridos() {
        return (int) Math.ceil(segundosDecorridos);
    }

    // #endregion

    // #region MÉTODOS
    @Override
    public void ligarEsteira() {
        for (int i = 0; i < pedidos.length; i++) {

            double volumePedido = pedidos[i].getNumProdutos() * 250;
            int quantidadePacotes = (int) Math.ceil(volumePedido / PACOTE_VOL_MAX);
            int tempoGastoNoPedido = 0;

            for (int y = 0; y < quantidadePacotes; y++) {
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

    @Override
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

    public String relatorio() {

        String string = "\n##### RELATÓRIO FCFS #####\n" +
                "Total de pedidos empacotados: " + listaTempoProduzido.size() + "\n" +
                "Tempo total: " + ((double) (getSegundosDecorridos() / 60)) + " minutos \n" +
                "Hora início: 08:00\nHora Fim: " + getTempoDecorrido() + "\n" +
                "Tempo médio para empacotar cada pedido: " + ((int) getSegundosDecorridos() / listaTempoProduzido.size())
                + " segundos \n" +
                "Pedidos produzidos até 12H: " + pedidosAtendidosAteHorario(12, 00) + "\n" +
                "Não houve priorização de pedidos\n";
        return string;
    }
    // #endregion
}
