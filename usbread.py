#!/usr/bin/env python

import serial

ser = serial.Serial('/dev/tty.wchusbserial1410', 115200, timeout=2) #Tried with and without the last 3 parameters, and also at 1Mbps, same happens.
ser.flushInput()
ser.flushOutput()

file = open("DataFile.txt","w") 
run = True;


while run:
  data_raw = ser.readline()
  
  if "done" not in data_row:
  	file.write(data_raw);
  else:
  	run = False;

file.close()
