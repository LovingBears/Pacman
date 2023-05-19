import bagel.Font;
import bagel.util.Point;

import java.util.ArrayList;

public class Level1 extends Level{
    private ArrayList<Enemy> ghosts = new ArrayList<Enemy>();

    /** Method that checks the win conditions for level 1
     *
     * @return boolean Returns true if won
     */
    public boolean levelWin(){
        if (this.getDots().isEmpty()) {
            return true;
        }
        return false;
    }

    /** Overridden method that draws level 1
     *
     * @param currentFrame Given frame counter to calculate when to do certain behaviours
     */
    public void drawLevel(int currentFrame){
        this.drawPlayer();
        this.drawHearts();
        this.drawScore();
        this.drawDots();
        this.drawWalls();
        this.drawGhosts();
        super.getPlayer().switchSprite(currentFrame);
        super.getPlayer().isCollidingEnemy(ghosts);
    }

    /** Method that draws the default ghosts of level 1
     *
     */
    public void drawGhosts(){
        for (Enemy ghost: ghosts){
            ghost.draw();
        }
    }

    /** Getter
     *
     * @return ArrayList<Enemy></Enemy> The array list the ghost are stored in
     */
    public ArrayList<Enemy> getGhosts() {
        return ghosts;
    }

    /** Adds an enemy to the array
     *
     * @param enemy The given enemy
     */
    public void addEnemy(Enemy enemy){
        ghosts.add(enemy);
    }
}
