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
    
    private int maxDataReadings = 100; //This is how many times during 5 minutes the robot was pinged for data
    private double[] robotY; // list of distances retrieved at each data retrieval location
    private double[] xValues; // y data of the missile track retrieved by the robot
    private double[] canyonY; // x data of the canyon
    private GUIComponent gui = null;
    private Calculator calculator = null;
    private Driver driver = null;
    private Processor processor;
    
    public void initDriver() {
        robotY = new double[maxDataReadings];
        xValues = new double[maxDataReadings];
        canyonY = new double[maxDataReadings];
        gui = new GUIComponent();
        calculator = new Calculator();
    }
    
    public void setMaxDataReadings(int maxDataReadings) {
        this.maxDataReadings = maxDataReadings;
    }
    
    public void setRobotY(double[] robotY) {
        this.robotY = robotY;
    }
    
    public void setXValues(double[] xValues) {
        this.xValues = xValues;
    }
    
    public void setCanyonY(double[] canyonY) {
        this.canyonY = canyonY;
    }
    
    
    public void createChart() {
        // Create Chart
        XYChart chart = gui.getChart(canyonY, robotY, maxDataReadings);
        // Show it
        final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();
    }
    
    public void run() {
        try {
            while (true) {
                    //read in/process data
                processor.processFile("DataFile.txt", this);
                initDriver();
                
                    //perform calculations
                //calculator.processData(0, 0, 0);
                    //plot data
                createChart();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void test(GUIComponent gui) {
        //PROCESS
        try {
        //1. Get simulated random robot y values
        double[] simRobotY = fillArrayWithRand(gui, 100, 1);
            //2. Store them in the gui
        setRobotY(simRobotY);
            //3. Get simulated sensor projection data
        double[] simSensorData = fillArrayWithRand(gui, 50, 1);
            //5. Calculate the y data of the canyon wall with current data
        setCanyonY(calculator.calculateCanyonYData(xValues, simSensorData, maxDataReadings));
            //6. Fill x values with a time interval
        for (int i = 0; i < maxDataReadings; i++) {
           xValues[i] = i;
        }
        } catch (Exception e) {
            System.out.println("Exception thrown: calculateCanyonYData, " + e.getMessage());
        }
    }
    
    public double[] fillArrayWithRand(GUIComponent gui, double max, double min) {
        Random rand = new Random();
        double[] randValues = new double[maxDataReadings];
        for (int i = 0; i < maxDataReadings; i++) {
            randValues[i] = min + (max - min) * rand.nextDouble(); // gets random value between 1 and 100
        }
        return randValues;
    }
    
    public int getMaxDataReadings() {
        return maxDataReadings;
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        
        GUIComponent gui = new GUIComponent();;
        Calculator calculator = new Calculator();
        Driver driver = new Driver();
        Processor processor = new Processor();
        //test(gui);
        driver.run();
    }
}
