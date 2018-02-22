#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 22/02/2018
#Last Update: 22/02/2018

echo "=======================List IPs in Use======================="

IP_INIT=192.168.205    #LCR     192.168.205.*
#IP_INIT=172.28.107      #Eduroam 172.28.107.*

for ip in $IP_INIT.{1..254}; do
  ping -c 1 -W 1 $ip | grep "64 bytes" &
done

sleep 1

echo "============================Done============================="
