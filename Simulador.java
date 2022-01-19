package com.company;

public class Simulador {

    public static void main(String[] args) {
        Club c = new Club(20, 20);
        Jugador j1 = new Jugador(1, 'e', 1, 5, 2,1, c);
        Jugador j2 = new Jugador(2,'e', 1, 5, 4, 1, c);
        Jugador j3 = new Jugador(3, 'n', 1, 5, 2, 3, c);
        Jugador j4 = new Jugador(4, 'n', 1, 5, 2, 2, c);
        Jugador j5 = new Jugador(5, 'n', 1, 5, 2, 5, c);
        Jugador j6 = new Jugador(6, 'e', 1, 5, 5,1, c);
        Jugador j7 = new Jugador(7, 'n', 1, 5, 2, 3, c);
        Jugador j8 = new Jugador(8, 'e', 1, 5, 4, 1, c);
        Jugador j9 = new Jugador(9, 'e', 1, 5, 2, 1, c);
        Jugador j10 = new Jugador(10, 'n', 1, 5, 2, 4, c);

        System.out.println("Arrancan los hilos");

        j1.start();
        j2.start();
        j3.start();
        j4.start();
        j5.start();
        j6.start();
        j7.start();
        j8.start();
        j9.start();
        j10.start();
    }

    public static long tiempoDescanso(){
        return (long) (Math.random() * 1000);
    }

    public static long tiempoJuego(){
        return (long) (Math.random() * 1000);
    }

    public static void log(int nPalos, int nPelotas){
        System.out.println("En el Club quedan disponibles: - Pelotas: "+nPelotas+"\n"+
        "- Palos: "+nPalos);
    }
}
