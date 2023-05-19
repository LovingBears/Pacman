import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Entity {
    private Point point;
    private Image sprite;
    private Rectangle rectangle;

    /** Constructor
     *
     * @param point The initial point of the entity (from top left)
     * @param img   The entity sprite
     * @param spriteSize    The length of the sprite as a square
     */
    public Entity(Point point, Image img, double spriteSize) {
        this.point = point;
        this.sprite = img;
        this.rectangle = new Rectangle(point, spriteSize, spriteSize);
    }

    /** Getters and setters
     * for the above attributes
     */
    public Point getPoint() { return point; }
    public void setPoint(Point point) { this.point = point; }
    public Image getSprite() { return sprite; }
    public void setSprite(Image sprite) { this.sprite = sprite; }
    public Rectangle getRectangle() { return rectangle; }
    public void moveRectangle(Point point) { this.rectangle.moveTo(point); }

    /** Draw function that draws the given entity
     */
    public void draw(){
        sprite.drawFromTopLeft(getPoint().x, getPoint().y);
    }

    /** Checks if this entity is colliding with another entity
     *
     * @param entity The given entity
     * @return boolean Returns true if collision is happening
     */
    protected boolean isColliding(Entity entity){
        return this.rectangle.intersects(entity.getRectangle());
    }
}
