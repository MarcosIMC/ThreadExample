package com.company;

public class Jugador extends Thread{
    private int numJugador;
    private char experience;
    private int action;
    private int numRepeticion;
    private int nPalos;
    private int nPelotas;
    private Club c;
    enum Actions {Reservar, Jugar, Devolver, Descansar};

    public Jugador(int numJugador, char experience, int action, int numRepeticion, int nPalos, int nPelotas, Club c) {
        this.numJugador = numJugador;
        this.experience = experience;
        this.action = action;
        this.numRepeticion = numRepeticion;
        this.nPalos = nPalos;
        this.nPelotas = nPelotas;
        this.c = c;
    }

    public void run(){
        System.out.println("Se ejecuta el run");
        while (numRepeticion > 0){
            switch (action){
                case 1:
                    c.reserva(this.nPelotas, this.nPalos);
                    System.out.println(this.numJugador+String.valueOf(this.experience)+"["+this.nPelotas+","+this.nPalos+"]"+Actions.Reservar);
                    this.action = 2;
                    break;

                case 2:
                    try {
                        System.out.println(this.numJugador+String.valueOf(this.experience)+"["+this.nPelotas+","+this.nPalos+"]"+Actions.Jugar);
                        Thread.sleep(Simulador.tiempoJuego());
                        this.action = 3;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    c.devolucion(this.nPelotas, this.nPalos);
                    System.out.println(this.numJugador+String.valueOf(this.experience)+"["+this.nPelotas+","+this.nPalos+"]"+Actions.Devolver);
                    this.action = 4;
                    break;

                case 4:
                    try {
                        System.out.println(this.numJugador+String.valueOf(this.experience)+"["+this.nPelotas+","+this.nPalos+"]"+Actions.Descansar);
                        Thread.sleep(Simulador.tiempoDescanso());
                        this.action = 1;
                        this.numRepeticion -= 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
