package ija_proj.common;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class King extend Class Disk and specifies figure of type King
 */
public class King extends Disk {


    /**
     * Constructor.
     *
     * @param  isWhite  if figure is white set true else black
     */
    public King(boolean isWhite) {
        super(isWhite, Figure.type.king);
        game.getBoard().myKings.add(this);
        setImage();
    }


    /**
     * Function controll all moves
     *
     * @return true if is no checkmate and false if it is
     */
    public boolean checkAllMoves(){
        Game myGame = game.getBoard().myGame;
        Field my_pos = this.location;
        boolean succ = false;
        if (myGame.move(this,my_pos.nextField(Field.Direction.U),false)) {
            if (this.mate()) {
                System.out.println("VICTORY1!");
            }
            else succ = true;
            System.out.println("VICTORYa");
            game.getBoard().myGame.undo(false);

        }
        if (myGame.move(this,my_pos.nextField(Field.Direction.RU),false)) {
            if (this.mate()) {
                System.out.println("VICTORY!");
            }
            else succ = true;
            game.getBoard().myGame.undo(false);

        }
        if (myGame.move(this,my_pos.nextField(Field.Direction.LU),false)) {
            if (this.mate()) {
                System.out.println("VICTORY!");
            }
            else succ = true;
            game.getBoard().myGame.undo(false);

        }
        if (myGame.move(this,my_pos.nextField(Field.Direction.R),false)){
            if (this.mate()) {
            System.out.println("VICTORY!");
            }
            else succ = true;
            game.getBoard().myGame.undo(false);

        }
        if (myGame.move(this,my_pos.nextField(Field.Direction.L),false)){
            if (this.mate()) {
                System.out.println("VICTORY!");
            }
            else succ = true;
            game.getBoard().myGame.undo(false);

        }
        if (myGame.move(this,my_pos.nextField(Field.Direction.D),false)){
            if (this.mate()) {
                System.out.println("VICTORY!");
            }
            else succ = true;
            game.getBoard().myGame.undo(false);

        }
        if (myGame.move(this,my_pos.nextField(Field.Direction.RD),false)){
            if (this.mate()) {
                System.out.println("VICTORY!");
            }
            else succ = true;
            game.getBoard().myGame.undo(false);

        }
        if (myGame.move(this,my_pos.nextField(Field.Direction.LD),false)){
            if (this.mate()) {
                System.out.println("VICTORY!");
            }
            else succ = true;
            game.getBoard().myGame.undo(false);

        }


        return succ;
    }

    /**
     * Function check if it is possible to move to the set Field
     *
     * @param moveTo where figure will be moved
     * @return true if operation ends successfully
     */
    private boolean myMove(Field moveTo){
        if (moveTo == null) return false;
        int actual_col = this.location.getCol();
        int actual_row = this.location.getRow();

        if (moveTo.getRow() > actual_row + 1 || moveTo.getCol() > actual_col + 1 || moveTo.getRow() < actual_row - 1 || moveTo.getCol() < actual_col - 1) {

            return false;
        } else {
            return true;
        }
    }


    /**
     * Function move Figure to the Field if it is possible
     *
     * @param moveTo where figure will be moved
     * @return true if operation ends successfully
     */
    @Override
    public boolean move(Field moveTo) {
        if (moveTo == null) return false;
        boolean is_oponnent = false;
        if (!moveTo.isEmpty()) {
            if (moveTo.get().isWhite() == this.isWhite()) {
                is_oponnent = false;
            } else is_oponnent = true;
        }

        if (myMove(moveTo)) {
            if (moveTo.isEmpty()) {
                location.remove(this);
                moveTo.put(this);
                return true;
            } else if (is_oponnent) {
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
        if (this.isWhite())
            image = new Image("ija_proj/images/white_king.png");
            // image = new Image(getClass().getResourceAsStream("ija_proj/images/white_king.png"));
        else
            image = new Image("ija_proj/images/black_king.png");
        //  image = new Image(getClass().getResourceAsStream("ija_proj/images/black_king.png"));

        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(45);
        imageview.setFitWidth(40);
        this.button.setGraphic(imageview);

    }
}
