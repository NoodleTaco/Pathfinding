import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;
import java.lang.Math;

/**
 * Represents one testing environment
 * An instance of this class holds a randomly generated ship and spawn locations of the experiment variables
 */
public class ExperimentController {

    private Ship ship;

    private Tile bot;

    private Tile button;

    private Tile botStartTile;
    
    private Tile buttonStartTile;

    private Tile fireStartTile;

    private ArrayList<Tile> fireTiles;


    private ArrayList<Tile> botPath;

    private double q;

    /**
     * Constructs an ExperimentController with a given q value.
     * @param q The q value used in the experiment.
     */
    public ExperimentController(double q)
    {
        ship = new Ship();
        fireTiles = new ArrayList<Tile>();
        botPath = new ArrayList<Tile>();
        this.q = q;
    }

    /**
     * Constructs an ExperimentController with a given q value and ship edge length.
     * @param q The q value used in the experiment.
     * @param shipEdgeLength The edge length of the ship to be generated.
     */
    public ExperimentController(double q, int shipEdgeLength)
    {
        ship = new Ship(shipEdgeLength);
        fireTiles = new ArrayList<Tile>();
        botPath = new ArrayList<Tile>();
        this.q = q;
    }

    /**
     * Forms ship and generates the starting positions of the bot, button, and fire
     */
    public void spawn()
    {
        
        ship.formShip();

        Random rand = new Random();

        while(true)
        {
            bot = ship.getShipTile(rand.nextInt(ship.getShipEdgeLength()), rand.nextInt(ship.getShipEdgeLength()));

            if(bot.getOpen())
            {
                botStartTile = bot;
                break;
            }
            
        }

        
        while(true)
        {
            button = ship.getShipTile(rand.nextInt(ship.getShipEdgeLength()), rand.nextInt(ship.getShipEdgeLength()));
            if (!bot.equals(button) && button.getOpen())
            {
                buttonStartTile = button;
                break;
            }
        }

        while(true)
        {
            Tile fireStart = ship.getShipTile(rand.nextInt(ship.getShipEdgeLength()), rand.nextInt(ship.getShipEdgeLength()));
            if(!fireStart.equals(bot) && !fireStart.equals(button) && fireStart.getOpen())
            {
                fireStartTile = fireStart;
                fireTiles.add(fireStart);
                break;
            }
        }

        /* 
        System.out.println("Bot Starting Position: " + bot.toString());
        System.out.println("Button Starting Position: " + button.toString());
        System.out.println("Fire Starting Position: " + fireTiles.get(0).toString());
        */
    }
    
    /**
     * Generates the bot path avoiding any tiles on fire
     * BFS algorithim 
     */
    public void bfsAvoidFire()
    {
        Queue<Tile> queue = new LinkedList<>();
        Set<Tile> visited = new HashSet<>();
        Map<Tile, Tile> parent = new HashMap<>();
        Set<Tile> botNeighbors = new HashSet<Tile>();

        botPath.clear();

        queue.add(bot);
        visited.add(bot);
        parent.put(bot, null);
        

        while(!queue.isEmpty())
        {
            Tile curr = queue.poll();

            if(curr.equals(button))
            {
                break;
            }

            fillNeighborsSet(curr, botNeighbors);

            for(Tile tile : botNeighbors)
            {
                if(!visited.contains(tile) && !fireTiles.contains(tile))
                {
                    queue.add(tile);
                    visited.add(tile);
                    parent.put(tile, curr);
                }
            }

            botNeighbors.clear();

        }

        if(!parent.containsKey(button))
        {
            return;
        }

        Tile currentTile = button;

        while(currentTile != null)
        {
            botPath.add(currentTile);
            currentTile = parent.get(currentTile);
        }

        

        Collections.reverse(botPath);

        botPath.remove(0);

    }

