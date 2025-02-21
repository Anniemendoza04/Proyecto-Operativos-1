/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author annie
 */
public class MultithreadThing implements Runnable {
    
    private int threadNumber;
    public MultithreadThing (int threadNumber) {
        this.threadNumber = threadNumber;
    }
    
    public void run() {
        for (int i = 1; i <= 5; i++ ) {
            System.out.println(i + " from thread " + threadNumber);
            try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
                
                }
    }
}
}