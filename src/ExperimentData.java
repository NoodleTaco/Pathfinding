import java.util.ArrayList;
/**
 * Conducts the data collection of the bot experiments
 */
public class ExperimentData
{



    private ArrayList<Double> botOneList;

    private ArrayList<Double> botTwoList;

    private ArrayList<Double> botThreeList;

    private ArrayList<Double> botFourList;

    private ArrayList<Double> botFiveList;

    private ArrayList<Long> botOneRunTime;

    private ArrayList<Long> botTwoRunTime;

    private ArrayList<Long> botThreeRunTime;

    private ArrayList<Long> botFourRunTime;

    private ArrayList<Long> botFiveRunTime;

    //Final Test Value: 
    static final int NUMBER_OF_TESTS = 10;
    //Final Test Value: 
    static final int SHIP_LENGTH_FOR_Q_GRAPH = 50;

    /**
     * Default Constructor
     * Initializes the array lists
     */
    public ExperimentData()
    {
        botOneList = new ArrayList<Double>();
        botTwoList = new ArrayList<Double>();
        botThreeList = new ArrayList<Double>();
        botFourList = new ArrayList<Double>();
        botFiveList = new ArrayList<Double>();

        botOneRunTime = new ArrayList<Long>();
        botTwoRunTime = new ArrayList<Long>();
        botThreeRunTime = new ArrayList<Long>();
        botFourRunTime = new ArrayList<Long>();
        botFiveRunTime = new ArrayList<Long>();
    }

    /**
     * Fills the data lists for each bot's success rates.
     */
    public void fillBotDataLists()
    {
        double qVal = 0.1; 
        for(int i = 0 ; i < 10; i ++)
        {
            ArrayList<Integer> botOneResults = new ArrayList<Integer>();
            ArrayList<Integer> botTwoResults = new ArrayList<Integer>();
            ArrayList<Integer> botThreeResults = new ArrayList<Integer>();
            ArrayList<Integer> botFourResults = new ArrayList<Integer>();
            ArrayList<Integer> botFiveResults = new ArrayList<Integer>();

            for(int test = 0; test < NUMBER_OF_TESTS; test ++)
            {
                ExperimentController experimentController = new ExperimentController(qVal, SHIP_LENGTH_FOR_Q_GRAPH);
                experimentController.spawn();
                botOneResults.add(experimentController.botOneExperiment());
                experimentController.resetSpawn();
                botTwoResults.add(experimentController.botTwoExperiment());
                experimentController.resetSpawn();
                botThreeResults.add(experimentController.botThreeExperiment());
                experimentController.resetSpawn();
                botFourResults.add(experimentController.botFourExperiment());
                experimentController.resetSpawn();
                botFiveResults.add(experimentController.botFiveExperiment());
            }

            botOneList.add(getAverageOfIntList(botOneResults));
            botTwoList.add(getAverageOfIntList(botTwoResults));
            botThreeList.add(getAverageOfIntList(botThreeResults));
            botFourList.add(getAverageOfIntList(botFourResults));
            botFiveList.add(getAverageOfIntList(botFiveResults));
            //System.out.println("q val " + qVal + " completed");
            qVal += 0.1;
        }
    }

