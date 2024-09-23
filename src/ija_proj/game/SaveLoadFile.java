package ija_proj.game;

import ija_proj.gui.BoardCreator;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


import java.io.*;

/**
 * Abstract class. Purpose: Saves and Loads files.
 */
public abstract class SaveLoadFile {

    /**
     * Number of moves loaded from file.
     */
    private static int numberMoves = 0;

    /**
     * Method for saving file created by user.
     * @param filename Name of the file.
     * @param tab Tab of the file.
     */
    public static void save(String filename, Tab tab) {

        BorderPane pane = (BorderPane) tab.getContent();
        VBox vbox = (VBox) pane.getRight();
        Label lab = (Label) vbox.getChildren().get(2);
        String str = lab.getText();


        try (FileWriter file = new FileWriter(new File("src/ija_proj/saved_games",filename))){

            int i = 2;
            while (str != null){
                i++;
                System.out.println(str);
                str.trim();
                file.write(str+"\n");
                try {
                    lab = (Label) vbox.getChildren().get(i);
                }
                catch (IndexOutOfBoundsException e){
                    break;
                }
                str = lab.getText();

            }

        }
        catch (IOException e){
            e.printStackTrace();
        }



    }

    /**
     * Getter for number of moves loaded from file.
     * @return Number of moves.
     */
    public static int getNumberMoves(){
        return numberMoves;
    }

    /**
     * Loads a file from folder "saved_games".
     * @param filename Name of the file.
     * @param boardCreator Object BoardCreator used in the game.
     * @return Success of action.
     */
    public static boolean load(String filename, BoardCreator boardCreator) {


        boolean longNotation = false;


        try (BufferedReader file = new BufferedReader(new FileReader(new File("src/ija_proj/saved_games",filename)))){

            String st;

            boolean notchecked = true;

            while ((st = file.readLine()) != null) {
                numberMoves++;
                st = st.trim();
                if (st.isEmpty()) break;
                String[] splited = st.split("\\s+");
                if (notchecked) {
                    if (splited[1].length() == 2 || splited[1].length() == 3) {
                        longNotation = false;
                    }
                    else if (splited[1].length() == 4 || splited[1].length() == 5)
                        longNotation = true;
                    else {
                        System.err.println("Error: Not long notation or short notation");
                        System.exit(1);
                    }



                    notchecked = false;
                }



                if (!splited[0].matches("[1-9][0-9]*.")) {
                    System.out.println("Wrong anotation 1 !");
                }


                if (!splited[1].matches("^[KDVJSP]?+([abcdefgh]+[12345678])?+([x])?+([abcdefgh]+[12345678])?+([+#])?")) {
                    System.out.println("Wrong anotation 2! for: "+splited[1]);
                    System.exit(1);
                }

                if (!splited[2].matches("^[KDVJSP]?+([abcdefgh]+[12345678])?+([x])?+([abcdefgh]+[12345678])?+([+#])?")) {
                    System.out.println("Wrong anotation 3! for: "+splited[2]);
                    System.exit(1);
                }

                boardCreator.writeInfo(st,true);

            }


    }
        catch (IOException e){
            e.printStackTrace();
        }

        return longNotation;

    }




}


