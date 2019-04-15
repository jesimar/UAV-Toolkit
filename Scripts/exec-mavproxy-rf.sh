#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 10/03/2019
#Last Update: 10/03/2019
#Description: Script that runs MAVProxy software on CC (Raspberry Pi, Intel Edison and BeagleBone Black) for real in-flight drone testing.
#Descrição: Script que executa o software MAVProxy no CC (Raspberry Pi, Intel Edison e BeagleBone Black) para testes reais em voo no drone.
#Note: You need to specify IP of the GCS (IP_GCS) and the device (DEV) used.
#    APM: in general use serial0.
#    Pixhawk: in general use ttyS0 or ttyAMA0.
#    APM or Pixhawk: use ttyUSB0 if you use the USB connector.
#DEV:
#	[serial0, ttyAMA0, ttyS0, ttyUSB0]
#CC:
#	[RPi, Edison, BBB]

if [ -z $1 ]
then
	CC='RPi'
	IP_GCS=192.168.43.124
	DEV='serial0'
	echo "CC used:" $CC
	echo "IP_GCS used:" $IP_GCS
	echo "DEV used:" $DEV
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format: ./exec-mavproxy-rf.sh CC IP_GCS DEV"
	echo "    Example: ./exec-mavproxy-rf.sh RPi 192.168.43.124 serial0"
	echo "    Example: ./exec-mavproxy-rf.sh RPi 192.168.43.124 ttyAMA0"
	echo "    Example: ./exec-mavproxy-rf.sh RPi 192.168.43.124 ttyS0"
	echo "    Example: ./exec-mavproxy-rf.sh Edison 192.168.43.124 ttyUSB0"
	echo "    Example: ./exec-mavproxy-rf.sh BBB 192.168.43.124 ttyUSB0"
	exit 1
else
	CC=$1
	IP_GCS=$2
	DEV=$3
fi

IP_CC=127.0.0.1

PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master=/dev/$DEV --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2

#mavproxy.py --master=/dev/serial0 --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2
#mavproxy.py --master=/dev/ttyAMA0 --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2
#mavproxy.py --master=/dev/ttyS0 --baudrate 57600 --aircraft MyCopter --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2
#mavproxy.py --master=/dev/ttyUSB0 --baudrate 57600 --aircraft MyCopter --out udp:192.168.43.9:14550 --out 127.0.0.1:14551
