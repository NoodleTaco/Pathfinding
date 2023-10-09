import java.util.*;

public class TilePriorityQueue {
    private PriorityQueue<TileWithPriority> priorityQueue;

    public TilePriorityQueue() 
    {
        this.priorityQueue = new PriorityQueue<>(Comparator.comparingInt(TileWithPriority::getPriority));
    }

    public void add(Tile tile, int priority) 
    {
        TileWithPriority tileWithPriority = new TileWithPriority(tile, priority);

        priorityQueue.add(tileWithPriority);
    }

    public Tile poll()
    {

        TileWithPriority tileWithPriority = priorityQueue.poll();

        if (tileWithPriority != null) {
            return tileWithPriority.getTile();
        }
        return null;
    }

    public boolean isEmpty() 
    {
        return priorityQueue.isEmpty();
    }

    private static class TileWithPriority {
        private Tile tile;
        private int priority;

        public TileWithPriority(Tile tile, int priority) 
        {
            this.tile = tile;
            this.priority = priority;
        }


        public Tile getTile() 
        {
            return tile;
        }

        public int getPriority() 
        {
            return priority;
        }
    }

}