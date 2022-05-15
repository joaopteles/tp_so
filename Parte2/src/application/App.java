package application;

import entities.EmpacotarSJF;
import entities.EmpacotarFCFS;
import entities.EmpacotarPrioridades;

public class App {
    public static void main(String[] args) {
        EmpacotarSJF sjf = new EmpacotarSJF();
        EmpacotarFCFS fcfs = new EmpacotarFCFS();
        EmpacotarPrioridades prioridades = new EmpacotarPrioridades();
    }
}