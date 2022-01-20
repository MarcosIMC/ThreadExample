package com.company;

public class Club {
    private volatile int nPelotas;
    private volatile int nPalos;

    public Club(int nPelotas, int nPalos) {
        this.nPelotas = nPelotas;
        this.nPalos = nPalos;
    }

    public int getnPelotas() {
        return this.nPelotas;
    }

    public void setnPelotas(int nPelotas) {
        this.nPelotas = nPelotas;
    }

    public int getnPalos() {
        return this.nPalos;
    }

    public void setnPalos(int nPalos) {
        this.nPalos = nPalos;
    }

    public synchronized void reserva(int nPel, int nPal){
        if ((getnPalos() < nPal || getnPelotas() < nPel)){
            try {
                System.out.println("El jugador debe esperar...");
                wait();
                reserva(nPel, nPal);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (getnPalos() >= nPal && getnPelotas() >= nPel){
            setnPalos(getnPalos()-nPal);
            setnPelotas(getnPelotas()-nPel);
            Simulador.log(nPalos, nPelotas);
        }
    }

    public synchronized  void devolucion(int nPel, int nPal){
        setnPelotas(getnPelotas()+nPel);
        setnPalos(getnPalos()+nPal);
        Simulador.log(nPalos, nPelotas);
        notifyAll();
    }
}
