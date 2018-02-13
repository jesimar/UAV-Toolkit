#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017

echo 'my ip:'

ip addr | grep 'state UP' -A2 | tail -n1 | awk '{print $2}' | cut -f1  -d'/'
