#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 12/01/2018
#Last Update: 13/02/2018
#Description: Script that automatically login in to the Intel Edison Companion Computer (CC) via SSH.
#Descrição: Script que faz login automático no Companion Computer (CC) Intel Edison através de SSH.

IP_EDISON=192.168.205.220  #IP to network LCR
#IP_EDISON=172.28.107.227   #IP to network eduroam
#IP_EDISON=192.168.0.102    #IP to network RedeDrone
#IP_EDISON=192.168.43.133   #IP to network redeDroneC (celular veronica)
#IP_EDISON=192.168.43.9     #IP to network RedeDroneCjes (celular jesimar)
#IP_EDISON=10.42.0.199      #IP to network RedeDronePC
#IP_EDISON=192.168.0.100    #IP to network Alice

USER=root
SENHA='inteledison'

sshpass -p $SENHA  ssh $USER@$IP_EDISON
