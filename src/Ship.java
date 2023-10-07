import java.util.Random;
import java.util.ArrayList;
public class Ship {

    private Tile[][] ship;
    private int shipEdgeLength;
    private ArrayList<Tile> neighbors;
    private ArrayList<Tile> deadEnds;

    public Ship()
    {
        this.shipEdgeLength = 20;
        ship = new Tile[20][20];
        neighbors = new ArrayList<Tile>();
        deadEnds = new ArrayList<Tile>();
    }

    public Ship(int shipEdgeLength)
    {
        this.shipEdgeLength = shipEdgeLength;
        ship = new Tile[shipEdgeLength][shipEdgeLength];
    }

    public void formShip()
    {
        for(int row = 0; row < shipEdgeLength; row++)
        {
            for (int col = 0; col < shipEdgeLength; col++)
            {
                ship[row][col] = new Tile();
                ship[row][col].setRow(row);
                ship[row][col].setCol(col);
            }
        }

        Random rand = new Random();
        rand.setSeed(416);     // FOR TESTING , MUST CHANGE WHEN CONDUCTING EXPERIMENTS


        int xStart = rand.nextInt(shipEdgeLength);
        int yStart = rand.nextInt(shipEdgeLength);
        ship[xStart][yStart].setOpen(true);
        checkNeighbors(ship[xStart][yStart]);

        while(!neighbors.isEmpty())
        {
            int randNeighbor = rand.nextInt(neighbors.size());
            if (!ship[neighbors.get(randNeighbor).getRow()][neighbors.get(randNeighbor).getCol()].getTooManyNeighbors())
            {
                ship[neighbors.get(randNeighbor).getRow()][neighbors.get(randNeighbor).getCol()].setOpen(true);
                checkNeighbors(ship[neighbors.get(randNeighbor).getRow()][neighbors.get(randNeighbor).getCol()]);
            }
            neighbors.remove(randNeighbor);
        }
        createDeadEnds();


        
    }

    private void checkNeighbors(Tile tile)
    {
        if(tile.getRow() + 1 < shipEdgeLength && !ship[tile.getRow()][tile.getCol()].getTooManyNeighbors())
        {
            if(ship[tile.getRow() +1][tile.getCol()].getIsNeighbor())
            {
                ship[tile.getRow() +1][tile.getCol()].setTooManyNeighbors();
            }
            else if(!ship[tile.getRow() +1][tile.getCol()].getOpen())
            {
                ship[tile.getRow() +1][tile.getCol()].setIsNeighbor();
                neighbors.add(ship[tile.getRow() +1][tile.getCol()]);
            }
            
        }

        if(tile.getRow() -1 > -1 && !ship[tile.getRow()][tile.getCol()].getTooManyNeighbors())
        {
            if(ship[tile.getRow() -1][tile.getCol()].getIsNeighbor())
            {
                ship[tile.getRow() -1][tile.getCol()].setTooManyNeighbors();
            }
            else if(!ship[tile.getRow() -1][tile.getCol()].getOpen())
            {
                ship[tile.getRow() -1][tile.getCol()].setIsNeighbor();
                neighbors.add(ship[tile.getRow() -1][tile.getCol()]);
            }
        }
        
        if(tile.getCol() -1 > -1 && !ship[tile.getRow()][tile.getCol()].getTooManyNeighbors())
        {
            if(ship[tile.getRow()][tile.getCol() -1].getIsNeighbor())
            {
                ship[tile.getRow()][tile.getCol() -1].setTooManyNeighbors();
            }
            else if(!ship[tile.getRow()][tile.getCol() -1].getOpen())
            {
                ship[tile.getRow()][tile.getCol() -1].setIsNeighbor();
                neighbors.add(ship[tile.getRow()][tile.getCol() -1]);
            }
        }

        if(tile.getCol() +1 < shipEdgeLength && !ship[tile.getRow()][tile.getCol()].getTooManyNeighbors())
        {
            if(ship[tile.getRow()][tile.getCol() +1].getIsNeighbor())
            {
                ship[tile.getRow()][tile.getCol() +1].setTooManyNeighbors();
            }
            else if(!ship[tile.getRow()][tile.getCol() +1].getOpen())
            {
                ship[tile.getRow()][tile.getCol() +1].setIsNeighbor();
                neighbors.add(ship[tile.getRow()][tile.getCol() +1]);
            }
        }
    }



    
    //Prints a visual representation of the ship, used for testing. 
    private void printShip()
    {

        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String white = "\u001B[37m";
        String purple = "\u001B[35m";
        
        for(int row = 0; row < shipEdgeLength; row++)
        {
            for (int col = 0; col < shipEdgeLength; col++)
            {
                
                if(ship[row][col].getOpen())
                {
                    if(ship[row][col].getIsDeadEnd())
                    {
                        System.out.print(yellow + "■ " + reset);
                    }
                    else
                    {
                        System.out.print(white + "■ " + reset);
                    }
                }

                else if(ship[row][col].getTooManyNeighbors())
                {
                    System.out.print(green + "■ " + reset);
                }

                else if(ship[row][col].getIsNeighbor())
                {
                    System.out.print(purple + "■ " + reset);
                }

                else if(!ship[row][col].getOpen())
                {
                    System.out.print(red + "■ " + reset);
                }
            }
            System.out.println();
        }
    }

