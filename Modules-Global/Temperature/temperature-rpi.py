#!/usr/bin/python
#Author: Jesimar da Silva Arantes
#Date: 16/08/2018
#Last Update: 11/09/2018
#Description: Code that start the temperature sensor MAX6675 with Type-K Thermocouple on Raspberry Pi.

from max6675 import MAX6675, MAX6675Error
import RPi.GPIO as GPIO
import time
import sys

clock_pin = int(sys.argv[1]) # sends the signal clock (pin 24) # BCM24 # PIN CLK
cs_pin    = int(sys.argv[2]) # sends the signal clock (pin 4)  # BCM4  # PIN CS
data_pin  = int(sys.argv[3]) # sends the signal clock (pin 25) # BCM25 # PIN SO

units = "c"    # c = Celsius      f = fahrenheit

GPIO.setwarnings(False)

thermocouple = MAX6675(cs_pin, clock_pin, data_pin, units)

while(True):
    print thermocouple.get()
    time.sleep(0.5)
thermocouple.cleanup()
