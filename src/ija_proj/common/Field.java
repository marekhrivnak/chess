package ija_proj.common;


import javafx.scene.layout.StackPane;

/**
 * Interface Field - every Field is placed on Board. Field knows it's neighbors.
 */
public interface Field {

    /**
     * Enum represents the way to move on the Board
     */
    enum Direction  {
        D,
        L,
        LD,
        LU,
        R,
        RD,
        RU,
        U
    }

    /**
     * getter for column
     *
     * @return column of the Field
     */
    int getCol();

    /**
     * getter for tow
     *
     * @return row of the Field
     */
    int getRow();

    /**
     *
     *
     * @return disk placed on the Field
     */
    Disk getDisk();


    /**
     * ADD field in the direction dirs
     *
     * @param dirs Direction where field is add.
     * @param field Adding field.
     */
    void addNextField(Field.Direction dirs, Field field);


    /**
     * Return field in direction
     * @param dirs Direction of field
     * @return Field in set direction.
     */
    Field nextField(Field.Direction dirs);


    /**
     * Puts Disk on the Field
     * @param disk Putting disk.
     * @return Success of action.
     */
    boolean put(Disk disk);


    /**
     * Remove selected disk from the Field
     * @param disk Disk to be removed
     * @return Success of action.
     */
    boolean remove(Disk disk);


    /**
     * Test if the Field is empty
     * @return Success of action.
     */
    boolean isEmpty();

    //Vrací kámen, který leží na poli.

    /**
     * Return Disk on the Field
     * @return Disk on the Field.
     */
    Disk get();

    /**
     * setter for Pane
     */
    void setPane();

    /**
     *  method logic set the action-handler for Field
     */
    void logic();

    /**
     * getter for Pane
     *
     * @return Retruns pane used for Field
     */
    StackPane getPane();

}
