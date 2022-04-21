package entities.test;

import entities.EsteiraFCFS;
import entities.Pedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EsteiraTest {

    @Test
    void ligarEsteira() {
        Pedido[] pedidos = new Pedido[2];
        pedidos[0] = new Pedido("cliente1", 40, 10);
        pedidos[1] = new Pedido("cliente2", 60, 10);
        EsteiraFCFS esteira = new EsteiraFCFS(pedidos);

        esteira.ligarEsteira();

        assertEquals(8, esteira.getHoraFinal());
        assertEquals(0, esteira.getMinutoFinal());
        assertEquals(27.5, esteira.getSegundoFinal(), 0.5);

        Pedido[] pedidos2 = new Pedido[5];
        for (int i = 0; i < pedidos2.length; i++) {
            pedidos2[i] = new Pedido("cliente"+1, (i+1)*20, 10);
        }
        EsteiraFCFS esteira2 = new EsteiraFCFS(pedidos2);

        esteira2.ligarEsteira();

        assertEquals(8, esteira2.getHoraFinal());
        assertEquals(1, esteira2.getMinutoFinal());
        assertEquals(22.5, esteira2.getSegundoFinal(), 0.5);


    }

    @Test
    void pedidosAtendidosAteHorario(){
        Pedido[] pedidos = new Pedido[4];
        pedidos[0] = new Pedido("cliente1", 200, 10);
        pedidos[1] = new Pedido("cliente2", 200, 10);
        pedidos[2] = new Pedido("cliente3", 200, 10);
        pedidos[3] = new Pedido("cliente4", 200, 10);

        EsteiraFCFS esteira = new EsteiraFCFS(pedidos);
        esteira.ligarEsteira();

        assertEquals(10, esteira.pedidosAtendidosAteHorario(8,1));
    }
}