#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 03/10/2018
#Last Update: 03/10/2018
#Description: Script that test if has connection with the internet.
#Descrição: Script que testa se tem conexão com a internet.

echo "==============================================="

if ping -c5  192.168.0.1 > /dev/null 2>&1
then 
	echo "internet-ok"
else 
	echo "internet-problems"
fi

echo "==============================================="
