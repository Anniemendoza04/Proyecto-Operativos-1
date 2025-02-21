/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author annie
 */
public class Main {
    public static void main(String[] args) {
        SimuladorOS simulador = new SimuladorOS(2, 1000, Planificador.AlgoritmoPlanificacion.SRT);
        InterfazSimulacion vista = new InterfazSimulacion();
        vista.setVisible(true);
        simulador.pruebaSRT();
    }
}

