import java.util.ArrayList;
import bagel.util.*;

public interface Moveable {
    double RIGHT = 0;
    double UP = ((Math.PI)*1.5);
    double LEFT = (Math.PI);
    double DOWN = ((Math.PI)/2);


    /** Methods to move in the 4 cardinal directions
     *
     * @param walls The walls that need to considered for a valid move
     */
    void moveLeft(ArrayList<Wall> walls);
    void moveRight(ArrayList<Wall> walls);
    void moveDown(ArrayList<Wall> walls);
    void moveUp(ArrayList<Wall> walls);

    /** Method that checks for validity of a move
     *
     * @param walls The walls
     * @param movingEntity The future move of an enitity
     * @return boolean Returns true if move is valid
     */
    boolean isLegalMove(ArrayList<Wall> walls, Entity movingEntity);
}
