/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author annie
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.concurrent.Semaphore;
/**
 *
 * @author annie
 */
public class SimuladorOS {
    private CPU[] cpus;
    private Cola colaListos;
    private Cola[] colasPrioridad;
    private Cola colaBloqueados;
    private Planificador planificador;
    private int cicloReloj;
    private long duracionCiclo;
    private int numProcesadores;
    private String tipo;
    private Semaphore semaphore;

    public SimuladorOS(int numProcesadores, long duracionCiclo, Planificador.AlgoritmoPlanificacion algoritmoPlanificacion) {
        this.numProcesadores = numProcesadores;
        this.duracionCiclo = duracionCiclo;
        this.cicloReloj = 0;
        this.semaphore = new Semaphore(1);
        this.cpus = new CPU[numProcesadores];
        for (int i = 0; i < numProcesadores; i++) {
            cpus[i] = new CPU(i, semaphore);
            new Thread(cpus[i]).start();
        }
        this.colasPrioridad = new Cola[3]; //3 niveles de prioridad
        for (int i = 0; i < colasPrioridad.length; i++) {
            colasPrioridad[i] = new Cola();
        }
        this.colaListos = new Cola();
        this.colaBloqueados = new Cola();
        this.planificador = new Planificador(algoritmoPlanificacion, this);
        this.tipo = "SISTEMA_OPERATIVO";
    }

    // Métodos para planificar procesos
    public void planificarProcesos() {
        planificador.planificar(colasPrioridad,colaListos, cpus);
    }

    // Métodos para manejar excepciones (E/S)
    public void manejarExcepcionES(Proceso proceso) {
        // Implementar lógica para manejar excepciones de E/S
        colaBloqueados.encolar(proceso);
    }

    // Método para cambiar el algoritmo de planificación
    public void cambiarAlgoritmoPlanificacion(Planificador.AlgoritmoPlanificacion nuevoAlgoritmo) {
        this.planificador.cambiarAlgoritmo(nuevoAlgoritmo);
    }
    
