#Author: Jesimar da Silva Arantes
#Date: 02/04/2018
#Last Update: 06/11/2018
#Description: Code that spraying. 
#Descricao: Codigo que inicia a pulveriacao.

import Adafruit_BBIO.GPIO as GPIO
import sys

pin = sys.argv[1] # sends the signal (pin "P8_12")

GPIO.setup(pin, GPIO.OUT)

GPIO.output(pin, GPIO.HIGH)
