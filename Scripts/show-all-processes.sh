#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017
#Last Update: 21/02/2018
#Description: Script that shows all running uav-toolkit processes.
#Descrição: Script que mostra todos os processos do uav-toolkit em execução.

echo "======= show all processes uav-toolkit ======="

ps -All | grep apm
ps -All | grep dronekit-sitl
ps -All | grep mavproxy
ps -All | grep soa-inter
ps -All | grep ifa
ps -All | grep mosa
ps -All | grep uav-tests
ps -All | grep insert-failure
ps -All | grep gcs

echo "=============================================="
