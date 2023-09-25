public class Tile {
    private boolean isOpen;
    private boolean isClosed;
    private boolean isNeighbor;
    private boolean tooManyNeighbors;
    private boolean isDeadEnd;

    public Tile ()
    {
        isOpen = false;
        isClosed = true;
        isNeighbor = false;
        tooManyNeighbors = false;
        isDeadEnd = false;
    }

    public boolean getOpen()
    {
        return isOpen;
    }

    public boolean getClosed()
    {
        return isClosed;
    }
    public boolean getIsNeighbor()
    {
        return isNeighbor;
    }
    public boolean getTooManyNeighbors()
    {
        return tooManyNeighbors;
    }
    public boolean getIsDeadEnd()
    {
        return isDeadEnd;
    }

    public void setOpen()
    {
        isOpen = true;
        isClosed = false;
    }

    public void setTooManyNeighbors()
    {
        tooManyNeighbors = true;
    }

    public void setIsNeighbor()
    {
        isNeighbor = true;
    }
}
