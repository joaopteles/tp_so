package application;

import entities.Empacotar;
import entities.Pedido;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Empacotar e = new Empacotar();

        for (Pedido p: e.getPedidos()) {
            System.out.println(p.toString());
        }
    }
}
