#Author: Jesimar da Silva Arantes
#Date: 21/02/2018
#Last Update: 23/02/2018
#Description: Code that turns off the buzzer if is turns on the buzzer on the Intel Edison.
#Descricao: Codigo que desliga o buzzer caso o mesmo esteja ligado na Intel Edison.

import mraa
import time

pin = 8

buzzer = mraa.Gpio(pin)
buzzer.dir(mraa.DIR_OUT)

buzzer.write(0)
