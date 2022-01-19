package com.company;

public class Club {
    private volatile int nPelotas;
    private volatile int nPalos;

    public Club(int nPelotas, int nPalos) {
        this.nPelotas = nPelotas;
        this.nPalos = nPalos;
    }

    public int getnPelotas() {
        return nPelotas;
    }

    public void setnPelotas(int nPelotas) {
        this.nPelotas = nPelotas;
    }

    public int getnPalos() {
        return nPalos;
    }

    public void setnPalos(int nPalos) {
        this.nPalos = nPalos;
    }

    public synchronized void reserva(int nPel, int nPal){
        if (!(getnPalos() - nPal >= 0 && getnPelotas() - nPel >= 0)){
            try {
                System.out.println("El jugador debe esperar...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setnPalos(getnPalos()-nPal);
        setnPelotas(getnPelotas()-nPel);
        Simulador.log(getnPalos(), getnPelotas());
    }

    public synchronized  void devolucion(int nPel, int nPal){
        setnPelotas(getnPelotas()+nPel);
        setnPalos(getnPalos()+nPal);
        Simulador.log(getnPalos(), getnPelotas());
        notifyAll();
    }
}
