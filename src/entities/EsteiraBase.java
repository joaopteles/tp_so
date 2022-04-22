package entities;

import java.util.ArrayList;
import java.util.List;

public abstract class EsteiraBase {

    //#region CONSTANTES
    public static final double PACOTE_VOL_MAX = 5000;
    public static final double PACOTE_TEMPO_MEDIO = 5;
    public static final double TEMPO_TRANSICAO = 0.5;
    //#endregion

    private Pedido[] pedidos;
	private List<Pedido> retorno;
    
    public EsteiraBase(Pedido[] pedidos){
        this.setPedidos(pedidos);
    }
       
    protected abstract void atualizaTempoTotal();
    
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