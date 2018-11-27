#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 02/07/2018
#Last Update: 27/11/2018
#Description: Script that runs MAVProxy software on CC (Raspberry Pi) for real in-flight drone testing.
#Descrição: Script que executa o software MAVProxy no CC (Raspberry Pi) para testes reais em voo no drone.
#Note: You need to specify IP of the GCS (IP_GCS) and the device (DEV) used.
#    APM: in general use serial0.
#    Pixhawk: in general use ttyS0 or ttyAMA0.

if [ -z $1 ]
then
	IP_GCS=192.168.43.124
	DEV='serial0'
	echo "IP_GCS used:" $IP_GCS
	echo "DEV used:" $DEV
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format: ./exec-mavproxy-real-rpi.sh IP_GCS DEV"
	echo "    Example: ./exec-mavproxy-real-rpi.sh 192.168.43.124 serial0"
	echo "    Example: ./exec-mavproxy-real-rpi.sh 192.168.43.124 ttyAMA0"
	echo "    Example: ./exec-mavproxy-real-rpi.sh 192.168.43.124 ttyS0"
	exit 1
else
	IP_GCS=$1
	DEV=$2
fi

IP_CC=127.0.0.1

PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master=/dev/$DEV --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2

#mavproxy.py --master=/dev/serial0 --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2
#mavproxy.py --master=/dev/ttyAMA0 --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2
#mavproxy.py --master=/dev/ttyS0 --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2
#mavproxy.py --master=/dev/ttyS0 --baudrate 57600 --aircraft MyCopter --out udp:192.168.43.9:14550 --out 127.0.0.1:14551
