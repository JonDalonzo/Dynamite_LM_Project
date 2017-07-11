/*
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
    final int MAXDISTANCE = 100;
    
    
    public DynamiteGUI() {
        canyonYData = null;
        robotYData = null;
    }
    
    public void setRobotYData(double[] data) {
        robotYData = data;
    }
      
    public double[] calculateCanyonYData(double[] sensorRead) {
      
        double[] data = fillArrayWithRand(100, 1);
        


        return data;
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
        double[] xAges = new double[] { 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87,
            88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100 };

        double[] yLiability = new double[] { 672234, 691729, 711789, 732431, 753671, 775528, 798018, 821160, 844974, 869478, 907735, 887139, 865486,
            843023, 819621, 795398, 770426, 744749, 719011, 693176, 667342, 641609, 616078, 590846, 565385, 540002, 514620, 489380, 465149, 441817,
            419513, 398465, 377991, 358784, 340920, 323724, 308114, 293097, 279356, 267008, 254873 };
        double[] yPercentile75th = new double[] { 800000, 878736, 945583, 1004209, 1083964, 1156332, 1248041, 1340801, 1440138, 1550005, 1647728,
            1705046, 1705032, 1710672, 1700847, 1683418, 1686522, 1674901, 1680456, 1679164, 1668514, 1672860, 1673988, 1646597, 1641842, 1653758,
            1636317, 1620725, 1589985, 1586451, 1559507, 1544234, 1529700, 1507496, 1474907, 1422169, 1415079, 1346929, 1311689, 1256114, 1221034 };
        // @formatter:on

        XYSeries seriesLiability = chart.addSeries("Liability", xAges, yLiability);
        seriesLiability.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        seriesLiability.setMarker(SeriesMarkers.NONE);

        chart.addSeries("75th Percentile", xAges, yPercentile75th);

        return chart;
    }
}