    public void pruebaSRT() {
        // Crear algunos procesos de ejemplo
        Proceso proceso1 = new Proceso(1, "Proceso 1", Proceso.STATUS.READY, 0, 10, true, 0, 0, 5);
        proceso1.setTiempoLlegada(0);
        Proceso proceso2 = new Proceso(2, "Proceso 2", Proceso.STATUS.READY, 0, 20, true, 0, 0, 5);
        proceso2.setTiempoLlegada(1);
        Proceso proceso3 = new Proceso(3, "Proceso 3", Proceso.STATUS.READY, 0, 15, true, 0, 0, 5);
        proceso3.setTiempoLlegada(2);

        // Encolar procesos en la cola de listos
        colaListos.encolar(proceso1);
        colaListos.encolar(proceso2);
        colaListos.encolar(proceso3);

        // Simular ciclos de reloj
        for (int i = 0; i < 30; i++) {
            planificarProcesos();
            incrementarCicloReloj();
            System.out.println("Ciclo de reloj: " + cicloReloj);
            for (CPU cpu : cpus) {
                System.out.println(cpu);
            }
            try {
                Thread.sleep(duracionCiclo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // Método de prueba para Feedback
    public void pruebaFeedback() {
        // Crear algunos procesos de ejemplo
        Proceso proceso1 = new Proceso(1, "Proceso 1", Proceso.STATUS.READY, 0, 10, true, 0, 0, 5);
        proceso1.setPrioridad(0);
        Proceso proceso2 = new Proceso(2, "Proceso 2", Proceso.STATUS.READY, 0, 20, true, 0, 0, 5);
        proceso2.setPrioridad(0);
        Proceso proceso3 = new Proceso(3, "Proceso 3", Proceso.STATUS.READY, 0, 15, true, 0, 0, 5);
        proceso3.setPrioridad(0);

        // Encolar procesos en la cola de mayor prioridad
        colasPrioridad[0].encolar(proceso1);
        colasPrioridad[0].encolar(proceso2);
        colasPrioridad[0].encolar(proceso3);

        // Simular ciclos de reloj
        for (int i = 0; i < 30; i++) {
            planificarProcesos();
            incrementarCicloReloj();
            System.out.println("Ciclo de reloj: " + cicloReloj);
            for (CPU cpu : cpus) {
                System.out.println(cpu);
            }
            try {
                Thread.sleep(duracionCiclo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
   // Método de prueba para SJF
   public void pruebaSJF() {
        Proceso proceso1 = new Proceso(1, "Proceso 1", Proceso.STATUS.READY, 0, 10, true, 0, 0, 5);
        proceso1.setTipo(Proceso.Tipo.SISTEMA_OPERATIVO);
        Proceso proceso2 = new Proceso(2, "Proceso 2", Proceso.STATUS.READY, 0, 20, true, 0, 0, 5);
        proceso2.setTipo(Proceso.Tipo.USUARIO);
        Proceso proceso3 = new Proceso(3, "Proceso 3", Proceso.STATUS.READY, 0, 15, true, 0, 0, 5);
        proceso3.setTipo(Proceso.Tipo.USUARIO);
        this.colaListos.encolar(proceso1);
        this.colaListos.encolar(proceso2);
        this.colaListos.encolar(proceso3);

        for (int i = 0; i < 30; i++) {
            this.planificarProcesos();
            this.incrementarCicloReloj();
            System.out.println("Ciclo de reloj: " + this.cicloReloj);
            for (CPU cpu : this.cpus) {
                System.out.println(cpu);
            }

            try {
                Thread.sleep(this.duracionCiclo);
            } catch (InterruptedException var9) {
                var9.printStackTrace();
            }
        }
    }


    public void pruebaFCFS() {
        Proceso proceso1 = new Proceso(1, "Proceso 1", Proceso.STATUS.READY, 0, 10, true, 0, 0, 5);
        Proceso proceso2 = new Proceso(2, "Proceso 2", Proceso.STATUS.READY, 0, 20, true, 0, 0, 5);
        Proceso proceso3 = new Proceso(3, "Proceso 3", Proceso.STATUS.READY, 0, 15, true, 0, 0, 5);

        // Encolar procesos en la cola de listos
        colaListos.encolar(proceso1);
        colaListos.encolar(proceso2);
        colaListos.encolar(proceso3);

        // Simular ciclos de reloj
        for (int i = 0; i < 30; i++) {
            planificarProcesos();
            incrementarCicloReloj();
            System.out.println("Ciclo de reloj: " + cicloReloj);
            for (CPU cpu : cpus) {
                System.out.println(cpu);
            }
            try {
                Thread.sleep(duracionCiclo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
       }
    }
    
    // Getters y Setters
    public CPU[] getCpus() {
        return cpus;
    }

    public Cola getColaListos() {
        return colaListos;
    }

    public Cola getColaBloqueados() {
        return colaBloqueados;
    }

    public Planificador getPlanificador() {
        return planificador;
    }

    public Cola[] getColasPrioridad() {
        return colasPrioridad;
    }

    public Planificador.AlgoritmoPlanificacion getAlgoritmoPlanificacion() {
        return planificador.getAlgoritmoActual();
    }

    public int getCicloReloj() {
        return cicloReloj;
    }

    public void incrementarCicloReloj() {
        this.cicloReloj++;
    }

    public long getDuracionCiclo() {
        return duracionCiclo;
    }

    public int getNumProcesadores() {
        return numProcesadores;
    }

    @Override
    public String toString() {
        return "SimuladorSO{" +
                "numProcesadores=" + numProcesadores +
                ", duracionCiclo=" + duracionCiclo +
                ", algoritmoPlanificacion=" + planificador.getAlgoritmoActual() +
                ", cicloReloj=" + cicloReloj +
                ", tipo=" + (colaListos.estaVacia() ? "Ninguno" : colaListos.getFrente().getProceso().getTipo()) +
                '}';
    }


    public static void main(String[] args) {
        // Crear una instancia del SimuladorOS
        SimuladorOS simulador = new SimuladorOS(2, 1000L, Planificador.AlgoritmoPlanificacion.SJF);
        simulador.pruebaSJF();
    }
}

        // Procesos de ejemplo
        //Proceso proceso1 = new Proceso(1, "Proceso 1", Proceso.STATUS.READY, 0, 10, true, 0, 0, 5);
        //Proceso proceso2 = new Proceso(2, "Proceso 2", Proceso.STATUS.BLOCKED, 0, 20, false, 5, 10, 5);
        //Proceso proceso3 = new Proceso(3, "Proceso 3", Proceso.STATUS.READY, 0, 15, true, 0, 0, 5);

        // encolar
        //colaListos.encolar(proceso1);
        //colaBloqueados.encolar(proceso2);
        //colaListos.encolar(proceso3);

        // desencola y printea los procesos
        //System.out.println("Proceso desencolado de la cola de listos: " + colaListos.desencolar());
        //System.out.println("Proceso desencolado de la cola de bloqueados: " + colaBloqueados.desencolar());
        //System.out.println("Proceso desencolado de la cola de listos: " + colaListos.desencolar());
        
        //for (int i = 0; i < 3; i++){
        //MultithreadThing myThing = new MultithreadThing(i);
        //Thread myThread = new Thread(myThing);
        //myThread.start();
   


