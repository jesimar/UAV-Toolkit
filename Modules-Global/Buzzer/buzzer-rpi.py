#Author: Jesimar da Silva Arantes
#Date: 13/09/2018
#Last Update: 06/11/2018
#Description: Code that turns on the buzzer for one second and then turns off the buzzer on the Raspberry Pi.
#Descricao: Codigo que liga o buzzer por um segundo e entao o desliga na Raspberry Pi.

import RPi.GPIO as GPIO
import time
import sys

GPIO.setmode(GPIO.BOARD)

pin = int(sys.argv[1]) # sends the signal (pin 40)

GPIO.setup(pin, GPIO.OUT)

GPIO.output(pin, GPIO.HIGH)
time.sleep(1.0)
GPIO.output(pin, GPIO.LOW)
