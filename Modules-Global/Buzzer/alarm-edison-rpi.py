#Author: Jesimar da Silva Arantes
#Date: 15/03/2018
#Last Update: 21/08/2018
#Description: Code that turns on the alarm when the buzzer is turned on several times on the Raspberry Pi.
#Descricao: Codigo que liga soa um alarme ao ligar o buzzer diversas vezes na Raspberry Pi.
#PRECISA FAZER

import mraa
import time
import sys

pin = int(sys.argv[1])
alarm = mraa.Gpio(pin)
alarm.dir(mraa.DIR_OUT)

while True:
    alarm.write(1)
    time.sleep(1.0)
    alarm.write(0)
    time.sleep(0.5)