    /**
     * Calculates the distance from a tile to the button
     * BFS algorithim 
     * @param startTile The starting tile for the calculation.
     * @return The distance from the startTile to the button.
     */
    public int distToButton(Tile startTile)
    {
        Queue<Tile> queue = new LinkedList<>();
        Set<Tile> visited = new HashSet<>();
        Map<Tile, Tile> parent = new HashMap<>();
        Set<Tile> openNeighbors = new HashSet<Tile>();

        queue.add(startTile);
        visited.add(startTile);
        parent.put(startTile, null);
        

        while(!queue.isEmpty())
        {
            Tile curr = queue.poll();

            if(curr.equals(button))
            {
                break;
            }

            fillNeighborsSet(curr, openNeighbors);

            for(Tile tile : openNeighbors)
            {
                if(!visited.contains(tile))
                {
                    queue.add(tile);
                    visited.add(tile);
                    parent.put(tile, curr);
                }
            }

            openNeighbors.clear();

        }

        int count = 0;

        Tile currentTile = button;

        while(currentTile != null)
        {
            count++;
            currentTile = parent.get(currentTile);
        }

        return count;
    }

    
    /**
     * Generates the bot path avoiding any tiles on fire or adjacent to fire tiles
     * BFS algorithim 
     */
    public void bfsAvoidFireNeighbor()
    {
        Queue<Tile> queue = new LinkedList<>();
        Set<Tile> visited = new HashSet<>();
        Map<Tile, Tile> parent = new HashMap<>();
        Set<Tile> botNeighbors = new HashSet<Tile>();
        Set<Tile> fireNeighbors = new HashSet<Tile>();
        botPath.clear();

        queue.add(bot);
        visited.add(bot);
        parent.put(bot, null);

        


        for(Tile tile: fireTiles)
        {
            fillNeighborsSet(tile, fireNeighbors);
        }
        

        while(!queue.isEmpty())
        {
            Tile curr = queue.poll();

            if(curr.equals(button))
            {
                break;
            }

            fillNeighborsSet(curr, botNeighbors);

            for(Tile tile : botNeighbors)
            {
                if(!visited.contains(tile) && !fireTiles.contains(tile) && !fireNeighbors.contains(tile))
                {
                    queue.add(tile);
                    visited.add(tile);
                    parent.put(tile, curr);
                }
            }

            botNeighbors.clear();

        }

        if(!parent.containsKey(button))
        {
            bfsAvoidFire();
            return;
        }

        Tile currentTile = button;

        while(currentTile != null)
        {
            botPath.add(currentTile);
            currentTile = parent.get(currentTile);
        }

        

        Collections.reverse(botPath);

        botPath.remove(0);
    }

    /**
     * Generates the bot path avoiding any tiles on fire or adjacent to fire tiles
     * A* algorithim
     */
    private void aStarAvoidFireNeighbors()
    {
        TilePriorityQueue fringe = new TilePriorityQueue();
        Map<Tile, Integer> distTo = new HashMap<>();
        Map<Tile, Tile> parent = new HashMap<>();
        Set<Tile> botNeighbors = new HashSet<Tile>();
        Set<Tile> fireNeighbors = new HashSet<Tile>();

        botPath.clear();

        fringe.add(bot, h(bot));
        distTo.put(bot, 0);
        parent.put(bot, null);

        for(Tile tile: fireTiles)
        {
            fillNeighborsSet(tile, fireNeighbors);
        }

        while(!fringe.isEmpty())
        {
            Tile curr = fringe.poll();
            if(curr.equals(button))
            {
                break;
            }

            fillNeighborsSet(curr, botNeighbors);

            for(Tile tile : botNeighbors)
            {
                if(!fireTiles.contains(tile) && !fireNeighbors.contains(tile))
                {
                    int tempDist = distTo.get(curr) + 1;

                    if(distTo.containsKey(tile))
                    {
                        if(distTo.get(tile) > tempDist)
                        {
                            distTo.put(tile, tempDist);
                            parent.put(tile, curr);
                            fringe.add(tile, distTo.get(tile)  + h(tile));
                        }
                    }
                    else
                    {
                        distTo.put(tile, tempDist);
                        parent.put(tile, curr);
                        fringe.add(tile, distTo.get(tile)  + h(tile));
                    }
                }
                
            }
            botNeighbors.clear();
        }
        if(!parent.containsKey(button))
        {
            aStarAvoidFire();
            return;
        }
        
        Tile currentTile = button;

        while(currentTile != null)
        {

            botPath.add(currentTile);
            currentTile = parent.get(currentTile);
        }

        Collections.reverse(botPath);

        botPath.remove(0);

    }


    
    /**
     * Generates the bot path avoiding any tiles on fire
     * BFS algorithim 
     */
    private void aStarAvoidFire()
    {
        TilePriorityQueue fringe = new TilePriorityQueue();
        Map<Tile, Integer> distTo = new HashMap<>();
        Map<Tile, Tile> parent = new HashMap<>();
        Set<Tile> botNeighbors = new HashSet<Tile>();

        botPath.clear();

        fringe.add(bot, h(bot));
        distTo.put(bot, 0);
        parent.put(bot, null);

        while(!fringe.isEmpty())
        {
            Tile curr = fringe.poll();
            if(curr.equals(button))
            {
                break;
            }

            fillNeighborsSet(curr, botNeighbors);

            for(Tile tile : botNeighbors)
            {
                if(!fireTiles.contains(tile))
                {
                    int tempDist = distTo.get(curr) + 1;

                    if(distTo.containsKey(tile))
                    {
                        if(distTo.get(tile) > tempDist)
                        {
                            distTo.put(tile, tempDist);
                            parent.put(tile, curr);
                            fringe.add(tile, distTo.get(tile)  + h(tile));
                        }
                    }
                    else
                    {
                        distTo.put(tile, tempDist);
                        parent.put(tile, curr);
                        fringe.add(tile, distTo.get(tile)  + h(tile));
                    }
                }
                
            }
            botNeighbors.clear();
        }
        if(!parent.containsKey(button))
        {
            return;
        }
        
        Tile currentTile = button;

        while(currentTile != null)
        {

            botPath.add(currentTile);
            currentTile = parent.get(currentTile);
        }

        Collections.reverse(botPath);

        botPath.remove(0);

    }

    

