#Author: Jesimar da Silva Arantes
#Date: 06/11/2018
#Last Update: 06/11/2018
#Description: Code that start the sonar [HC-SR04] on BeagleBone Black.

import Adafruit_BBIO.GPIO as GPIO
import time
import sys

trig = sys.argv[1] # sends the signal (pin P8_8)
echo = sys.argv[2] # listens for the signal (pin P8_10)

GPIO.setup(trig, GPIO.OUT)
GPIO.setup(echo, GPIO.IN)

def measure_distance():
	GPIO.output(trig, True)
	time.sleep(0.00001)
	GPIO.output(trig, False)
	while GPIO.input(echo) == 0: pass
	start = time.time()  # reached when echo listens
	while GPIO.input(echo) == 1: pass
	end = time.time() # reached when signal arrived
	distance = ((end - start) * 343.0)/2.0
	return distance

if __name__ == '__main__':
	try:
		while True:
			distance = measure_distance() 
			print(distance)
			time.sleep(0.5)
	finally:
		GPIO.cleanup()
