#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017
#Last Update: 21/02/2018
#Description: Script that filters information and displays the IP of the computer.
#Descrição: Script que filtra informações e exibe apenas o IP do computador.

echo "my ip:"

ip addr | grep 'BROADCAST,MULTICAST,UP,LOWER_UP' -A2 | tail -n1 | awk '{print $2}' | cut -f1  -d'/'

#ip addr | grep [0-9]*[.][0-9] | tail -n 1 | awk '{print $2}' | cut -f1  -d/
