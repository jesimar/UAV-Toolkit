#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 16/11/2017
#Last Update: 16/11/2017
#Description: Script that runs MAVProxy software on Intel Edison for real in-flight drone testing.
#Descrição: Script que executa o software MAVProxy na Intel Edison para testes reais em voo no drone.

rm mav.parm
rm mav.tlog
rm mav.tlog.raw

#IP_GCS=192.168.43.124
IP_GCS=192.168.205.231
IP_EDISON=127.0.0.1

PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master=/dev/ttyUSB0 --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_EDISON:$PORT_OUT_2
