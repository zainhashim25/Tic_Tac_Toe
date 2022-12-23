import java.util.Scanner;

public class TTTConsoleNonOO {
   
   public static final int CROSS   = 0;
   public static final int NOUGHT  = 1;
   public static final int NO_SEED = 2;

   // The game board
   public static final int ROWS = 3, COLS = 3;  // number of rows/columns
   public static int[][] board = new int[ROWS][COLS]; // EMPTY, CROSS, NOUGHT

   // The current player
   public static int currentPlayer;  // CROSS, NOUGHT

   public static final int PLAYING    = 0;
   public static final int DRAW       = 1;
   public static final int CROSS_WON  = 2;
   public static final int NOUGHT_WON = 3;
   // The current state of the game
   public static int currentState;

   public static Scanner in = new Scanner(System.in); // the input Scanner

   public static void main(String[] args) {
      // Initialize the board, currentState and currentPlayer
      initGame();

      // Play the game once
      do {
         stepGame();
         // Refresh the display
         paintBoard();
         // Print message if game over
         if (currentState == CROSS_WON) {
            System.out.println("'X' won!\nBye!");
         } else if (currentState == NOUGHT_WON) {
            System.out.println("'O' won!\nBye!");
         } else if (currentState == DRAW) {
            System.out.println("It's a Draw!\nBye!");
         }
         // Switch currentPlayer
         currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
      } while (currentState == PLAYING); // repeat if not game over
   }

   public static void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = NO_SEED;  // all cells empty
         }
      }
      currentPlayer = CROSS;   // cross plays first
      currentState  = PLAYING; // ready to play
   }

   public static void stepGame() {
      boolean validInput = false;  // for input validation
      do {
         if (currentPlayer == CROSS) {
            System.out.print("Player 'X', enter your move (row[1-3] column[1-3]): ");
         } else {
            System.out.print("Player 'O', enter your move (row[1-3] column[1-3]): ");
         }
         int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
         int col = in.nextInt() - 1;
         if (row >= 0 && row < ROWS && col >= 0 && col < COLS
                      && board[row][col] == NO_SEED) {
            currentState = stepGameUpdate(currentPlayer, row, col);
            validInput = true;  // input okay, exit loop
         } else {
            System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                  + ") is not valid. Try again...");
         }
      } while (!validInput);  // repeat if input is invalid
   }

   public static int stepGameUpdate(int player, int selectedRow, int selectedCol) {
      // Update game board
      board[selectedRow][selectedCol] = player;
      if (board[selectedRow][0] == player       // 3-in-the-row
                && board[selectedRow][1] == player
                && board[selectedRow][2] == player
             || board[0][selectedCol] == player // 3-in-the-column
                && board[1][selectedCol] == player
                && board[2][selectedCol] == player
             || selectedRow == selectedCol      // 3-in-the-diagonal
                && board[0][0] == player
                && board[1][1] == player
                && board[2][2] == player
             || selectedRow + selectedCol == 2  // 3-in-the-opposite-diagonal
                && board[0][2] == player
                && board[1][1] == player
                && board[2][0] == player) {
         return (player == CROSS) ? CROSS_WON : NOUGHT_WON;
      } else { 
         for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
               if (board[row][col] == NO_SEED) {
                  return PLAYING; // still have empty cells
               }
            }
         }
         return DRAW; // no empty cell, it's a draw
      }
   }
   public static void paintBoard() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            paintCell(board[row][col]); // print each of the cells
            if (col != COLS - 1) {
               System.out.print("|");   // print vertical partition
            }
         }
         System.out.println();
         if (row != ROWS - 1) {
            System.out.println("-----------"); // print horizontal partition
         }
      }
      System.out.println();
   }

   public static void paintCell(int content) {
      switch (content) {
         case CROSS:   System.out.print(" X "); break;
         case NOUGHT:  System.out.print(" O "); break;
         case NO_SEED: System.out.print("   "); break;
      }
   }
}
