/*
Philip Jeffries
Networking and Security 
Checkers Game
*/

//I just copied my first networking lab to be able to build on top of it:
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//There will be a checkers Server and Client
//Server Starts the Game
//SERVER IS THE BLACK PLAYER BECAUSE RED GOES FIRST
class CheckersServer {
   public static int [][] pieces = new int [8][8];
   public static void main(String argv[]) throws Exception {
      //Wait for Checkers Client 
      String clientMove = "", serverMove = "";
      char temp;
      int fromCol, fromRow, toCol, toRow;
      boolean valid = false;
        
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
      while (true) { 
         //LOOP OF MOVES ::
         //Wait for Client move
         System.out.println("Waiting for Client... ");
         clientMove = inFromClient.readLine();
         System.out.println("Client Move: " + clientMove);
         temp = clientMove.charAt(0);
         fromCol = temp - 65;
         temp = clientMove.charAt(1);
         fromRow = temp - 48;
         temp = clientMove.charAt(3);
         toCol = temp - 65;
         temp = clientMove.charAt(4);
         toRow = temp - 48;
         
         //Update Matrix
         System.out.println("Client From  : " + fromCol + " " + fromRow);
         System.out.println("Client To    : " + toCol + " " + toRow);
         
         //Repaint
         PrintBoard();
         
         valid = false;
         while(!valid)
         {
            //Ask user for move (Have user type moves Ex: A1 to B2)
            System.out.println("Where do you want to move? From(Col)(Row) To(Col)(Row)");
            serverMove = inFromUser.readLine();         
            temp = serverMove.charAt(0);
            fromCol = temp - 65;
            temp = serverMove.charAt(1);
            fromRow = temp - 48;
            temp = serverMove.charAt(3);
            toCol = temp - 65;
            temp = serverMove.charAt(4);
            toRow = temp - 48;
   
            //Tell user if move is valid
            if(pieces[fromCol][fromRow]!=1 || pieces[fromCol][fromRow]!=3) //valid piece square
            {
              System.out.println("There is no piece at the location you selected.");
              continue; 
            } 
            else if(pieces[fromCol][fromRow]==1 && ((fromCol-1)!=toCol || ((fromRow+1)!=toRow || (fromRow-1)!=toRow))  ) //pawn moves
            {
              System.out.println("Invalid Pawn Move");
              continue;
            } 
            else if(pieces[fromCol][fromRow]==3 && (((fromCol-1)!=toCol || (fromCol+1)!=toCol) || ((fromRow+1)!=toRow || (fromRow-1)!=toRow))  ) //king moves
            {
              System.out.println("Invalid King Move");
              continue;
            }
            else if(toCol<0 || toRow<0 || toCol>8 || toRow>8) //outside the playing field
            {
              System.out.println("Out of Bounds Move");
              continue;
            }
                        
            //Check to see if there is a another piece in the next square
            //there is a piece but nothing past
            
            /*Commented out to compile*/
            //if((pieces[toCol][toRow]==2 || pieces[toCol][toRow]==4) && )
            
            
            //there is a piece and something on the other side
            
            //there are multiple hops
            
            
            
            //Change Matrix value
            

            valid = true;
         }
         
         
         System.out.println("Server From  : " + fromCol + " " + fromRow);
         System.out.println("Server To    : " + toCol + " " + toRow);

         
         //Send valid move to other player
         outToClient.writeBytes(serverMove + "\n");
         
         //Repaint
         PrintBoard();

         
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
   /*So I was thinking we can create a base JFrame that has no buttons and it is just squares that alternate Red and Black.
   Then we can set buttons ontop of this GUI that are color-less and has those act as the pieces. ie, click A1 then click on B2 to move a piece.*/
   public class CheckersBoard extends JFrame
   {
      private Container contents;
      private JButton [][] squares;
      int sides = 8;
      
      public void PaintBoard()
      {
         //super( "CHECKERS!" );
         contents = getContentPane( );

         // set layout to an 8-by-8 Grid
         contents.setLayout( new GridLayout( sides, sides ) );

         squares = new JButton[sides][sides];

         ButtonHandler bh = new ButtonHandler( );
         setSize( 800, 800 );
         setVisible( true );
      }
      
      private class ButtonHandler implements ActionListener
      {
         public void actionPerformed( ActionEvent ae )
         {
            for ( int i = 0; i < sides; i++ )
             {
               for ( int j = 0; j < sides; j++ )
               {
                 if (sides==8)//place holder
                 {
                   
                   return;
                 }
               }
             }
         }
      }
   }
   
}