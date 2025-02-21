/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author annie
 */
public class Planificador {
    public enum AlgoritmoPlanificacion {
        FCFS, SJF, RoundRobin, SRT, Feedback
    }

    private AlgoritmoPlanificacion algoritmoActual;
    private int quantum; // Tiempo de quantum para Round Robin
    private SimuladorOS simulador;

    public Planificador(AlgoritmoPlanificacion algoritmoInicial, SimuladorOS simulador) {
        this.algoritmoActual = algoritmoInicial;
        this.quantum = 4; // Valor por defecto del quantum
        this.simulador = simulador;
    }

    public void cambiarAlgoritmo(AlgoritmoPlanificacion nuevoAlgoritmo) {
        this.algoritmoActual = nuevoAlgoritmo;
    }

    public AlgoritmoPlanificacion getAlgoritmoActual() {
        return algoritmoActual;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public void planificar(Cola [] colasPrioridad,Cola colaListos, CPU[] cpus) {
        switch (algoritmoActual) {
            case FCFS:
                planificarFCFS(colaListos, cpus);
                break;
            case SJF:
                planificarSJF(colaListos, cpus);
                break;
            case RoundRobin:
                planificarRoundRobin(colaListos, cpus);
                break;
            case SRT:
                planificarSRT(colaListos, cpus);
                break;
            case Feedback:
                planificarFeedback(colasPrioridad, cpus);
                break;
        }
    }

    private void planificarFCFS(Cola colaListos, CPU[] cpus) {
        for (CPU cpu : cpus) {
            if (cpu.getProcesoEjecutando() == null && !colaListos.estaVacia()) {
                Proceso siguienteProceso = colaListos.desencolar();
                cpu.setProcesoEjecutando(siguienteProceso);
                cpu.setCiclosEjecucionCPU(0);
            }
            if (cpu.getProcesoEjecutando() != null) {
                cpu.incrementarCiclos();
            }
        }
    }

    private void planificarSJF(Cola colaListos, CPU[] cpus) {
    for (CPU cpu : cpus) {
        if (cpu.getProcesoEjecutando() == null && !colaListos.estaVacia()) {
            Proceso procesoMasCorto = null;
            Nodo nodoAnterior = null;
            Nodo nodoActual = colaListos.getFrente();
            Nodo nodoMasCorto = null;

            // Encontrar el shortest job
            while (nodoActual != null) {
                Proceso procesoActual = nodoActual.getProceso();
                if (procesoMasCorto == null || procesoActual.getNumInstrucciones() < procesoMasCorto.getNumInstrucciones()) {
                    procesoMasCorto = procesoActual;
                    nodoMasCorto = nodoAnterior;
                }
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getSiguiente();
            }

            // Desencolar el proceso más corto
            if (nodoMasCorto == null) {
                colaListos.setFrente(colaListos.getFrente().getSiguiente());
            } else {
                nodoMasCorto.setSiguiente(nodoMasCorto.getSiguiente().getSiguiente());
            }

            cpu.setProcesoEjecutando(procesoMasCorto);
            cpu.setCiclosEjecucionCPU(0);
        }
        if (cpu.getProcesoEjecutando() != null) {
            cpu.incrementarCiclos();
        }
    }
}

    private void planificarRoundRobin(Cola colaListos, CPU[] cpus) {
        for (CPU cpu : cpus) {
            if (cpu.getProcesoEjecutando() == null || cpu.getCiclosEjecucionCPU() >= quantum) {
                if (cpu.getProcesoEjecutando() != null) {
                    colaListos.encolar(cpu.getProcesoEjecutando());
                }
                if (!colaListos.estaVacia()) {
                    Proceso siguienteProceso = colaListos.desencolar();
                    cpu.setProcesoEjecutando(siguienteProceso);
                    cpu.setCiclosEjecucionCPU(0);
                }
            }
            if (cpu.getProcesoEjecutando() != null) {
                cpu.incrementarCiclos();
            }
        }
    }
    
    private void planificarSRT(Cola colaListos, CPU[] cpus) {
    for (CPU cpu : cpus) {
        if (cpu.getProcesoEjecutando() == null && !colaListos.estaVacia()) {
            Proceso procesoMasCorto = null;
            Nodo nodoAnterior = null;
            Nodo nodoActual = colaListos.getFrente();
            Nodo nodoMasCorto = null;

            // Encontrar el proceso con el menor tiempo de ejecución restante
            while (nodoActual != null) {
                Proceso procesoActual = nodoActual.getProceso();
                int tiempoRestante = procesoActual.getNumInstrucciones() - procesoActual.getPc();
                if (procesoMasCorto == null || tiempoRestante < (procesoMasCorto.getNumInstrucciones() - procesoMasCorto.getPc())) {
                    procesoMasCorto = procesoActual;
                    nodoMasCorto = nodoAnterior;
                }
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getSiguiente();
            }

            // Desencolar el proceso más corto
            if (nodoMasCorto == null) {
                colaListos.setFrente(colaListos.getFrente().getSiguiente());
            } else {
                nodoMasCorto.setSiguiente(nodoMasCorto.getSiguiente().getSiguiente());
            }

            cpu.setProcesoEjecutando(procesoMasCorto);
            cpu.setCiclosEjecucionCPU(0);
        } else if (cpu.getProcesoEjecutando() != null) {
            // Verificar si hay un proceso en la cola con menor tiempo de ejecución restante
            Proceso procesoActual = cpu.getProcesoEjecutando();
            int tiempoRestanteActual = procesoActual.getNumInstrucciones() - procesoActual.getPc();
            Nodo nodoAnterior = null;
            Nodo nodoActual = colaListos.getFrente();
            Nodo nodoMasCorto = null;
            Proceso procesoMasCorto = null;

            while (nodoActual != null) {
                Proceso procesoEnCola = nodoActual.getProceso();
                int tiempoRestanteEnCola = procesoEnCola.getNumInstrucciones() - procesoEnCola.getPc();
                if (tiempoRestanteEnCola < tiempoRestanteActual) {
                    procesoMasCorto = procesoEnCola;
                    nodoMasCorto = nodoAnterior;
                    tiempoRestanteActual = tiempoRestanteEnCola;
                }
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getSiguiente();
            }

            // Si se encuentra un proceso con menor tiempo de ejecución restante, interrumpir el proceso actual
            if (procesoMasCorto != null) {
                colaListos.encolar(cpu.getProcesoEjecutando());
                if (nodoMasCorto == null) {
                    colaListos.setFrente(colaListos.getFrente().getSiguiente());
                } else {
                    nodoMasCorto.setSiguiente(nodoMasCorto.getSiguiente().getSiguiente());
                }
                cpu.setProcesoEjecutando(procesoMasCorto);
                cpu.setCiclosEjecucionCPU(0);
            }
        }

        if (cpu.getProcesoEjecutando() != null) {
            cpu.incrementarCiclos();
        }
    }
}

    private void planificarFeedback(Cola[] colasPrioridad, CPU[] cpus) {
        for (CPU cpu : cpus) {
            if (cpu.getProcesoEjecutando() == null) {
                // Buscar el primer proceso en las colas de mayor a menor prioridad
                for (int i = 0; i < colasPrioridad.length; i++) {
                    if (!colasPrioridad[i].estaVacia()) {
                        Proceso siguienteProceso = colasPrioridad[i].desencolar();
                        cpu.setProcesoEjecutando(siguienteProceso);
                        cpu.setCiclosEjecucionCPU(0);
                        break;
                    }
                }
            } else {
                // Incrementar el contador de ciclos de ejecución del proceso actual
                cpu.incrementarCiclos();

                // Mover el proceso a una cola de menor prioridad si ha utilizado su quantum
                int prioridadActual = cpu.getProcesoEjecutando().getPrioridad();
                if (cpu.getCiclosEjecucionCPU() >= quantum) {
                    if (prioridadActual < colasPrioridad.length - 1) {
                        colasPrioridad[prioridadActual + 1].encolar(cpu.getProcesoEjecutando());
                    } else {
                        colasPrioridad[prioridadActual].encolar(cpu.getProcesoEjecutando());
                    }
                    cpu.setProcesoEjecutando(null);
                }
            }
        }
    }
}
