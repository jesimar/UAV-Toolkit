#Author: Jesimar da Silva Arantes
#Date: 29/03/2018
#Last Update: 06/11/2018
#Description: Code that blink the led. 
#Descricao: Codigo que pisca o led.

import mraa
import time
import sys

pin = int(sys.argv[1])
led = mraa.Gpio(pin)
led.dir(mraa.DIR_OUT)
while True:
	led.write(1)
	time.sleep(1.0)
	led.write(0)
	time.sleep(1.0)
