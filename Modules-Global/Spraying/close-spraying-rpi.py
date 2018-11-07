#Author: Jesimar da Silva Arantes
#Date: 02/04/2018
#Last Update: 06/11/2018
#Description: Code that spraying. 
#Descricao: Codigo que encerra a pulveriacao. 

import RPi.GPIO as GPIO
import sys

GPIO.setmode(GPIO.BOARD)

pin = int(sys.argv[1]) # sends the signal (pin 40)

GPIO.setup(pin, GPIO.OUT)

GPIO.output(pin, GPIO.LOW)

