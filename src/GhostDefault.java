import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public class GhostDefault extends Enemy{
    private static final Image GHOSTIMG = new Image("res/ghostRed.png");
    private static final int GHOSTPIXELS = 25;

    /** Constructor
     *
     * @param point A given point (from top left)
     */
    public GhostDefault(Point point){
        super(point, GHOSTIMG, GHOSTPIXELS);
    }

}
