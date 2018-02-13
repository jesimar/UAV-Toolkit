#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 18/01/2018

echo 'list of ports in use:'

sudo lsof -i -P -n
