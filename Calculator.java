/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamite;

/**
 *
 * @author jonathandalonzo
 */
public class Calculator {
    
    final double ONE_TURN = 360;
    final double CIRCUM = Math.PI * 6.4; //cm 
    
    
    public double[] findDistanceTravelled(double[] leftMotor, double[] rightMotor) {
        
        double[] distances = new double[leftMotor.length];
        //start at 0 because we want the distances travelled between the two points
        for (int i = 0; i < leftMotor.length; i++) {
           
            double leftDist = (leftMotor[i] / 360.0) * CIRCUM;
            
            double rightDist = (rightMotor[i] / 360.0) * CIRCUM;
            
            distances[i] = leftDist + rightDist;
        }
        return distances;
    }
    
    public double[] changeInY(double[] distance, double[] theta, double startingY) {
        
        double[] changeInY = new double[distance.length];
        changeInY[0] = startingY;
        for (int i = 1; i < distance.length; i++) {
            changeInY[i] = (distance[i] * Math.sin(Math.toRadians(theta[i])));
        }
        
        return changeInY;
    }
    
    public double[] changeInX(double[] distance, double theta[]) {
        
        double[] changeInX = new double[distance.length];
        changeInX[0] = 0.00;
        for (int i = 1; i < distance.length; i++) {
            if (distance[i] > 0.00) {
                changeInX[i] = (distance[i] * Math.cos(Math.toRadians(theta[i])));
            }
            else {
                changeInX[i] = changeInX[i-1];
            }
        }
        
        return changeInX;
    }
    
    public double[] findNewCoordinates(double[] values) {
        
        double[] coordinates = new double[values.length];
        coordinates[0] = values[0];
        for (int i = 1; i < values.length; i++) {
            coordinates[i] = coordinates[i-1] + values[i];
        }
        
        return coordinates;
    }
    
    /**
     * The reason why the sensor data is being subtracted from the robot's y data
     * is because the canyon wall will be on the right of the robot.
     * @param sensorData
     * @return 
     */
    public double[] calculateCanyonYData(double[] robotY, double[] sensorDist, int maxDataReadings) {
        
        double[] canyonYData = new double[robotY.length];
        
        if (robotY == null || sensorDist == null) {
            return null;
        }
        else {
            for (int i = 0; i < maxDataReadings; i++) {
                if (sensorDist[i] > 200) {
                    //if current sensor read is too high use last reading...
                    canyonYData[i] = canyonYData[i-1];
                }
                //find the canyons y position from robots y and the sensor read
                canyonYData[i] = robotY[i] - sensorDist[i];
            }
        }
        return canyonYData;
    }
    
}