    /**
     * Heuristic function for the A* algorithm.
     * @param tile The tile for which to calculate the heuristic value.
     * @return The heuristic value for the tile.
     */
    private int h(Tile tile)
    {
        return Math.abs(tile.getRow() - button.getRow()) + Math.abs(tile.getCol() - button.getCol());
    }


    /**
     * Adds open tiles adjacent to a select tile to a list
     * @param tile The tile whose neighbors are being considered
     * @param list The list where tile objects are being added 
     */
    private void fillNeighborsList(Tile tile, ArrayList<Tile> list)
    {
        if(tile.getRow() + 1 < ship.getShipEdgeLength() && ship.getShipTile(tile.getRow() + 1, tile.getCol()).getOpen() && !list.contains(ship.getShipTile(tile.getRow() + 1, tile.getCol()))) 
        {
            list.add(ship.getShipTile(tile.getRow() + 1, tile.getCol()));
        }

        if(tile.getRow() - 1 > -1 && ship.getShipTile(tile.getRow() - 1, tile.getCol()).getOpen() && !list.contains(ship.getShipTile(tile.getRow() - 1, tile.getCol())))
        {
            list.add(ship.getShipTile(tile.getRow() - 1, tile.getCol()));
        }

        if(tile.getCol() + 1 < ship.getShipEdgeLength() && ship.getShipTile(tile.getRow() , tile.getCol() + 1).getOpen() && !list.contains(ship.getShipTile(tile.getRow() , tile.getCol() + 1)))
        {
            list.add(ship.getShipTile(tile.getRow(), tile.getCol() + 1));
        }

        if(tile.getCol() - 1 > -1 && ship.getShipTile(tile.getRow() , tile.getCol() -1).getOpen() && !list.contains(ship.getShipTile(tile.getRow() , tile.getCol() - 1)))
        {
            list.add(ship.getShipTile(tile.getRow(), tile.getCol() -1));
        }
        
    }

    /**
     * Adds open tiles adjacent to a select tile to a set
     * @param tile The tile whose neighbors are being considered
     * @param set The set where tile objects are being added 
     */
    private void fillNeighborsSet(Tile tile, Set<Tile> set)
    {
        if(tile.getRow() + 1 < ship.getShipEdgeLength() && ship.getShipTile(tile.getRow() + 1, tile.getCol()).getOpen()) 
        {
            set.add(ship.getShipTile(tile.getRow() + 1, tile.getCol()));
        }

        if(tile.getRow() - 1 > -1 && ship.getShipTile(tile.getRow() - 1, tile.getCol()).getOpen() )
        {
            set.add(ship.getShipTile(tile.getRow() - 1, tile.getCol()));
        }

        if(tile.getCol() + 1 < ship.getShipEdgeLength() && ship.getShipTile(tile.getRow() , tile.getCol() + 1).getOpen() )
        {
            set.add(ship.getShipTile(tile.getRow(), tile.getCol() + 1));
        }

        if(tile.getCol() - 1 > -1 && ship.getShipTile(tile.getRow() , tile.getCol() -1).getOpen() )
        {
            set.add(ship.getShipTile(tile.getRow(), tile.getCol() -1));
        }
    }


    /**
     * Conducts one run of the experiment with Bot One 
     * @return The result of the bot one experiment (1 for success, 0 for failure).
     */
    public int botOneExperiment()
    {
        //System.out.println("**Bot 1 Experiment**");

        bfsAvoidFire();

        if(botPath.isEmpty())
        {
            //System.out.println("Button Unreachable");
            return 0;
        }
        //printShip();
        while(true)
        {
            if(fireTiles.contains(bot))
            {
                //System.out.println("destruction");
                return 0;
            }

            bot = botPath.remove(0);

            if(bot.equals(button))
            {
                //System.out.println("YIPPPEEEE");
                return 1;
            }

            if(fireTiles.contains(bot))
            {
                //System.out.println("destruction");
                return 0;
            }

            fireSpread();


            //System.out.println();

            //printShip();

        }
    }

