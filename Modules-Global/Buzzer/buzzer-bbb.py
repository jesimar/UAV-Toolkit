#Author: Jesimar da Silva Arantes
#Date: 06/11/2018
#Last Update: 06/11/2018
#Description: Code that turns on the buzzer for one second and then turns off the buzzer on the BeagleBone Black.
#Descricao: Codigo que liga o buzzer por um segundo e entao o desliga na BeagleBone Black.

import Adafruit_BBIO.GPIO as GPIO
import time
import sys

pin = sys.argv[1] # sends the signal (pin "P8_10")

GPIO.setup(pin, GPIO.OUT)

GPIO.output(pin, GPIO.HIGH)
time.sleep(1.0)
GPIO.output(pin, GPIO.LOW)
