/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonathandalonzo
 */
public class Processor {
    
    private double[] leftMotor;
    private double[] rightMotor;
    private double[] zAxis;
    private double[] sonicDistance;
    private Calculator calc = new Calculator();
    
    
    /**
     * Will process the whole data file at once...
     * @param filePath
     * @return 
     */
    public void processFile(String filePath, Driver driver) {
        
        double[] distTravelled;
        double[] changeInY;
        double[] changeInX;
        String[] lineArray;
        String[] data;
        String line = "START OF FILE";
        boolean run = true;
        
        data = parseFile(filePath);
        initializeArray(data.length/4);
        
        if (data.length != 0 || data == null) {
            //separate data
            separateData(data);
            //set the amount of times data was taken during the journey
            driver.setMaxDataReadings(leftMotor.length);
            //find theta values
//            findTheta();
            //calculate the distance travelled from previous data retrieval
            distTravelled = calc.findDistanceTravelled(leftMotor, rightMotor);
            //calculate the change in y from previous data retrieval 
            changeInY = calc.changeInY(distTravelled, zAxis, sonicDistance[0]);
            //calculate the change in x from previous data retrieval
            changeInX = calc.changeInX(distTravelled, zAxis);
            //calculate the new y coordinates and then set them
            double[] robotY = calc.findNewCoordinates(changeInY);
            //calculate the new x coordinates and then store them
            double[] xValues = calc.findNewCoordinates(changeInX);
            //calculate the canyons y values then store them
            double[] canyonY = calc.calculateCanyonYData(robotY, sonicDistance, driver.getMaxDataReadings());
            double minimumCanyonY = findMin(canyonY);
            //format all data sets to put them on the plot
            robotY = formatValues(robotY, minimumCanyonY);;
            canyonY = formatValues(canyonY, minimumCanyonY);
            driver.setMinAndMax(findMin(canyonY), findMax(robotY));
            driver.setRobotY(robotY);
            driver.setXValues(xValues);
            driver.setCanyonY(canyonY);
        }
    }
    
    public double findMin(double[] data) {
        double min = 100.00;
        for (int i = 0; i < data.length; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }
    
    public double findMax(double[] data) {
        double min = 0.00;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > min) {
                min = data[i];
            }
        }
        return min;
    }
    
    public double[] formatValues(double[] dataSet, double bufferValue) {
        if (bufferValue < 0) {
            bufferValue = -(bufferValue);
        }
        for (int i = 0; i < dataSet.length; i++) {
            dataSet[i] += bufferValue;
        }
        return dataSet;
    }
    
    private void initializeArray(int size) {
        leftMotor = new double[size];
        rightMotor = new double[size];
        zAxis = new double[size];
        sonicDistance = new double[size];
    }
    
    private void separateData(String[] data) {
        
        int j = 0;
        int size = (data.length / 4);
        boolean run = true;
        for (int i = 0; j < size && run; i++) {
            String dataLine = data[i] + data[i+1] + data[i+2] + data[i+3];
            if (dataLine.contains("done") || dataLine == null) {
                run = false;
            }
            else {
                leftMotor[j] = Double.parseDouble(data[i]);
                rightMotor[j] = Double.parseDouble(data[++i]);
                zAxis[j] = Double.parseDouble(data[++i]);
                sonicDistance[j] = Double.parseDouble(data[++i]);
                j++;
            }
        }
    }

    public String[] parseFile(String filePath) {
        
        String[] lineArray;
        String[] results;
        List <String> data = new ArrayList<String>();
        String line = "";
        boolean run = true;
        
        try {
            BufferedReader textReader = new BufferedReader(new FileReader(filePath));
            
            do {
                line = textReader.readLine();
                if (line.contains("done") || line == null) {
                    run = false;
                }
                else {
                    lineArray = line.split(", ");
                    for (int i = 0; i < 4; i++) {
                        if (lineArray[i] != "") {
                            data.add(lineArray[i]);
                        }
                    }
                    textReader.readLine(); //consume \n
                }
            } while (run);
            
            textReader.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        results = new String[data.size()];
        return data.toArray(results);
    }

}