    /**
     * Conducts one run of the experiment with Bot Two 
     * @return The result of the bot one experiment (1 for success, 0 for failure).
     */
    public int botTwoExperiment()
    {
        //System.out.println("**Bot 2 Experiment**");
        //printShip();
        while(true)
        {
            bfsAvoidFire();

            if(botPath.isEmpty())
            {
                //System.out.println("Button Unreachable");
                return 0;
            }
            
            if(fireTiles.contains(bot))
            {
                //System.out.println("destruction");
                return 0;
            }

            bot = botPath.remove(0);

            if(bot.equals(button))
            {
                //System.out.println("YIPPPEEEE");
                return 1;
            }

            if(fireTiles.contains(bot))
            {
                //System.out.println("destruction");
                return 0;
            }

            fireSpread();
             
            //System.out.println();

            //printShip();
            
        }
    }

    /**
     * Conducts one run of the experiment with Bot Three 
     * @return The result of the bot one experiment (1 for success, 0 for failure).
     */
    public int botThreeExperiment()
    {
        //System.out.println("**Bot 3 Experiment**");
        //printShip();

        while(true)
        {
            bfsAvoidFireNeighbor();

            if(botPath.isEmpty())
            {
                //System.out.println("Button Unreachable");
                return 0;
            }
            
            if(fireTiles.contains(bot))
            {
                //System.out.println("destruction");
                return 0;
            }

            bot = botPath.remove(0);

            if(bot.equals(button))
            {
                //System.out.println("YIPPPEEEE");
                return 1;
            }

            if(fireTiles.contains(bot))
            {
                //System.out.println("destruction");
                return 0;
            }

            fireSpread();

            //System.out.println();

            //printShip();

        }
    }

    /**
     * Conducts one run of the experiment with Bot Four 
     * @return The result of the bot one experiment (1 for success, 0 for failure).
     */
    public int botFourExperiment()
    {
        //System.out.println("**Bot 4 Experiment**");
        //printShip();

        while(true)
        {
            aStarAvoidFireNeighbors();

            if(botPath.isEmpty())
            {
                //System.out.println("Button Unreachable");
                return 0;
            }
            
            if(fireTiles.contains(bot))
            {
                //System.out.println("destruction");
                return 0;
            }

            bot = botPath.remove(0);

            if(bot.equals(button))
            {
                //System.out.println("YIPPPEEEE");
                return 1;
            }

            if(fireTiles.contains(bot))
            {
                //System.out.println("destruction");
                return 0;
            }

            fireSpread();

            //System.out.println();

            //printShip();

        }
    }
    
    /**
     * Conducts one run of the experiment with Bot Five 
     * @return The result of the bot one experiment (1 for success, 0 for failure).
     */
    public int botFiveExperiment()
    {
        if(isBotCloser())
        {
            return 1;
        }
        else
        {
            while(true)
            {
                aStarAvoidFireNeighbors();

                if(botPath.isEmpty())
                {
                    //System.out.println("Button Unreachable");
                    return 0;
                }
                
                if(fireTiles.contains(bot))
                {
                    //System.out.println("destruction");
                    return 0;
                }

                bot = botPath.remove(0);

                if(bot.equals(button))
                {
                    //System.out.println("YIPPPEEEE");
                    return 1;
                }

                if(fireTiles.contains(bot))
                {
                    //System.out.println("destruction");
                    return 0;
                }

                fireSpread();

                //System.out.println();

                //printShip();

            }
        }
    }

    /**
     * Checks if botStartTile is closer to the button than fireStartTile.
     * @return true if botStartTile is closer, false otherwise.
     */
    private boolean isBotCloser()
    {
        return distToButton(botStartTile) <= distToButton(fireStartTile);
    }


    /**
     * Progresses the spread of the fire by one time tick 
     */
    private void fireSpread()
    {
        ArrayList<Tile> fireNeighbors = new ArrayList<Tile>();
        Set<Tile> fireTilesToBeAdded = new HashSet<>();
        

        for(Tile tile: fireTiles)
        {
            fillNeighborsList(tile, fireNeighbors);
        }
        
        for(Tile tile: fireNeighbors)
        {
            if(!fireTiles.contains(tile) && !tile.equals(button))
            {
                double imScared = 1-q;
                double fireChance = 1 - Math.pow(imScared, numFireNeighbors(tile));

                if(probabilityRoll(fireChance))
                {
                    fireTilesToBeAdded.add(tile);
                }
            }
        }


        if(!fireTilesToBeAdded.isEmpty())
        {
            Iterator<Tile> iterator = fireTilesToBeAdded.iterator(); 
            while(iterator.hasNext())
            {
                fireTiles.add(iterator.next());
            }
        }


    }

