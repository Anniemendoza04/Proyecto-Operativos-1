/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.concurrent.Semaphore;
/**
 *
 * @author annie
 */
public class CPU implements Runnable{
    private int id;
    private Proceso procesoEjecutando;
    private int ciclosEjecucionCPU;
    private Semaphore semaphore;

    public CPU(int id, Semaphore semaphore) {
        this.id = id;
        this.procesoEjecutando = null;
        this.ciclosEjecucionCPU = 0;
        this.semaphore = semaphore;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proceso getProcesoEjecutando() {
        return procesoEjecutando;
    }

    public void setProcesoEjecutando(Proceso procesoEjecutando) {
        this.procesoEjecutando = procesoEjecutando;
    }

    public int getCiclosEjecucionCPU() {
        return ciclosEjecucionCPU;
    }

    public void setCiclosEjecucionCPU(int ciclosEjecucionCPU) {
        this.ciclosEjecucionCPU = ciclosEjecucionCPU;
    }

    public void incrementarCiclos() {
        this.ciclosEjecucionCPU++;
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.acquire();
                if (procesoEjecutando != null) {
                    procesoEjecutando.run();
                    incrementarCiclos();
                }
                semaphore.release();
                Thread.sleep(100); // Simular el tiempo de ejecución de la CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
     @Override
    public String toString() {
        String procesoInfo = (procesoEjecutando != null) ? procesoEjecutando.getNombre() + " (" + procesoEjecutando.getTipo() + ")" : "Ninguno";
        return "CPU{" +
                "id=" + id +
                ", procesoEjecutando=" + procesoInfo +
                ", ciclosEjecucionCPU=" + ciclosEjecucionCPU +
                '}';
    }
}