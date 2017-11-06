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
//SERVER IS THE BLACK PLAYER BECAUSE RED GOES FIRST
class CheckersServer {
   public static int [][] pieces = new int [8][8];
   public static void main(String argv[]) throws Exception {
      //Wait for Checkers Client 
      String clientMove = "", serverMove = "";
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
      
      
      //continue to receive and send data
      while (true) { //remove old code
         //LOOP OF MOVES ::
         //Wait for Client move
         System.out.println("Waiting for Client... ");
         clientMove = inFromClient.readLine();
         System.out.println("Client Move: " + clientMove);
         
         //Update Matrix
         //Repaint
         
         //Ask user for move (Have user type moves Ex: A1 to B2
         System.out.println("Where do you want to move? ");
         serverMove = inFromUser.readLine();         

         //Tell user if move is valid
         
         //Change Matrix value
         
         
         //Send valid move to other player
         outToClient.writeBytes(serverMove + "\n");
         
         //Repaint

         
         if(serverMove.equals("q") || clientMove.equals("q"))
            break;
         
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