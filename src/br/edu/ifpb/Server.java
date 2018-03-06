
package br.edu.ifpb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wtporto
 */
 public class Server {
     
     private ServerSocket server;
     private Socket socket;
     
     public void startServer(){
         
         try {
             
             server = new ServerSocket(12345, 100);             
             while( true ){
                 
                 socket = server.accept();
                 
                 ThreadCliente t = new ThreadCliente(socket);
                 t.start();
                 
             }
             
         } catch (IOException ex) {
             ex.printStackTrace();
         }
     }
     
     public static void main(String[] args) {
         new Server().startServer();
     }
 }