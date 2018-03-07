#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017
#Last Update: 19/10/2017
#Description: Script that runs the MAVProxy software on the local computer for SITL tests on the PC.
#Descrição: Script que executa o software MAVProxy no computador local para testes SITL no PC.

IP=127.0.0.1
PORT_IN_1=5760
PORT_IN_2=5501
PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master tcp:$IP:$PORT_IN_1 --sitl $IP:$PORT_IN_2 --out $IP:$PORT_OUT_1 --out $IP:$PORT_OUT_2
