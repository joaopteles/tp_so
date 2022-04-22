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

        System.out.println(relatorio(pedidos, esteiraFcfs));
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


    private static String relatorio(Pedido[] pedidos, EsteiraFCFS esteira){

        String string =
                "\n##### RELATÓRIO #####\n" +
                        "Total de pedidos: " + pedidos.length + "\n" +
                        "Tempo total: " + ((double) (esteira.getSegundosDecorridos() / 60)) + " minutos \n" +
                        "Hora início: 08:00\nHora Fim: " + esteira.getTempoDecorrido() + "\n" +
                        "Tempo médio para empacotar cada pedido: " + ((int) esteira.getSegundosDecorridos() / pedidos.length) + " segundos \n" +
                        "Pedidos produzidos até 12H: " + esteira.pedidosAtendidosAteHorario(12,00) + "\n" +
                        "Não houve priorização de pedidos\n";
        return string;
    }
    //#region
}