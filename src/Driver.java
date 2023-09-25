import java.util.Random;
public class Driver {
    public static void main(String[] args) throws Exception {
        // TO DO: Try making basic custom class for the ship that details whether a spot is open, closed, has one open neighbor, or 2 or more
        // TO DO: Figure out dead end mechanism 
        int shipEdgeLength;
        Random rand = new Random();

        if(args.length > 0)
        {
            shipEdgeLength = Integer.parseInt(args[0]);
            System.out.println("The ship will be " + shipEdgeLength + " by " + shipEdgeLength);
        }
        else {
            System.out.println("No Command Line argument found");
            return;
        }

        int[][] ship = new int[shipEdgeLength][shipEdgeLength];

        //To be changed into one statement when data structure of ship is finalized 
        int xStart = rand.nextInt(shipEdgeLength);
        int yStart = rand.nextInt(shipEdgeLength);
        
        System.out.println(xStart);
        System.out.println(yStart);
    }
}
