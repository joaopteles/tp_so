package entities.test;

import entities.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void criarPedido(){
        Pedido pedido = new Pedido("nome", 10, 20);
        assertEquals("nome", pedido.getCliente());
        assertEquals(10,pedido.getNumProdutos());
        assertEquals(20,pedido.getPrazo());
        String str =  "nome 10 20";
        assertEquals(str, pedido.toString());
    }
}