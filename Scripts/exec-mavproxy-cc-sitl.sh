#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 21/02/2018
#Last Update: 24/08/2018
#Description: Script that runs MAVProxy software on CC for SITL tests.
#Descrição: Script que executa o software MAVProxy no CC para testes SITL.

if [ -z $1 ]
then
	IP_GCS=192.168.43.124
	echo "IP_GCS used:" $IP_GCS
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format: ./exec-mavproxy-cc-sitl.sh IP_GCS"
	echo "    Example: ./exec-mavproxy-cc-sitl.sh 192.168.43.124"
	exit 1
else
	IP_GCS=$1
fi

IP_CC=127.0.0.1

PORT_IN_1=5760
PORT_IN_2=5501
PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master tcp:$IP_GCS:$PORT_IN_1 --sitl $IP_GCS:$PORT_IN_2 --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_CC:$PORT_OUT_2
