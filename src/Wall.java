import bagel.Image;
import bagel.util.Point;

public class Wall extends Entity{
    private static final Image WALLIMG = new Image("res/wall.png");
    private static final int WALLPIXELS = 50;

    /** Constructor
     *
     * @param point A given point (from top left)
     */
    protected Wall(Point point){
        super(point, WALLIMG, WALLPIXELS);
    }
}
