#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 23/02/2018
#Last Update: 23/02/2018
#Description: Script that shows the version of the Operating System among other information.
#Descrição: Script que mostra a versão do Sistema Operacional entre outras informações.

echo "==================Show my Linux Version================="

echo "-------------------cat /etc/*release--------------------"

cat /etc/*release

echo "-------------------------uname -a-----------------------"

uname -a

echo "--------------------cat /proc/version-------------------"

cat /proc/version

echo "-------------------cat /etc/issue.net-------------------"

cat /etc/issue.net

echo "==========================Done=========================="
