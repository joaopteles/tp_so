package entities;

import java.util.ArrayList;
import java.util.List;

public class EsteiraRoundRobin extends EsteiraBase{

    List<Pedido> pedidoList = new ArrayList<>();

    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    public EsteiraRoundRobin(Pedido[] pedidos) {
        super(pedidos);
    }

    @Override
    public void ligarEsteira() {
        this.pedidos = unirPedidosMesmoCliente();
        addTodosItensDe_pedidos_Em_pedidoList();

        /*
        * Recebe o valor da menor quantidade de Pacotes Pendentes
        * para fazer o algoritmo Round Robin.
        * */
        int menorQuantidadePacotesPendentes = (int) Math.ceil(menorQuantidadePacotes(pedidoList));

        /*
        * A repetição do do-while é finalizada se um dos dois casos ocorrerem:
        * 1 - menorQuantidadePacotesPendentes for 0 (zero), isso significa que não há pacotes pendentes.
        * 2 - segundosDecorridos for maior ou igual a TEMPO_FUNCIONAMENTO, isso significa que passou das 17 horas.
        * */
        do{
            /*
            * O objetivo desse for é realizar em cada iteração um único decremento de pacotes pendentes
            * na posição atual i da lista pedidoList. Isso conforme o algoritmo Round Robin.
            * A próxima iteração somente ocorrerá se a lista não houver acabado e o tempo corrido não alcançou 17 horas.
            * */
            for(int i = 0; (i < pedidoList.size()) && (segundosDecorridos < TEMPO_FUNCIONAMENTO); i++){
                //A posição atual i da lista pedidoList só é modificado se for > 0.
                if(pedidoList.get(i).getNumProdutosPendentes() > 0 ){

                    int quantidadeProdutosPendentes = pedidoList.get(i).getNumProdutosPendentes();
                    int quantidadePacotesPendentes = (int)Math.ceil((double) quantidadeProdutosPendentes * 250 / PACOTE_VOL_MAX);

                    double tempoGastoNaIteracao = menorQuantidadePacotesPendentes * PACOTE_TEMPO_MEDIO + TEMPO_TRANSICAO;
                    segundosDecorridos += tempoGastoNaIteracao;

                    if(quantidadePacotesPendentes <= menorQuantidadePacotesPendentes){
                        pedidoList.get(i).setNumProdutosPendentes(0);
                        pedidoNumero++;
                        pedidoList.get(i).setMomentoProduzidoSegundos(segundosDecorridos);
                        listaTempoProduzido.add(pedidoList.get(i));
                    } else {
                        int novaQuantidadeProdutosPendentes = quantidadeProdutosPendentes - (menorQuantidadePacotesPendentes * 20);
                        pedidoList.get(i).setNumProdutosPendentes(novaQuantidadeProdutosPendentes);
                    }
                }
            }
            //atualiza menorQuantidadePacotesPendentes para continuar ou parar a próxima iteração do do-while.
            menorQuantidadePacotesPendentes = (int) Math.ceil(menorQuantidadePacotes(pedidoList));
        } while ((menorQuantidadePacotesPendentes > 0) && (segundosDecorridos < TEMPO_FUNCIONAMENTO));
    }

    //ok
    public int getHoraFinal() {
        atualizaTempoTotal();
        return horaFinal;
    }

    //hora
    public int getMinutoFinal() {
        atualizaTempoTotal();
        return minutoFinal;
    }

    //ok
    public int getSegundoFinal() {
        atualizaTempoTotal();
        return segundoFinal;
    }

    //ok
    @Override
    public String getTempoDecorrido() {
        StringBuilder string = new StringBuilder();

        if (getHoraFinal() < 10) {
            string.append("0" + getHoraFinal());
        } else {
            string.append(getHoraFinal());
        }

        if (getMinutoFinal() < 10) {
            string.append(":0" + minutoFinal);
        } else {
            string.append(":" + getMinutoFinal());
        }

        if (getSegundoFinal() < 10) {
            string.append(":0" + getSegundoFinal());
        } else {
            string.append(":" + getSegundoFinal());
        }

        return string.toString();
    }

    //ok
    @Override
    public int getSegundosDecorridos() {
        return (int) Math.ceil(segundosDecorridos);
    }

    //ok
    public Pedido[] getPedidosVetor(){
        return pedidos;
    }

    //ok
    /**
     * Unir a quantidade de produtos se um cliente tiver mais de um pedido
     * Obs.: não está sendo verificado prioridade
     * @return
     */
    public Pedido[] unirPedidosMesmoCliente(){
        List<Pedido> pedidosLista = new ArrayList<>();

        for (int i = 0; i < pedidos.length ; i++) {
            if(!pedidosLista.isEmpty()){
                boolean igual = false;
                for(int y = 0; y < pedidosLista.size(); y++){
                    if(pedidos[i].getCliente().compareTo(pedidosLista.get(y).getCliente()) == 0){
                        int numProdutos = pedidos[i].getNumProdutos() + pedidosLista.get(y).getNumProdutos();
                        pedidosLista.get(y).setNumProdutosPendentes(numProdutos);
                        igual = true;
                    }
                }
                if(!igual){
                    pedidosLista.add(pedidos[i]);
                }
            } else {
                pedidosLista.add(pedidos[i]);
            }
        }

        Pedido[] novosPedidos = new Pedido[pedidosLista.size()];
        for(int i = 0; i < novosPedidos.length; i++){
            novosPedidos[i] = pedidosLista.get(i);
        }
        return novosPedidos;
    }

    public void addTodosItensDe_pedidos_Em_pedidoList() {
        for (Pedido p: pedidos){
            p.setNumProdutosPendentes(p.getNumProdutos());
            pedidoList.add(p);
        }
    }

    public double menorQuantidadePacotes(List<Pedido> pedidoList){
        int menorNumeroDeProdutosPendentes = 0;

        for(int i = 0; i < pedidoList.size(); i++){
            if(menorNumeroDeProdutosPendentes < pedidoList.get(i).getNumProdutosPendentes()){
                menorNumeroDeProdutosPendentes = pedidoList.get(i).getNumProdutosPendentes();
            }
        }

        return (menorNumeroDeProdutosPendentes * 250) / PACOTE_VOL_MAX;
    }

    public String relatorio() {

        String string = "\n##### RELATÓRIO ROUND ROBIN #####\n" +
                "Total de pedidos empacotados: " + listaTempoProduzido.size() + "\n" +
                "Tempo total: " + ((double) (getSegundosDecorridos() / 60)) + " minutos \n" +
                "Hora início: 08:00\nHora Fim: " + getTempoDecorrido() + "\n" +
                "Tempo médio para empacotar cada pedido: " + ((int) getSegundosDecorridos() / listaTempoProduzido.size())
                + " segundos \n" +
                "Pedidos produzidos até 12H: " + pedidosAtendidosAteHorario(12, 00) + "\n" +
                "Não houve priorização de pedidos\n";
        return string;
    }


}
