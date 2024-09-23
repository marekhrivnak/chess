package ija_proj.game;

import ija_proj.common.*;


/**
 * Class for simulating chess game.
 */
public class Simulator  {
    private Game game;

    /**
     * Setter for actual Game.
     * @param game Actual Game.
     */
    public void setMyGame(Game game){
        this.game = game;
    }

    /**
     * Method which simulates chess game by one line-
     * @param line Line to be simulated.
     * @param longNot Infromation about notation.
     * @return Success of action.
     */
    public boolean simulate(String line,Boolean longNot){

        if (line.equals("")){
            System.out.println("Error: You can not step forward - DEADEND");
            return false;
        }
        boolean isMat = false;
        boolean isKick = false;
        Figure.type typFigury = Figure.type.pawn;

        if (line.charAt(0) == 'S')
            typFigury = Figure.type.bishop;
        else if (line.charAt(0) == 'V')
            typFigury = Figure.type.tower;
        else if (line.charAt(0) == 'K')
            typFigury = Figure.type.king;
        else if (line.charAt(0) == 'D')
            typFigury = Figure.type.queen;
        else if (line.charAt(0) == 'J')
            typFigury = Figure.type.horse;
        else {
            typFigury = Figure.type.pawn;
        }

        if (line.charAt(line.length() - 1) == 43){
            isMat = true;
        }
        try {
            if (isMat) {
                if (line.charAt(line.length() - 4) == 120) {
                    isKick = true;
                }
            } else {
                if (line.charAt(line.length() - 3) == 120) {
                    isKick = true;
                }
            }
        }
        catch (IndexOutOfBoundsException e){
            isKick=false;
        }

        int row;
        int col;
        if (isMat){
            row = line.charAt(line.length() - 2) - 48;
            col = line.charAt(line.length() - 3) - 96;
        }
        else {
            row = line.charAt(line.length() - 1) - 48;
            col = line.charAt(line.length() - 2) - 96;
        }

       // System.out.println("moj col: "+col+" moj row: "+row);

        Field myField = game.getBoard().getField(col,row);

        if (!longNot) {
            int special_row = 0;
            int special_col = 0;
            if (line.length() == 3 && typFigury == Figure.type.pawn){
                if (line.charAt(0) < 97){
                    special_row = line.charAt(0) - 48;
                }
                else special_col = line.charAt(0) - 96;
            }
            switch (typFigury) {
                case horse:
                    for (Horse horse : game.getBoard().myHorses) {
                        if (game.move(horse, myField, false))
                            break;
                    }
                    break;
                case queen:
                    for (Queen queen : game.getBoard().myQueens) {
                        if (game.move(queen, myField, false))
                            break;
                    }
                    break;
                case bishop:
                    for (Bishop bishop : game.getBoard().myBishops) {
                        if (game.move(bishop, myField, false))
                            break;
                    }
                    break;
                case king:
                    for (King king : game.getBoard().myKings) {
                        if (game.move(king, myField, false))
                            break;
                    }
                    break;
                case pawn:
                    if (special_col != 0){
                        for (Pawn pawn : game.getBoard().myPawns) {
                            if (pawn.getField().getCol() != special_col)
                                continue;
                            if (game.move(pawn, myField, false))
                                break;
                        }

                    }
                    else if (special_row != 0){
                        for (Pawn pawn : game.getBoard().myPawns) {
                            if (pawn.getField().getCol() != special_row)
                                continue;
                            if (game.move(pawn, myField, false))
                                break;
                        }

                    }
                    else {
                        for (Pawn pawn : game.getBoard().myPawns) {
                            if (game.move(pawn, myField, false))
                                break;
                        }
                    }
                    break;
                case tower:
                    for (Tower tower : game.getBoard().myTowers) {
                        if (game.move(tower, myField, false))
                            break;
                    }
                    break;
                default:
                    System.err.println("ERROR! Unexpected type of Figure");
                    System.exit(1);

            }
        }
        else {

                int fromCol;
                int fromRow;
                if (isMat){
                    if (isKick){
                        fromCol = line.charAt(line.length() - 6) - 96;
                        fromRow = line.charAt(line.length() - 5) - 48;
                    }
                    else {
                        fromCol = line.charAt(line.length() - 5) - 96;
                        fromRow = line.charAt(line.length() - 4) - 48;
                    }
                }
                else {
                    if (isKick){
                        fromCol = line.charAt(line.length() - 5) - 96;
                        fromRow = line.charAt(line.length() - 4) - 48;
                    }
                    else {
                        fromCol = line.charAt(line.length() - 4) - 96;
                        fromRow = line.charAt(line.length() - 3) - 48;
                    }
                }
            System.out.println(fromCol+"   "+fromRow);
            Figure figure = game.getBoard().getField(fromCol,fromRow).get();
            if (figure.getType() != typFigury) {
                System.err.println("ERROR: On the field is unexpected type of Figure");
                System.exit(1);
            }

            game.move(figure,myField,false);

        }

        return true;



    }

}
