public class Tile {
    private boolean isOpen;
    private boolean isClosed;
    private boolean isNeighbor;
    private boolean tooManyNeighbors;
    private boolean isDeadEnd;

    public Tile ()
    {
        this.isOpen = false;
        this.isClosed = true;
        this.isNeighbor = false;
        this.tooManyNeighbors = false;
        this.isDeadEnd = false;
    }
    
    
}
