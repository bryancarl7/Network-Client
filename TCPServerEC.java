/**
 * Project 8 EC
 * @author Bryan Carl
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServerEC{
    
    public static void main(String args[]) throws Exception{
        //Setup Arraylist Vars//
        ArrayList<Integer> clientInput = null;
        ArrayList<Integer> clientOutput = null;
        ObjectOutputStream outToClient = null;
        ObjectInputStream clientFrom = null;
        
        //Connect to Socket and Object Streams//
        try{
        ServerSocket welcomeSocket = new ServerSocket(5000);
        Socket connectionSocket = welcomeSocket.accept();
        outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());           
        clientFrom = new ObjectInputStream(connectionSocket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        
        //Enter loop for standby//
        while (true) {
            //Attempt to read client input//
            try{
                clientInput = (ArrayList<Integer>) clientFrom.readObject();
                clientOutput = new ArrayList<Integer>();
            } catch(EOFException e){
                break;
            } catch(Exception e){
                e.printStackTrace();
            }
            //Add to return list
            for (int i = 0; i < clientInput.size();i++){
                int n = clientInput.get(i);
                if (prime(n)){
                    clientOutput.add(n);
                }
            }
            
            //return to client//
            try {
            outToClient.writeObject(clientOutput);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
            //Close all sockets for security//
//            outToClient.close();
//            clientFrom.close();
//            welcomeSocket.close();
        
    }
    
    //Prime Checker Function
    public static boolean prime(int n){
        for (int i=2; 2*i < n; i++){
            if (n % i==0){
               return false;
            }
        }
        return true;
    }
}