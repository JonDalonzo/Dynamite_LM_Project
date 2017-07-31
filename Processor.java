/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamite;

import java.io.BufferedReader;
import java.io.FileReader;

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
        
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader textReader = new BufferedReader(fr);
            
            do {
                line = textReader.readLine();
                if (line.contains("done") || line == "") {
                    run = false;
                }
                else {
                    lineArray = line.split(", ");
                    int j = 0;
                    for (int i = data.length; i < lineArray.length; i++) {
                        data[i] = lineArray[j];
                        j++;
                    }
                }
            } while (line != null && run);
            
            if (data.length != 0 || data == null) {
                //separate data
                separateData(data);
                //set the amount of times data was taken during the journey
                driver.setMaxDataReadings(leftMotor.length);
                //calculate the distance travelled from previous data retrieval
                distTravelled = calc.findDistanceTravelled(leftMotor, rightMotor);
                //calculate the change in y from previous data retrieval 
                changeInY = calc.changeInY(distTravelled, zAxis, sonicDistance[0]);
                //calculate the change in x from previous data retrieval
                changeInX = calc.changeInX(distTravelled, zAxis);
                //calculate the new y coordinates and then set them
                double[] robotY = calc.findNewCoordinates(changeInY);
                driver.setRobotY(robotY);
                //calculate the new x coordinates and then store them
                driver.setXValues(calc.findNewCoordinates(changeInX));
                //calculate the canyons y values then store them
                driver.setCanyonY(calc.calculateCanyonYData(robotY, sonicDistance, driver.getMaxDataReadings()));
            }
            
            fr.close();
            textReader.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void separateData(String[] data) {
        
        int j = 0;
        boolean run = true;
        for (int i = 0; i < data.length || run; i++) {
            String dataLine = data[i] + data[i+1] + data[i+2] + data[i+3];
            if (dataLine.contains("done")) {
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
    
    public void findRobotYData() {
        
    }
}
