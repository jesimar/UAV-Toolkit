#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 18/09/2018
#Last Update: 18/09/2018
#Description: Script that automatically login in to the BeagleBone Black Wireless Companion Computer (CC) via SSH.
#Descrição: Script que faz login automático no Companion Computer (CC) BeagleBone Black Wireless através de SSH.

if [ -z $1 ]
then
	#IP_BBB=192.168.0.3      #IP to network JeitoCaetano
	#IP_BBB=192.168.205.102  #IP to network LCR
	#IP_BBB=172.28.107.227   #IP to network eduroam
	#IP_BBB=192.168.0.102    #IP to network RedeDrone
	#IP_BBB=192.168.43.133   #IP to network redeDroneC (cell phone veronica)
	IP_BBB=192.168.43.2     #IP to network RedeDroneCjes (cell phone jesimar)
	#IP_BBB=10.42.0.199      #IP to network RedeDronePC
	echo "IP_BBB used:" $IP_BBB
elif [ $1 == '--help' ]
then
	echo "How to use: "
	echo "    Format: ./exec-login-bbb.sh IP_BBB"
	echo "    Example: ./exec-login-bbb.sh 192.168.7.2"
	exit 1
else
	IP_BBB=$1
fi

USER=bbb
SENHA='debian'

sshpass -p $SENHA  ssh $USER@$IP_BBB
