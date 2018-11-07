#!/usr/bin/python
#Author: Jesimar da Silva Arantes
#Date: 06/11/2018
#Last Update: 06/11/2018
#Description: Code that start the temperature sensor MAX6675 with Type-K Thermocouple on BeagleBone Black.

from max6675 import MAX6675, MAX6675Error
import Adafruit_BBIO.GPIO as GPIO
import time
import sys

clock_pin = sys.argv[1] # sends the signal clock (pin P8_8)  # PIN CLK
cs_pin    = sys.argv[2] # sends the signal clock (pin P8_10) # PIN CS
data_pin  = sys.argv[3] # sends the signal clock (pin P8_12) # PIN SO

units = "c"    # c = Celsius      f = fahrenheit

GPIO.setwarnings(False)

thermocouple = MAX6675(cs_pin, clock_pin, data_pin, units)

while(True):
    print thermocouple.get()
    time.sleep(0.5)
thermocouple.cleanup()
