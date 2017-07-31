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
    
    double[] leftMotor;
    double[] rightMotor;
    double[] zAxis;
    double[] sonicDistance;
    
    
    /**
     * Will return an array of data that in such form:
     * double results:
     * [0] - change in y value
     * [1] - change in x value
     * [2] - sensor distance
     * @param filePath
     * @return 
     */
    public double[] processFile(String filePath) {
        
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader textReader = new BufferedReader(fr);
            
            String[] data;
            double[] results; //[0]-change in y, [1]-change in x, [2]-sensor data
            String line = "START OF FILE";
            boolean run = true;
            do {
                line = textReader.readLine();
                if (line.contains("done") || line == "") {
                    run = false;
                }
                else {
                    data = new String[4]; //should be 4 due to only reading in 4 components of data
                    data = line.split(", ");
                    //separate data
                    separateData(data);
                    
                }
            } while (line != null && run);
            
            
            fr.close();
            textReader.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    private void separateData(String[] data) {
        
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            leftMotor[j] = Double.parseDouble(data[i]);
            rightMotor[j] = Double.parseDouble(data[++i]);
            zAxis[j] = Double.parseDouble(data[++i]);
            sonicDistance[j] = Double.parseDouble(data[++i]);
            j++;
        }
    }
}