    /**
     * Fills the run time data lists for each bot.
     */
    public void fillRunTimes()
    {
        long botOneTotalRunTime = 0;
        long botTwoTotalRunTime = 0;
        long botThreeTotalRunTime = 0;
        long botFourTotalRunTime = 0;
        long botFiveTotalRunTime = 0;

        for(int shipEdgeLength = 25; shipEdgeLength <= 150; shipEdgeLength +=25)
        {
            for(int test = 0; test < NUMBER_OF_TESTS; test++)
            {
                ExperimentController experimentController = new ExperimentController(0.5, shipEdgeLength);
                experimentController.spawn();

                //Bot One
                long botOneStartTime = System.currentTimeMillis();
                experimentController.botOneExperiment();
                long botOneEndTime = System.currentTimeMillis();
                botOneTotalRunTime += (botOneEndTime-botOneStartTime);

                experimentController.resetSpawn();

                //Bot Two
                long botTwoStartTime = System.currentTimeMillis();
                experimentController.botTwoExperiment();
                long botTwoEndTime = System.currentTimeMillis();
                botTwoTotalRunTime += (botTwoEndTime-botTwoStartTime);

                experimentController.resetSpawn();

                //Bot Three
                long botThreeStartTime = System.currentTimeMillis();
                experimentController.botThreeExperiment();
                long botThreeEndTime = System.currentTimeMillis();
                botThreeTotalRunTime += (botThreeEndTime-botThreeStartTime);

                experimentController.resetSpawn();

                //Bot Four
                long botFourStartTime = System.currentTimeMillis();
                experimentController.botFourExperiment();
                long botFourEndTime = System.currentTimeMillis();
                botFourTotalRunTime += (botFourEndTime-botFourStartTime);

                experimentController.resetSpawn();

                //Bot Five
                long botFiveStartTime = System.currentTimeMillis();
                experimentController.botFiveExperiment();
                long botFiveEndTime = System.currentTimeMillis();
                botFiveTotalRunTime += (botFiveEndTime-botFiveStartTime);
            }

            botOneRunTime.add(botOneTotalRunTime / NUMBER_OF_TESTS);
            botTwoRunTime.add(botTwoTotalRunTime / NUMBER_OF_TESTS);
            botThreeRunTime.add(botThreeTotalRunTime / NUMBER_OF_TESTS);
            botFourRunTime.add(botFourTotalRunTime / NUMBER_OF_TESTS);
            botFiveRunTime.add(botFiveTotalRunTime / NUMBER_OF_TESTS);
            //System.out.println("Ship Edge Length: " + shipEdgeLength + " Completed");
        }
    }


    /**
     * Prints the success rate data for all bots.
     * Used For Testing
     */
    public void printAllBotDataLists()
    {

        System.out.println(" Bot One Data: ");

        this.printBotSet(this.getBotOneList());

        System.out.println();

        System.out.println(" Bot Two Data: ");

        this.printBotSet(this.getBotTwoList());

        System.out.println();

        System.out.println(" Bot Three Data: ");

        this.printBotSet(this.getBotThreeList());

        System.out.println();

        System.out.println(" Bot Four Data: ");

        this.printBotSet(this.getBotFourList());
    }

    /**
     * Prints the run time data for all bots.
     * Used For Testing
     */
    public void printAllBotRunTimes()
    {
         System.out.println(" Bot One Run Time : " + "milliseconds");

        this.printBotRunTime(this.getBotOneRunTime());

        System.out.println();


        System.out.println(" Bot Two Run Time : " + "milliseconds");

        this.printBotRunTime(this.getBotTwoRunTime());

        System.out.println();

        System.out.println(" Bot Three Run Time : " + "milliseconds");

        this.printBotRunTime(this.getBotThreeRunTime());

        System.out.println();

        System.out.println(" Bot Four Run Time : " + "milliseconds");

        this.printBotRunTime(this.getBotFourRunTime());

        System.out.println();

        System.out.println(" Bot Five Run Time : " + "milliseconds");

        this.printBotRunTime(this.getBotFiveRunTime());

    }

    /**
     * Calculates the average of a list of integers.
     * @param list The list of integers 
     * @return The average of the integers in the list.
     */
    public double getAverageOfIntList(ArrayList<Integer> list)
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

    /**
     * Prints success rate data for a specific bot.
     * @param list The list of success rates for the bot.
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

    /**
     * Prints run time data for a specific bot.
     * @param list The list of run times for the bot.
     */
    public void printBotRunTime(ArrayList<Long> list)
    {
        int index = 0;
        for (int i = 25; i <= 150; i +=25)
        {
            System.out.println("Ship length: " + i + " Average Run Time: " + list.get(index));
            index++;
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

    public ArrayList<Double> getBotFourList()
    {
        return botFourList;
    }

    public ArrayList<Double> getBotFiveList()
    {
        return botFiveList;
    }
    
    public ArrayList<Long> getBotOneRunTime()
    {
        return botOneRunTime;
    }

    public ArrayList<Long> getBotTwoRunTime()
    {
        return botTwoRunTime;
    }

    public ArrayList<Long> getBotThreeRunTime()
    {
        return botThreeRunTime;
    }

    public ArrayList<Long> getBotFourRunTime()
    {
        return botFourRunTime;
    }

    public ArrayList<Long> getBotFiveRunTime()
    {
        return botFiveRunTime;
    }
    

    /**
     * Testbed, Shows an example output of the bot succes rate and run time collection
     * Configure constants controlling number of tests and ship length to increase speed
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception 
    {
        ExperimentData test = new ExperimentData();
        test.fillBotDataLists();
        test.printAllBotDataLists();
        test.fillRunTimes();
        test.printAllBotRunTimes();


        
    }

}