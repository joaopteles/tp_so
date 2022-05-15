package entities;

import util.ArquivoLeitura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Empacotar {

    private static final String NOME_ARQUIVO = "./arq-teste.txt";

    List<Pedido> pedidos = new ArrayList<>();
    List<PacoteProduzido> pacoteProduzidos = new ArrayList<>();
    Semaphore bloquearLista;

    public Empacotar() {
        criarListaPedidosDoArquivo();

        // Collections.sort(pedidos, new Comparator<Pedido>() {

        //     @Override
        //     public int compare(Pedido o1, Pedido o2) {
        //         return (o1.getMomentoChegadaMinuto() - o2.getMomentoChegadaMinuto());
        //     }

        // });

        ligarEsteiras();
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

    // #region Getter e Setter

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public List<PacoteProduzido> getPacoteProduzidos() {
        return pacoteProduzidos;
    }

    // #endregion

    /**
     * Mescla pedidos do mesmo cliente.
     * Condições para serem iguais:
     * Nome, prazo e momento da chegada do pedido (momentoChegadaSegundos)
     * Comportamento:
     * Percorre a lista de pedidos comparando se a posição atual
     * é igual a alguma posição restante da lista.
     * Se forem iguais é feito a soma da quantidade de produtos na primeira
     * ocorrência de igualdade
     * Após percorrer toda lista, essa ocorrência é passada para nova lista.
     * A nova lista substitui a lista anterior.
     */
    private void mesclarPedidos() {
        List<Pedido> listMesclada = new ArrayList<>();

        for (int i = 0; i < pedidos.size(); i++) {
            for (int j = i + 1; j < pedidos.size(); j++) {
                if (pedidos.get(i).equals(pedidos.get(j))) {
                    pedidos.get(i).adicionarProdutos(pedidos.get(j).getNumProdutos());
                }
            }

            if (!listMesclada.contains(pedidos.get(i))) {
                listMesclada.add(pedidos.get(i));
            }
        }

        pedidos = listMesclada;
    }

    public void ligarEsteiras() {
        Semaphore bloquearLista = new Semaphore(1);

        EsteiraSjf esteira = new EsteiraSjf(pedidos, bloquearLista);
        EsteiraSjf esteira2 = new EsteiraSjf(pedidos, bloquearLista);

        esteira.start();
        esteira2.start();
        try {
            esteira.join();
            esteira2.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        int segundos;
        if (esteira.getSegundosDecorridos() > esteira.getSegundosDecorridos()) {
            segundos = esteira.getSegundosDecorridos();
        } else {
            segundos = esteira2.getSegundosDecorridos();
        }



        System.out.println("\n##### RELATORIO #####\n" +
        "Pedidos atendidos: " + (esteira.getPedidosAtendidos() + esteira2.getPedidosAtendidos()) +
                "\nTempo total: " + segundos + " segundos\n" +
                "Pedidos produzidos ate 12H: " + (esteira.pedidosAtendidosAteHorario(12, 00) +
                esteira2.pedidosAtendidosAteHorario(12, 00)));


        System.out.println("\nEsteira 1\n" + esteira.relatorio());
        System.out.println("Esteira 2\n" + esteira2.relatorio());
    }

    public String relatorio(EsteiraSjf esteira) {

        String string = "\n##### RELATORIO SJF #####\n" +
                "Pedidos atendidos: " + esteira.getPedidosAtendidos() + "\n" +
                "Tempo total: " + (esteira.getSegundosDecorridos()) + " segundos \n" +
                "Hora inicio: 08:00\nHora Fim: " + esteira.getTempoDecorrido() + "\n" +
                "Tempo medio para empacotar cada pedido: "
                + ((int) esteira.getSegundosDecorridos() / esteira.getPedidosAtendidos())
                + " segundos \n" +
                "Pedidos produzidos ate 12H: " + esteira.pedidosAtendidosAteHorario(12, 00) + "\n";
        return string;
    }
}
