/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex
 */
public class Ping implements Runnable{

    private String ip;
    private static boolean resultado;
    private boolean ping;
    private final int TIMEOUTPING = 1000;

    public Ping(String ip){
        this.ip = ip;
        
    }
    
    
    public void ping(){
        
        String hostName = "";
        try {
            InetAddress address = InetAddress.getByName(ip);

            try {
                resultado = address.isReachable(TIMEOUTPING);
                if (resultado) {
                    hostName = address.getHostName();
                }
            } catch (IOException ex) {
                Logger.getLogger(Lanzador.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Lanzador.class.getName()).log(Level.SEVERE, null, ex);
        }
        Lanzador.setResultado(ip,hostName, resultado);
    }


    @Override
    public void run() {
        ping();
    }


}
