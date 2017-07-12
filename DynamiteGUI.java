/*
 * Test: did this get pushed?
 * Created by Jon D'Alonzo
 */
package dynamite;

import java.util.Random;
import org.knowm.xchart.*;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.demo.charts.area.AreaLineChart03;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;

/**
 *
 * @author e356227
 */
public class DynamiteGUI {
    
    double[] robotYData;
    double[] canyonYData;
    double[] sensorData;
    final int MAXDISTANCE = 100;
    
    
    public DynamiteGUI() {
        canyonYData = new double[MAXDISTANCE];
        robotYData = null;
        sensorData = null;
    }
    
    public void setRobotYData(double[] data) {
        robotYData = data;
    }
    
    public void setSensorData(double[] data) {
        sensorData = data;
    }
    
    /**
     * The reason why the y data of the robot is being subtracted to the sensor data is because
     * the canyon wall will be on the right of the robot
     * @param sensorData
     * @return 
     */
    public void calculateCanyonYData() {
        
        for (int i = 0; i < MAXDISTANCE; i++) {
            canyonYData[i] = robotYData[i] - sensorData[i];
        }
    }
    
    public double[] fillArrayWithRand(double max, double min) {
        Random rand = new Random();
        double[] randValues = new double[MAXDISTANCE];
        for (int i = 0; i < MAXDISTANCE; i++) {
            randValues[i] = min + (max - min) * rand.nextDouble(); // gets random value between 1 and 100
        }
        return randValues;
    }
        

    public XYChart getChart() {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName()).xAxisTitle("Age").yAxisTitle("Amount").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        chart.getStyler().setYAxisDecimalPattern("$ #,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        // Series
        // @formatter:off
        double[] xDistance = new double[MAXDISTANCE]; 
        for (int index = 1; index < MAXDISTANCE+1; index++) {
            xDistance[index-1] = index;
        }
        
        // @formatter:on
        XYSeries seriesLiability = chart.addSeries("Liability", xDistance, canyonYData);
        seriesLiability.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        seriesLiability.setMarker(SeriesMarkers.NONE);

        chart.addSeries("75th Percentile", xDistance, robotYData);

        return chart;
    }
}
