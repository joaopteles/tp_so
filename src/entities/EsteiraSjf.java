package entities;

import java.text.CompactNumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EsteiraSjf extends EsteiraBase {

	//#region Getters e setters
    public int getPedidoNumero() {
		return pedidoNumero;
	}

	public void setPedidoNumero(int pedidoNumero) {
		this.pedidoNumero = pedidoNumero;
	}

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
	//#endregion

	//#region ATRIBUTOS
    private int horaFinal;
    private int minutoFinal;
    private int segundoFinal;
    private double segundosDecorridos;
    private int pedidoNumero;
    private int pacoteNumero;
    List<Pedido> listaTempoProduzido = new ArrayList<>();
    //#endregion

	public EsteiraSjf(Pedido[] pedidos) {
		super(pedidos);
	}

	@Override
	public String getTempoDecorrido() {
        StringBuilder string = new StringBuilder();
        
        atualizaTempoTotal();

        if(this.horaFinal < 10){
            string.append("0" + this.horaFinal);
        } else {
            string.append(this.horaFinal);
        }

        if(this.minutoFinal < 10){
            string.append(":0" + this.minutoFinal);
        } else {
            string.append(":" + this.minutoFinal);
        }

        if(this.segundoFinal < 10){
            string.append(":0" + this.segundoFinal);
        } else {
            string.append(":" + this.segundoFinal);
        }

        return string.toString();
	}
	@Override
	protected void atualizaTempoTotal() {
        int horaInicio = 8;
        int segundos = (int) Math.ceil(this.segundosDecorridos);
        int minutos = segundos / 60;
        int horas = segundos / 60 / 60;

        setSegundoFinal(segundos % 60);
        setMinutoFinal(minutos % 60);
        setHoraFinal( horaInicio + horas);		
	}

	@Override
	public void ligarEsteira() {
		List<Pedido> pedidos = getPedidos();

        Comparator<Pedido> compSjf = new Comparator<Pedido>() {

            @Override
            public int compare(Pedido o1, Pedido o2) {
                return (o1.getNumProdutos() - o2.getNumProdutos());
            }

        };
		Collections.sort(pedidos, compSjf);
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

	@Override
	public int pedidosAtendidosAteHorario(int hora, int min) {
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
	public int getSegundosDecorridos() {
		return (int) Math.ceil(segundosDecorridos);
	}
	
    @Override
    public String relatorio(){

        String string =
                "\n##### RELATORIO SJF #####\n" +
                        "Total de pedidos: " + listaTempoProduzido.size() + "\n" +
                        "Tempo total: " + ((double) (getSegundosDecorridos() / 60)) + " minutos \n" +
                        "Hora inicio: 08:00\nHora Fim: " + getTempoDecorrido() + "\n" +
                        "Tempo medio para empacotar cada pedido: " + ((int) getSegundosDecorridos() / listaTempoProduzido.size()) + " segundos \n" +
                        "Pedidos produzidos ate 12H: " + pedidosAtendidosAteHorario(12,00) + "\n";
        return string;
    }

}
