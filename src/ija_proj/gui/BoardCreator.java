package ija_proj.gui;

import ija_proj.GameFactory;
import ija_proj.common.Game;
import ija_proj.game.Board;
import ija_proj.game.Simulator;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Class creates graphical user interface for one tab and set all important objects.
 *
 */
public class BoardCreator {
    /** ArrayList of labels in "Game Description". */
    public ArrayList<Label> labels;
    /** BoarderPane used for current tab.*/
    private BorderPane mainPane;
    /** 2D array of panes used in Tab.  */
    public StackPane[][] my_arr;
    /** Counter for labels used in "Game Description". */
    public int labCounter = -1;
    /**  Game used on current tab. */
    public Game game;
    /** Tab for current game. */
    private Tab myTab;
    /** Boarod used for current Tab.  */
    public Board myBoard;
    /**  Infromtaion about notation. */
    private boolean longNot = true;
    /** Name of the loaded file. */
    private String filename;
    /** Vertical box for buttons of the current Tab. */
    private VBox myVbox;
    /**  Infromation of part of the Labe.l */
    private int counter = 0;
    /**  Text used in Label for "Game Description". */
    private String myText = "";
    /**  Helpful counter.*/
    private int my_count = 1;
    /**  String for all labels used in "Game Description"  */
    private String myComplexString;
    /**  Simulator of current game in tab. */
    private Simulator simulator;
    /**  Restart button. */
    private Button restartButton;
    /**  Forward button.  */
    public Button simulateBut;
    /** Simulation button.   */
    public Button simulation;
    /**  Thread used for auto simulation.  */
    private Thread thread = null ;
    /** Information if UNDO works with first or second half. */
    private boolean firstHalf = true;

    /**
     * Getter for current Tab.
     * @return Current Tab of the game.
     */
    public Tab getTab(){
        return myTab;
    }

    /**
     * Incrementation of labCounter.
     */
    public void labCounterInc(){
        labCounter++;
    }
    /**
     * Decrementation of labCounter.
     */
    public void labCounterDec(){
        labCounter--;
    }

    /**
     * Setter for filename.
     * @param filename Filename to set.
     */
    public void setFilename(String filename){
        this.filename = filename;
    }

    /**
     * Setter for notation.
     * @param notation Notation to set.
     */
    public void setNotation(boolean notation){
        this.longNot = notation;
    }

    /**
     * Getter for notation.
     * @return Notation.
     */
    public boolean getNotation(){
        return this.longNot;
    }

    /**
     * Method creates graphical user interface for one tab.
     * @param gameName Name of the game.
     */
    public BoardCreator(String gameName) {

        labels = new ArrayList<>();
        HBox hbox = new HBox();
        mainPane = new BorderPane();
        GridPane root = new GridPane();
        mainPane.setCenter(root);
        this.setVbox();
        my_arr = new StackPane[9][9];
        final int size = 9 ;
        for (int row = size-1; row >= 0; row--) {
            for (int col = size-1; col >= 0; col--) {
                StackPane square = new StackPane();
                my_arr[col][row] = square;
                String color = "white" ;
                if (row != 8 && col != 0 ) {
                    if ((row + col) % 2 == 0) {
                        color = "white";
                    } else {
                        color = "black";
                    }
                }
                else {
                    if (row != 8 || col != 0) {
                        Label newLable = null;
                        if (row == 8 && col > 0) {
                            int n = col + 96;
                            char my_char = (char) n;
                            newLable = new Label(Character.toString(my_char));
                            newLable.setFont(new Font("Arial", 20));
                        } else if (col == 0 && row < 8) {
                            newLable = new Label(Integer.toString(abs(row-8)));
                            newLable.setFont(new Font("Arial", 20));
                        }

                        square.getChildren().add(newLable);
                        square.maxHeight(10);
                        square.maxWidth(10);
                    }
                }
                square.setStyle("-fx-background-color: "+color+";");
                square.minHeight(10);
                square.maxHeight(30);


                square.minWidth(10);
                square.maxHeight(30);

                root.add(square, col, row);
            }
        }

        root.getColumnConstraints().add(new ColumnConstraints(10, 30,200));
        for (int i = size-1; i > 0; i--) {
            root.getRowConstraints().add(new RowConstraints(5, 100,200));
        }
        for (int i = 1; i < size; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(5, 100,200));
        }

        // create game and board

        Board board = new Board(8,this);
        myBoard = board;
        game = GameFactory.createChessGame(board);
        myBoard.setGame(game);
        game.setBoardCreator(this);
        Button undo_but = new Button("UNDO");
        Button redo_but = new Button("REDO");


