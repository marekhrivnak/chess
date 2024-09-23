package ija_proj.common;

import ija_proj.game.Simulator;
import ija_proj.gui.BoardCreator;
import ija_proj.game.Board;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Stack;


/**
 * Creates new game and set everything important.
 */
public class SetGame implements Game {

    /**
     * Name of the Game.
     */
    private String game;
    /**
     * Board of the game.
     */
    private Board my_board = null;
    /**
     * Stack for operation UNDO.
     */
    private Stack<Figure> stack_figure = new Stack<>();
    /**
     * Stack for operation UNDO.
     */
    private Stack<Field> stack_field = new Stack<>();
    /**
     * Stack for operation UNDO.
     */
    private Stack<String> kick = new Stack<>();
    /**
     * Stack for operation REDO.
     */
    private Stack<Figure> redoFigure = new Stack<>();
    /**
     * Stack for operation REDO.
     */
    private Stack<Field> redoField = new Stack<>();
    /**
     * ArrayList of all moves.
     */
    private ArrayList<String> moves;
    /**
     * Counter for number of lines of "Game Description"
     */
    private int count = 0;
    /**
     * Object BoardCreator used for this game.
     */
    private BoardCreator myGuiBoard;
    /**
     * Mode of the game.
     */
    private boolean simulatedMode = false;

    /**
     * Information if redo was pressed.
     */
    private boolean redoStep = false;
    /**
     * Information if came from simulation.
     */
    private boolean afterSimulation = false;

    //private boolean isBlackCheck = false;
    //private boolean isWhiteCheck = false;



    public void setBoardCreator(BoardCreator myGui){
        this.myGuiBoard = myGui;
    }

    public void clearStacks(){
        redoField.clear();
        redoFigure.clear();
    }

    public void setAfterSimulation(boolean bool){
        afterSimulation = bool;
    }

    public BoardCreator getBoardCreator(){
        return this.myGuiBoard;
    }

    public SetGame(Board board, String game) {
        this.my_board = board;
        this.game = game;
        this.moves = new ArrayList<>();
    }

    public void backLine(){
        count--;
    }

    public String getLine(){
        System.out.println("COUNT JE: "+count + "SIZE JE: "+moves.size());
        if (moves.size() == count || count < 0){
            System.out.println("ERROR: IndexOutOfBoudns! ");
            return "";
        }
        return moves.get(count++);
    }

    public String getString(){
        return game;
    }

    public Board getBoard(){
        return this.my_board;
    }

    public boolean move(Figure figure, Field field, boolean real){
        if (this.simulatedMode && real) return false;
        if (field == null) return false;
        if (figure == null) return false;


        // if you control MAT, dont care about turn
        if (field != my_board.nightKing.getField() && field != my_board.whiteKing.getField())
            if (figure.isWhite() != my_board.turn) {
                return false;
            }

        // make move in middle of simulation
        if(real && afterSimulation){
            afterSimulation=false;
            int counter = 1;
            // in case that you make move whene game was stopped simulated somwhere in middel
            // games delete rest of loaded description and create new game
            if ((myGuiBoard.labCounter+1)/2 != myGuiBoard.labels.size()) {
                //System.out.println("moj lab counter: "+myGuiBoard.labCounter + "moj size labels: "+myGuiBoard.labels.size() );

                while ((myGuiBoard.labCounter+1)/2 != myGuiBoard.labels.size()) {
                    myGuiBoard.simulateBut.fire();
                    counter++;
                }
                for (int i = 1; i < counter;i++) {
                    this.undo(true);
                }

            }

            }


        if (this.game.equals("chess")) {
            Field old_pos = figure.getField();
            Figure kick_figure = null;

            if (!field.isEmpty()) {
                kick_figure = field.get();

            }

            boolean success = figure.move(field);




            if (success) {
                my_board.turn = !my_board.turn;
                if (kick_figure != null) {
                    kick.push("yes");
                    this.stack_figure.push(kick_figure);
                    this.stack_field.push(field);
                    this.stack_figure.push(figure);
                    this.stack_field.push(old_pos);

                } else {
                    kick.push("no");
                    this.stack_figure.push(figure);
                    this.stack_field.push(old_pos);
                }

            }

            if (my_board.isWhiteMat && figure.isWhite() && real && success ) {
                if (figure.whiteMate()){
                    this.undo(false);
                    return false;
                }
                else my_board.isWhiteMat = false;
            }
            else if (my_board.isBlackMat && !figure.isWhite() && real && success) {
                if (figure.blackMate()){
                    this.undo(false);
                    return false;
                }
                else my_board.isBlackMat = false;
            }


            if (success && real){

                // if moved -> redo cannot be DONE
                if (!redoStep) {
                    redoFigure.clear();
                    redoField.clear();
                    redoStep = false;
                }
                else
                    redoStep = false;

                boolean checkMate;
                count++;
                myGuiBoard.labCounterInc(); // increment number of labels
                String mat = "";
                String checkForMate = "";
                if(figure.blackMate()){
                    if (figure.isWhite()){
                        my_board.isBlackMat = true;
                        mat = "+";
                    }
                    else {
                        this.undo(false);
                        return false;
                    }

                }

                if(figure.whiteMate()){
                    if (!figure.isWhite()){
                        my_board.isWhiteMat = true;
                        mat = "+";
                    }
                    else {
                        this.undo(false);
                        return false;
                    }

                }

                if (mat.equals("+")){
                     if(this.controlAllMoves()){
                        System.out.println("nieje sach mat");
                    }
                    else {
                        checkForMate = "#";
                        System.out.println("je sach mat");
                    }
                }





                String wFig = "";
                switch (figure.getType()){
                    case king:
                        wFig ="K";
                        break;
                    case bishop:
                        wFig="S";
                        break;
                    case queen:
                        wFig="D";
                        break;
                    case horse:
                        wFig="J";
                        break;
                    case tower:
                        wFig="V";
                        break;
                    default:
                        wFig = "";

                }
                String kicked = "";
                String pos_x;
                String pos_y;
                String old_x;
                String old_y;
                if (kick_figure != null){
                    kicked = "x";
                }
                else kicked = "";
                pos_x = Character.toString((char) (field.getCol()+96));
                pos_y = String.valueOf(field.getRow());
                old_x = Character.toString((char) (old_pos.getCol()+96));
                old_y = String.valueOf(old_pos.getRow());
                String my_out;
                if (myGuiBoard.getNotation()) {
                    my_out = wFig + old_x + old_y + kicked + pos_x + pos_y + mat + checkForMate;
                }
                else {
                    my_out = wFig + kicked + pos_x + pos_y + mat + checkForMate;
                }
                myGuiBoard.writeInfo(my_out,false);






            }
            

            return success;
        }
        else {

            return true;

        }
        

    }

