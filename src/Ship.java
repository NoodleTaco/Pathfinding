import java.util.Random;
import java.util.ArrayList;
public class Ship {

    private Tile[][] ship;
    private int shipEdgeLength;
    private ArrayList<Coordinate> neighbors;

    public Ship()
    {
        this.shipEdgeLength = 10;
        ship = new Tile[10][10];
        neighbors = new ArrayList<Coordinate>();
    }

    public Ship(int shipEdgeLength)
    {
        this.shipEdgeLength = shipEdgeLength;
        ship = new Tile[shipEdgeLength][shipEdgeLength];
    }

    private void formShip()
    {
        for(int row = 0; row < shipEdgeLength; row++)
        {
            for (int col = 0; col < shipEdgeLength; col++)
            {
                ship[row][col] = new Tile();
            }
        }

        Random rand = new Random();
        rand.setSeed(450);      // FOR TESTING , MUST CHANGE WHEN CONDUCTING EXPERIMENTS


        int xStart = rand.nextInt(shipEdgeLength);
        int yStart = rand.nextInt(shipEdgeLength);
        ship[xStart][yStart].setOpen();
        checkNeighbors(xStart, yStart);

        while(!neighbors.isEmpty())
        {
            int randNeighbor = rand.nextInt(neighbors.size());
            if (!ship[neighbors.get(randNeighbor).getXCoordinate()][neighbors.get(randNeighbor).getYCoordinate()].getTooManyNeighbors())
            {
                ship[neighbors.get(randNeighbor).getXCoordinate()][neighbors.get(randNeighbor).getYCoordinate()].setOpen();
                checkNeighbors(neighbors.get(randNeighbor).getXCoordinate(), neighbors.get(randNeighbor).getYCoordinate());
            }
            neighbors.remove(randNeighbor);
        }

    }

    private void checkNeighbors(int row, int col)
    {
        if(row + 1 < shipEdgeLength && !ship[row][col].getTooManyNeighbors())
        {
            if(ship[row +1][col].getIsNeighbor())
            {
                ship[row +1][col].setTooManyNeighbors();
            }
            else if(!ship[row +1][col].getOpen())
            {
                ship[row +1][col].setIsNeighbor();
                neighbors.add(new Coordinate(row +1, col));
            }
            
        }

        if(row -1 > -1 && !ship[row][col].getTooManyNeighbors())
        {
            if(ship[row -1][col].getIsNeighbor())
            {
                ship[row -1][col].setTooManyNeighbors();
            }
            else if(!ship[row -1][col].getOpen())
            {
                ship[row -1][col].setIsNeighbor();
                neighbors.add(new Coordinate(row -1, col));
            }
        }
        
        if(col -1 > -1 && !ship[row][col].getTooManyNeighbors())
        {
            if(ship[row][col -1].getIsNeighbor())
            {
                ship[row][col -1].setTooManyNeighbors();
            }
            else if(!ship[row][col -1].getOpen())
            {
                ship[row][col -1].setIsNeighbor();
                neighbors.add(new Coordinate(row , col -1));
            }
        }

        if(col +1 < shipEdgeLength && !ship[row][col].getTooManyNeighbors())
        {
            if(ship[row][col +1].getIsNeighbor())
            {
                ship[row][col +1].setTooManyNeighbors();
            }
            else if(!ship[row][col +1].getOpen())
            {
                ship[row][col +1].setIsNeighbor();
                neighbors.add(new Coordinate(row , col +1));
            }
        }
    }



    
    //Prints a visual representation of the ship, used for testing. 
    private void printShip()
    {
        for(int row = 0; row < shipEdgeLength; row++)
        {
            for (int col = 0; col < shipEdgeLength; col++)
            {
                if(ship[row][col].getOpen())
                {
                    System.out.print("O ");
                }

                else if(ship[row][col].getTooManyNeighbors())
                {
                    System.out.print("T ");
                }

                else if(ship[row][col].getIsNeighbor())
                {
                    System.out.print("N ");
                }

                else if(ship[row][col].getClosed())
                {
                    System.out.print("C ");
                }
            }
            System.out.println();
        }
    }

    private void printNeighborsList()
    {
        for(int i = 0; i < neighbors.size(); i++)
        {
            neighbors.get(i).printCoordinate();
        }
        
    }

    //testbed Main
    public static void main(String[] args) throws Exception 
    {
        Ship ship = new Ship();
        ship.formShip();
        ship.printShip();
        ship.printNeighborsList();
    }
    
}
