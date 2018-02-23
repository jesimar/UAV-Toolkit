#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 18/01/2018
#Last Update: 23/02/2018
#Description: Script that displays information about the network ports used.
#Descrição: Script que exibe informações das portas da rede utilizadas.

echo "==================List of Ports in Use=================="

sudo lsof -i -P -n

echo "==========================Done=========================="
