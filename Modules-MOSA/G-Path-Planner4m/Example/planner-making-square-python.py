#Author: Jesimar da Silva Arantes
#Date: 08/10/2018
#Last Update: 08/10/2018
#Description: Code that generates a square-shaped route in python.
#Descricao: Codigo que gera uma rota em formato de quadrado em python.

print "#G-Path-Planner [Python]"
print "#Planejador que faz uma rota em formato de quadrado"
text = []
arq = open("output.txt", 'w')
text.append("0.0 0.0\n")
text.append("0.0 2.0\n")
text.append("0.0 4.0\n")
text.append("0.0 6.0\n")
text.append("0.0 8.0\n")
text.append("0.0 10.0\n")
text.append("2.0 10.0\n")
text.append("4.0 10.0\n")
text.append("6.0 10.0\n")
text.append("8.0 10.0\n")
text.append("10.0 10.0\n")
text.append("10.0 8.0\n")
text.append("10.0 6.0\n")
text.append("10.0 4.0\n")
text.append("10.0 2.0\n")
text.append("10.0 0.0\n")
text.append("8.0 0.0\n")
text.append("6.0 0.0\n")
text.append("4.0 0.0\n")
text.append("2.0 0.0\n")
text.append("0.0 0.0\n")
arq.writelines(text)
arq.close()
print "#Rota terminada"
