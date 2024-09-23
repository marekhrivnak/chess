package ija_proj.common;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import ija_proj.GameFactory;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class Disk represents one stone of 2 colors white or black.
 */
public class Disk implements Figure  {

    protected Field location = null;
    private int color;
    protected Figure.type type;
    protected Button button;
    protected Game game;

    /**
     *  method logic set the action-handler for Disk
     */
    public void logic(){
        this.button.setOnAction(e -> {

            System.out.println(button);

            if (game.getBoard().turn == this.isWhite()){
                game.getBoard().glob_button = this.button;
                game.getBoard().glob_disk = this;
            }
            else {
                if (game.getBoard().glob_button == null) {
                    game.getBoard().glob_button = this.button;
                    game.getBoard().glob_disk = this;
                } else {
                    if (game.move(game.getBoard().glob_disk, this.location,true)) {
                        game.getBoard().glob_button = null;
                        game.getBoard().glob_disk = null;

                    } else
                        game.getBoard().glob_button = null;
                    game.getBoard().glob_disk = null;
                }
            }


        });


    }

    /**
     * setter for Button
     *
     * @param b button to set
     */
    public void setButton(Button b){
        this.button = b;
        this.button.setPrefSize(10,10);

    }
    /**
     * getter for Button
     *
     * @return button
     */
    public Button getButton(){
        return this.button;
    }

    /**
     * Constructor.
     *
     * @param  isWhite  if figure is white set true else black
     * @param  type  set the type of the figure
     */
    public Disk(boolean isWhite,Figure.type type){

        this.game=GameFactory.getGame();

        if (isWhite){
            color = 0;
        }
        else this.color = 1;

        this.type = type;

        this.button = new Button();
        this.button.setPrefSize(10,10);
        game.getBoard().myFigures.add(this);


    }

    /**
     *
     * @return exact position of Disk
     */
    public String getState(){
        int row = this.location.getRow();
        int col= this.location.getCol();
        char farba;
        char typ;
        if (this.isWhite()){
            farba = 'W';
        }
        else{
            farba = 'B';
        }

        if(this.type == Figure.type.tower){
            typ = 'V';
        }
        else if(this.type == Figure.type.pawn) {
            typ = 'P';
        }
        else if (this.type == Figure.type.king) {
            typ = 'K';
        }
        else if (this.type == Figure.type.queen) {
            typ = 'D';
        }
        else if (this.type == Figure.type.horse) {
            typ = 'J';
        }
        else typ = 'S';



        return typ+"["+farba+"]"+col+":"+row;


    }

    /**
     * Function move Figure to the Field if it is possible
     *
     * @param moveTo where figure will be moved
     * @return true if operation ends successfully
     */
    public boolean move(Field moveTo){
        return true;
    }


    /**
     * setter for Field
     * @param new_location Field to set
     */
    public void setField(Field new_location){
        this.location = new_location;

    }

    /**
     * getter for Field
     * @return Field
     */
    public Field getField(){
        return this.location;
    }

    /**
     *
     * @return true is Figure is white and false it is not
     */
    public boolean isWhite(){

        if ( color == 0 ) {
            return true;
        }
        else return false;

    }

    /**
     *
     * @param field set field to put the Figure
     * @return true if successful
     */
    public boolean put(Field field){
        return field.put(this);
    }

    /**
     *
     * @param field remove Figure from field
     * @return true if successful
     */
    public boolean remove(Field field){
        //game.getBoard().myFigures.remove(this);
        location = null;
        return field.remove(this);
    }

    /**
     * Check if black figures have "Check"
     *
     * @return true if succes
     */
    public boolean blackMate(){
        Figure myFigure;
        for (int i=0;i < game.getBoard().myFigures.size();i++){
            myFigure = game.getBoard().myFigures.get(i);
            if (myFigure.getField() == null) {
                continue;
            }
            if (!myFigure.isWhite()){
                continue;
            }

            if (game.move(myFigure, game.getBoard().nightKing.getField(),false)) {
                game.undo(false);
                game.getBoard().isBlackMat = true;
                return true;
            }
        }

        return false;

    }

    /**
     * Check if white figures have "Check"
     *
     * @return true if succes
     */
    public boolean whiteMate(){
        Figure myFigure;
        for (int i=0;i < game.getBoard().myFigures.size();i++){
            myFigure = game.getBoard().myFigures.get(i);
            if (myFigure.getField() == null)
                continue;
            if (myFigure.isWhite()){
                continue;
            }
            if (game.move(myFigure, game.getBoard().whiteKing.getField(),false)) {
                game.undo(false);
                game.getBoard().isWhiteMat = true;
                return true;
            }
        }
        return false;

    }


    /**
     * Check if there is "Check" in the game
     *
     * @return true if succes
     */
    public boolean mate(){
        Figure myKing;
        Figure myFigure;


        for (int i=0;i < game.getBoard().myFigures.size();i++) {
            myFigure = game.getBoard().myFigures.get(i);
            if (myFigure.getField() == null)
                continue;
            if (myFigure.isWhite()) {
                if (game.move(myFigure, game.getBoard().nightKing.getField(),false)) {
                    game.undo(false);
                    game.getBoard().isWhiteMat = true;
                    return true;
                }
            }
            else {
                if (game.move(myFigure, game.getBoard().whiteKing.getField(),false)) {
                    game.undo(false);
                    game.getBoard().isBlackMat = true;
                    return true;
                }
            }
        }
       return false;

    }

    /**
     * check "check-mate" and finish game if necessary
     */
    public void checkFinish(){


    }

    /**
     *
     * @return type of the Figure
     */
    public Figure.type getType(){
        return this.type;
    }

}

