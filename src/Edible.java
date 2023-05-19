public interface Edible {
    /** Method that checks if it is being eaten by the player
     *
     * @param player The player
     * @return boolean Returns true if currently being eaten
     */
    public boolean isCollidingPlayer(Player player);
}
