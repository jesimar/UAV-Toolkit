#Author: Jesimar da Silva Arantes
#Date: 08/10/2018
#Last Update: 08/10/2018
#Description: Code that generates a line-shaped route in python.
#Descricao: Codigo que gera uma rota em formato de linha em python.

print "#G-Path-Replanner [Python]"
print "#Planejador que faz uma rota em formato de linha"
text = []
arq = open("output.txt", 'w')
text.append("0.0 0.0\n")
text.append("0.0 2.0\n")
text.append("0.0 4.0\n")
text.append("0.0 6.0\n")
text.append("0.0 8.0\n")
text.append("0.0 10.0\n")
arq.writelines(text)
arq.close()
print "#Rota terminada"
