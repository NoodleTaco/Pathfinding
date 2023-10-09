import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;

public class DataDriver {

     public static void main(String[] args) {
        /* 
        // Create x-values and multiple series of y-values
        List<Double> xData = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> yData1 = Arrays.asList(2.0, 4.0, 1.0, 5.0, 3.0);
        List<Double> yData2 = Arrays.asList(3.0, 2.0, 4.0, 1.0, 5.0);

        // Create an XYChart
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Multiple Series Line Graph")
                .xAxisTitle("X Values")
                .yAxisTitle("Y Values")
                .build();

        // Add series to the chart
        chart.addSeries("Series 1", xData, yData1);
        chart.addSeries("Series 2", xData, yData2);

        // Customize the chart appearance (optional)
        chart.getStyler().setMarkerSize(8);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        // Display the chart in a Swing window
        new SwingWrapper<>(chart).displayChart();
        */

        ExperimentData experimentData = new ExperimentData();

        experimentData.fillRunTimes();
        experimentData.fillBotDataLists();

        ArrayList<Double> qVals = new ArrayList<Double>();

        qVals.add(0.1); qVals.add(0.2); qVals.add(0.3); qVals.add(0.4); qVals.add(0.5); qVals.add(0.6); qVals.add(0.7); qVals.add(0.8); qVals.add(0.9); 

        ArrayList<Integer> shipEdgeLengths = new ArrayList<Integer>();

        shipEdgeLengths.add(25); shipEdgeLengths.add(50); shipEdgeLengths.add(75); shipEdgeLengths.add(100); shipEdgeLengths.add(125); //shipEdgeLengths.add(150); 

        //System.out.println("QVals size: " + qVals.size() + " Bot One List size: " + experimentData.getBotOneList());


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

        botSuccessRateChart.getStyler().setMarkerSize(8);
        botSuccessRateChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        
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

        botRunTimeChart.getStyler().setMarkerSize(8);
        botRunTimeChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        
        new SwingWrapper<>(botRunTimeChart).displayChart();


    }
    
}