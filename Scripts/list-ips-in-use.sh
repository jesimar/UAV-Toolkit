#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 22/02/2018
#Last Update: 24/08/2018
#Description: Script that displays information from IPs logged on the same network.
#Descrição: Script que exibe informações de IPs logados na mesma rede.

if [ -z $1 ]
then
	#IP_INIT=192.168.205    #LCR            192.168.205.*
	#IP_INIT=192.168.43     #redeDroneC     192.168.43.*
	IP_INIT=192.168.43     #RedeDroneCjes  192.168.43.*
	#IP_INIT=192.168.0      #JeitoCaetano   192.168.0.*
	echo "Mask IP used:" $IP_INIT
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format: ./list-ips-in-use.sh MASK_IP"
	echo "    Example: ./list-ips-in-use.sh 192.168.0"
	exit 1
else
	IP_INIT=$1
fi

echo "=======================List IPs in Use======================="

for ip in $IP_INIT.{1..254}; do
  ping -c 1 -W 1 $ip | grep "64 bytes" &
done

sleep 1

echo "============================Done============================="
