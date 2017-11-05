/*
Philip Jeffries
Networking and Security 
Checkers Game
*/
import java.io.*;
import java.net.*;

//There will be a checkers Server and Client
//Server Starts the Game and Client connects
class CheckersClient {
   public static void main(String argv[]) throws Exception {
   //Connect to Checkers Server 
      String sentence = "";
      String modifiedSentence = "";
      String operation = "";
      int clientAnswer = 0;
      int serverAnswer = 0;
      String temp = "";
      String Continue = "y";
      
      //read user input
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      
      //setup socket
      Socket clientSocket = new Socket("localhost", 6789);
      
       //setup data output stream
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      
      //setup server reader
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      
      //Create Matrix of Pieces
   
      //Create GUI of board
   
      
      while (Continue.equals("y"))
      {
         //LOOP OF MOVES ::
         //Wait for Server move
         //Update Matrix
         //Repaint
         //Ask user for move (Have user type moves Ex: A1 to B2
         //Tell user if move is valid
         //Change Matrix value
         //Send valid move to other player
         //Repaint

         System.out.println("Select which operation to perform: +,-,/,*,q to exit");
         operation = inFromUser.readLine();
         
         //send over operation
         outToServer.writeBytes(operation + '\n');
         
         //receive and print server message
         modifiedSentence = inFromServer.readLine();
         //System.out.println("FROM SERVER: " + modifiedSentence + "");
         
         if(modifiedSentence.equals("quit"))
         {
            break;
         }
         
         if(operation.equals("+") || operation.equals("-") || operation.equals("/") || operation.equals("*"))
         {        
            String[] SNumbers = modifiedSentence.split("\\s+");
            int[] Numbers = new int[2];
            for(int i = 0; i < 2; i++)
            {
               Numbers[i] = Integer.parseInt(SNumbers[i]);
            }
            
            //perform operations on #s 1 and 2
            if(operation.equals("+"))
            {
               clientAnswer = Numbers[0] + Numbers[1];
            }
            else if(operation.equals("-"))
            {
               clientAnswer = Numbers[0] - Numbers[1];
            }
            else if(operation.equals("/"))
            {
               clientAnswer = Numbers[0] / Numbers[1];
            }
            else if(operation.equals("*"))
            {
               clientAnswer = Numbers[0] * Numbers[1];
            }
            
            //send value back to server
            //System.out.println("Sending ANSWER back to server: " + clientAnswer);
            outToServer.writeBytes("" + clientAnswer + '\n');
            //System.out.println("ANSWER sent back to server.");
            
            //receive server value
            temp = inFromServer.readLine();
            serverAnswer = Integer.parseInt(temp);
            //System.out.println("FROM SERVER: " + serverAnswer + "");
            
            //print both the client and server operations            
            System.out.println("Client ANSWER is: " + clientAnswer);
            System.out.println("Server ANSWER is: " + serverAnswer + "\n");
            
            Continue = inFromServer.readLine();
         }
      } 
      
      //close socket
      clientSocket.close();

   
   
      
   }
}