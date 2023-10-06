import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class ExperimentController {

    private Ship ship;

    private Tile bot;

    private Tile button;

    private ArrayList<Tile> fireTiles;

    private ArrayList<Tile> botNeighbors;

    private ArrayList<Tile> botPath;

    public ExperimentController()
    {
        ship = new Ship();
        fireTiles = new ArrayList<Tile>();
        botNeighbors = new ArrayList<Tile>();
        botPath = new ArrayList<Tile>();
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
                break;
            }
        }

        
        while(true)
        {
            button = ship.getShipTile(rand.nextInt(ship.getShipEdgeLength()), rand.nextInt(ship.getShipEdgeLength()));
            if (!bot.equals(button) && button.getOpen())
            {
                break;
            }
        }

        while(true)
        {
            Tile fireStart = ship.getShipTile(rand.nextInt(ship.getShipEdgeLength()), rand.nextInt(ship.getShipEdgeLength()));
            if(!fireStart.equals(bot) && !fireStart.equals(button) && fireStart.getOpen())
            {
                fireTiles.add(fireStart);
                break;
            }
        }

        System.out.println("Bot Starting Position: " + bot.toString());
        System.out.println("Button Starting Position: " + button.toString());
        System.out.println("Fire Starting Position: " + fireTiles.get(0).toString());
    }
    
    /**
     * Creates the botPath for bot one
     * Uses a single run of bfs 
     */
    public void runBotOne()
    {
        Queue<Tile> queue = new LinkedList<>();
        Set<Tile> visited = new HashSet<>();
        Map<Tile, Tile> parent = new HashMap<>();

        queue.add(bot);
        visited.add(bot);
        parent.put(bot, null);

        while(!queue.isEmpty())
        {
            Tile curr = queue.poll();

            if(curr.equals(button))
            {
                System.out.println("Found it");
                break;
            }

            fillBotOneNeighbors(curr);

            for(Tile tile : botNeighbors)
            {
                if(!visited.contains(tile))
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

        System.out.println("Bot Path Size: " + botPath.size());

        Collections.reverse(botPath);

        System.out.print("Bot Path: ");

        for(Tile tile: botPath)
        {
            System.out.print(tile.toString() + " ");
        }

    }

    /**
     * Adds open tiles adjacent to the tile parameter to the botNeighbors list avoiding the initial fire cell
     * @param tile the tile whose neighbors are being considered
     */
    private void fillBotOneNeighbors(Tile tile)
    {
        if(tile.getRow() + 1 < ship.getShipEdgeLength() && ship.getShipTile(tile.getRow() + 1, tile.getCol()).getOpen() && !fireTiles.contains(ship.getShipTile(tile.getRow() + 1, tile.getCol()))) 
        {
            botNeighbors.add(ship.getShipTile(tile.getRow() + 1, tile.getCol()));
        }

        if(tile.getRow() - 1 > -1 && ship.getShipTile(tile.getRow() - 1, tile.getCol()).getOpen() && !fireTiles.contains(ship.getShipTile(tile.getRow() - 1, tile.getCol())))
        {
            botNeighbors.add(ship.getShipTile(tile.getRow() - 1, tile.getCol()));
        }

        if(tile.getCol() + 1 < ship.getShipEdgeLength() && ship.getShipTile(tile.getRow() , tile.getCol() + 1).getOpen() && !fireTiles.contains(ship.getShipTile(tile.getRow() , tile.getCol() + 1)))
        {
            botNeighbors.add(ship.getShipTile(tile.getRow(), tile.getCol() + 1));
        }

        if(tile.getCol() - 1 > -1 && ship.getShipTile(tile.getRow() , tile.getCol() -1).getOpen() && !fireTiles.contains(ship.getShipTile(tile.getRow() , tile.getCol() - 1)))
        {
            botNeighbors.add(ship.getShipTile(tile.getRow(), tile.getCol() -1));
        }
        
    }

    public void playBall()

    {

        
    }

    public Ship getShip()
    {
        return ship;
    }

    
    
    public static void main(String[] args) throws Exception 
    {
        ExperimentController experimentController = new ExperimentController();
        experimentController.spawn();
        experimentController.getShip().printShip();
        experimentController.runBotOne();


    }
    
}
