#Author: Jesimar da Silva Arantes
#Date: 15/03/2018
#Last Update: 07/11/2018
#Description: Code that turns on the alarm when the buzzer is turned on several times on the Raspberry Pi.
#Descricao: Codigo que liga soa um alarme ao ligar o buzzer diversas vezes na Raspberry Pi.

import RPi.GPIO as GPIO
import time
import sys

GPIO.setmode(GPIO.BOARD)

GPIO.setwarnings(False)

pin = int(sys.argv[1]) # sends the signal (pin 38 - called too BCM20)

GPIO.setup(pin, GPIO.OUT)

while True:
	GPIO.output(pin, GPIO.HIGH)
	time.sleep(1.0)
	GPIO.output(pin, GPIO.LOW)
	time.sleep(1.0)
