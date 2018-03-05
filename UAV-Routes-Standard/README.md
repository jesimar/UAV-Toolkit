# UAV-Routes-Standard
 
Este projeto foi desenvolvido em C usando o IDE [Geany](https://www.geany.org/). 
Esse projeto auxilia a projetar rotas para VANT com formatos regulares como círculos, triângulos e retângulos. 
De acordo com uma discretização especificada (quantidade de waypoints). 
As rotas são projetadas sempre em uma altitude de voo cruzeiro especificada e os dados são em coordenadas cartesianas (x, y, z) e não em coordenadas geográficas (lat, lng, alt). 
As dimensões (tamanhos) assumidos na rota também podem ser alteradas. 

## Como Compilar

Para compilar o código digite no terminal: 

```
make all
```

Para apagar o código digite no terminal:

```
make clean
```

## Arquivos de Entrada

Existe um arquivo de configuração (config.txt) de parâmetros de entrada do algoritmo:

```
#POS_Z
10
#RADIUS_CIRCLE
10
#BASE_TRIANGLE
20
#BASE_RECTANGLE
20
``` 

## Arquivos de Saída

Um conjunto de arquivos de saída (route*.txt) são gerados nas pastas Circle, Triangle, Rectangle.
Os arquivos de saída contém rotas calculadas em coordenadas cartesianas separadas por vírgulas.

## Arquivos de Saída

As funções que criam as rotas de circulo, triângulo e retângulo são respectivamente: 

```c
void createRouteCircle(int discretization, char name[]);
void createRouteTriangle(int discretization, char name[]);
void createRouteRectangle(int discretization, char name[]);
``` 
