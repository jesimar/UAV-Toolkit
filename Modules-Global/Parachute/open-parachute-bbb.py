#Author: Jesimar da Silva Arantes
#Date: 29/03/2018
#Last Update: 06/11/2018
#Description: Code that open the parachute.
#Descricao: Codigo que abre o paraquedas. 

import Adafruit_BBIO.GPIO as GPIO
import sys

pin = sys.argv[1] # sends the signal (pin "P8_12")

GPIO.setup(pin, GPIO.OUT)

GPIO.output(pin, GPIO.HIGH)

