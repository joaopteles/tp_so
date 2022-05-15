package util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ListaSem<T> {
        List<T> pedidos;
        Semaphore posicoesCheias;
        Semaphore bloqLista;

        public ListaSem(List<T> lista){
            pedidos = lista;
            posicoesCheias = new Semaphore(1);
            bloqLista = new Semaphore(1);
        }

        public void add(T novo) throws InterruptedException{
            this.bloqLista.acquire();
                this.pedidos.add(novo);
                this.posicoesCheias.release();
            this.bloqLista.release();
        }

        public boolean remove(T removido) throws InterruptedException{
            //this.posicoesCheias.acquire();
            this.bloqLista.acquire();
                boolean aux = this.pedidos.remove(removido);
            this.bloqLista.release();
            return aux;
        }

        public int getSize() throws InterruptedException{
            int tam;
            this.bloqLista.acquire();
                tam =  this.pedidos.size();
            this.bloqLista.release();
        return tam;
        }
}
