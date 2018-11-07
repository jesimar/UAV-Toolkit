#Author: Jesimar da Silva Arantes
#Date: 29/03/2018
#Last Update: 06/11/2018
#Description: Code that open the parachute.
#Descricao: Codigo que abre o paraquedas.

import mraa
import sys

pin = int(sys.argv[1])
buzzer = mraa.Gpio(pin)
buzzer.dir(mraa.DIR_OUT)

buzzer.write(1)

