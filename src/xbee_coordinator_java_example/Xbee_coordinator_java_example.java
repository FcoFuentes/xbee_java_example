/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package xbee_coordinator_java_example;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francisco
 */
public class Xbee_coordinator_java_example {

    private static final String PORT = "/dev/ttyUSB0";
    private static final int BAUD_RATE = 9600;
    private static final Coordinator coordinator = new Coordinator(PORT, BAUD_RATE);
  
    public static void main(String[] args) {
        coordinator.getInfo();
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(getWaitTime());
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Xbee_coordinator_java_example.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //el tiempo de peticion y recepcion de datos puede ser variable, 
    //calcula los segundos faltante para hacer peticiones cada 10 min exactos.
    public static int getWaitTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int dec = minutes / 10;
        int uni = minutes % 10;
        int segundosEspera = ((9 - uni) * 60) + (60 - seconds);
        System.out.println("dec: " + dec + "\t uni: " + uni);
        System.out.println("esperar: " + segundosEspera + " seg.");
        System.out.println("Hora inicio: " + hour + ":" + minutes + ":" + seconds);
        return segundosEspera;
    }
    
    //ejecuta comando en consola para actualizar la hora con google.cl
    public static void updateTime() {
        try {
            Process process = Runtime.getRuntime().exec("sudo date -s \"$(wget -qSO- --max-redirect=0 google.cl 2>&1 | grep Date: | cut -d' ' -f5-8)Z\"");
        } catch (IOException ex) {
            Logger.getLogger(Xbee_coordinator_java_example.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
