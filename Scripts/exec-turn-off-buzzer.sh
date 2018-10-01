#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 28/11/2017
#Last Update: 24/08/2018
#Description: Script that turns off the buzzer of intel edison if it is turns on.
#Descrição: Script que desliga o buzzer da intel edison caso o mesmo esteja ligado.

if [ -z $1 ]
then
	PIN=8
	echo "Pin used:" $PIN
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format: ./exec-turn-off-buzzer.sh PIN"
	echo "    Example: ./exec-turn-off-buzzer.sh 8"
	exit 1
else
	PIN=$1
fi

cd ../Modules-Global/Buzzer/
python turn-off-buzzer-edison.py $PIN
