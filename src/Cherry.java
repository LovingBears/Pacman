import bagel.Image;
import bagel.util.Point;

public class Cherry extends Entity implements Edible{
    /** The score cherries are worth
     */
    public static final int SCORE = 20;
    private static final Image CHERRY_IMG = new Image("res/cherry.png");
    private static final int CHERRYPIXELS = 25;

    /** Constructor
     *
     * @param point A given point (from top left)
     */
    public Cherry(Point point) {
        super(point, CHERRY_IMG, CHERRYPIXELS);
    }

    /** Function that checks if this edible object is being eaten by the player
     *
     * @param player The player
     * @return boolean Returns true if it is being eaten this frame
     */
    public boolean isCollidingPlayer(Player player) {
        if (this.isColliding(player)) {
            return true;
        }
        return false;
    }

}
