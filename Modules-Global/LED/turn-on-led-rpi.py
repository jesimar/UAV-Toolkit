#Author: Jesimar da Silva Arantes
#Date: 29/03/2018
#Last Update: 07/11/2018
#Description: Code that turn on led.
#Descricao: Codigo que liga o led.

import RPi.GPIO as GPIO
import sys

GPIO.setmode(GPIO.BOARD)

GPIO.setwarnings(False)

pin = int(sys.argv[1]) # sends the signal (pin 36 - called too BCM16)

GPIO.setup(pin, GPIO.OUT)

GPIO.output(pin, GPIO.HIGH)
