import bagel.util.Point;

import java.util.ArrayList;

public class Level2 extends Level{
    private static final int WINNING_SCORE = 800;
    private static final int FRENZY_DURATION = 1000;
    private GhostRed redGhost;
    private Point redRespawn;
    private GhostBlue blueGhost;
    private Point blueRespawn;
    private GhostGreen greenGhost;
    private Point greenRespawn;
    private GhostPink pinkGhost;
    private Point pinkRespawn;
    private ArrayList<Cherry> cherries = new ArrayList<Cherry>();
    private Pellet pellet;
    private boolean frenzyOn = false;
    private int duration = FRENZY_DURATION;


    /** Method that checks for win requirements for level 2
     *
     * @return boolean Returns true if level is won
     */
    public boolean levelWin(){
        if (this.getPlayer().getScore() >= WINNING_SCORE){
            return true;
        }
        return false;
    }

    /** Method that draws level 2
     *
     * @param currentFrame Given frame counter to calculate when to do certain behaviours
     */
    public void drawLevel(int currentFrame){
        this.drawPlayer();
        this.drawHearts();
        this.drawScore();
        drawGhosts();
        this.drawDots();
        this.drawCherries();
        this.drawPellet();
        this.drawWalls();
        super.getPlayer().switchSprite(currentFrame);
        this.collisionCheck();
        if (duration == 0){
            turnOffFrenzy();
        }
    }

    /** Method that draws the different ghosts
     *
     */
    public void drawGhosts(){
        if (redGhost != null){
            this.redGhost.draw();
        }
        if (blueGhost != null){
            this.blueGhost.draw();
        }
        if (greenGhost != null){
            this.greenGhost.draw();
        }
        if (pinkGhost != null){
            this.pinkGhost.draw();
        }
    }

    /** Method that draws the cherries
     *
     */
    public void drawCherries(){
        Cherry eatenCherry = null;
        for (Cherry cherry: cherries){
            if (cherry.isCollidingPlayer(this.getPlayer())){
                getPlayer().addScore(cherry.SCORE);
                eatenCherry = cherry;
            }
            else {
                cherry.draw();
            }
        }
        cherries.remove(eatenCherry);
    }

    /** Method that draws the pellet and checks the frenzy behaviour as well as when it ends
     *
     */
    public void drawPellet(){
        if (pellet == null){
            duration--;
            return;
        }
        if (pellet.isCollidingPlayer(this.getPlayer())){
            this.turnOnFrenzy();
            pellet = null;
        }
        if (pellet != null){
            pellet.draw();
        }
    }

    /** Method for the behaviour of the consequences of collisions before and after frenzy
     *
     */
    public void collisionCheck(){
        if (!frenzyOn) {
            if (this.getPlayer().isCollidingEnemy(redGhost)) {
                this.redGhost.returnToStart();
            }
            if (this.getPlayer().isCollidingEnemy(blueGhost)) {
                this.blueGhost.returnToStart();
            }
            if (this.getPlayer().isCollidingEnemy(greenGhost)) {
                this.greenGhost.returnToStart();
            }
            if (this.getPlayer().isCollidingEnemy(pinkGhost)) {
                this.pinkGhost.returnToStart();
            }
        }
        else {
            if ((redGhost != null) && this.getPlayer().isCollidingEnemy(redGhost, frenzyOn)) {
                redRespawn = redGhost.getStartingPosition();
                redGhost = null;
            }
            if ((blueGhost != null) && this.getPlayer().isCollidingEnemy(blueGhost, frenzyOn)) {
                blueRespawn = blueGhost.getStartingPosition();
                blueGhost = null;;
            }
            if ((greenGhost != null) && this.getPlayer().isCollidingEnemy(greenGhost, frenzyOn)) {
                greenRespawn = greenGhost.getStartingPosition();
                greenGhost = null;
            }
            if ((pinkGhost != null) && this.getPlayer().isCollidingEnemy(pinkGhost, frenzyOn)) {
                pinkRespawn = pinkGhost.getStartingPosition();
                pinkGhost = null;
            }

        }
    }

