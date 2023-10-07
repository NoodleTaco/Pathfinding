import java.util.ArrayList;
public class ExperimentData
{
    // Maybe use one hashmap per bot, mapping the q value to its respective arraylist

    private ArrayList<Integer> qList;

    public ExperimentData()
    {
        qList = new ArrayList<Integer>();
    }

    public void testBotOne(int numTimes)
    {
        for(int i = 0; i < numTimes; i ++)
        {
            ExperimentController experimentController = new ExperimentController(0.7);
            experimentController.spawn();
            qList.add(experimentController.botOneExperiment());
        }
    }

    public void testBotTwo(int numTimes)
    {
        for(int i = 0; i < numTimes; i ++)
        {
            ExperimentController experimentController = new ExperimentController(0.7);
            experimentController.spawn();
            qList.add(experimentController.botTwoExperiment());
        }
    }


    public ArrayList<Integer> getQList()
    {
        return qList;
    }

    public static double getAverageOfList(ArrayList<Integer> list)
    {
        int sum = 0;
        for (int num : list) 
        {
            sum += num;
        }

        if (list.size() > 0) 
        {
            System.out.println("Average is: " + sum + " divided by " + list.size());
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
        experimentData.testBotTwo(1000);
        System.out.println("Average Success Rate : " + getAverageOfList( experimentData.getQList()));

        





        
    }

}