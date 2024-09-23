package ija_proj.common;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class Tower extend Class Disk and specifies figure of type Tower
 */
public class Tower extends Disk {


    /**
     * Constructor.
     *
     * @param  isWhite  if figure is white set true else black
     */
    public Tower(boolean isWhite) {
        super(isWhite, Figure.type.tower);
        game.getBoard().myTowers.add(this);
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

        int new_row = location.getRow();
        int new_col = location.getCol();
        Field temp = location;
        boolean is_oponnent = false;
        if (!moveTo.isEmpty()) {
            if (moveTo.get().isWhite() == this.isWhite()) {
                is_oponnent = false;
            } else is_oponnent = true;
        }

        assert moveTo.getCol()>=1 : "Errow! Column cannot be less than 1.";
        assert moveTo.getCol()<=8 : "Error! Column cannot be more than size of Board.";
        assert moveTo.getRow()>=1 : "Errow! Row cannot be less than 1.";
        assert moveTo.getRow()<=8 : "Error! Row cannot be more than size of Board." ;

        if (this.location == null){
            return false;
        }



        if (( moveTo.getRow() != new_row) && (moveTo.getCol() != new_col) ) {
            return false;
        }



        if (moveTo.getRow() != new_row) {
            while (moveTo.getRow() > new_row) {
                if (moveTo.getRow() != new_row + 1) {
                    if (temp.nextField(Field.Direction.U).isEmpty()) {
                        new_row++;
                        temp = temp.nextField(Field.Direction.U);
                    } else return false;
                }
                else new_row++;
            }

            while (moveTo.getRow() < new_row) {
                if (moveTo.getRow() != new_row - 1) {
                    if (temp.nextField(Field.Direction.D).isEmpty()) {
                        new_row--;
                        temp = temp.nextField(Field.Direction.D);
                    } else return false;
                }
                else new_row--;

            }
        }

        else if (moveTo.getCol() != new_col) {
            while (moveTo.getCol() > new_col) {
                if (moveTo.getCol() != new_col+1) {
                    if (temp.nextField(Field.Direction.R).isEmpty()) {
                        new_col++;
                        temp = temp.nextField(Field.Direction.R);
                    } else return false;
                }
                else {
                    new_col++;
                }
            }


            while (moveTo.getCol() < new_col) {
                if (moveTo.getCol() != new_col-1) {
                    if (temp.nextField(Field.Direction.L).isEmpty()) {
                        new_col--;
                        temp = temp.nextField(Field.Direction.L);
                    } else return false;
                }
                else new_col--;
            }


        }

        if (moveTo.getRow() == new_row || moveTo.getRow() == new_row || moveTo.getCol() == new_col || moveTo.getCol() == new_col) {
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

        return false;
    }

    /**
     *  function set image for the figure
     */
    private void setImage(){
        Image image;
        if (this.isWhite()){
            image = new Image("ija_proj/images/white_tower.png");
            // Image image = new Image(getClass().getResourceAsStream("../images/white_tower.png"));

        }
        else{
            image = new Image("ija_proj/images/black_tower.png");
            // Image image = new Image(getClass().getResourceAsStream("../images/black_tower.png"));
        }

        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(45);
        imageview.setFitWidth(40);
        this.button.setGraphic(imageview);

    }



}