    public boolean undo(boolean real){


       if (this.kick.empty() || this.stack_field.empty() || this.stack_figure.empty()){
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("ERROR");
           alert.setHeaderText("Need to make move before UNDO!");
           my_board.turn = true;
           alert.showAndWait();
           return false;

       }

        Figure fig1;
        Field fil1;

            String kicked = this.kick.pop();
            if (kicked.equals("yes")) {

                fig1 = this.stack_figure.pop();
                fil1 = this.stack_field.pop();
                this.redoFigure.push(fig1);

                fig1.remove(fig1.getField());
                fig1.put(fil1);

                fig1 = this.stack_figure.pop();
                fil1 = this.stack_field.pop();
                this.redoField.push(fil1);
                //fig1.remove(fig1.getField());
                fig1.put(fil1);

            } else {

                fig1 = this.stack_figure.pop();
                fil1 = this.stack_field.pop();
                this.redoFigure.push(fig1);
                this.redoField.push(fig1.getField());
                fig1.remove(fig1.getField());
                fig1.put(fil1);

            }

            if (real) {
                if (fig1.blackMate()) {
                    my_board.isBlackMat = true;
                }
                if (fig1.whiteMate()) {
                    my_board.isWhiteMat = true;
                }
            }

            if(real) {
                myGuiBoard.labCounterDec();
                count--;
                myGuiBoard.undoInfo();
                if (moves.size() > 0 && count%2 == 0)
                    moves.remove(moves.size()-1);
            }
        my_board.turn = !my_board.turn;
            return true;

    }

    public boolean redo(){
        if ( this.redoField.empty() || this.redoFigure.empty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Need to make UNDO before REDO!");
            my_board.turn = true;
            alert.showAndWait();
            return false;
        }

        Field fil1 = this.redoField.pop();
        Figure fig1 = this.redoFigure.pop();
        redoStep=true;
        this.move(fig1,fil1,true);

        return true;
    }

    public Simulator simulate(String filename, boolean notation) {


        Simulator simulator = new Simulator();
        simulator.setMyGame(this);

        String[] splitted;

        BorderPane pane = (BorderPane) myGuiBoard.getTab().getContent();
        VBox vbox = (VBox) pane.getRight();
        Label lab = (Label) vbox.getChildren().get(2);
        String str = lab.getText();

        moves.clear();
        int i = 2;
        while (str != null){
            System.out.println(str);
            i++;
            str = str.trim();
            splitted = str.split("\\s+");
            try {
                moves.add(splitted[1]);
                moves.add(splitted[2]);
            }
            catch (IndexOutOfBoundsException e){
                System.out.println("Need to make move with both figures");
                return null;
            }

            try {
                lab = (Label) vbox.getChildren().get(i);
            }
            catch (IndexOutOfBoundsException e){
                break;
            }
            str = lab.getText();

        }
        
        return simulator;


    }

    // function to set mode of the game: simulated or manual
    public void setMode(boolean mode){
        simulatedMode = mode;
    }

    // function to get mode of the game: simulated or manual
    public boolean getMode(){
        return simulatedMode;
    }
    
    private boolean controlAllMoves(){
        boolean moveMade;


        for (Figure figure: my_board.myFigures){
            if (my_board.turn != figure.isWhite())
                continue;
            if (figure.getField() == null)
                continue;

            for(int i = 1; i <= 8; i++){
                 for(int j = 1; j <= 8; j++){

                     Field myField = my_board.getField(j,i);


                    moveMade = this.move(figure,myField,false);

                    if(moveMade){
                        if(!figure.isWhite()){
                            if(!figure.blackMate()){
                                System.out.println("NIEJE SACH-MAT LEBO: "+figure.getState() + "FROM: "+myField.getCol()+" "+myField.getRow());
                                this.undo(false);
                                return true;
                            }
                            else {
                                this.undo(false);
                            }
                        }
                        else {
                            if(!figure.whiteMate()){
                                System.out.println("NIEJE SACH-MAT LEBO: "+figure.getState() + "FROM: "+myField.getCol()+" "+myField.getRow());
                                this.undo(false);
                                return true;
                            }
                            else {
                                this.undo(false);
                            }

                        }

                    }

                }

            }
        }

        return false;
        
    }






}


