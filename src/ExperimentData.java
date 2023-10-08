import java.util.ArrayList;

public class ExperimentData
{
    // Generate one ship, then perform each bot's simulation on it using the same spawn points 

    private ArrayList<Double> botSet;

    private ArrayList<Double> botOneList;

    private ArrayList<Double> botTwoList;

    private ArrayList<Double> botThreeList;

    public final int NUMBER_OF_TESTS = 100;

    public ExperimentData()
    {
        botSet = new ArrayList<Double>();
        botOneList = new ArrayList<Double>();
        botTwoList = new ArrayList<Double>();
        botThreeList = new ArrayList<Double>();
    }

    public void fillBotSets()
    {
        double qVal = 0.1; 
        for(int i = 0 ; i < 10; i ++)
        {
            ArrayList<Integer> botOneResults = new ArrayList<Integer>();
            ArrayList<Integer> botTwoResults = new ArrayList<Integer>();
            ArrayList<Integer> botThreeResults = new ArrayList<Integer>();

            for(int test = 0; test < NUMBER_OF_TESTS; test ++)
            {
                ExperimentController experimentController = new ExperimentController(qVal);
                experimentController.spawn();
                botOneResults.add(experimentController.botOneExperiment());
                experimentController.resetSpawn();
                botTwoResults.add(experimentController.botTwoExperiment());
                experimentController.resetSpawn();
                botThreeResults.add(experimentController.botThreeExperiment());
            }

            botOneList.add(getAverageOfList(botOneResults));
            botTwoList.add(getAverageOfList(botTwoResults));
            botThreeList.add(getAverageOfList(botThreeResults));
            
            qVal += 0.1;
        }
    }

    /* 
    public void fillBotOneSet()
    {
        double qVal = 0.1; 
        for(int i = 0 ; i < 10; i ++)
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            testBotOne(qVal, list);
            botSet.add(getAverageOfList(list));
            qVal += 0.1;
        }
    }

    public void fillBotTwoSet()
    {
        double qVal = 0.1; 
        for(int i = 0 ; i < 10; i ++)
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            testBotTwo(qVal, list);
            botSet.add(getAverageOfList(list));
            qVal += 0.1;
        }
    }

    public void fillBotThreeSet()
    {
        double qVal = 0.1; 
        for(int i = 0 ; i < 10; i ++)
        {
            ArrayList<Integer> list = new ArrayList<Integer>();
            testBotThree(qVal, list);
            botSet.add(getAverageOfList(list));
            qVal += 0.1;
        }
    }

    
    

    public void testBotOne(double qVal, ArrayList<Integer> list)
    {
        for(int i = 0; i < NUMBER_OF_TESTS; i ++)
        {
            ExperimentController experimentController = new ExperimentController(qVal);
            experimentController.spawn();
            list.add(experimentController.botOneExperiment());
        }
    }

    public void testBotTwo(double qVal, ArrayList<Integer> list)
    {
        for(int i = 0; i < NUMBER_OF_TESTS; i ++)
        {
            ExperimentController experimentController = new ExperimentController(qVal);
            experimentController.spawn();
            list.add(experimentController.botTwoExperiment());
        }
    }

    
    public void testBotThree(double qVal, ArrayList<Integer> list)
    {
        for(int i = 0; i < NUMBER_OF_TESTS; i ++)
        {
            ExperimentController experimentController = new ExperimentController(qVal);
            experimentController.spawn();
            list.add(experimentController.botThreeExperiment());
        }
    }

    */
    public double getAverageOfList(ArrayList<Integer> list)
    {
        int sum = 0;
        for (int num : list) 
        {
            sum += num;
        }

        if (list.size() > 0) 
        {
            //System.out.println("Average is: " + sum + " divided by " + list.size());
            return (double) sum / list.size();
        } 

        else 
        {
            return 0.0; 
        }
    }

    /* 
    public  ArrayList<Double> getBotSet()
    {
        return botSet;
    }
    */
    

    public void printBotSet(ArrayList<Double> list)
    {
        
        double qVal = 0.1; 
        for(int i = 0 ; i < 10; i ++)
        {

            System.out.println(" Q Value: " + qVal + " Average Success Rate: " + list.get(i));
            qVal += 0.1;
        }
    }

    public ArrayList<Double> getBotOneList()
    {
        return botOneList;
    }

    public ArrayList<Double> getBotTwoList()
    {
        return botTwoList;
    }

    public ArrayList<Double> getBotThreeList()
    {
        return botThreeList;
    }
    
    public static void main(String[] args) throws Exception 
    {
        long startTime = System.currentTimeMillis();

        ExperimentData playBall = new ExperimentData();

        playBall.fillBotSets();

        System.out.println(" Bot One Data: ");

        playBall.printBotSet(playBall.getBotOneList());

        System.out.println();

        System.out.println(" Bot Two Data: ");

        playBall.printBotSet(playBall.getBotTwoList());

        System.out.println();

        System.out.println(" Bot Three Data: ");

        playBall.printBotSet(playBall.getBotThreeList());


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");

        
    }

}