    /** Method that enables frenzy behaviour for affected objects
     *
     */
    public void turnOnFrenzy(){
        this.frenzyOn = true;
        getPlayer().startFrenzy();
        redGhost.startFrenzy();
        blueGhost.startFrenzy();
        greenGhost.startFrenzy();
        pinkGhost.startFrenzy();
    }

    /** Method that disables frenzy behaviour for affected objects
     *
     */
    public void turnOffFrenzy(){
        this.frenzyOn = false;
        getPlayer().stopFrenzy();
        respawnGhosts();
        redGhost.stopFrenzy();
        blueGhost.stopFrenzy();
        greenGhost.stopFrenzy();
        pinkGhost.stopFrenzy();
    }

    /** Method that checks for dead ghosts and respawns them
     *
     */
    public void respawnGhosts(){
        if (redRespawn != null){
            redGhost = new GhostRed(redRespawn);
        }
        if (blueRespawn != null){
            blueGhost = new GhostBlue(blueRespawn);
        }
        if (greenRespawn != null){
            greenGhost = new GhostGreen(greenRespawn);
        }
        if (pinkRespawn != null){
            pinkGhost = new GhostPink(pinkRespawn);
        }
    }

    @Override
    /** Adds enemies to their respective class using instanceOf
     *
     */
    public void addEnemy(Enemy enemy) {
        if (enemy instanceof GhostRed){
            redGhost = (GhostRed) enemy;
        }
        if (enemy instanceof GhostBlue){
            blueGhost = (GhostBlue) enemy;
        }
        if (enemy instanceof GhostGreen){
            greenGhost = (GhostGreen) enemy;
        }
        if (enemy instanceof GhostPink){
            pinkGhost = (GhostPink) enemy;
        }
    }

    /** Adds the cherries
     *
     * @param cherry
     */
    public void addCherry(Cherry cherry){
        cherries.add(cherry);
    }

    /** Adds the pellets
     *
     * @param pellet
     */
    public void addPellet(Pellet pellet){
        this.pellet = pellet;
    }

    /** Method that moves all ghosts
     *
     */
    public void moveGhosts(){
        this.moveRed();
        this.moveBlue();
        this.moveGreen();
        this.movePink();
    }

    /** Method that represents the movement of the red ghost
     *
     */
    public void moveRed(){
        if (redGhost == null){}
        else if (redGhost.isMovingRight()){
            redGhost.moveRight(getWalls());
        }
        else {
            redGhost.moveLeft(getWalls());
        }
    }

    /** Method that represents the movement of the blue ghost
     *
     */
    public void moveBlue(){
        if (blueGhost == null){}
        else if (blueGhost.isMovingDown()){
            blueGhost.moveDown(getWalls());
        }
        else {
            blueGhost.moveUp(getWalls());
        }
    }

    /** Method that represents the movement of the green ghost
     *
     */
    public void moveGreen(){
        if (greenGhost == null){}
        else if (greenGhost.isHorizontal()){
            if (greenGhost.isMovingDirection()){
                greenGhost.moveRight(getWalls());
            }
            else {
                greenGhost.moveLeft(getWalls());
            }
        }
        else {
            if (greenGhost.isMovingDirection()){
                greenGhost.moveDown(getWalls());
            }
            else {
                greenGhost.moveUp(getWalls());
            }
        }
    }

    /** Method that represents the movement of the pink ghost
     *
     */
    public void movePink(){
        if (pinkGhost == null){}
        else {
            if (pinkGhost.getDirection() == 0) {
                pinkGhost.moveUp(getWalls());
            }
            if (pinkGhost.getDirection() == 1) {
                pinkGhost.moveDown(getWalls());
            }
            if (pinkGhost.getDirection() == 2) {
                pinkGhost.moveLeft(getWalls());
            }
            if (pinkGhost.getDirection() == 3) {
                pinkGhost.moveRight(getWalls());
            }
        }
    }
}
