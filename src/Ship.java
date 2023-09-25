import java.util.Random;
public class Ship {

    private Tile[][] ship;
    private int shipEdgeLength;


    public Ship()
    {
        this.shipEdgeLength = 10;
        ship = new Tile[10][10];
    }

    public Ship(int shipEdgeLength)
    {
        this.shipEdgeLength = shipEdgeLength;
        ship = new Tile[shipEdgeLength][shipEdgeLength];
    }

    private void formShip()
    {
        
    }
    
}
