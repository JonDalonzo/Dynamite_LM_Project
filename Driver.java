/*
 * Created by Jon D'Alonzo
 */
package dynamite;

import java.util.Random;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 *
 * @author e356227
 */
public class Driver {
      
    public static void main(String[] args) throws Exception {

//        Random rand = new Random(); 
//        double[][] initdata = new double[0][0];
        
        DynamiteGUI gui = new DynamiteGUI();
        test(gui);
        // Create Chart
        XYChart chart = gui.getChart();
        // Show it
        final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();
    }
    
    public static void test(DynamiteGUI gui) {
        //PROCESS
            //1. Get simulated random robot y values
        double[] simData = fillArrayWithRand(gui, 100, 1);
            //2. Store them in the gui
        gui.setRobotYData(simData);
            //3. Get simulated sensor projection data
        double[] simData2 = fillArrayWithRand(gui, 50, 1);
            //4. Store sensor data
        gui.setSensorData(simData2);
            //5. Calculate the y data of the canyon wall with current data
        try {
            gui.calculateCanyonYData();
        } catch (Exception e) {
            System.out.println("Exception thrown: calculateCanyonYData, " + e.getMessage());
        }
    }

    public static double[] fillArrayWithRand(DynamiteGUI gui, double max, double min) {
        Random rand = new Random();
        double[] randValues = new double[gui.MAXDISTANCE];
        for (int i = 0; i < gui.MAXDISTANCE; i++) {
            randValues[i] = min + (max - min) * rand.nextDouble(); // gets random value between 1 and 100
        }
        return randValues;
    }

}


        // This section of code will constantly pull the data to plot it...

//        while (true) {  
//            Thread.sleep(100);
//            //update the data collected here...
//            double[] xData = getSimulatedData(rand);
//            javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
////                chart.updateXYSeries("sine", data[0], data[1], null);
//                sw.repaintChart();
//            }
//            });
//        }

//return new double[][] { xData, yData };
