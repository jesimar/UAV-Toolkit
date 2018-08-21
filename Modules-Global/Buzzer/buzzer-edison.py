#Author: Jesimar da Silva Arantes
#Date: 15/03/2018
#Last Update: 21/08/2018
#Description: Code that turns on the buzzer for one second and then turns off the buzzer on the Intel Edison.
#Descricao: Codigo que liga o buzzer por um segundo e entao o desliga na Intel Edison.

import mraa
import time
import sys

pin = sys.argv[1]
buzzer = mraa.Gpio(pin)
buzzer.dir(mraa.DIR_OUT)

buzzer.write(1)
time.sleep(1.0)
buzzer.write(0)
