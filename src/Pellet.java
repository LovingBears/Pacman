import bagel.Image;
import bagel.util.Point;

public class Pellet extends Entity implements Edible{
    private static final Image PELLET_IMG = new Image("res/pellet.png");
    private static final int PELLETPIXELS = 25;

    /** Constructor
     *
     * @param point A given point (from top left)
     */
    public Pellet(Point point) {
        super(point, PELLET_IMG, PELLETPIXELS);
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
