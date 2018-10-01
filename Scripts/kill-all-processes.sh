#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 21/02/2018
#Last Update: 21/02/2018
#Description: Script that kills all processes related to the UAV-Toolkit.
#Descrição: Script que mata todos os processos relacionados ao UAV-Toolkit.

echo "======= kill all processes uav-toolkit ======="

pkill apm
pkill dronekit-sitl
pkill mavproxy
pkill s2dk
pkill ifa
pkill mosa
pkill uav-tests
pkill exec-gcs

echo "=============================================="
