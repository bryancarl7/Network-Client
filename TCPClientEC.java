 /*
 * @author Bryan Carl
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClientEC {
    
    public static void main(String args[]) throws Exception{
        //Define Vars//
        String sentence = "";
        String num;
        ArrayList<Integer> returnSentence=null;
        Socket clientSocket = null;
        ObjectOutputStream outServer = null;
        ObjectInputStream inServer = null;
        
        //Setup Input for user//
        Scanner inUser = new Scanner(new InputStreamReader(System.in));
        
        //Connect Socket to Server//
        try{
        clientSocket = new Socket("localhost",5000);
        System.out.println("Connected..");
        }catch(UnknownHostException e){
            e.printStackTrace();
        } catch (IOException f){
            f.printStackTrace();
        }
        
        //Output Stream setup//
        try{
        outServer = new ObjectOutputStream(clientSocket.getOutputStream());
        outServer.flush();
        } catch(IOException e){
            e.printStackTrace();
        }
        
        //Input Stream connected to Socket//
        try{
        inServer = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        
        while (true){
            //Define List//
            ArrayList list = new ArrayList<>();

            //Get some input//
            while (true){
                try{
                //Get input and reset flag each time//
                System.out.println("Press '!' to start and stop (# to exit):");
                num = inUser.nextLine();
                boolean flag = true;
                if (num.equals("!"))
                    while (flag == true){
                        //Pass on info to Server//
                        ArrayList<Integer> list2 = genList();
                        outServer.writeObject(list2);
                        System.out.println("To Server: "+list2);
                        
                        //Get info back//
                        try {
                            returnSentence = (ArrayList<Integer>) inServer.readObject();
                            System.out.println("From Server: "+returnSentence);          
                        } catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }
                        //wait//
                        Thread.sleep(1000);
                        if (!(inUser.nextLine().isEmpty()))
                            if (inUser.next().equals("!"))
                                flag=false;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
                if (!(inUser.nextLine().isEmpty()))
                            if (inUser.next().equals("#"))
                                break;
            }
            outServer.close();
            inServer.close();
            clientSocket.close();
            
            
        }
    }
    
    public static ArrayList<Integer> genList(){
        ArrayList<Integer> list = new ArrayList();
        int i =0;
        while (i < 5){
            //I got this code from Stack Overflow//
            //https://stackoverflow.com/questions/5271598/java-generate-random-number-between-two-given-values
            Random r = new Random();
            int Low = 2;
            int High = 100;
            Integer Result = r.nextInt(High-Low) + Low;
            list.add(Result);
            i++;
        }
        return list;
    }
}
