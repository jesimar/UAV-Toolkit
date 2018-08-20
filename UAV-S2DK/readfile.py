#Authors: Jesimar da Silva Arantes
#Date: 07/09/2017
#Last Update: 15/03/2018
#Description: Code that reads some configuration parameters
#Descricao: Codigo que faz a leitura de alguns parametros de configuracao.

def readFileParameters():
	parameters = ''
	fileConfig = open('config-s2dk.properties', 'r')
	text = fileConfig.readlines()
	for line in text:
		l1 = line.find('#')
		l2 = line.find('=')
		if (l1 == -1) and (l2 != -1):
			substring = line.split('=')
			if substring[0] == 'prop.global.host_mavproxy':
				parameters +=substring[1].replace('\n', '')
				parameters += ' '
			if substring[0] == 'prop.global.port_mavproxy':
				parameters +=substring[1].replace('\n', '')
				parameters += ' '
			if substring[0] == 'prop.global.host_http':
				parameters +=substring[1].replace('\n', '')
				parameters += ' '
			if substring[0] == 'prop.global.port_http':
				parameters +=substring[1].replace('\n', '')
	fileConfig.close()
	return parameters
