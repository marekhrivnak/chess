package ija_proj.game;

import ija_proj.common.*;
import ija_proj.gui.BoardCreator;
import javafx.scene.control.Button;

import java.util.List;

/**
 * Class represents game board. Board has size of N x N field.
 */
public class Board {
    /** Button in action. */
    public Button glob_button;
    /** Disk in aciton. */
    public Disk glob_disk;
    /** Who's turn is. White if true */
    public boolean turn = true;
    /** White king in the game */
    public King whiteKing;
    /** Black king in the game */
    public King nightKing;
    /** If black players have "check" */
    public boolean isBlackMat;
    /** If white players have "check" */
    public boolean isWhiteMat;
    /** List of figures used in the game */
    public List<Figure> myFigures;
    /** List of King used in the game */
    public List<King> myKings;
    /** List of Queen used in the game */
    public List<Queen> myQueens;
    /** List of Pawn used in the game */
    public List<Pawn> myPawns;
    /** List of Tower used in the game */
    public List<Tower> myTowers;
    /** List of Horse used in the game */
    public List<Horse> myHorses;
    /** List of Bishop used in the game */
    public List<Bishop> myBishops;
    /** Actual game */
    public Game myGame;
    /** Size of the Board. */
    private int size;
    /** 2D array of fields */
    private BoardField[][] my_boardfield;
    /** Current field. */
    private BoardField my_board;
    /** Object BoardCreator used in game. */
    public BoardCreator myBoardCreator;

    /**
     *
     * @param size Size of the board.
     * @param myBoardCreator BoardCreator used in game.
     */
    public Board(int size ,BoardCreator myBoardCreator){
        this.myBoardCreator = myBoardCreator;
        my_boardfield =  new BoardField[size+1][size+1];
        this.size = size;
        for (int i=1;i <= size;i++) {
            for (int j=1;j <= size;j++) {
                my_board = new BoardField(j,i,this);
                my_boardfield[j][i] = my_board;
            }
        }
        for (int i=1;i <= size;i++) {
            for (int j=1;j <= size;j++) {

                my_boardfield[j][i].set_directions(size, my_boardfield);

            }
        }
    }

    /**
     * Setter for Game.
     * @param game Game to be set.
     */
    public void setGame(Game game){
        this.myGame = game;
    }

    /**
     * Getter for Field on col and row.
     * @param col Column where is Field placed.
     * @param row Row where is Field placed.
     * @return Field on col and row.
     */
    public Field getField(int col, int row){


        assert col>=1 : "Errow! Column cannot be less than 1.";
        assert col<=this.size : "Error! Column cannot be more than size of Board.";
        assert row>=1 : "Errow! Row cannot be less than 1.";
        assert row<=this.size : "Error! Row cannot be more than size of Board." ;
        return  my_boardfield[col][row];


    }

    /**
     * Getter for size of the Board.
     * @return Size of the Board.
     */
    public int getSize(){

        return this.size;

    }
}
