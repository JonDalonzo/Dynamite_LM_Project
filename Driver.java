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
    
    final int TIME_INTERVAL = 100; //This is how many times during 5 minutes the robot was pinged for data
    double[] robotX = new double[TIME_INTERVAL];
    double[] yValues = new double[TIME_INTERVAL];
    double[] canyonX = new double[TIME_INTERVAL];
    DynamiteGUI gui = null;
    Calculator calculator = null;
    Driver driver = null;
    
    public Driver() throws Exception {
        gui = new DynamiteGUI();
        calculator = new Calculator();
        driver = new Driver();
        //test(gui);
    }
    
    public void createChart() {
        gui.setYValues(yValues);
        // Create Chart
        XYChart chart = gui.getChart();
        // Show it
        final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();
    }
    
    public void test(DynamiteGUI gui) {
        //PROCESS
            //1. Get simulated random robot y values
        double[] simData = fillArrayWithRand(gui, 100, 1);
            //2. Store them in the gui
        gui.setYValues(simData);
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
    
    public double[] fillArrayWithRand(DynamiteGUI gui, double max, double min) {
        Random rand = new Random();
        double[] randValues = new double[gui.MAXDISTANCE];
        for (int i = 0; i < gui.MAXDISTANCE; i++) {
            randValues[i] = min + (max - min) * rand.nextDouble(); // gets random value between 1 and 100
        }
        return randValues;
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        
        Driver driver;
        try {
            driver = new Driver();
            while (true) {
                    //read in data
//                byte[] scData = sc.readFromSerialPort();
//                char[] sc2Data = sc2.readFromSerialPort();
                    //process data
                    //perform calculations
                //calculator.processData(0, 0, 0);
                    //plot data
                driver.createChart();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
