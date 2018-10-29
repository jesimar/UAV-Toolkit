#Author: Jesimar da Silva Arantes
#Date: 13/09/2018
#Last Update: 13/09/2018
#Description: Code that turns on the buzzer for one second and then turns off the buzzer on the Raspberry Pi.
#Descricao: Codigo que liga o buzzer por um segundo e entao o desliga na Raspberry Pi.
#PRECISA FAZER

import mraa
import time
import sys

pin = int(sys.argv[1])
buzzer = mraa.Gpio(pin)
buzzer.dir(mraa.DIR_OUT)

buzzer.write(1)
time.sleep(1.0)
buzzer.write(0)
