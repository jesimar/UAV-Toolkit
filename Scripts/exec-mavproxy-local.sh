#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017

rm mav.parm
rm mav.tlog
rm mav.tlog.raw

IP=127.0.0.1
PORT_IN_1=5760
PORT_IN_2=5501
PORT_OUT_1=14550
PORT_OUT_2=14551

mavproxy.py --master tcp:$IP:$PORT_IN_1 --sitl $IP:$PORT_IN_2 --out $IP:$PORT_OUT_1 --out $IP:$PORT_OUT_2
