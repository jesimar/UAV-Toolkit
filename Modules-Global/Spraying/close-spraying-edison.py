#Author: Jesimar da Silva Arantes
#Date: 02/04/2018
#Last Update: 06/11/2018
#Description: Code that spraying. 
#Descricao: Codigo que encerra a pulveriacao. 

import mraa
import sys

pin = int(sys.argv[1])
buzzer = mraa.Gpio(pin)
buzzer.dir(mraa.DIR_OUT)

buzzer.write(0)