    private void printNeighborsList()
    {
        for(int i = 0; i < neighbors.size(); i++)
        {
            System.out.print(neighbors.get(i).getRow() + ", " + neighbors.get(i).getCol());
            System.out.println();
        }
        
    }

    private void printDeadEndsList()
    {
        System.out.println("Number of Dead ends: " + deadEnds.size());
        for(int i = 0; i < deadEnds.size(); i++)
        {
            System.out.print(deadEnds.get(i).getRow() + ", " + deadEnds.get(i).getCol());
            System.out.println();
        }
    }

    private void findDeadEnds()
    {
        for(int row = 0; row < shipEdgeLength; row++)
        {
            for (int col = 0; col < shipEdgeLength; col++)
            {
                isDeadEnd(row, col);
            }
        }
        

    }

    private void isDeadEnd(int row, int col)
    {
        if((row < shipEdgeLength && row > -1 && col < shipEdgeLength && col > -1))
        {
            if(ship[row][col].getOpen())
            {
                int count = 0;
                if(row + 1 < shipEdgeLength && ship[row +1][col].getOpen())
                {
                    count ++;
                }

                if(row - 1 > -1 && ship[row -1][col].getOpen())
                {
                    count ++;
                }               
                
                if(col -1 > -1 && ship[row][col-1].getOpen())
                {
                    count ++;
                }

                if(col +1 < shipEdgeLength && ship[row][col+1].getOpen())
                {
                    count ++;
                }           
                if(count == 1)
                {
                    ship[row][col].setIsDeadEnd(true);
                    if(!deadEnds.contains(ship[row][col]))
                    {
                        deadEnds.add(ship[row][col]);
                    }
                }
                else
                {
                    ship[row][col].setIsDeadEnd(false);
                    if(deadEnds.contains(ship[row][col]))
                    {
                        deadEnds.remove(ship[row][col]);
                    }
                }                
            }


        }


    }

    private void createDeadEnds()
    {
        findDeadEnds();

        int startingDeadEnds = deadEnds.size();
        System.out.println("Number of Starting Dead ends: " + startingDeadEnds);
        Random rand = new Random();
        rand.setSeed(416);
        
        //for(int i = 0; i < 1; i++)
        while(deadEnds.size() > startingDeadEnds/2)
        {
            Tile tile = deadEnds.get(rand.nextInt(deadEnds.size()));
            ArrayList<Tile> deadEndNeighbors = new ArrayList<Tile>();
            if(tile.getRow() + 1 < shipEdgeLength && !ship[tile.getRow() +1][tile.getCol()].getOpen())
            {
                deadEndNeighbors.add(ship[tile.getRow()+1][tile.getCol()]);
            }

            if(tile.getRow() - 1 > -1 && !ship[tile.getRow() -1][tile.getCol()].getOpen())
            {
                deadEndNeighbors.add(ship[tile.getRow()-1][tile.getCol()]);
            }               
            
            if(tile.getCol() -1 > -1 && !ship[tile.getRow()][tile.getCol()-1].getOpen())
            {
                deadEndNeighbors.add(ship[tile.getRow()][tile.getCol()-1]);
            }

            if(tile.getCol() +1 < shipEdgeLength && !ship[tile.getRow()][tile.getCol()+1].getOpen())
            {
                deadEndNeighbors.add(ship[tile.getRow()][tile.getCol()+1]);
            }

            
            int randTile = rand.nextInt(deadEndNeighbors.size());
            rand.setSeed(16);
            deadEndNeighbors.get(randTile).setOpen(true);
            tile.setIsDeadEnd(false);
            deadEnds.remove(tile);
            isDeadEnd( deadEndNeighbors.get(randTile).getRow() +1,  deadEndNeighbors.get(randTile).getCol());
            isDeadEnd( deadEndNeighbors.get(randTile).getRow() -1 , deadEndNeighbors.get(randTile).getCol());
            isDeadEnd( deadEndNeighbors.get(randTile).getRow() , deadEndNeighbors.get(randTile).getCol() -1);
            isDeadEnd( deadEndNeighbors.get(randTile).getRow() , deadEndNeighbors.get(randTile).getCol() +1);
            //System.out.println("Tile Coords are "+ tile.getRow() + ", " + tile.getCol());
            //System.out.println ("deadEndNeighbors size: " + deadEndNeighbors.size());
        }
    }


    public int getShipEdgeLength()
    {
        return shipEdgeLength;
    }

    public Tile getShipTile(int row, int col)
    {
        return ship[row][col];
    }

    

    

    //testbed Main
    public static void main(String[] args) throws Exception 
    {
        Ship ship = new Ship();
        ship.formShip();
        System.out.println();
        ship.printShip();

    }
    
}
