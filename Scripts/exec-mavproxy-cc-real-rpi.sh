#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 02/07/2018
#Last Update: 03/07/2018
#Description: Script that runs MAVProxy software on CC for real in-flight drone testing.
#Descrição: Script que executa o software MAVProxy no CC para testes reais em voo no drone.

if [ -z $1 ]
then
	IP_GCS=192.168.0.1
else
	IP_GCS=$1
fi

IP_CC=127.0.0.1

PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master=/dev/ttyAMA0 --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2
