/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xbee_coordinator_java_example;

//import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francisco
 */

public class Coordinator extends XBeeDevice implements IDataReceiveListener {
    String keySend = "sendMe";

    public Coordinator(String port, int baudRate) {
        super(port, baudRate);
    }

  
    public void getInfo(){
        try {
            System.out.println("****************************************");
            System.out.println("FirmwareVersion: "+this.getFirmwareVersion());
            System.out.println("HardwareVersion: "+this.getHardwareVersion());
            System.out.println("PANID: "+Arrays.toString(this.getPANID()));
            System.out.println("****************************************");
        } catch (XBeeException ex) {
            Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //imprime los datos que llegan desde los nodos xbees
    @Override
    public void dataReceived(XBeeMessage xbm) {
         System.out.format("From %s >> %s | %s%n", xbm.getDevice().get64BitAddress(),
                HexUtils.prettyHexString(HexUtils.byteArrayToHexString(xbm.getData())),
                new String(xbm.getData()));
    }
    
    public void requestData(){
        
        try {
            this.sendBroadcastData(keySend.getBytes());
        } catch (XBeeException ex) {
            Logger.getLogger(Coordinator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    
    
    
}
