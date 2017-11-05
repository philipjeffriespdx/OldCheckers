/*
Philip Jeffries
Networking and Security 
Checkers Game
*/

//I just copied my first networking lab to be able to build on top of it:
import java.io.*;
import java.net.*;

//There will be a checkers Server and Client
//Server Starts the Game
class CheckersServer {
   public static int [][] pieces = new int [8][8];
   public static void main(String argv[]) throws Exception {
      //Wait for Checkers Client 
      String clientSentence = "";
      String operation = "";
      String messageToClient = "";
      String numbers = "";
      int clientAnswer = 0;
      int serverAnswer = 0;
      String temp = "";
        
      //set up socket
      ServerSocket welcomeSocket = new ServerSocket(6789);
   
      //accept traffic from socket
      Socket connectionSocket = welcomeSocket.accept();
      
      //read in from user
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      
      //reading in from client
      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
      
      //reading out to client
      DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
      
      //AFTER CONNECTION::
      
      //Create Matrix of Peices
      /*
      Empty = 0
      Red Pawn = 1
      Black Pawn = 2
      Red King = 3
      Black King = 4
      */
      // pieces[rows][columns]
      SetBoard();
      PrintBoard();
      
      //Create GUI of board
      
      
      //FIRST SERVER MOVE
      //Ask user for move (Have user type moves Ex: A1 to B2
      //Tell user if move is valid
      //Change Matrix value
      //Send valid move to other player
      //Repaint

      
      
      //continue to receive and send data
      while (true) {
         //LOOP OF MOVES ::
         //Wait for Client move
         //Update Matrix
         //Repaint
         //Ask user for move (Have user type moves Ex: A1 to B2
         //Tell user if move is valid
         //Change Matrix value
         //Send valid move to other player
         //Repaint

         operation = inFromClient.readLine();
      
         System.out.println("Operation: " + operation);
         
         
         if(operation.equals("q") || operation.equals("Q") || operation.equals("quit"))
         {
             messageToClient = "quit";
             //System.out.println("Message to Client is:" + messageToClient);
             outToClient.writeBytes(messageToClient + "\n");
             //System.out.println("Message SENT to Client");
             break;
         }
         else if(!(operation.equals("+") || operation.equals("-") || operation.equals("/") || operation.equals("*")))
         {
             messageToClient = "Please enter a valid operation or enter q to quit.";
             //System.out.println("Message to Client is:" + messageToClient);
             outToClient.writeBytes(messageToClient + "\n");
             //System.out.println("Message SENT to Client");    
         }
         else
         {
            //enter 4 numbers
            System.out.println("Enter 4 numbers to perform operation on (first 2 on client and second 2 on server: ");
            numbers = inFromUser.readLine();
            String[] SNumbers = numbers.split("\\s+");
            int[] Numbers = new int[4];
            for(int i = 0; i < 4; i++)
            {
               Numbers[i] = Integer.parseInt(SNumbers[i]);
            }
   
            
            messageToClient = (Numbers[0] + " " + Numbers[1]);
            //System.out.println("Message to Client is:" + messageToClient);
            outToClient.writeBytes(messageToClient + "\n");
            //System.out.println("Message SENT to Client");
                    
                        
            //perform operations on #s 3 and 4
            if(operation.equals("+"))
            {
               serverAnswer = Numbers[2] + Numbers [3];
            }
            else if(operation.equals("-"))
            {
               serverAnswer = Numbers[2] - Numbers [3];
            }
            else if(operation.equals("/"))
            {
               serverAnswer = Numbers[2] / Numbers [3];
            }
            else if(operation.equals("*"))
            {
               serverAnswer = Numbers[2] * Numbers [3];
            }
            
            temp = inFromClient.readLine();
      
            clientAnswer = Integer.parseInt(temp);
            
            //System.out.println("RECEIVED Client Answer: " + clientAnswer);
            
            //System.out.println("Answer to Client is: " + serverAnswer);
            //send answer to client
            outToClient.writeBytes("" + serverAnswer + "\n");
            //System.out.println("Answer SENT to Client");
   
            //print both the client and server operations            
            System.out.println("Client ANSWER is: " + clientAnswer);
            System.out.println("Server ANSWER is: " + serverAnswer  + "\n");
            
            System.out.println("Do you want to perform another operation? (y/n)");
            temp = inFromUser.readLine();
            
            if(temp.equals("y") || temp.equals("Y") || temp.equals("yes"))
            {
               outToClient.writeBytes("y\n");
               System.out.println("\n");
   
            }
            else
            {
               outToClient.writeBytes("no\n");
               break;
            }
         }
      }   
   }
   
   public static void SetBoard() //initially sets up pieces on gameboard for a new game
   {
      int temp = 0;
      /*
      Empty = 0
      Red Pawn = 1
      Black Pawn = 2
      Red King = 3
      Black King = 4
      */
      // pieces[columns][rows]
      
      //set all zeros
      for(int i = 0; i<8; i++)
      {
         for(int j = 0; j<8; j++)
         {
            pieces[i][j]=0;         
         }
      }
      
      
      //red left side of board
      for(int i = 0; i<3; i++) //columns
      {    
         for(int j = i%2; j<8; j+=2) //rows
         {
            pieces[i][j] = 1;
         }
         
      }
      //black right side of board
      for(int i = 5; i<8; i++) //columns
      {
         for(int j = i%2; j<8; j+=2) //rows
         {
            pieces[i][j] = 2;
         }
      }
   }
   
   public static void PrintBoard()
   {
      System.out.println();
      System.out.println("    A B C D E F G H");
      for(int i = 0; i<8; i++)
      {
         System.out.print(i + ":  ");
         for(int j = 0; j<8; j++)
         {
           System.out.print(pieces[j][i] + " ");         
         }
         System.out.println();
      }
   }
   
}