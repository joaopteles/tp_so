package entities;

import util.ArquivoLeitura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;

public class EmpacotarPrioridades {

    private static final String NOME_ARQUIVO = "./arq-teste.txt";

    List<Pedido> pedidos = new ArrayList<>();
    List<PacoteProduzido> pacoteProduzidos = new ArrayList<>();
    Semaphore bloquearLista;

    public EmpacotarPrioridades() {
        criarListaPedidosDoArquivo();

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
        al.fecharArq();
    }

    // #region Getter e Setter

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public List<PacoteProduzido> getPacoteProduzidos() {
        return pacoteProduzidos;
    }

    // #endregion

    public void ligarEsteiras() {
        Semaphore bloquearLista = new Semaphore(1);

        EsteiraPrioridades esteira = new EsteiraPrioridades(pedidos, bloquearLista);
        EsteiraPrioridades esteira2 = new EsteiraPrioridades(pedidos, bloquearLista);

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

        System.out.println("\n##### RELATORIO Prioridades #####\n" +
        "Pedidos atendidos: " + (esteira.getPedidosAtendidos() + esteira2.getPedidosAtendidos()) +
                "\nTempo total: " + segundos + " segundos\n" +
                "Pedidos produzidos ate 12H: " + (esteira.pedidosAtendidosAteHorario(12, 00) +
                esteira2.pedidosAtendidosAteHorario(12, 00)));


        System.out.println("\nEsteira 1\n" + esteira.relatorio());
        System.out.println("Esteira 2\n" + esteira2.relatorio());
    }

}
