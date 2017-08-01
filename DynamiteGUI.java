/*
 * Test: did this get pushed?
 * Created by Jon D'Alonzo
 */

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
    
   
    
    
    public XYChart getChart(double[] canyonY, double[] robotY, double[] xValues, int maxDataReadings) {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName()).xAxisTitle("").yAxisTitle("").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        chart.getStyler().setYAxisDecimalPattern("## cm");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        // Series
        // @formatter:off
        
        // @formatter:on
        XYSeries seriesLiability = chart.addSeries("Canyon Wall", xValues, canyonY);
        seriesLiability.setFillColor(Color.orange);
        seriesLiability.setLineColor(Color.orange);
        seriesLiability.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        seriesLiability.setMarker(SeriesMarkers.NONE);

        chart.addSeries("Missile Track", xValues, robotY).setLineColor(Color.black);

        return chart;
    }
}
