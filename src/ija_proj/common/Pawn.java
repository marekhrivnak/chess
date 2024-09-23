package ija_proj.common;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class Pawn extend Class Disk and specifies figure of type Pawn
 */
public class Pawn extends Disk
{

    /**
     * Constructor.
     *
     * @param  isWhite  if figure is white set true else black
     */
    public Pawn(boolean isWhite) {

        super(isWhite, Figure.type.pawn);
        game.getBoard().myPawns.add(this);
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


        if (this.type == Figure.type.pawn){
            int my_row = this.location.getRow();
            int my_col = this.location.getCol();
            // if pawin is white move it up
            if (this.isWhite()){
                // if pawn hasn't moved yet -> can move 2 fields
                if (this.location.getRow() == 2){
                    if ((moveTo.getRow() != my_row+1) &&  (moveTo.getRow() != my_row+2))
                        return false;
                }
                else
                    if (moveTo.getRow() != my_row+1)
                        return false;

            }
            // if pawn is black move it down
            else {
                // if pawn hasn't moved yet -> can move 2 fields
                if (this.location.getRow() == 7){
                    if ((moveTo.getRow() != my_row-1) &&  (moveTo.getRow() != my_row-2))
                        return false;
                }

                else
                    if (moveTo.getRow() != my_row -1)
                        return false;
            }


            if ((moveTo.getCol() == my_col-1 ) || (moveTo.getCol() == my_col+1 ))
                if (this.isWhite()) {
                    if (moveTo.isEmpty() || moveTo.getRow() != my_row + 1)
                        return false;
                    else if (is_oponnent) {
                        moveTo.get().remove(moveTo);
                        location.remove(this);
                        moveTo.put(this);
                        return true;
                    }
                    else return false;
                }
                else {
                    if (moveTo.isEmpty() || moveTo.getRow() != my_row -1 )
                        return false;
                    else if (is_oponnent) {
                        moveTo.get().remove(moveTo);
                        location.remove(this);
                        moveTo.put(this);
                        return true;
                    }
                    else return false;
                }

            else if (moveTo.getCol() != my_col)   {
                return false;
            }
            if (!moveTo.isEmpty())
                return false;
            if (this.isWhite() && !location.nextField(Field.Direction.U).isEmpty()){
                return false;
            }
            if (!this.isWhite() && !location.nextField(Field.Direction.D).isEmpty()){
                return false;
            }
            location.remove(this);
            moveTo.put(this);
            return true;
        }
        return true;

    }
    /**
     *  function set image for the figure
     */
    private void setImage(){
        Image image;
        if (this.isWhite()){
            //image = new Image(getClass().getClassLoader().getResourceAsStream("ija_proj/images/white_pawn.png"));
            image = new Image("ija_proj/images/white_pawn.png");
        }
        else{
            //image = new Image(getClass().getClassLoader().getResourceAsStream("ija_proj/images/black_pawn.png"));
            image = new Image("ija_proj/images/black_pawn.png");
        }

        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(45);
        imageview.setFitWidth(40);
        this.button.setGraphic(imageview);

    }




}
