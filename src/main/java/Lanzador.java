/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex
 */
public class Lanzador {

    private static Scanner scan;

    //Constantes de los actuales menus de opciones.
    private static final int MENUMIN = 1;
    private static final int MENUMAX = 2;

    //Constante de límite máximo de un rango de IP
    private static final int RANGOMAX = 255;

    //Constante para los hilos simultáneos a la hora de escanear ip's
    private static final int THREADS_SIZE = 500;

    //Declaración e inicialización de variables del aplicativo.
    private static String ipInicio = "";
    private static String ipFinal = "";
    private static String ipEscan = "";
    private static String puertos = "";
    private static String[] arrayIpInicio;
    private static String[] arrayIpFinal;
    private static String[] arrayIpEscan;
    private static String[] arrayPuertos;
    private static int contadorIpScan = 0;
    private static ArrayList<Thread> listaThreads = new ArrayList<>();
    private static ArrayList<String> ipListaCompleta = new ArrayList<>();
    private static ArrayList<String> ips;
    private static ArrayList<Integer> arrayPuertosInt = new ArrayList<>();
    private static int[] arrayIntInicio;
    private static int[] arrayIntFinal;

    //Variables del control de finalizar o continuar aplicación.
    private static boolean cerrarAplicacion = false;
    private static int contadorAplicacion = 0;

    //Objeto FileWriter para guardar el archivo de log mas adelaante.
    private static FileWriter fichero;

