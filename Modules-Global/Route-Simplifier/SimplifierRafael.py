import math

#le o arquivo e transforma em um vetor
arq = open("route_circle_80wpt.txt", 'r')
lista = []
lista = arq.readlines()
arq.close

#recupera separadamente as coordenadas do vetor
coordx = []
coordy = []
coordenadas = [coordx,coordy]
for i in range(len(lista)):
    aux = lista[i].split()
    coordx.append(float(aux[0]))
    coordy.append(float(aux[1]))
    print coordx[i], coordy[i]

print coordenadas

#retirada dos pontos redundantes
cx = []
cy = []
coordenadas2 = []
for l in range(len(coordx)-1):
    x = coordx[l] - coordx[l-1]
    y = coordy[l] - coordy[l-1]
    coef1 = math.atan2(x,y)
    x = coordx[l+1] - coordx[l]
    y = coordy[l+1] - coordy[l]
    coef2 = math.atan2(x,y)
    if((abs(coef1 - coef2)) >= 0.09) or (l==0):
        cx.append(coordx[l])
        cy.append(coordy[l])
        coordenadas2.append([coordx[l],coordy[l]])

print("coordenadas :\n")
for i in range(len(coordenadas2)):
    print coordenadas2[i][0], coordenadas2[i][1]

# cria um arquivo com a resposta
texto = []
arquivo = open("output.txt", 'w')
for i in range(len(coordenadas2)):
    texto.append(str(coordenadas2[i][0]))
    texto.append(" ")
    texto.append(str(coordenadas2[i][1]))
    texto.append("\n")
arquivo.writelines(texto)
arquivo.close()

