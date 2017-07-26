/*
 * Test: did this get pushed?
 * Created by Jon D'Alonzo
 */
package dynamite;

import java.awt.Color;
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
     
    double[] missileYData; // y data of the missile track retrieved by the robot
    double[] canyonYData; // y data of the canyon
    double[] sensorData; // list of distances retrieved at each ping location
    final int MAXDISTANCE = 100; // the max x distance of enviornment
    
    
    public DynamiteGUI() {
        canyonYData = new double[MAXDISTANCE];
        missileYData = new double[MAXDISTANCE];
        sensorData = new double[MAXDISTANCE];
    }
    
    public void setYValues(double[] data) {
        missileYData = data;
    }
    
    public void setSensorData(double[] data) {
        sensorData = data;
    }
    
    /**
     * The reason why the sensor data is being subtracted from the robot's y data
     * is because the canyon wall will be on the right of the robot.
     * @param sensorData
     * @return 
     */
    public void calculateCanyonYData() throws Exception {
        
        if (missileYData == null || sensorData == null) {
            throw new Exception();
        }
        else {
            for (int i = 0; i < MAXDISTANCE; i++) {
                canyonYData[i] = missileYData[i] - sensorData[i];
            }
        }
    }
   
    public XYChart getChart() {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName()).xAxisTitle("").yAxisTitle("").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        chart.getStyler().setYAxisDecimalPattern("## in");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        // Series
        // @formatter:off
        double[] xDistance = new double[MAXDISTANCE]; 
        for (int index = 1; index < MAXDISTANCE+1; index++) {
            xDistance[index-1] = index;
        }
        
        // @formatter:on
        XYSeries seriesLiability = chart.addSeries("Canyon Wall", xDistance, canyonYData);
        seriesLiability.setFillColor(Color.orange);
        seriesLiability.setLineColor(Color.orange);
        seriesLiability.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        seriesLiability.setMarker(SeriesMarkers.NONE);

        chart.addSeries("Missile Track", xDistance, missileYData).setLineColor(Color.black);

        return chart;
    }
}
