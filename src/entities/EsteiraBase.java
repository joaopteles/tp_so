package entities;

import java.util.ArrayList;
import java.util.List;

public abstract class EsteiraBase {

    //#region CONSTANTES
    public static final double PACOTE_VOL_MAX = 5000;
    public static final double PACOTE_TEMPO_MEDIO = 5;
    public static final double TEMPO_TRANSICAO = 0.5;
    public static final double TEMPO_FUNCIONAMENTO = 32400; // 8 h a 17 h em segundos
    //#endregion

    //#region ATRIBUTOS
    protected Pedido[] pedidos;
    protected int horaFinal;
    protected int minutoFinal;
    protected int segundoFinal;
    protected double segundosDecorridos;
    protected int pedidoNumero;
    protected int pacoteNumero;
    //#endregion

	private List<Pedido> retorno;
    
    public EsteiraBase(Pedido[] pedidos){
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
    
    public abstract int pedidosAtendidosAteHorario(int hora, int min);

	public List<Pedido> getPedidos() {
		List<Pedido> retorno= new ArrayList<Pedido>();
		for (Pedido pedido : pedidos) {
			retorno.add(pedido);
		}
		return retorno;
	}

	public void setPedidos(Pedido[] pedidos) {
		this.pedidos = pedidos;
	}

	public abstract String getTempoDecorrido() ;

	public abstract int getSegundosDecorridos();
}