        simulateBut = new Button(">>");
        simulateBut.setOnAction(e ->{

                boolean succ;
                succ = simulator.simulate(game.getLine(),longNot);
                System.out.println(succ);
                if (succ) {
                    labCounter++;
                    if ((labCounter % 2) == 0) {
                        if (labCounter > 1)
                            labels.get(labCounter / 2 - 1).setStyle("-fx-background-color:rgba(0, 0, 255,0)");
                        labels.get(labCounter / 2).setStyle("-fx-background-color:rgba(85, 255, 68,0.7)");
                    }
                }


        });

        Button backBut = new Button("<<");

        backBut.setOnAction(e ->{
            if(game.undo(false)) {
                game.backLine();
                if (labCounter % 2 == 0) {
                    labels.get(labCounter / 2).setStyle("-fx-background-color:rgba(0, 0, 255,0)");
                    if (labCounter > 0)
                        labels.get(labCounter / 2 - 1).setStyle("-fx-background-color:rgba(85, 255, 68,0.7)");
                }
                labCounter--;
            }
        });

        restartButton = new Button("Restart");
        restartButton.setCancelButton(true);
        restartButton.setVisible(false);
        restartButton.setOnAction(e -> {
            while (labCounter != -1)
                backBut.fire();
        });



        Button autoSimulation = new Button("Play");
        autoSimulation.setVisible(false);

        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(100);
        slider.setValue(40);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);


        autoSimulation.setOnAction(e -> {

                if (autoSimulation.getText().equals("Play")) {
                    autoSimulation.setText("Stop");
                    hbox.getChildren().removeAll(backBut,simulateBut,restartButton);
                    hbox.getChildren().add(slider);
                    hbox.getChildren().add(restartButton);
                    thread = new Thread(() -> {
                        try {
                            while (labCounter < labels.size()*2-1){
                                Platform.runLater(() -> simulateBut.fire());
                                long interval = (long) slider.getValue();
                                interval = 1500 - interval*10;
                                Thread.sleep(interval);
                            }
                        } catch (InterruptedException exc) {
                            // interrupted signal when auto-simulation is off
                        }
                    });
                    thread.setDaemon(true);
                    thread.start();



                }
                else {
                    autoSimulation.setText("Play");
                    hbox.getChildren().remove(slider);
                    hbox.getChildren().remove(restartButton);
                    hbox.getChildren().addAll(backBut,simulateBut,restartButton);
                    thread.interrupt();
                }

        });

        // Creation of button Simulation which change mode of the game to "simulation"
        // and adapt a interface to it
        simulation = new Button("Start simulation");
        simulation.setOnAction(e -> {
            if (labels.size() > 0) {
                simulator = game.simulate(filename, longNot);
                if (simulator != null) {
                    if (simulation.getText().equals("Start simulation")) {
                        simulation.setText("Stop simulation");
                        simulateBut.setVisible(true);
                        labels.get(labCounter / 2 ).setStyle("-fx-background-color:rgba(85, 255, 68,0.7)");
                        backBut.setVisible(true);
                        restartButton.setVisible(true);
                        undo_but.setVisible(false);
                        redo_but.setVisible(false);
                        autoSimulation.setVisible(true);
                        redo_but.setText("");
                        undo_but.setText("");
                        game.setMode(true);
                    } else {
                        simulation.setText("Start simulation");
                        game.clearStacks();
                        game.setAfterSimulation(true);
                        labels.get(labCounter / 2).setStyle("-fx-background-color:rgba(0, 0, 255,0)");
                        //labels.get(labCounter / 2-1).setStyle("-fx-background-color:rgba(0, 0, 255,0)");
                        if(autoSimulation.getText().equals("Stop"))
                            autoSimulation.fire();
                        simulateBut.setVisible(false);
                        backBut.setVisible(false);
                        restartButton.setVisible(false);
                        autoSimulation.setVisible(false);
                        undo_but.setVisible(true);
                        redo_but.setVisible(true);
                        redo_but.setText("REDO");
                        undo_but.setText("UNDO");
                        game.setMode(false);
                    }
                }
            }
            else System.out.println("Error: Nothing to simulate.\nNeed to make move white and black figure.");


        });

        // Create HBox and add buttons to it
        undo_but.setOnAction(e ->  game.undo(true));
        redo_but.setOnAction(e -> game.redo());
        BorderPane top_pane = new BorderPane();
        hbox.getChildren().add(simulation);
        hbox.getChildren().add(undo_but);
        hbox.getChildren().add(redo_but);
        hbox.getChildren().add(autoSimulation);
        hbox.getChildren().add(backBut);
        hbox.getChildren().add(simulateBut);
        hbox.getChildren().add(restartButton);



        simulateBut.setVisible(false);
        backBut.setVisible(false);

        top_pane.setLeft(hbox);
        mainPane.setTop(top_pane);


        Tab ownTab = new Tab(gameName);
        ownTab.setContent(mainPane);
        this.myTab = ownTab;


    }

    /**
     * Setter for name of the game.
     * @param name Name of the game.
     */
    public void setName(String name){
        this.myTab.setText(name);
    }

    /**
     * Creates Vbox and set his Alignments
     */
    public void setVbox(){
        myText = "";
        counter = 0;
        myVbox = new VBox();
        myVbox.setMinWidth(100);
        myVbox.setAlignment(Pos.TOP_CENTER);
        Label spaces = new Label("\t");
        Label heading = new Label(" Game description ");
        heading.setFont(new Font("Arial", 20));
        myVbox.getChildren().add(heading);
        myVbox.getChildren().add(spaces);
        myVbox.setAlignment(Pos.TOP_LEFT);
        String cssLayout = "-fx-border-color: black;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: solid centered, solid centered ";
        myVbox.setStyle(cssLayout);
        mainPane.setRight(myVbox);
    }

    /** Method write moves to "Game Description".
     *
     * @param string Info to write.
     * @param fromFile Information if it is from file.
     * */
    public void writeInfo(String string,boolean fromFile){

        if (!fromFile) {
            if (firstHalf){
                firstHalf=false;
            }
            else firstHalf = true;
            if (my_count == 1) {
                my_count++;
                counter++;
                myText = " " + counter + ". " + string;
                myText = "  " + myText;
                myComplexString = myText;
                Label myLabel = new Label(myText);
                myLabel.setOnMouseExited(e -> {
                    myLabel.setScaleX(1);
                    myLabel.setScaleY(1);
                });
                myLabel.setOnMouseEntered(e ->{
                    myLabel.setScaleX(1.2);
                    myLabel.setScaleY(1.2);
                });
                this.myVbox.getChildren().add(myLabel);


            } else {
                my_count = 1;
                String temp = myText;
                myText += " " + string;
                myComplexString += " " + string +"\n";
                Label myLabel = new Label(myText);
                myLabel.setOnMouseClicked(e -> {
                    if (game.getMode()){
                        String myString = myLabel.getText();
                        viewState(myString);
                    }
                });
                myLabel.setOnMouseExited(e -> {
                    myLabel.setScaleX(1);
                    myLabel.setScaleY(1);
                });
                myLabel.setOnMouseEntered(e ->{
                    myLabel.setScaleX(1.2);
                    myLabel.setScaleY(1.2);
                });
                if (counter > 0) {
                    this.myVbox.getChildren().remove(counter + 1);
                }
                this.myVbox.getChildren().add(myLabel);
                labels.add(myLabel);
                myText = temp;
            }
        }
        else {
            counter++;
            myText = "   "+string;
            Label myLabel = new Label(myText);
            myLabel.setOnMouseExited(e -> {
                myLabel.setScaleX(1);
                myLabel.setScaleY(1);
            });
            myLabel.setOnMouseEntered(e ->{
                myLabel.setScaleX(1.2);
                myLabel.setScaleY(1.2);
            });
            myLabel.setOnMouseClicked(e -> {
                if (game.getMode()){
                    String myString = myLabel.getText();
                    viewState(myString);
                }
            });
            myComplexString += string+"\n";
            labels.add(myLabel);
            myVbox.getChildren().add(myLabel);

        }
    }



    /**
     *  function take move from Game Description whene UNDO is used
     */
    public void undoInfo(){
        if (my_count == 1){
            my_count=2;
        }
        else my_count = 1;
        Label myLab =(Label) this.myVbox.getChildren().get(counter+1);
        String myStr = myLab.getText();
        String[] splited = myStr.split("\\s+");
        if (firstHalf){
            String newStr = "   "+splited[1]+" "+splited[2];
            System.out.println(newStr);
            Label myLabel = new Label(newStr);
            if (counter > 0)
                this.myVbox.getChildren().remove(counter+1);
            this.myVbox.getChildren().add(myLabel);
            firstHalf=false;

        }
        else {
            this.myVbox.getChildren().remove(counter+1);
            firstHalf=true;
            labels.remove(labels.size()-1);
            counter--;
        }

    }

    // function take a string when label is touched and set board to
    // the state of the touched label

    /**
     * Function take a string when label is touched and set board to.
     *
     * @param string The state of the touched label.
     */
    private void viewState(String string){
        String[] splitted;
        string = string.trim();
        splitted = string.split("\\s+");
        int index1 = 0;
        int index2 = 0;
        char mychar1 = splitted[0].charAt(0);
        char mychar2 = splitted[0].charAt(1);

        if (mychar2 == '.') {
            index1 = mychar1 - 48;
        }
        else{
            index1 = mychar1 - 48;
            index2 = mychar2 - 48;
            index1 = 10*index1;
            index1 += index2;
        }
        System.out.println("moje i je: "+index1);
        restartButton.fire();
        for(int j = 0; j < index1;j++){
            simulateBut.fire();
            simulateBut.fire();
        }




    }
}