    /**
     * Gives the number of tiles on fire that neighbor a given tile
     * @param tile tile being checked
     * @return the number of fire tiles neighboring tile
     */
    private int numFireNeighbors(Tile tile )
    {
        int count = 0;
        if(tile.getRow() + 1 < ship.getShipEdgeLength() && fireTiles.contains(ship.getShipTile(tile.getRow() + 1, tile.getCol()))) 
        {
            count ++;
        }

        if(tile.getRow() - 1 > -1 && fireTiles.contains(ship.getShipTile(tile.getRow() - 1, tile.getCol())))
        {
            count ++;
        }

        if(tile.getCol() + 1 < ship.getShipEdgeLength() && fireTiles.contains(ship.getShipTile(tile.getRow() , tile.getCol() + 1)))
        {
            count ++;
        }

        if(tile.getCol() - 1 > -1 && fireTiles.contains(ship.getShipTile(tile.getRow() , tile.getCol() - 1)))
        {
            count ++;
        }
        
        return count;
    }

    /**
     * Performs a probability roll based on the given probability.
     * @param probability The probability of success.
     * @return true if the roll is successful, false otherwise.
     */
    public boolean probabilityRoll(double probability)
    {
        Random rand = new Random();
        double randomValue = rand.nextDouble();         
        return randomValue < probability;
    }

    /**
     * Prints a visual representation of the ship for testing purposes.
     * Orange (looks yellow to me) indicates a tile on fire
     * Blue represents the bot
     * Purple represents the button
     * Red Represents the path the bot has traced out to reach the button
     * White represents a plain open tile
     * Green represents a closed tile
     */
    private void printShip()
    {
        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String blue = "\u001B[34m";
        String white = "\u001B[37m";
        String purple = "\u001B[35m";
        String orange = "\u001B[33m";


        for(int row = 0; row < ship.getShipEdgeLength(); row++)
        {
            for (int col = 0; col < ship.getShipEdgeLength(); col++)
            {

                if(fireTiles.contains(ship.getShipTile(row, col)))
                {
                    System.out.print(orange + "■ " + reset);
                }

                else if(bot.equals(ship.getShipTile(row, col)))
                {
                    System.out.print(blue + "■ " + reset);
                }

                else if(button.equals(ship.getShipTile(row, col)))
                {
                    System.out.print(purple + "■ " + reset);
                }

                else if(botPath.contains(ship.getShipTile(row, col)))
                {
                    System.out.print(red + "■ " + reset);
                }

                else if(ship.getShipTile(row, col).getOpen())
                {
                    System.out.print(white + "■ " + reset);
                }

                else 
                {
                    System.out.print(green + "■ " + reset);
                }
            }
            System.out.println();
        }
    }

    /**
     * Resets the bot, fire, and button to their starting positions
     */
    public void resetSpawn()
    {
        bot = botStartTile;
        button = buttonStartTile;
        fireTiles.clear();
        fireTiles.add(fireStartTile);
        botPath.clear();
    }

    /**
     * Returns the ship object 
     * @return The ship object.
     */
    public Ship getShip()
    {
        return ship;
    }

    /**
     * Runs the bot three experiment with the print functions
     * Used in testing to visually trace the algorithim 
     */
    public void botThreeExperimentDemonstration()
    {
        System.out.println("**Bot 3 Experiment**");
        printShip();

        while(true)
        {
            bfsAvoidFireNeighbor();

            if(botPath.isEmpty())
            {
                System.out.println("Button Unreachable");
                return;
            }
            
            if(fireTiles.contains(bot))
            {
                System.out.println("destruction");
                return;
            }

            bot = botPath.remove(0);

            if(bot.equals(button))
            {
                System.out.println("YIPPPEEEE");
                return;
            }

            if(fireTiles.contains(bot))
            {
                System.out.println("destruction");
                return;
            }

            fireSpread();

            System.out.println();

            printShip();

        }
    }

    
     /**
     * Testbed, Shows a visual demonstraiton of bot three's run
     * @param args 
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception 
    {
        ExperimentController experimentController = new ExperimentController(0.5, 25);
        experimentController.spawn();
        experimentController.botThreeExperimentDemonstration();


    }
    
}
