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
public class GUIComponent {
    
   
    
    
    public XYChart getChart(double[] canyonX, double[] robotX, int maxDataReadings) {

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
        double[] xDistance = new double[maxDataReadings]; 
        for (int index = 1; index < maxDataReadings+1; index++) {
            xDistance[index-1] = index;
        }
        
        // @formatter:on
        XYSeries seriesLiability = chart.addSeries("Canyon Wall", xDistance, canyonX);
        seriesLiability.setFillColor(Color.orange);
        seriesLiability.setLineColor(Color.orange);
        seriesLiability.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        seriesLiability.setMarker(SeriesMarkers.NONE);

        chart.addSeries("Missile Track", xDistance, robotX).setLineColor(Color.black);

        return chart;
    }
}
