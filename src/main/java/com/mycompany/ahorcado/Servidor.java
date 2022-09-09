package com.mycompany.ahorcado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
            
     public static void main(String[] args) {
        try{
            int pto =8000;
            
            ServerSocket servidor = new ServerSocket(pto);
            System.out.println("Servidor iniciado en el puerto "+pto+" .. esperando cliente..");
            
            
            while(true){ //Este ciclo es infinito
                /*A pesar de tener un ciclo infinito, las instrucciones de un socket bloqueante
                detienen el flujo del programa (se queda en espera) hasta obtener todo lo necesario para poder llevarla a cabo
                Por esa razón, el ciclo no da vueltas todo el tiempo, sino que las instrucciones que están dentro, se quedan esperando hasta poder llevarse a cabo
                */
                
                //Esperamos a que un cliente solicite conexión al servidor 
                Socket cliente = servidor.accept(); //Servidor espera hasta aceptar una conexión, se define como socket en cliente (socket servidor en espera)
                
                //Imprimimos que se ha aceptado la conexión
                System.out.println("Cliente conectado desde "+cliente.getInetAddress()+":"+cliente.getPort()); 
                
                //Establecemos buffers para lectura y escritura  
                PrintWriter escritor = new PrintWriter(new OutputStreamWriter(cliente.getOutputStream(),"ISO-8859-1"));//Buffer para escritura sobre el socket del cliente
                BufferedReader lector = new BufferedReader(new InputStreamReader(cliente.getInputStream(),"ISO-8859-1"));//Buffer para lectura del socket del cliente
                
                while(true){//CICLO PARA MANTENER MENSAJERÍA CON EL CLIENTE QUE SE HA CONECTADO                    
                    String msj = lector.readLine(); //  Cuardamos lo que se lee del buffer (espera hasta que se haya algo en el buffer de lectura)
                    
                    if(msj.compareToIgnoreCase("salir")==0){ //SI EL CLIENTE INDICA LA PALABRA "salir", se cierra la conexión con ese cliente
                        System.out.println("Cliente cierra conexion");
                        lector.close();
                        escritor.close();
                        cliente.close();
                        break;
                    } else{
                        System.out.println("Mensaje recibido: "+msj+" devolviendo eco"); //IMPRIMIMOS EL MENSAJE QUE LLEGÓ POR EL SOCKET
                        escritor.println("El servidor ha recibido el mensaje: "+msj); //Le escribimos al cliente, que el mensaje fue recibido
                        escritor.flush(); //Se limpia el buffer de escritura
                    }                    
                }                                
                
                //UNA VEZ QUE ATENDIÓ A UN CLIENTE, SE QUEDA ESPERANDO PARA ATENDER A ALGÚN OTRO
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}