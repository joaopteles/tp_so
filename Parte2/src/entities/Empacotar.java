package entities;

import util.ArquivoLeitura;

import java.util.ArrayList;
import java.util.List;

public class Empacotar {

    private static final String NOME_ARQUIVO = "./arq-teste.txt";

    List<Pedido> pedidos = new ArrayList<>();

    public Empacotar() {
        criarListaPedidos();
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    private void criarListaPedidos() {
        ArquivoLeitura al = new ArquivoLeitura(NOME_ARQUIVO);

        String s = al.lerLinha();
        int quantidadePedidos = Integer.parseInt(s);
        for (int i = 0; i < quantidadePedidos; i++) {
            String[] dadosPedido = al.lerLinha().split(";");
            pedidos.add(new Pedido(dadosPedido[0],
                    Integer.parseInt(dadosPedido[1]),
                    Integer.parseInt(dadosPedido[2]),
                    Integer.parseInt(dadosPedido[3])));
        }
    }

}

