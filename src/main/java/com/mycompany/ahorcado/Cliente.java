package com.mycompany.ahorcado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {
     public static void main(String[] args) {
          try{
            int pto = 8000;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in,"ISO-8859-1"));//CONJUNTO DE CARACTERES QUE ACEPTA EL ESPAÑOL LATINO (buffer de teclado)
            InetAddress host = null; //PARA GUARDAR LA DIRECCIÓN 1P
            String dir=""; //CADENA PARA LEER LA IP
            
            try{
                System.out.println("Escribe la direccion del servidor:"); 
                dir = br.readLine();  //Leemos COMO CADENA la dirección del server (DESDE TECLADO)
                host = InetAddress.getByName(dir); //Pasa de CADENA al formato de dirección IP
            }catch(Exception n){
               main(args); 
            }

            Socket cl = new Socket(host,pto); //Establecemos conexión con el servidor mediante el socket (INSTANCIAMOS EL SOCKET CLIENTE)
            
            System.out.println("Conexion con el servidor "+dir+":"+pto+" establecida\n"); //IMPRIMIMOS QUE SE HA CONECTADO CON EL SERVER
            
            
            //CREAMOS BUFFERS PARA LECTURA Y ESCRITURA DEL SOCKET
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream(),"ISO-8859-1")); //BUFFER DE ESCRITURA QUE ACEPTA EL CONJUNTO DE CARACTERES PARA ESPAÑOL LATINO
            BufferedReader br1 = new BufferedReader(new InputStreamReader(cl.getInputStream(),"ISO-8859-1")); //BUFFER DE LECTURA QUE ACEPTA EL CONJUNTO DE CARACTERES PARA ESPAÑOL LATINO
            
            
            while(true){
                System.out.println("Escribe un mensaje, <ENTER> para enviar, \"salir\" para terminar");
                String mensaje = br.readLine(); //LEEMOS DE TECLADO EL MENSAJE QUE QUIERO ENVIEAR
                pw.println(mensaje); //ESCRIBIMOS (MANDAMOS) EL MENSAJE a través del socket
                pw.flush(); //limpiamos el buffer de escritura
                
                if(mensaje.compareToIgnoreCase("salir")==0){ //EN CASO DE QUERER CERRAR LA CONEXIÓN CON EL SERVIDOR, SE ESCRIBE "salir"
                    br.close();
                    br1.close();
                    pw.close();
                    cl.close();
                    System.exit(0);
                } else{
                    String eco = br1.readLine();  //LEEMOS LO QUE NOS RESPONDE EL SERVIDOR LUEGO DE ENVIARLE EL MENSAJE
                    System.out.println("Eco recibido desde "+cl.getInetAddress()+":"+cl.getPort()+" "+eco); //IMPRIMIMOS LO QUE RESPONDIÓ EL SERVER
                }
                
            }
            
                        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}