    public static void main(String args[]) {

        System.out.println("#########################################");
        System.out.println("## BIENVENIDO A LA APLICACIÓN ESCANER ##");
        System.out.println("#########################################");
        System.out.println("");

        do {
            scan = new Scanner(System.in);

            System.out.println("###############################");
            System.out.println("## OPCIONES DE LA APLICACIÓN ##");
            System.out.println("###############################");
            System.out.println("1) ESCANER DE RED");
            System.out.println("2) PORT ESCANER");
            System.out.println("###############################");

            //Variable para control del bucle while del menú, si se introduce un tipo de dato o valor incorrecto, vuelve a pedirlo.
            boolean selecMenu = true;
            do {
                System.out.print("Elija una opción:");
                boolean menuSeleccion = scan.hasNextInt();

                if (menuSeleccion) {
                    int valorMenu = scan.nextInt();
                    if (valorMenu >= MENUMIN && valorMenu <= MENUMAX) {
                        scan.nextLine();

                        //Switch con los actuales menús (opciones de la aplicación).
                        switch (valorMenu) {
                            case 1:
                                escanearRed();
                                selecMenu = false;
                                break;
                            case 2:
                                portEscaner();
                                selecMenu = false;
                                break;

                        }

                        //Finalizamos bucle de selección de menú.
                        menuSeleccion = false;

                    } else {

                        //Si hay error al introducir las opciones del menú.
                        System.out.println("Debe seleccionar un número del menú, del " + MENUMIN + " al " + MENUMAX);
                    }
                } else {
                    scan.nextLine();
                    //Si hay error por tipo de dato, deben ser integers, los que indica el menú.
                    System.out.println("Debe seleccionar un número del menú, del " + MENUMIN + " al " + MENUMAX);
                }
            } while (selecMenu);

            contadorAplicacion++;

            if (contadorAplicacion > 0) {
                System.out.println("");
                System.out.println("##############################");
                System.out.println("## ¿DESEA REPETIR ACCIONES? ##");
                System.out.println("##############################");
                System.out.println("1) Sí");
                System.out.println("2) No");
                System.out.println("###############################");

                boolean respuestaContinua = scan.hasNextInt();
                if (respuestaContinua) {
                    int valorRespuesta = scan.nextInt();
                    if (valorRespuesta >= 1 && valorRespuesta <= 2) {
                        scan.nextLine();

                        //Switch con los actuales menús (opciones de la aplicación).
                        switch (valorRespuesta) {
                            case 1:
                                break;
                            case 2:
                                cerrarAplicacion = true;
                                break;

                        }

                    } else {

                        //Si hay error al introducir las opciones disponible.
                        System.out.println("Debe seleccionar un número de opción válida,  1) Sí o 2)No");
                    }
                } else {
                    scan.nextLine();
                    //Si hay error por tipo de dato, deben ser integers, los que indica las opciones disponibles..
                    System.out.println("Debe seleccionar un número de opción válida,  1) Sí o 2)No");
                }

            }

        } while (!cerrarAplicacion);

        System.out.println("#####################################");
        System.out.println("## ¡MUCHAS GRACIAS Y HASTA PRONTO! ##");
        System.out.println("#####################################");

    }

//############### INICIO ESCANER DE RED ###############
    public static void escanearRed() {

        System.out.println("####################");
        System.out.println("## ESCANER DE RED ##");
        System.out.println("####################");

        boolean asignaIpInicio = true;

        do {
            System.out.println("Por favor, indique ip de INICIO:");
            ipInicio = scan.nextLine();

            arrayIpInicio = ipInicio.split("\\.");

            if (arrayIpInicio.length == 4) {
                asignaIpInicio = false;
            } else {
                System.out.println("Algo ocurre con la ip asignada, vuelva a intentarlo.");
            }
        } while (asignaIpInicio);

        boolean asignaIpFinal = true;

        do {
            System.out.println("Por favor, indique ip de FINAL:");
            ipFinal = scan.nextLine();

            arrayIpFinal = ipFinal.split("\\.");

            boolean testRangos = false;

            if (arrayIpFinal.length == 4) {

                if (ipInicio.compareTo(ipFinal) > 0) {
                    testRangos = true;
                }

                if (testRangos) {
                    System.out.println("La ip de inicio es mayor que la ip final, reviselo, por favor..");
                    System.out.println("IP Inicio: " + ipInicio + " - IP Final: " + ipFinal);
                    System.out.println("Vuelva a intentearlo.");
                    asignaIpFinal = true;
                } else {

                    asignaIpFinal = false;
                    System.out.println("COMIENZA EL ESCANEO.");
                }
            } else {
                System.out.println("Algo ocurre con la ip asignada, vuelva a intentarlo.");
            }
        } while (asignaIpFinal);

        try {

            recorrerRangoIp();

            //Objeto calendar, para la obtención de horas, cara a guardar el log.
            Calendar calendario = Calendar.getInstance();
            int dia = calendario.get(Calendar.DAY_OF_MONTH);
            int mes = calendario.get(Calendar.MONTH);
            int year = calendario.get(Calendar.YEAR);
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int minutos = calendario.get(Calendar.MINUTE);
            int segundos = calendario.get(Calendar.SECOND);

            String fecha = hora + "h" + minutos + "m" + segundos + "s_" + dia + "-" + mes + "-" + year;

            //inicialización del objeto fichero, para indicar dónde guardaremos el log y su estructura de nombree
            fichero = new FileWriter(fecha + "_escaner.log");
            //Creamos el fichero .log.
            PrintWriter escribe = new PrintWriter(fichero);

            System.out.println("#########################################################################################");
            System.out.println("#################### IP INICIO: " + ipInicio + " -- IP FINAL: " + ipFinal + " ####################");
            System.out.println("Finalizó el escaneo de la red y se detectaron " + ipListaCompleta.size() + " ip's conectadas en " + contadorIpScan + " escaneadas.");
            System.out.println("#########################################################################################");
            System.out.println("IP's registradas en la red:");

            //Recorremos todas las ip's encontradas y las escribimos en el log con println.
            for (String s : ipListaCompleta) {

                System.out.println("IP: " + s);
                escribe.println(s);
            }
            System.out.println("#################################################################################1#########");

        } catch (IOException ex) {
            Logger.getLogger(Lanzador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }

            } catch (IOException ex) {
                System.out.println("Fichero NULL");
            }
        }
    }

    public static void recorrerRangoIp() {

        //Arrays de enteros para pasar los arrays de string obtenidos de los strings de ipInicio e ipFinal.
        arrayIntInicio = new int[4];
        arrayIntFinal = new int[4];

        //Pasamos el array de Strings a entero.
        for (int i = 0; i < arrayIpInicio.length; i++) {
            arrayIntInicio[i] = Integer.parseInt(arrayIpInicio[i]);
            arrayIntFinal[i] = Integer.parseInt(arrayIpFinal[i]);
        }

        int ipIn_1 = arrayIntInicio[0];
        int ipIn_2 = arrayIntInicio[1];
        int ipIn_3 = arrayIntInicio[2];
        int ipIn_4 = arrayIntInicio[3];

        ips = new ArrayList<>();

        String ip;

        do {

            ip = ipIn_1 + "." + ipIn_2 + "." + ipIn_3 + "." + ipIn_4;
            contadorIpScan++;

            if (ips.size() < THREADS_SIZE) {
                ips.add(ip);
            } else {
                threadPing(ips);
                ips = new ArrayList<>();
                ips.add(ip);
            }

            if (ipIn_4 < RANGOMAX) {
                ipIn_4++;
            } else {

                if (ipIn_3 < RANGOMAX) {
                    ipIn_4 = 1;
                    ipIn_3++;
                } else {

                    if (ipIn_2 < RANGOMAX) {
                        ipIn_4 = 1;
                        ipIn_3 = 1;
                        ipIn_2++;
                    } else {
                        if (ipIn_1 < RANGOMAX) {
                            ipIn_4 = 1;
                            ipIn_3 = 1;
                            ipIn_2 = 1;
                            ipIn_1++;
                        }
                    }

                }

            }
            System.out.println(ip);
        } while (!ipFinal.equals(ip));

        if (ips.size() != THREADS_SIZE) {
            threadPing(ips);
        }

    }

    public static void threadPing(ArrayList<String> ips) {

        for (String ip : ips) {
            Ping ping = new Ping(ip);
            Thread t = new Thread(ping);
            t.setName(ip);
            t.start();
            listaThreads.add(t);
        }
        boolean estado = true;
        while (estado) {

            estado = false;
            for (Thread t : listaThreads) {
                if (t.isAlive()) {
                    t.interrupt();
                    estado = true;
                }
            }
        }
    }

    public static void setResultado(String ip, String hostName, boolean resultado) {

        if (resultado) {
            ipListaCompleta.add(ip + " -> hostname: " + hostName);
        }

    }
    //############### FINAL ESCANER DE RED ###############

    //############### INICIO ESCANER DE PUERTOS ###############
    //Método para escanear los puertos, opción del menú número 2.
    public static void portEscaner() {
        System.out.println("########################");
        System.out.println("## ESCANER DE PUERTOS ##");
        System.out.println("########################");

        boolean asignaIpEscanear = true;

        do {
            System.out.println("Por favor, indique ip a escanear:");
            ipEscan = scan.nextLine();

            arrayIpEscan = ipEscan.split("\\.");

            if (arrayIpEscan.length == 4) {
                asignaIpEscanear = false;
            } else {
                System.out.println("Algo ocurre con la ip asignada, vuelva a intentarlo.");
            }
        } while (asignaIpEscanear);

        boolean seleccionPuertos = true;
        boolean puertoIndividual = true;
        do {

            System.out.println("Por favor, indique los puertos. Individual, concretos, rango");
            System.out.println("Ejemplo individual: 2000");
            System.out.println("Ejemplo concretos: 2000,2001,8080 ...");
            System.out.println("Ejemplo rango: 2000-2010");
            puertos = scan.nextLine();

            boolean tipoSeparacion = false;

            for (int i = 0; i < puertos.length(); i++) {
                if (puertos.charAt(i) == ',') {
                    //Paraa separación por coma
                    tipoSeparacion = true;
                    puertoIndividual = false;
                } else if (puertos.charAt(i) == '-') {
                    tipoSeparacion = false;
                    puertoIndividual = false;
                }
            }

            if (isNumeric(puertos) && puertoIndividual == true) {
                arrayPuertosInt.add(Integer.parseInt(puertos));
                seleccionPuertos = false;
                escanerPuertos();
            }

            if (!puertoIndividual) {
                //if para gestión de puertos individuales
                if (tipoSeparacion) {
                    System.out.println("numeric");
                    arrayPuertos = puertos.split(",");

                    boolean multiplesPuertosOk = true;
                    for (int i = 0; i < arrayPuertos.length; i++) {
                        if (isNumeric(arrayPuertos[i])) {
                            arrayPuertosInt.add(Integer.parseInt(arrayPuertos[i]));
                            seleccionPuertos = false;
                        } else {
                            System.out.println("El caràcter '" + arrayPuertos[i] + "' no corresponde a un número, se reinicia la aplicación.");
                            multiplesPuertosOk = false;
                            seleccionPuertos = true;
                        }

                    }

                    if (multiplesPuertosOk) {
                        escanerPuertos();
                    }

                } else {//else para gestión de rango de puertos.
                    arrayPuertos = puertos.split("-");
                    int puertoInicio = 0;
                    int puertoFinal = 0;
                    if (isNumeric(arrayPuertos[0])) {
                        puertoInicio = Integer.parseInt(arrayPuertos[0]);
                    } else {
                        System.out.println("El carácter '" + arrayPuertos[0] + "' no corresponde a un número, se reinicia la aplicación.");
                    }

                    if (isNumeric(arrayPuertos[1])) {
                        puertoFinal = Integer.parseInt(arrayPuertos[1]);
                    } else {
                        System.out.println("El carácter '" + arrayPuertos[1] + "' no corresponde a un número, se reinicia la aplicación.");
                    }
                    if (isNumeric(arrayPuertos[0]) && isNumeric(arrayPuertos[1])) {
                        seleccionPuertos = false;
                        escanerPuertosRango(puertoInicio, puertoFinal);
                    }

                }
            }
        } while (seleccionPuertos);

    }

    public static void escanerPuertos() {

        EscannerPuertos escaner = new EscannerPuertos();

        for (int i : arrayPuertosInt) {
            escaner.getPuerto(ipEscan, i);
        }
        ArrayList<Integer> array = escaner.getArray();

        if (array.size() == 0) {
            System.out.println("No se han localizado ningún puerto abierto.");
        } else {
            System.out.println("###############################################");
            System.out.println("### Finalizó el escaneo de puertos abiertos ###");
            System.out.println("###############################################");
            for (int i : array) {
                System.out.println(i + " - open");
            }
            System.out.println("###############################################");
        }

    }

    public static void escanerPuertosRango(int puertoInicio, int puertoFinal) {
        EscannerPuertos escaner = new EscannerPuertos();

        ArrayList<Integer> array = escaner.getArray();
        array = escaner.getRangoPuerto(ipEscan, puertoInicio, puertoFinal);

        if (array.size() == 0) {
            System.out.println("No se han localizado ningún puerto abierto.");
        } else {

            System.out.println("###############################################");
            System.out.println("### Finalizó el escaneo de puertos abiertos ###");
            System.out.println("###############################################");
            System.out.println("Puertos del " + puertoInicio + " al " + puertoFinal);
            for (int i : array) {
                System.out.println(i + " - open");
            }
            System.out.println("###############################################");

        }

    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

//############### FINAL ESCANER DE PUERTOS ###############
    }

}
