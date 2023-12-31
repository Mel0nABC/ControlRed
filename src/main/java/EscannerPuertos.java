/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

/**
 *
 * @author alex
 */
public class EscannerPuertos {

    private ArrayList<Integer> arrayPuertosAbiertos = new ArrayList<>();
    private Socket sock;

    public void getPuerto(String host, int puerto) {

        try {
            //new Socket(host, puerto);
            Socket sock = new Socket(host, puerto);
            if (sock.isConnected()) {
                sock.close();
                System.out.println("Puerto abierto: " + puerto);
            }

            arrayPuertosAbiertos.add(puerto);
        } catch (IOException ex) {
            System.out.println("Puerto cerrado: " + puerto);
        }
    }

    public ArrayList<Integer> getRangoPuerto(String host, int puertoInicio, int puertoFinal) {

        for (int i = puertoInicio; i <= puertoFinal; i++) {
            sock = new Socket();
            try {
                sock = new Socket(host, i);
                if (sock.isConnected()) {
                    System.out.println("Puerto abierto: " + i);
                }
                arrayPuertosAbiertos.add(i);
                sock.close();
            } catch (IOException ex) {
                System.out.println("Puerto cerrado: " + i);
            }

        }

        return arrayPuertosAbiertos;
    }

    public ArrayList<Integer> getArray() {
        return arrayPuertosAbiertos;
    }

}
