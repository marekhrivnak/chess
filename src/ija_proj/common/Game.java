package ija_proj.common;

import ija_proj.game.Board;
import ija_proj.game.Simulator;
import ija_proj.gui.BoardCreator;


/**
 * Interface for every new created game
 */
public interface Game {

    /**
     * Moves figure to the field.
     * @param figure Figure to be moved.
     * @param field Field where Figure will be moved.
     * @param real Set true if it is real move not just "check".
     * @return Success of action.
     */
    boolean move(Figure figure, Field field,boolean real);

    /**
     * Do undo of last move.
     * @param real Set true if it is real undo not just "check".
     * @return Success of action.
     */
    boolean undo(boolean real);

    /**
     * Creates new class for simulating loaded file.
     * @param filename Name of the file loaded.
     * @param notation Set true if notaition is long.
     * @return Created class.
     */
    Simulator simulate(String filename, boolean notation);

    /**
     * Getter for board of the game.
     * @return Board used by game.
     */
    Board getBoard();

    /**
     * Getter for line from the loaded file.
     * @return Line of the loaded file.
     */
    String getLine();

    /**
     * Decrements linecounter.
     */
    void backLine();

    /**
     * Setter for object BoardCreator.
     * @param myGui Object to be saved.
     */
    void setBoardCreator(BoardCreator myGui);

    /**
     * Getter for object BoardCreator.
     * @return Object BoardCreator.
     */
    BoardCreator getBoardCreator();

    /**
     * Setter for mode of the game.
     *
     * @param mode True if simulation and false if user game.
     */
    void setMode(boolean mode);

    /**
     * Getter for mode of the game.
     * @return Mode of the game.
     */
    boolean getMode();

    /**
     * Do redo of last move.
     * @return Success of action.
     */
    boolean redo();

    /**
     * Set value to AfterSimulation if it cames right after simulation.
     * @param bool Value to set - if true than after.
     */
    void setAfterSimulation(boolean bool);

    /**
     * Clear all stacks in game.
     */
    void clearStacks();

}