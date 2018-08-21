#Authors: Jesimar da Silva Arantes
#Date: 07/09/2017
#Last Update: 21/08/2018
#Description: Code that reads some configuration parameters
#Descricao: Codigo que faz a leitura de alguns parametros de configuracao.

def readFileParameters():
	parameters = ''
	fileConfig = open('../Modules-Global/config-global.properties', 'r')
	text = fileConfig.readlines()
	for line in text:
		l1 = line.find('#')
		l2 = line.find('=')
		if (l1 == -1) and (l2 != -1):
			substring = line.split('=')
			if substring[0] == 'prop.global.comm.host_s2dk':
				parameters +=substring[1].replace('\n', '')
				parameters += ' '
			if substring[0] == 'prop.global.comm.port_network_mavproxy':
				parameters +=substring[1].replace('\n', '')
				parameters += ' '
			if substring[0] == 'prop.global.comm.host_s2dk':
				parameters +=substring[1].replace('\n', '')
				parameters += ' '
			if substring[0] == 'prop.global.comm.port_network_s2dk':
				parameters +=substring[1].replace('\n', '')
	fileConfig.close()
	return parameters
