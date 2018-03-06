package br.edu.ifpb;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ThreadCliente extends Thread {
   
    private InputStream input;
    private Socket socket;
    
    public ThreadCliente( Socket socket ){
        
        try {
            
            this.socket = socket;
            this.input = socket.getInputStream();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
      
   @Override
   public void run(){
	   //String com primeiro numero e segundo numero e a operação concatenado separado por virgula.
	   
       String request = request( input ); //requisiÃ§Ã£o
       
       String[] protocol = request.split(";");//quebra o protocolo
       
       String num1 = protocol[0];
       String num2 = protocol[1];
       String operacao = protocol[2]; //recupera a chave do protocolo

       String str = calcular(Double.parseDouble(num1),Double.parseDouble(num2),Integer.parseInt(operacao));
       response(str);
       
   }
   
   //Efetua os calculos
   private String calcular(Double n1, Double n2, int op){
	   Double resultado;
	   	switch (op) {
		case 1:
			resultado = n1 + n2;
			break;
		case 2:
			resultado = n1 - n2;
			break;
		case 3:
			resultado = n1 / n2;
			break;
		case 4:
			resultado = n1 * n2;
			break;

		default:
			resultado = 0D;
			break;
		}
	   return resultado.toString();
   }
   
   //Prepara a string de requisição
   private String request( InputStream in ){
       
       ByteArrayOutputStream temp = new ByteArrayOutputStream();
     
        byte[] b = new byte[1];
     
        try {
            
            while ( in.read(b) != -1) {
                temp.write(b);
            }
            
        } catch (IOException ex) {
            System.out.println( ex.getMessage() );
        }
        
        return new String( temp.toByteArray() );
        
   }
   
   //retorna a saída para o cliente
   private void response( String response ){
        
       try {
            this.socket.getOutputStream().write( response.getBytes() );
            this.socket.getOutputStream().flush();
            //this.socket.close();
            
        } catch (IOException ex) {
            System.out.println( ex.getMessage() );
        }
       
   }
   
}