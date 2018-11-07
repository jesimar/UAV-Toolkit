#Author: Jesimar da Silva Arantes
#Date: 02/04/2018
#Last Update: 07/11/2018
#Description: Code that spraying. 
#Descricao: Codigo que encerra a pulveriacao. 

import RPi.GPIO as GPIO
import sys

GPIO.setmode(GPIO.BOARD)

GPIO.setwarnings(False)

pin = int(sys.argv[1]) # sends the signal (pin 40 - called too BCM21)

GPIO.setup(pin, GPIO.OUT)

GPIO.output(pin, GPIO.LOW)

