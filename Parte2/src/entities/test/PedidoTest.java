package entities.test;

import entities.Pedido;
import org.junit.jupiter.api.Test;

class PedidoTest {

    @Test
    void teste() {
        Pedido pedido1 = new Pedido("cliente1", 10 , 0, 60);
        Pedido pedido2 = new Pedido("cliente1", 20 , 10, 60);
        Pedido pedido3 = new Pedido("cliente1", 30, 0, 60);

        System.out.println(pedido1);
        pedido1.mesclarPedidoDoMesmoCliente(pedido2);
        System.out.println(pedido1);
        pedido1.mesclarPedidoDoMesmoCliente(pedido3);
        System.out.println(pedido1);

    }

}