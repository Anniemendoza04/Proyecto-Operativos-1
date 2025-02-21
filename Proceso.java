/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author annie
 */
public class Proceso implements Runnable {
    
    public enum Tipo {
        SISTEMA_OPERATIVO, USUARIO
    }

    public enum STATUS {
        RUNNING, BLOCKED, READY
    }

    private int id;
    private String nombre;
    private STATUS estado;
    private int pc;
    private int mar;
    private int numInstrucciones;   
    private boolean cpuBound;
    private int ciclosExcepcion;
    private int ciclosSatisfaccionExcepcion;
    private int[] registros;
    private int tiempoLlegada;
    private Tipo tipo;
    private int prioridad;

    public Proceso(int id, String nombre, STATUS estado, int pc, int numInstrucciones, boolean cpuBound, int ciclosExcepcion, int ciclosSatisfaccionExcepcion, int numRegistros) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.pc = pc;
        this.mar = pc; 
        this.numInstrucciones = numInstrucciones;
        this.cpuBound = cpuBound;
        this.ciclosExcepcion = ciclosExcepcion;
        this.ciclosSatisfaccionExcepcion = ciclosSatisfaccionExcepcion;
        this.registros = new int[numRegistros];
        this.tiempoLlegada = 0; //Inicializa en 0
        this.tipo = Tipo.USUARIO; // Por defecto 
        this.prioridad = 0;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public STATUS getEstado() {
        return estado;
    }

    public void setEstado(STATUS estado) {
        this.estado = estado;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int nuevoPc) {
        this.pc = nuevoPc;
        this.mar = pc;
    }
    
    public void setMar(int mar) {
        this.mar = mar;
    }
    
    public int getMar() {
        return mar;
    }

    public int getNumInstrucciones() {
        return numInstrucciones;
    }

    public void setNumInstrucciones(int numInstrucciones) {
        this.numInstrucciones = numInstrucciones;
    }

    public boolean isCpuBound() {
        return cpuBound;
    }

    public void setCpuBound(boolean cpuBound) {
        this.cpuBound = cpuBound;
    }

    public int getCiclosExcepcion() {
        return ciclosExcepcion;
    }

    public void setCiclosExcepcion(int ciclosExcepcion) {
        this.ciclosExcepcion = ciclosExcepcion;
    }

    public int getCiclosSatisfaccionExcepcion() {
        return ciclosSatisfaccionExcepcion;
    }

    public void setCiclosSatisfaccionExcepcion(int ciclosSatisfaccionExcepcion) {
        this.ciclosSatisfaccionExcepcion = ciclosSatisfaccionExcepcion;
    }

    public int[] getRegistros() {
        return registros;
    }

    public void setRegistros(int[] registros) {
        this.registros = registros;
    }
    
    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public void run() {
        // Simulación del proceso
        while (pc < numInstrucciones) {
            // Simular la ejecución de una instrucción
            pc++;
            try {
                Thread.sleep(100); // Simular el tiempo de ejecución de una instrucción
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        estado = STATUS.BLOCKED; // Cambiar el estado del proceso cuando termina
    }
    
    @Override
    public String toString() {
        StringBuilder registrosStr = new StringBuilder("[");
        for (int i = 0; i < registros.length; i++) {
            registrosStr.append(registros[i]);
            if (i < registros.length - 1) {
                registrosStr.append(", ");
            }
        }
        registrosStr.append("]");

        return "Proceso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", pc=" + pc +
                ", mar=" + mar +
                ", numInstrucciones=" + numInstrucciones +
                ", cpuBound=" + cpuBound +
                ", ciclosExcepcion=" + ciclosExcepcion +
                ", ciclosSatisfaccionExcepcion=" + ciclosSatisfaccionExcepcion +
                ", registros=" + registrosStr.toString() +
                ", tiempoLlegada=" + tiempoLlegada + 
                ", tipo=" + tipo +
                ", prioridad=" + prioridad +
                '}';
    }
}
