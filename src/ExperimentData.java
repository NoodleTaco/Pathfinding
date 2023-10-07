import java.util.ArrayList;
public class ExperimentData
{
    // Maybe use one hashmap per bot, mapping the q value to its respective arraylist

    private ArrayList<Integer> qFifty;

    public ExperimentData()
    {
        qFifty = new ArrayList<Integer>();
    }

    public void testBotOne(int numTimes)
    {
        for(int i = 0; i < numTimes; i ++)
        {
            ExperimentController experimentController = new ExperimentController(0.7);
            experimentController.spawn();
            qFifty.add(experimentController.botOneExperiment());
        }
    }

    public void testBotTwo(int numTimes)
    {
        for(int i = 0; i < numTimes; i ++)
        {
            ExperimentController experimentController = new ExperimentController(0.7);
            experimentController.spawn();
            qFifty.add(experimentController.botTwoExperiment());
        }
    }


    public ArrayList<Integer> getQList()
    {
        return qFifty;
    }

    public static double getAverage(ArrayList<Integer> list)
    {
        int sum = 0;
        for (int num : list) 
        {
            sum += num;
        }

        if (list.size() > 0) 
        {
            return (double) sum / list.size();
        } 

        else 
        {
            return 0.0; 
        }
    }

    
    public static void main(String[] args) throws Exception 
    {
        ExperimentData experimentData = new ExperimentData();
        experimentData.testBotOne(100);
        System.out.println("Average Success Rate for Bot Two over 100 runs: " + getAverage( experimentData.getQList()));

        





        
    }

}