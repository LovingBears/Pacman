import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

public abstract class Enemy extends Entity{
    public static final Image FRENZYIMG = new Image("res/ghostFrenzy.png");
    public static final double FRENZY_SPEED = 0.5;

    /** Constructor
     *
     * @param point A given point (from top left)
     * @param sprite The given sprite
     * @param spriteSize Length of a side of the square sprite
     */
    public Enemy(Point point, Image sprite, double spriteSize){
        super(point, sprite, spriteSize);
    }
}
