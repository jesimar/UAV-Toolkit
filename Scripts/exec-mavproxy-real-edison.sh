#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 16/11/2017
#Last Update: 20/11/2018
#Description: Script that runs MAVProxy software on CC for real in-flight drone testing.
#Descrição: Script que executa o software MAVProxy no CC para testes reais em voo no drone.
#Note: You need to specify IP of the GCS (IP_GCS).

if [ -z $1 ]
then
	IP_GCS=192.168.43.124
	echo "IP_GCS used:" $IP_GCS
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format: ./exec-mavproxy-real-edison.sh IP_GCS"
	echo "    Example: ./exec-mavproxy-real-edison.sh 192.168.43.124"
	exit 1
else
	IP_GCS=$1
fi

IP_CC=127.0.0.1

PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master=/dev/ttyUSB0 --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2
