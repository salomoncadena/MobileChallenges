package co.simil.androidtictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Represents the internal state of the game
    private TicTacToeGame mGame;
    private boolean mGaming, mmHumanTurn;

    // Buttons making up the board
    private Button mBoardButtons[];

    // Various text displayed
    private TextView mInfoTextView, mInformation2, mTextViewHumanWon,mTextViewTie,mTextViewAndroidWon;

    //game results
    private int mAndroidWon, mHumanWon, mTie;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add("New Game");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startNewGame();
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.button1);
        mBoardButtons[1] = (Button) findViewById(R.id.button2);
        mBoardButtons[2] = (Button) findViewById(R.id.button3);
        mBoardButtons[3] = (Button) findViewById(R.id.button4);
        mBoardButtons[4] = (Button) findViewById(R.id.button5);
        mBoardButtons[5] = (Button) findViewById(R.id.button6);
        mBoardButtons[6] = (Button) findViewById(R.id.button7);
        mBoardButtons[7] = (Button) findViewById(R.id.button8);
        mBoardButtons[8] = (Button) findViewById(R.id.button9);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mInformation2 = (TextView) findViewById(R.id.information2);

        mTextViewAndroidWon = (TextView) findViewById(R.id.textViewAndroidWon);
        mTextViewHumanWon = (TextView) findViewById(R.id.textViewHumanWon);
        mTextViewTie= (TextView) findViewById(R.id.textViewTiesWon);

        mmHumanTurn = false;

        mAndroidWon=0;
        mHumanWon=0;
        mTie=0;



        mGame = new TicTacToeGame();
        startNewGame();
    }

    // Set up the game board.
    private void startNewGame() {

        mGame.clearBoard();
        mGaming = true;

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }

        mmHumanTurn = !mmHumanTurn;

        if(mmHumanTurn){

            // Human goes first
            //mInfoTextView.setText("You go first.");
            mInfoTextView.setText(R.string.first_human);
        }else{
            //mInfoTextView.setText("It's Android's turn.");
            mInfoTextView.setText(R.string.turn_computer);
            //mInfoTextView.setTextColor(Color.parseColor("#17202A"));
            int move = mGame.getComputerMove();
            mGame.displayBoard();
            //mInformation2.setText(String.valueOf(move));
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
            mInfoTextView.setText(R.string.turn_human);
        }
        mInfoTextView.setTextColor(Color.parseColor("#17202A"));

    }    // End of startNewGame

    // Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled() && mGaming) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                // If no winner yet, let the computer make a move
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    //mInfoTextView.setText("It's Android's turn.");
                    mInfoTextView.setText(R.string.turn_computer);
                    //mInfoTextView.setTextColor(Color.parseColor("#17202A"));
                    int move = mGame.getComputerMove();
                    mGame.displayBoard();
                    //mInformation2.setText(String.valueOf(move));
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }

                if (winner == 0){
                    //mInfoTextView.setText("It's your turn.");
                    mInfoTextView.setText(R.string.turn_human);
                    //mInfoTextView.setTextColor(Color.parseColor("#17202A"));
                    }
                else if (winner == 1){
                    //mInfoTextView.setText("It's a tie!");
                    mInfoTextView.setText(R.string.result_tie);
                    mInfoTextView.setTextColor(Color.parseColor("#D4AC0D"));
                    mTie++;
                    mGaming = false;}
                else if (winner == 2){
                    //mInfoTextView.setText("You won!");
                    mInfoTextView.setText(R.string.result_human_wins);
                    mInfoTextView.setTextColor(Color.parseColor("#196F3D"));
                    mHumanWon++;
                    mGaming = false;}
                else{
                    //mInfoTextView.setText("Android won!");
                    mInfoTextView.setText(R.string.result_computer_wins);
                    mInfoTextView.setTextColor(Color.parseColor("#CB4335"));
                    mAndroidWon++;
                    mGaming = false;}
                mTextViewAndroidWon.setText(String.valueOf(mAndroidWon));
                mTextViewHumanWon.setText(String.valueOf(mHumanWon));
                mTextViewTie.setText(String.valueOf(mTie));

            }
        }
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }

}