package application;

import entities.EsteiraFCFS;
import entities.EsteiraPrioridades;
import entities.EsteiraSjf;
import entities.Pedido;
import services.ArquivoLeitura;

public class App {
    //#region CONSTANTE
    private static final String NOME_ARQUIVO = "./arq-teste.txt";
    //#endregion

    public static void main(String[] args) {
        Pedido[] pedidos = preencherPedidos();

        EsteiraFCFS esteiraFcfs = new EsteiraFCFS(pedidos);
        EsteiraSjf esteiraSrt = new EsteiraSjf(pedidos);
        
        esteiraSrt.ligarEsteira();
        
        esteiraFcfs.ligarEsteira();

        System.out.println(esteiraFcfs.relatorio());
        System.out.println(esteiraSrt.relatorio(pedidos, esteiraSrt));

    }

    //#region MÉTODOS
    private static Pedido [] preencherPedidos(){
        ArquivoLeitura f = new ArquivoLeitura(NOME_ARQUIVO);

        String s = f.lerLinha();
        int quantPedidos = Integer.parseInt(s);
        Pedido[] pedidos = new Pedido[quantPedidos];

        for (int i = 0; i < quantPedidos; i++) {
            String[] dadosPedido = f.lerLinha().split(";");
            pedidos[i] = new Pedido(dadosPedido[0],
                    Integer.parseInt(dadosPedido[1]),
                    Integer.parseInt(dadosPedido[2]));

        }

        f.fecharArq();

        return pedidos;
    }
    //#region
}