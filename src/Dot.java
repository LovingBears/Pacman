import bagel.Image;
import bagel.util.Point;

public class Dot extends Entity implements Edible{
    /** The score dots are worth
     */
    public static final int SCORE = 10;
    private static final Image DOTIMG = new Image("res/dot.png");
    private static final int DOTPIXELS = 26;

    /** Constructor
     *
     * @param point A given point (from top left)
     */
    public Dot(Point point) {
        super(point, DOTIMG, DOTPIXELS);
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
