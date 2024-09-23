package ija_proj.common;

import javafx.scene.control.Button;

/**
 * Interface to Disk - Figure which can be placed to the board
 */
public interface Figure {

    /**
     * Enum for types of figures
     */
    enum type {
        tower,
        pawn,
        bishop,
        horse,
        king,
        queen
    }

    boolean move(Field moveTo);

    Figure.type getType();

    void checkFinish();

    boolean mate();

    void setField(Field new_location);

    String getState();

    Field getField();

    boolean isWhite();

    boolean put(Field field);

    boolean remove(Field field);

    void setButton(Button b);

    Button getButton();

    boolean whiteMate();

    boolean blackMate();

}