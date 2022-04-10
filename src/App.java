public class App {
    public static String nomeArq = "./arq-teste.txt";

    public static void main(String[] args) {

        ArquivoLeitura f = new ArquivoLeitura(nomeArq);

        String s = f.lerLinha();
        int quantPedidos = Integer.parseInt(s);
        Pedido[] pedidos = new Pedido[quantPedidos];

        for (int i = 0; i < quantPedidos; i++) {
            String[] dadosPedido = f.lerLinha().split(";");
            pedidos[i] = new Pedido(Integer.parseInt(dadosPedido[1]), Integer.parseInt(dadosPedido[2]));
            System.out.println(pedidos[i].toString());
        }

        f.fecharArq();

    }

}
