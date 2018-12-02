package co.simil.androidtictactoe;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeGame {

    public enum DifficultyLevel {Easy, Harder, Expert};

    // Current difficulty level
    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Expert;

    public DifficultyLevel getDifficultyLevel() {
        return mDifficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        mDifficultyLevel = difficultyLevel;
        if (difficultyLevel==DifficultyLevel.Easy)
            System.out.println("***************** EASY ****************");
        else if (difficultyLevel==DifficultyLevel.Harder)
            System.out.println("***************** HARDER ****************");
        else
            System.out.println("***************** EXPERT ****************");

    }

    private char mBoard[] = {'1','2','3','4','5','6','7','8','9'};
    public static final int BOARD_SIZE = 9;


    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';

    private Random mRand;

    public TicTacToeGame() {

        // Seed the random number generator
        mRand = new Random();
    }


    public void displayBoard()	{
        System.out.println();
        System.out.println("\n\nBoard\n" + mBoard[0] + " | " + mBoard[1] + " | " + mBoard[2] + "\n-----------\n" + mBoard[3] + " | " + mBoard[4] + " | " + mBoard[5]+"\n-----------\n"+mBoard[6] + " | " + mBoard[7] + " | " + mBoard[8]+"\nEnd Board\n");
        System.out.println();
    }

    // Check for a winner.  Return
    //  0 if no winner or tie yet
    //  1 if it's a tie
    //  2 if X won
    //  3 if O won
    public int checkForWinner() {

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3)	{
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+1] == HUMAN_PLAYER &&
                    mBoard[i+2]== HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+1]== COMPUTER_PLAYER &&
                    mBoard[i+2] == COMPUTER_PLAYER)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+3] == HUMAN_PLAYER &&
                    mBoard[i+6]== HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+3] == COMPUTER_PLAYER &&
                    mBoard[i+6]== COMPUTER_PLAYER)
                return 3;
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                (mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER) ||
                (mBoard[2] == COMPUTER_PLAYER &&
                        mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[6] == COMPUTER_PLAYER))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;
    }

    public int getComputerMoveBasic()
    {
        int move;

        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    System.out.println("(WIN) Computer is moving to " + (i + 1));
                    return i;
                }
                else
                    mBoard[i] = OPEN_SPOT;
            }
        }

        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    mBoard[i] = COMPUTER_PLAYER;
                    System.out.println("(BLOCK) Computer is moving to " + (i + 1));
                    return i;
                }
                else
                    mBoard[i] = OPEN_SPOT;
            }
        }

        // Generate random move
        do
        {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] != OPEN_SPOT);

        System.out.println("(RANDOM) Computer is moving to " + (move + 1));

        mBoard[move] = COMPUTER_PLAYER;

        return move;
    }

    public int getRandomMove(){

        int move;

        do
        {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] != OPEN_SPOT);

        System.out.println("(RANDOM) Computer is moving to " + (move + 1));

        mBoard[move] = COMPUTER_PLAYER;

        return move;
    }

    public int getWinningMove(){

        int move;

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    System.out.println("(WIN) Computer is moving to " + (i + 1));
                    return i;
                }
                else
                    mBoard[i] = OPEN_SPOT;
            }
        }

        return -1;
    }

    public int getBlockingMove(){

        int move;

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] == OPEN_SPOT) {
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    mBoard[i] = COMPUTER_PLAYER;
                    System.out.println("(BLOCK) Computer is moving to " + (i + 1));
                    return i;
                }
                else
                    mBoard[i] = OPEN_SPOT;
            }
        }
        return -1;
    }

    public int getComputerMove()
    {
        int move = -1;

        if (mDifficultyLevel == DifficultyLevel.Easy)
            {
                move = getRandomMove();
                System.out.println("################ EASY ###############");
            }
        else if (mDifficultyLevel == DifficultyLevel.Harder) {
            System.out.println("################ HARDER ###############");
            move = getWinningMove();
            if (move == -1)
                move = getRandomMove();
        }
        else if (mDifficultyLevel == DifficultyLevel.Expert) {
            System.out.println("################ EXPERT ###############");
            // Try to win, but if that's not possible, block.
            // If that's not possible, move anywhere.
            move = getWinningMove();
            if (move == -1)
                move = getBlockingMove();
            if (move == -1)
                move = getRandomMove();
        }

        return move;
    }


    public void clearBoard(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = OPEN_SPOT;
        }

    }

    /** Set the given player at the given location on the game board.
     *  The location must be available, or the board will not be changed.
     *
     * @param player - The HUMAN_PLAYER or COMPUTER_PLAYER
     * @param location - The location (0-8) to place the move
     */

    /*public void setMove(char player, int location){
        if (player == HUMAN_PLAYER){
            mBoard[location] = HUMAN_PLAYER;
        }else{
            mBoard[location] = COMPUTER_PLAYER;
        }


    }*/
    public boolean setMove(char player, int location) {

        // If input data is valid...
        if( (location >= 0 && location < BOARD_SIZE) &&
                (player == HUMAN_PLAYER || player == COMPUTER_PLAYER) ) {

            mBoard[location] = player;
            return true;

        } else {
            return false;
        }
    }


    public char getBoardOccupant(int i) {
        return mBoard[i];
    }


}


