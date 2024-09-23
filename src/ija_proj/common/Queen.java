package ija_proj.common;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Class Queen extend Class Disk and specifies figure of type Queen
 */
public class Queen extends Disk {

    /**
     * Constructor.
     *
     * @param  isWhite  if figure is white set true else black
     */
    public Queen(boolean isWhite) {
        super(isWhite,Figure.type.queen);
        game.getBoard().myQueens.add(this);
        setImage();
    }

    /**
     * Function move Figure to the Field if it is possible
     *
     * @param moveTo where figure will be moved
     * @return true if operation ends successfully
     */
    @Override
    public boolean move(Field moveTo){
        Field temp = location;
        int actual_col = this.location.getCol();
        int actual_row = this.location.getRow();
        boolean is_oponnent = false;
        if (!moveTo.isEmpty()) {
            if (moveTo.get().isWhite() == this.isWhite()) {
                is_oponnent = false;
            } else is_oponnent = true;
        }


        if (Math.abs(actual_row-moveTo.getRow()) == Math.abs(actual_col-moveTo.getCol())){
            if (moveTo.getCol() > actual_col && moveTo.getRow() > actual_row){
                while (actual_row < moveTo.getRow() && actual_col < moveTo.getCol() ){
                    if (moveTo.getRow() != actual_row + 1) {
                        if (temp.nextField(Field.Direction.RU).isEmpty()) {
                            actual_row++;
                            actual_col++;
                            temp = temp.nextField(Field.Direction.RU);
                        } else return false;
                    }
                    else {
                        actual_row++;
                        actual_col++;
                    }
                }
            }
            else if (moveTo.getCol() < actual_col && moveTo.getRow() > actual_row){
                while (actual_row < moveTo.getRow() && actual_col > moveTo.getCol() ){
                    if (moveTo.getRow() != actual_row + 1) {
                        if (temp.nextField(Field.Direction.LU).isEmpty()) {
                            actual_row++;
                            actual_col--;
                            temp = temp.nextField(Field.Direction.LU);
                        } else return false;
                    }
                    else {
                        actual_row++;
                        actual_col--;
                    }
                }
            }
            else if (moveTo.getCol() > actual_col && moveTo.getRow() < actual_row){
                while (actual_row > moveTo.getRow() && actual_col < moveTo.getCol() ){

                    if (moveTo.getRow() != actual_row -1 ) {
                        if (temp.nextField(Field.Direction.RD).isEmpty()) {
                            actual_row--;
                            actual_col++;
                            temp = temp.nextField(Field.Direction.RD);
                        } else return false;
                    }
                    else {
                        actual_row--;
                        actual_col++;
                    }
                }
            }
            else if (moveTo.getCol() < actual_col && moveTo.getRow() < actual_row){
                while (actual_row > moveTo.getRow() && actual_col > moveTo.getCol() ){
                    if (moveTo.getRow() != actual_row -1 ) {
                        if (temp.nextField(Field.Direction.LD).isEmpty()) {
                            actual_row--;
                            actual_col--;
                            temp = temp.nextField(Field.Direction.LD);
                        } else return false;
                    }
                    else {
                        actual_row--;
                        actual_col--;
                    }
                }
            }
            if (moveTo.getRow() == actual_row || moveTo.getRow() == actual_row || moveTo.getCol() == actual_col || moveTo.getCol() == actual_col){

                if (moveTo.isEmpty()) {
                    location.remove(this);
                    moveTo.put(this);
                    return true;
                }
                else if (is_oponnent){
                    //moveTo.remove(moveTo.get());
                    moveTo.get().remove(moveTo);
                    location.remove(this);
                    moveTo.put(this);
                    return true;
                }
                else return false;
            }
            else return false;
        }
        else if (moveTo.getCol() == actual_col || moveTo.getRow() == actual_row){

            if (moveTo.getRow() != actual_row) {
                while (moveTo.getRow() > actual_row) {
                    if (moveTo.getRow() != actual_row + 1) {
                        if (temp.nextField(Field.Direction.U).isEmpty()) {
                            actual_row++;
                            temp = temp.nextField(Field.Direction.U);
                        } else return false;
                    }
                    else actual_row++;
                }

                while (moveTo.getRow() < actual_row) {
                    if (moveTo.getRow() != actual_row - 1) {
                        if (temp.nextField(Field.Direction.D).isEmpty()) {
                            actual_row--;
                            temp = temp.nextField(Field.Direction.D);
                        } else return false;
                    }
                    else actual_row--;

                }
            }

            else if (moveTo.getCol() != actual_col) {
                while (moveTo.getCol() > actual_col) {
                    if (moveTo.getCol() != actual_col+1) {
                        if (temp.nextField(Field.Direction.R).isEmpty()) {
                            actual_col++;
                            temp = temp.nextField(Field.Direction.R);
                        } else return false;
                    }
                    else {
                        actual_col++;
                    }
                }


                while (moveTo.getCol() < actual_col) {
                    if (moveTo.getCol() != actual_col-1) {
                        if (temp.nextField(Field.Direction.L).isEmpty()) {
                            actual_col--;
                            temp = temp.nextField(Field.Direction.L);
                        } else return false;
                    }
                    else actual_col--;
                }


            }

            if (moveTo.getRow() == actual_row || moveTo.getRow() == actual_row || moveTo.getCol() == actual_col || moveTo.getCol() == actual_col) {
                if (moveTo.isEmpty()) {
                    location.remove(this);
                    moveTo.put(this);
                    return true;
                }
                else if (is_oponnent){
                    //moveTo.remove(moveTo.get());
                    moveTo.get().remove(moveTo);
                    location.remove(this);
                    moveTo.put(this);
                    return true;
                }
                else return false;
            }

        }
        else return false;


        return false;
    }

    /**
     *  function set image for the figure
     */
    private void setImage(){
        Image image;
        if (this.isWhite())
            //image = new Image(getClass().getResourceAsStream("../images/white_queen.png"));
            image = new Image("ija_proj/images/white_queen.png");

        else
            // image = new Image(getClass().getResourceAsStream("../images/black_queen.png"));
            image = new Image("ija_proj/images/black_queen.png");

        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(45);
        imageview.setFitWidth(40);
        this.button.setGraphic(imageview);

    }


}
