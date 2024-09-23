package ija_proj.game;

import ija_proj.common.Disk;
import ija_proj.common.Field;
import ija_proj.gui.BoardCreator;
import javafx.scene.layout.StackPane;


public class BoardField implements Field {

    private int col;
    private int row;
    private Disk figurka_na_doske = null;
    private Field[] dir_array;
    private StackPane fieldPane;
    private BoardCreator myBoardCreator;
    private Board board;

    public Disk getDisk(){
        return this.figurka_na_doske;
    }

    public void logic(){

            this.fieldPane.setOnMouseClicked(e -> {
                if(board.glob_button != null) {
                    if (board.myGame.move(board.glob_disk,this,true)) {
                        board.glob_button = null;
                        board.glob_disk = null;

                    } else
                        ;
                }
            });



    }

    public BoardField  (int col, int row, Board myboard){
        this.board = myboard;
        this.col=col;
        this.row=row;
        this.dir_array = new Field[8];
        this.myBoardCreator = this.board.myBoardCreator;
        setPane();

    }

    public void setPane(){
        if (this.row == 1)
            this.fieldPane = this.myBoardCreator.my_arr[this.col][7];
        else if (this.row == 2)
            this.fieldPane = this.myBoardCreator.my_arr[this.col][6];
        else if (this.row == 3)
            this.fieldPane = this.myBoardCreator.my_arr[this.col][5];
        else if (this.row == 4)
            this.fieldPane = this.myBoardCreator.my_arr[this.col][4];
        else if (this.row == 5)
            this.fieldPane = this.myBoardCreator.my_arr[this.col][3];
        else if (this.row == 6)
            this.fieldPane = this.myBoardCreator.my_arr[this.col][2];
        else if (this.row == 7)
            this.fieldPane = this.myBoardCreator.my_arr[this.col][1];
        else if (this.row == 8)
            this.fieldPane = this.myBoardCreator.my_arr[this.col][0];
    }

    public StackPane getPane(){
        return this.fieldPane;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public void addNextField(Field.Direction dirs, Field field) {

        switch (dirs) {
            case D:
                dir_array[0] = field;
                break;
            case L:
                dir_array[1] = field;
                break;
            case LD:
                dir_array[2] = field;
                break;
            case LU:
                dir_array[3] = field;
                break;
            case R:
                dir_array[4] = field;
                break;
            case RD:
                dir_array[5] = field;
                break;
            case RU:
                dir_array[6] = field;
                break;
            case U:
                dir_array[7] = field;
                break;

        }
    }

    public Field nextField(Field.Direction dirs) {

        switch (dirs) {
            case D:
                return dir_array[0];
            case L:
                return dir_array[1];
            case LD:
                return dir_array[2];
            case LU:
                return dir_array[3];
            case R:
                return dir_array[4];
            case RD:
                return dir_array[5];
            case RU:
                return dir_array[6];
            case U:
                return dir_array[7];
            default: return null;


        }
    }

    public void set_directions(int size,  BoardField[][] help_boardfield){
        if (row-1 != 0){
            this.addNextField(Direction.D,help_boardfield[col][row-1] );
            if (col-1 != 0){
                this.addNextField(Direction.LD,help_boardfield[col-1][row-1] );

            }
            else this.addNextField(Direction.LD,null );
            if (col != size){
                this.addNextField(Direction.RD,help_boardfield[col+1][row-1] );
            }
            else this.addNextField(Direction.RD,null );
        }
        else this.addNextField(Direction.D,null );

        if (row != size){
            this.addNextField(Direction.U,help_boardfield[col][row+1] );
            if (col-1 != 0){
                this.addNextField(Direction.LU,help_boardfield[col-1][row+1] );
            }
            else this.addNextField(Direction.LU,null );
            if (col != size){
                this.addNextField(Direction.RU,help_boardfield[col+1][row+1] );
            }
            else this.addNextField(Direction.RU,null );
        }
        else this.addNextField(Direction.U,null );

        if (col-1 != 0){
            this.addNextField(Direction.L,help_boardfield[col-1][row] );
        }
        else this.addNextField(Direction.L,null );

        if (col != size){
            this.addNextField(Direction.R,help_boardfield[col+1][row] );
        }
        else this.addNextField(Direction.R,null );

    }

    public boolean put (Disk disk){
        if (this.figurka_na_doske == null){
            this.figurka_na_doske = disk;
            disk.setField(this);
            this.getPane().getChildren().add(disk.getButton());
            return true;
        }
        else return false;
    }

    public boolean remove(Disk disk){
        if ((this.figurka_na_doske != disk ) || (this.figurka_na_doske == null)){
            return false;
        }
        this.figurka_na_doske = null;
        this.getPane().getChildren().remove(disk.getButton());


        return true;
    }

    public Disk get(){

        return this.figurka_na_doske;
    }


    public boolean isEmpty() {
        if (this.figurka_na_doske == null) {
            return true;
        }
        else return false;
    }

}

