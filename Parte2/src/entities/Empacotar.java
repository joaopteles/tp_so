package entities;

import util.ArquivoLeitura;

import java.util.ArrayList;
import java.util.List;

public class Empacotar {

    private static final String NOME_ARQUIVO = "./arq-teste.txt";

    List<Pedido> pedidos = new ArrayList<>();
    List<PacoteProduzido> pacoteProduzidos = new ArrayList<>();

    public Empacotar() {
        criarListaPedidosDoArquivo();
        mesclarPedidos();
    }

    private void criarListaPedidosDoArquivo() {
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

    //#region Getter e Setter

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public List<PacoteProduzido> getPacoteProduzidos() {
        return pacoteProduzidos;
    }


    //#endregion

    /**
     * Mescla pedidos do mesmo cliente.
     * Condições para serem iguais:
     *      Nome, prazo e momento da chegada do pedido (momentoChegadaSegundos)
     * Comportamento:
     *      Percorre a lista de pedidos comparando se a posição atual
     *      é igual a alguma posição restante da lista.
     *      Se forem iguais é feito a soma da quantidade de produtos na primeira ocorrência de igualdade
     *      Após percorrer toda lista, essa ocorrência é passada para nova lista.
     *      A nova lista substitui a lista anterior.
     */
    private void mesclarPedidos() {
        List<Pedido> listMesclada = new ArrayList<>();

        for (int i = 0; i < pedidos.size(); i++) {
            for (int j = i+1; j < pedidos.size(); j++) {
                if(pedidos.get(i).equals(pedidos.get(j))) {
                    pedidos.get(i).adicionarProdutos(pedidos.get(j).getNumProdutos());
                }
            }

            if(!listMesclada.contains(pedidos.get(i))) {
                listMesclada.add(pedidos.get(i));
            }
        }

        pedidos = listMesclada;
    }

    public void ligarEsteiras() {
        /*
        * E AGORA JOSÉ?
        * PAREI AQUI
        *
        * Obs:
        * Dentro de ligar esteiras acredito que tenha que fazer uma lista com os pedidos pendentes para produção
        * Porque eles somente entram na lista quando atingir o horário de entrada.
        * Exemplo: horário: 08:00 entram todos pedidos do minuto 0
        *                   08:05 entram todos os pedidos do minuto 5 e assim sucessivamente até 17 horas
        * Sugiro em cada início de funcionamento de cada esteira, registrar a entrada na esteira e a finalização da esteira para ajustar o horário atual
        * e atualizar a lista conforme o horário
        *
        * ou seja, as esteiras que serão o relógio
        * */

    }
}
