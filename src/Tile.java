public class Tile {
    private boolean isOpen;
    private boolean isNeighbor;
    private boolean tooManyNeighbors;
    private boolean isDeadEnd;
    private int row;
    private int col;

    public Tile ()
    {
        isOpen = false;
        isNeighbor = false;
        tooManyNeighbors = false;
        isDeadEnd = false;
    }

    public boolean getOpen()
    {
        return isOpen;
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

    public void setOpen(boolean open)
    {
        isOpen = open;
    }

    public void setTooManyNeighbors()
    {
        tooManyNeighbors = true;
    }

    public void setIsNeighbor()
    {
        isNeighbor = true;
    }

    public void setIsDeadEnd(boolean deadEnd)
    {
        isDeadEnd = deadEnd;
    }

    public void setRow(int row)
    {
        this.row = row;
    }
    public void setCol(int col)
    {
        this.col = col;
    }
    public int getRow()
    {
        return row;
    }
    public int getCol()
    {
        return col;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Tile other = (Tile)obj;
        if(this.getRow() == other.getRow() && this.getCol() == other.getCol())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    @Override
    public String toString() {
        return "(" + this.row + " , " + this.col + ")";
    }

}
