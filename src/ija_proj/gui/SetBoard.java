package ija_proj.gui;


import ija_proj.game.SaveLoadFile;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;


/**
 * Main class of the program. Creates basic user interface and all its elements.
 */
public class SetBoard extends Application {

    /** List of BoardCreators used in program. */
    private static ArrayList<BoardCreator> boardList;
    /** One line of text in "Game Description" */
    private static String myText = "";
    /** Name of the loaded file */
    private String filename;

    /**
     * Method which creates basic user interface and all its elements.
     * @param primaryStage Name of the primary stage of GUI.
     */
    @Override
    public void start(Stage primaryStage) {

        TabPane tabLayout = new TabPane();
        boardList = new ArrayList<>();

        // Creation of button and stage for newGame
        Button new_but = new Button("New game");

        Button start_but = new Button("Create");
        Button close_but = new Button("Cancel");
        HBox s_hbox = new HBox();
        VBox s_vbox = new VBox();
        Label start_label = new Label("Create new game");
        TextField textField = new TextField("new_game");
        s_vbox.getChildren().addAll(start_label,textField,s_hbox);
        s_vbox.setAlignment(Pos.CENTER);
        s_hbox.setAlignment(Pos.CENTER);
        s_hbox.setSpacing(10);
        s_vbox.setSpacing(10);
        s_hbox.getChildren().addAll(start_but,close_but);
        Scene start_scene = new Scene(s_vbox,300,150);
        Stage start_stage = new Stage();
        start_stage.setScene(start_scene);
        start_stage.setTitle("New game");


        new_but.setOnAction(e -> {
                start_stage.show();
                start_but.setOnAction(ed -> {
                    BoardCreator newBoard = new BoardCreator(textField.getText());
                    boardList.add(newBoard);
                    tabLayout.getTabs().add(newBoard.getTab());
                    tabLayout.getSelectionModel().select(newBoard.getTab());

                    start_stage.close();
                });
            close_but.setOnAction(es -> start_stage.close());
        });

        // Creation of button and stage for SaveGame
        Button yes_but = new Button("Save");
        Button cancel_but = new Button("Cancel");
        HBox n_hbox = new HBox();
        VBox n_vbox = new VBox();
        Label save_label = new Label("Save game");
        TextField text = new TextField("new_game");

        n_vbox.getChildren().addAll(save_label,text,n_hbox);
        n_vbox.setAlignment(Pos.CENTER);
        n_hbox.setAlignment(Pos.CENTER);
        n_hbox.setSpacing(10);
        n_vbox.setSpacing(10);
        n_hbox.getChildren().addAll(yes_but,cancel_but);
        Scene new_scene = new Scene(n_vbox,300,150);
        Stage new_stage = new Stage();
        new_stage.setScene(new_scene);
        new_stage.setTitle("Save game");

        // SAVE BUTTON
        Button save_but = new Button("Save game");
        save_but.setOnAction(e-> {
            text.setText(tabLayout.getSelectionModel().getSelectedItem().getText());
            new_stage.show();
            yes_but.setOnAction(e2 -> {
                String filename = text.getText();
                SaveLoadFile.save(filename,tabLayout.getSelectionModel().getSelectedItem());
                new_stage.close();
            });
            cancel_but.setOnAction(e3 -> {
                new_stage.close();
            });

        });

        // creation of button and stage for LoadGame
        HBox hbox3 = new HBox();
        VBox vbox2 = new VBox();
        Button loadBut = new Button("Load");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        vbox2.getChildren().addAll(choiceBox,loadBut);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setSpacing(10);
        Scene loadscene = new Scene(vbox2,300,150);
        Stage loadstage = new Stage();
        loadstage.setScene(loadscene);
        loadstage.setTitle("Load game");


        // LOAD BUTTON
        Button load_but = new Button("Load game");
        load_but.setOnAction(e -> {
            String[] myGames = getSavedgames();
            choiceBox.getItems().clear();
            choiceBox.setValue(myGames[0]);
            for (int i = 0; i < myGames.length; i++){
                choiceBox.getItems().add(myGames[i]);
            }

            loadstage.show();

            loadBut.setOnAction(ex -> {

                filename = choiceBox.getValue();
                BoardCreator newBoard = new BoardCreator(filename);
                boardList.add(newBoard);
                tabLayout.getTabs().add(newBoard.getTab());
                tabLayout.getSelectionModel().select(newBoard.getTab());
                loadstage.close();
                boolean longNot = SaveLoadFile.load(filename,newBoard);
                newBoard.setNotation(longNot);
                newBoard.setFilename(filename);
                newBoard.simulation.fire();
            });

        });


        BorderPane MainBorderPane = new BorderPane();
        MainBorderPane.setCenter(tabLayout);
        HBox mainHbox = new HBox();
        mainHbox.getChildren().addAll(new_but,save_but,load_but);
        StackPane mainTopPane = new StackPane();
        mainTopPane.getChildren().add(mainHbox);
        MainBorderPane.setTop(mainTopPane);



        Scene scene = new Scene(MainBorderPane, 900, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess");
        getSavedgames();

        // SAVE GAME

        primaryStage.show();
        // Create first board
        BoardCreator newBoard = new BoardCreator("unknown_game");
        boardList.add(newBoard);
        tabLayout.getTabs().add(newBoard.getTab());

        // Create new game
        Button setName = new Button("Set name");
        VBox myV = new VBox();
        myV.setSpacing(10);
        Label myLab = new Label("Set name of the game");
        TextField firstText = new TextField("new_game");
        firstText.setAlignment(Pos.CENTER);
        myV.getChildren().addAll(myLab,firstText,setName);
        myV.setAlignment(Pos.CENTER);
        Scene startScene = new Scene(myV,200,100);
        Stage startStage = new Stage();
        startStage.setScene(startScene);
        startStage.setTitle("New game");
        //startStage.show();
        setName.setOnAction(e-> {
            newBoard.setName(firstText.getText());
            startStage.close();
        });

        Button newG = new Button("New game");
        Button loadG = new Button("Load game");
        Label newLab = new Label("Chess");
        Image image = new Image("ija_proj/images/black_king.png");
        ImageView imageView = new ImageView(image);
        HBox startH = new HBox();
        startH.setAlignment(Pos.CENTER);
        startH.getChildren().addAll(newG,loadG);
        startH.setSpacing(10);
        VBox startV = new VBox();
        startV.setAlignment(Pos.CENTER);
        startV.setSpacing(10);
        startV.getChildren().addAll(imageView,newLab,startH);

        Scene firstScene = new Scene(startV,300,160);
        Stage firstStage = new Stage();
        firstStage.setScene(firstScene);
        firstStage.setTitle("Chess");
        firstStage.show();
        newG.setOnAction(e -> {
            firstStage.close();
            startStage.show();
        });
        loadG.setOnAction(e -> {
            firstStage.close();
            load_but.fire();
        });



    }

    /**
     * Main method - Start of the program.
     * @param args Arguments.
     */
    public static void main(String[] args) {
        launch(args);

    }

    /**
     * Loads all name of saved games in folder "saved_games"
     * @return Array of loaded saved games.
     */
    private String[] getSavedgames(){

        File folder = new File("src/ija_proj/saved_games");
        File[] listOfFiles = folder.listFiles();
        String[] myGames = new String[listOfFiles.length];


        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().charAt(0) == '.')
                    continue;
                myGames[i] = listOfFiles[i].getName();
            }
        }
        return myGames;

    }


}
