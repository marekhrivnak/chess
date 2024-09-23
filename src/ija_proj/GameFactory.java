package ija_proj;
import ija_proj.common.*;
import ija_proj.game.Board;
import ija_proj.game.BoardField;
import ija_proj.gui.SetBoard;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public abstract class GameFactory {

   private static Game myGame;


    public static Game createChessGame(Board board){

       // clearGame(board);
        //create new game
        Game game = new SetGame(board,"chess");
        myGame = game;
        myGame.getBoard().isBlackMat = false;
        myGame.getBoard().isWhiteMat = false;
        myGame.getBoard().turn = true;
        myGame.getBoard().myFigures = new ArrayList<Figure>();
        myGame.getBoard().myPawns = new ArrayList<Pawn>();
        myGame.getBoard().myKings = new ArrayList<King>();
        myGame.getBoard().myQueens = new ArrayList<Queen>();
        myGame.getBoard().myBishops = new ArrayList<Bishop>();
        myGame.getBoard().myTowers = new ArrayList<Tower>();
        myGame.getBoard().myHorses = new ArrayList<Horse>();

        /****** CREATE EVERY PIECE AND PUT IT ON TABLE ******/

        Field f1 = board.getField(1, 2);
        Pawn d1 = new Pawn(true);
        f1.put(d1);
        d1.logic();

        Field f2 = board.getField(2, 2);
        Pawn d2 = new Pawn(true);
        f2.put(d2);
        d2.logic();

        Field f3 = board.getField(3, 2);
        Pawn d3 = new Pawn(true);
        f3.put(d3);
        d3.logic();

        Field f4 = board.getField(4, 2);
        Pawn d4 = new Pawn(true);
        f4.put(d4);
        d4.logic();

        Field f5 = board.getField(5, 2);
        Pawn d5 = new Pawn(true);
        f5.put(d5);
        d5.logic();


        Field f6 = board.getField(6, 2);
        Pawn d6 = new Pawn(true);
        f6.put(d6);
        d6.logic();

        Field f7 = board.getField(7, 2);
        Pawn d7 = new Pawn(true);
        f7.put(d7);
        d7.logic();

        Field f8 = board.getField(8, 2);
        Pawn d8 = new Pawn(true);
        f8.put(d8);
        d8.logic();

        Field fp1 = board.getField(1, 7);
        Pawn p1 = new Pawn(false);
        fp1.put(p1);
        p1.logic();

        Field fp2 = board.getField(2, 7);
        Pawn p2 = new Pawn(false);
        fp2.put(p2);
        p2.logic();

        Field fp3 = board.getField(3, 7);
        Pawn p3 = new Pawn(false);
        fp3.put(p3);
        p3.logic();

        Field fp4 = board.getField(4, 7);
        Pawn p4 = new Pawn(false);
        fp4.put(p4);
        p4.logic();

        Field fp5 = board.getField(5, 7);
        Pawn p5 = new Pawn(false);
        fp5.put(p5);
        p5.logic();

        Field fp6 = board.getField(6, 7);
        Pawn p6= new Pawn(false);
        fp6.put(p6);
        p6.logic();

        Field fp7 = board.getField(7, 7);
        Pawn p7 = new Pawn(false);
        fp7.put(p7);
        p7.logic();

        Field fp8 = board.getField(8, 7);
        Pawn p8 = new Pawn(false);
        fp8.put(p8);
        p8.logic();
        fp8.logic();



        Field ft1 = board.getField(1, 1);
        Tower t1 = new Tower(true);
        ft1.put(t1);
        t1.logic();

        Field ft2 = board.getField(board.getSize(), 1);
        Tower t2 = new Tower(true);
        ft2.put(t2);
        t2.logic();

        Field ft3 = board.getField(1, board.getSize());
        Tower t3 = new Tower(false);
        ft3.put(t3);
        t3.logic();

        Field ft4 = board.getField(board.getSize(), board.getSize());
        Tower t4 = new Tower(false);
        ft4.put(t4);
        t4.logic();

        Field fb1 = board.getField(3, 8);
        Bishop b1 = new Bishop(false);
        fb1.put(b1);
        b1.logic();

        Field fb2 = board.getField(6, 8);
        Bishop b2 = new Bishop(false);
        fb2.put(b2);
        b2.logic();

        Field fb3 = board.getField(3, 1);
        Bishop b3 = new Bishop(true);
        fb3.put(b3);
        b3.logic();

        Field fb4 = board.getField(6, 1);
        Bishop b4 = new Bishop(true);
        fb4.put(b4);
        b4.logic();

        Field fh1 = board.getField(7, 1);
        Horse h1 = new Horse(true);
        fh1.put(h1);
        h1.logic();

        Field fh2 = board.getField(2, 1);
        Horse h2 = new Horse(true);
        fh2.put(h2);
        h2.logic();

        Field fh3 = board.getField(7, 8);
        Horse h3 = new Horse(false);
        fh3.put(h3);
        h3.logic();

        Field fh4 = board.getField(2, 8);
        Horse h4 = new Horse(false);
        fh4.put(h4);
        h4.logic();

        Field fk1 = board.getField(5, 1);
        King k1 = new King(true);
        myGame.getBoard().whiteKing = k1;
        fk1.put(k1);
        k1.logic();

        Field fk2 = board.getField(5, 8);
        King k2 = new King(false);
        myGame.getBoard().nightKing = k2;
        fk2.put(k2);
        k2.logic();

        Field fq1 = board.getField(4, 1);
        Queen q1 = new Queen(true);
        fq1.put(q1);
        q1.logic();

        Field fq2 = board.getField(4, 8);
        Queen q2 = new Queen(false);
        fq2.put(q2);
        q2.logic();

        for (int i = 1;i < 9;i++) {
            for (int j = 1; j < 9; j++) {
                Field ft5 = board.getField(j, i);
                ft5.logic();
            }
        }
       /* Field ft6 = board.getField(8, 6);
        ft6.logic();*/

        return game;

    }

    public static Game getGame(){
        return myGame;
    }



    public static void clearGame(Board board){
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                Field f1 = board.getField(j,i);
                f1.remove(f1.getDisk());
            }
        }
        Field f1 = board.getField(1,3);
        f1.remove(f1.getDisk());


    }



}



