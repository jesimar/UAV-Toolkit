#Author: Jesimar da Silva Arantes
#Date: 15/03/2018
#Last Update: 15/03/2018
#Description: Code that turns on the alarm when the buzzer is turned on several times.
#Descricao: Codigo que liga soa um alarme ao ligar o buzzer diversas vezes.

import mraa
import time

pin = 8
alarm = mraa.Gpio(pin)
alarm.dir(mraa.DIR_OUT)

while True:
    alarm.write(1)
    time.sleep(1.0)
    alarm.write(0)
    time.sleep(0.5)
