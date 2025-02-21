/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author annie
 */
public class Cola {
    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    /**
     * Verifica si la cola está vacía.
     * @return true si la cola está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return frente == null;
    }

    /**
     * Añade un proceso al final de la cola.
     * @param proceso El proceso a encolar.
     */
    public void encolar(Proceso proceso) {
        Nodo nuevoNodo = new Nodo(proceso);
        if (estaVacia()) {
            frente = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.setSiguiente(nuevoNodo);
            fin = nuevoNodo;
        }
    }

    /**
     * Elimina y devuelve el proceso al frente de la cola.
     * @return El proceso al frente de la cola, o null si la cola está vacía.
     */
    public Proceso desencolar() {
        if (estaVacia()) {
            return null;
        } else {
            Proceso proceso = frente.getProceso();
            frente = frente.getSiguiente();
            if (frente == null) {
                fin = null;
            }
            return proceso;
        }
    }

    /**
     * Devuelve el proceso al frente de la cola sin eliminarlo.
     * @return El proceso al frente de la cola, o null si la cola está vacía.
     */
    public Proceso verFrente() {
        if (estaVacia()) {
            return null;
        } else {
            return frente.getProceso();
        }
    }

    /**
     * Devuelve el nodo al frente de la cola.
     * @return El nodo al frente de la cola.
     */
    public Nodo getFrente() {
        return frente;
    }

    /**
     * Establece el nodo al frente de la cola.
     * @param frente El nodo a establecer como frente.
     */
    public void setFrente(Nodo frente) {
        this.frente = frente;
    }

    
}