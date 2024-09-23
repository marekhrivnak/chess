package ija_proj.common;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Class Horse extend Class Disk and specifies figure of type Horse
 */
public class Horse extends Disk {

    /**
     * Constructor.
     *
     * @param  isWhite  if figure is white set true else black
     */
    public Horse(boolean isWhite) {
        super(isWhite, Figure.type.horse);
        game.getBoard().myHorses.add(this);
        setImage();
    }


    /**
     * Function move Figure to the Field if it is possible
     *
     * @param moveTo where figure will be moved
     * @return true if operation ends successfully
     */
    @Override
    public boolean move(Field moveTo) {
        boolean is_oponnent = false;
        if (!moveTo.isEmpty()) {
            if (moveTo.get().isWhite() == this.isWhite()) {
                is_oponnent = false;
            } else is_oponnent = true;
        }

        int actual_col = this.location.getCol();
        int actual_row = this.location.getRow();

        if ((moveTo.getCol() == actual_col-2 && moveTo.getRow() == actual_row-1) ||
                (moveTo.getCol() == actual_col-2 && moveTo.getRow() == actual_row+1) ||
                (moveTo.getCol() == actual_col-1 && moveTo.getRow() == actual_row-2) ||
                (moveTo.getCol() == actual_col-1 && moveTo.getRow() == actual_row+2) ||
                (moveTo.getCol() == actual_col+2 && moveTo.getRow() == actual_row-1) ||
                (moveTo.getCol() == actual_col+2 && moveTo.getRow() == actual_row+1) ||
                (moveTo.getCol() == actual_col+1 && moveTo.getRow() == actual_row-2) ||
                (moveTo.getCol() == actual_col+1 && moveTo.getRow() == actual_row+2) ) {

            if (moveTo.isEmpty()){
                 location.remove(this);
                 moveTo.put(this);
                 return true;
            }
            else if (is_oponnent) {
                //moveTo.remove(moveTo.get());
                moveTo.get().remove(moveTo);
                location.remove(this);
                moveTo.put(this);
                return true;
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
        if (this.isWhite()){
            image = new Image("ija_proj/images/white_horse.png");

        }
        else{
            image = new Image("ija_proj/images/black_horse.png");

        }

        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(45);
        imageview.setFitWidth(40);
        this.button.setGraphic(imageview);

    }




}
