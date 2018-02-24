#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 21/02/2018
#Last Update: 21/02/2018
#Description: Script that runs MAVProxy software on Intel Edison for SITL testing at Edison.
#Descrição: Script que executa o software MAVProxy na Intel Edison para testes SITL na Edison.

rm mav.parm
rm mav.tlog
rm mav.tlog.raw

#IP_GCS=192.168.43.124
IP_GCS=192.168.205.231
IP_EDISON=127.0.0.1

PORT_IN_1=5760
PORT_IN_2=5501
PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master tcp:$IP_GCS:$PORT_IN_1 --sitl $IP_GCS:$PORT_IN_2 --out udp:$IP_GCS:$PORT_OUT_1 --out $IP_EDISON:$PORT_OUT_2
