import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;

/**
 * Generates graphs depicting the data collected in ExperimentData
 */
public class DataDriver {
        /**
         * Creates and runs an instance of ExperimentData and graphs its results
         * @param args
         * @throws Exception
         */

        public static void main(String[] args) {

        ExperimentData experimentData = new ExperimentData();

        experimentData.fillRunTimes();
        experimentData.fillBotDataLists();

        ArrayList<Double> qVals = new ArrayList<Double>();

        qVals.add(0.1); qVals.add(0.2); qVals.add(0.3); qVals.add(0.4); qVals.add(0.5); qVals.add(0.6); qVals.add(0.7); qVals.add(0.8); qVals.add(0.9); qVals.add(1.0); 

        ArrayList<Integer> shipEdgeLengths = new ArrayList<Integer>();

        shipEdgeLengths.add(25); shipEdgeLengths.add(50); shipEdgeLengths.add(75); shipEdgeLengths.add(100); shipEdgeLengths.add(125); shipEdgeLengths.add(150); 

        XYChart botSuccessRateChart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Bot Success Rates")
                .xAxisTitle("Q Values")
                .yAxisTitle("Average Success Rate")
                .build();

        botSuccessRateChart.addSeries("Bot 1", qVals, experimentData.getBotOneList());
        botSuccessRateChart.addSeries("Bot 2", qVals, experimentData.getBotTwoList());
        botSuccessRateChart.addSeries("Bot 3", qVals, experimentData.getBotThreeList());
        botSuccessRateChart.addSeries("Bot 4", qVals, experimentData.getBotFourList());
        botSuccessRateChart.addSeries("Bot 5", qVals, experimentData.getBotFiveList());

        botSuccessRateChart.getStyler().setMarkerSize(8);
        botSuccessRateChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        
        new SwingWrapper<>(botSuccessRateChart).displayChart();


        XYChart botRunTimeChart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Bot Run Times")
                .xAxisTitle("Ship Edge Length")
                .yAxisTitle("Average Run Time (milliseconds)")
                .build();

        botRunTimeChart.addSeries("Bot 1", shipEdgeLengths, experimentData.getBotOneRunTime());
        botRunTimeChart.addSeries("Bot 2", shipEdgeLengths, experimentData.getBotTwoRunTime());
        botRunTimeChart.addSeries("Bot 3", shipEdgeLengths, experimentData.getBotThreeRunTime());
        botRunTimeChart.addSeries("Bot 4", shipEdgeLengths, experimentData.getBotFourRunTime());
        botRunTimeChart.addSeries("Bot 5", shipEdgeLengths, experimentData.getBotFiveRunTime());

        botRunTimeChart.getStyler().setMarkerSize(8);
        botRunTimeChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        
        new SwingWrapper<>(botRunTimeChart).displayChart();


    }
    
}