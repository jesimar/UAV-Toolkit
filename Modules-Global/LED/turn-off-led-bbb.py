#Author: Jesimar da Silva Arantes
#Date: 29/03/2018
#Last Update: 06/11/2018
#Description: Code that turn off led.
#Descricao: Codigo que desliga o led.

import Adafruit_BBIO.GPIO as GPIO
import sys

pin = sys.argv[1] # sends the signal (pin "P8_10")

GPIO.setup(pin, GPIO.OUT)

GPIO.output(pin, GPIO.LOW)
