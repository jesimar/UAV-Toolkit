#Author: Jesimar da Silva Arantes
#Date: 06/11/2018
#Last Update: 06/11/2018
#Description: Code that turns on the alarm when the buzzer is turned on several times on the BeagleBone Black.
#Descricao: Codigo que liga soa um alarme ao ligar o buzzer diversas vezes na BeagleBone Black.

import Adafruit_BBIO.GPIO as GPIO
import time
import sys

pin = sys.argv[1] # sends the signal (pin "P8_10")

GPIO.setup(pin, GPIO.OUT)

while True:
	GPIO.output(pin, GPIO.HIGH)
	time.sleep(0.5)
	GPIO.output(pin, GPIO.LOW)
	time.sleep(0.5)
