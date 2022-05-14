package entities;

import util.ArquivoLeitura;

import java.util.ArrayList;
import java.util.List;

public class Empacotar {

    private static final String NOME_ARQUIVO = "./arq-teste.txt";

    List<Pedido> pedidos = new ArrayList<>();

    public Empacotar() {
        criarListaPedidos();
        mesclarPedidos();
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

    /**
     * Mescla pedidos do mesmo cliente.
     * Condições para serem iguais:
     *      Nome e Prazo
     * Comportamento:
     *      Percorre a lista de pedidos comparando se a posição atual
     *      é igual a alguma posição restante da lista.
     *      Se forem iguais é feito a soma da quantidade de produtos na primeira ocorrência de igualdade
     *      Após percorrer toda lista, essa ocorrência é passada para nova lista.
     *      A nova lista substitui a lista anterior.
     */
    private void mesclarPedidos() {
        List<Pedido> listMesclada = new ArrayList<>();
        /*
        * O equals está utilizando a comparação por nome e prazo (ver equals em Pedido)
        * */
        for (int i = 0; i < pedidos.size(); i++) {
            for (int j = i+1; j < pedidos.size(); j++) {
                if(pedidos.get(i).equals(pedidos.get(j))) {
                    pedidos.get(i).adicionarProdutos(pedidos.get(j).getNumProdutos());
                }
            }

            /*
            * Não permite pedidos duplicados na lista nova
            * É considerado que o equals utilize nome e prazo para comparação
            * */
            if(!listMesclada.contains(pedidos.get(i))) {
                listMesclada.add(pedidos.get(i));
            }
        }

        pedidos = listMesclada;
    }
}

/*
* VAI TER QUE CRIAR UM "LIBERADOR DE PEDIDOS" POR SEGUNDO
* */

