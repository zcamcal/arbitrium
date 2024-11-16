package cl.gz.cliente.conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import cl.gz.rmi.Cliente;
import cl.gz.cliente.configuration.Configuration;

/**
 *
 * @author Rednation
 */
public class Checker implements Runnable {
    
    private static Socket clientSocket;
    
    @Override
    public void run() {
        
        String runChecker = Configuration.getInstance().getProperty("runChecker");
        if(runChecker == null){
            runChecker = new String("1");
        }
        while (true) {
            if(runChecker.equals("1")){
                    if (!checkLanInterface()) {
                        Cliente.internLockComputer();
                    }
            }else{
                break;
            }
            try {
                Thread.currentThread().sleep(10_000);
            } catch (InterruptedException ex) {
                System.out.println("Error al dormir");
            }
        }

    }
    
    public static void setSocket(Socket socket){
        Checker.clientSocket = socket;
    }
    

    public boolean checkLanInterface() {

        boolean isConnected = true;

        boolean isTheSearchedInterface = false;
        
        int firstNewLine = 0;

        String command = "ipconfig -all";
        String interfaceToSearch = "Adaptador de Ethernet Ethernet";

        try {

            Process ping = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader;

            try (InputStreamReader inputStreamReader = new InputStreamReader(ping.getInputStream())) {

                bufferedReader = new BufferedReader(inputStreamReader);

                //ESTO DEBERIA SER STRINGBUILDER LUEGO CAMBIAR.
                String output;

                while ((output = bufferedReader.readLine()) != null) {
                    
                    //si son dos nuevas lineas quiere decir que ya paso por nuestra interfaz deseada, y llego a una nueva.
                    if(firstNewLine == 2){
                        break;
                    }
                    
                    if (isTheSearchedInterface && firstNewLine == 1 || output.matches("") && firstNewLine == 1) {
                        if (output.contains("medios desconectados")) {
                            isConnected = false;
                            break;
                        }else if(output.matches("")){
                            ++firstNewLine;
                        }
                    }

                    if (output.contains(interfaceToSearch)) {
                        isTheSearchedInterface = true;
                    }

                    if (isTheSearchedInterface && output.matches("")) {
                        ++firstNewLine;
                    }
                    
                }
            }

            bufferedReader.close();

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return isConnected;

    }

    /**
     * solucion interesante.
     * @return true pero no se por que ya que no se ha utilizado como implementacion.
     */
    public boolean getIpAndMac() {
        InetAddress ip;
        try {
            ip = clientSocket.getLocalAddress();
//            System.out.println("Current IP address : " + ip.getHostAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
//            System.out.print("Current MAC address : ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
        } catch (SocketException | NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }
    
}
