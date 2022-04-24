package application;

import entities.EsteiraLista_Prio;
import entities.Pedido;
import services.ArquivoLeitura;

public class App {
    // #region CONSTANTE
    private static final String NOME_ARQUIVO = "./arq-teste2.txt";
    // #endregion

    public static void main(String[] args) {
        Pedido[] pedidos = preencherPedidos();
//
        //EsteiraFCFS esteiraFcfs = new EsteiraFCFS(pedidos);
        //EsteiraSjf esteiraSrt = new EsteiraSjf(pedidos);
        //EsteiraPrioridades esteiraPrioridades = new EsteiraPrioridades(pedidos);
        EsteiraLista_Prio esteiraListaPrio = new EsteiraLista_Prio(pedidos);

        //esteiraSrt.ligarEsteira();
        //esteiraFcfs.ligarEsteira();
        //esteiraPrioridades.ligarEsteira();
        esteiraListaPrio.ligarEsteira();
//
        //try {
//
        //    System.out.println(esteiraFcfs.relatorio());
        //    Thread.sleep(2000);
        //    System.out.println(esteiraSrt.relatorio());
        //    Thread.sleep(2000);
        //    System.out.println(esteiraPrioridades.relatorio());
        //} catch(InterruptedException ex) {
        //
        //}
        System.out.println(esteiraListaPrio.relatorio());

    }

    // #region MÉTODOS
    private static Pedido[] preencherPedidos() {
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
    // #region
}