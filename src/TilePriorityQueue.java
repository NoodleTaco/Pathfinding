import java.util.*;

/**
 * Custom Prioirty Queue used in a*
 */
public class TilePriorityQueue {
    private PriorityQueue<TileWithPriority> priorityQueue;

    /**
     * Constructs a new TilePriorityQueue.
     * Initializes an empty priority queue with a custom comparator based on priority.
     */
    public TilePriorityQueue() 
    {
        this.priorityQueue = new PriorityQueue<>(Comparator.comparingInt(TileWithPriority::getPriority));
    }

    /**
     * Adds a tile to the queue with its priority
     * @param tile the tile to be added.
     * @param priority the priority of the tile.
     */
    public void add(Tile tile, int priority) 
    {
        TileWithPriority tileWithPriority = new TileWithPriority(tile, priority);

        priorityQueue.add(tileWithPriority);
    }

    /**
     * Removes and returns the tile with the highest priority from the priority queue.
     * @return the tile with the highest priority, or null if the queue is empty.
     */
    public Tile poll()
    {

        TileWithPriority tileWithPriority = priorityQueue.poll();

        if (tileWithPriority != null) {
            return tileWithPriority.getTile();
        }
        return null;
    }

    /**
     * Returns if the prioirty is empty
     * @return true if the priority queue is empty, false otherwise.
     */
    public boolean isEmpty() 
    {
        return priorityQueue.isEmpty();
    }

    
    /**
     * Inner class representing a tile with its associated priority.
     */
    private static class TileWithPriority {

        private Tile tile;
        private int priority;

        /**
         * Constructs a TileWithPriority object.
         * @param tile the tile
         * @param priority the priority of the tile
         */
        public TileWithPriority(Tile tile, int priority) 
        {
            this.tile = tile;
            this.priority = priority;
        }

        /**
         * Returns the tile
         * @return the tile.
         */
        public Tile getTile() 
        {
            return tile;
        }

        /**
         * Returns the priority
         * @return the priority.
         */
        public int getPriority() 
        {
            return priority;
        }
    }

}