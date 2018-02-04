#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 12/01/2018

#IP_EDISON=192.168.205.220  #IP para Rede LCR
#IP_EDISON=172.28.107.227   #IP para Rede eduroam
#IP_EDISON=192.168.0.102    #IP para Rede RedeDrone
#IP_EDISON=192.168.43.133   #IP para Rede RedeDroneC (celular veronica)
IP_EDISON=192.168.43.9     #IP para Rede RedeDroneCjes (celular jesimar)
#IP_EDISON=10.42.0.199      #IP para Rede RedeDronePC
#IP_EDISON=192.168.0.100    #IP para Rede Alice

USER=root
SENHA='inteledison'

sshpass -p $SENHA  ssh $USER@$IP_EDISON
