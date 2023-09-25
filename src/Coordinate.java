public class Coordinate {
    private int row;
    private int col;
    public Coordinate()
    {
        row = 0;
        col = 0;
    }
    public Coordinate(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public void printCoordinate()
    {
        System.out.print(row + "," + col + " ");
    }

    public int getXCoordinate()
    {
        return row;
    }
    public int getYCoordinate()
    {
        return col;
    }
}
