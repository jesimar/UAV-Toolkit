# G_PATH_PLANNER4m

Abaixo serão descritas as especificações de entrada e saída do planejador, chamado G_PATH_PLANNER4m.

## Arquivos de Entradas

### Entrada do Mapa

O seu planejador deverá utilizar como entrada um arquivo de mapa chamado "map-nfz.sgl".

O arquivo possui o seguinte padrão: "map-nfz.sgl"

```
<number of polygons>
6
<x..., y..., n = 4, id = 0, type = n>
-0.1574530915090589,35.683493236344965,35.76497352100013,-0.29027759355372845
0.18960489221798285,-0.20113919146741488,-3.590007201222733,-3.1303520957103608
<x..., y..., n = 4, id = 0, type = n>
-0.13697045585684411,2.7159695135371034,2.688223095078793,-0.006680790582055828
-2.8846638150184924,-2.92419757186272,-9.645645524801244,-9.645732521797555
<x..., y..., n = 4, id = 0, type = n>
32.66175233327787,32.76904558271187,35.66068100343954,35.74171183095551
-9.935302864599567,-3.3971705583698513,-3.27882987018375,-9.971451659932168
<x..., y..., n = 4, id = 0, type = n>
-0.08562668434207914,35.76294020677896,35.74002871834279,-0.13039356655633066
-9.426170508596003,-9.783270640742897,-12.856028962402823,-12.471999223221738
<x..., y..., n = 4, id = 0, type = n>
14.901128378151062,14.899784908422248,13.329448947164504,13.254233659495148
-3.161140026479857,-6.648586038235425,-6.648639731077173,-3.161194968543261
<x..., y..., n = 4, id = 0, type = n>
18.8915941927357,18.93777932696633,20.508115099753994,20.507021774555028
-9.790579190714476,-6.290114975736857,-6.290060849773693,-9.790524335512249
```

A primeira linha contém um comentário, informando que a linha de baixo representa o "<number of polygons>"
A segunda linha contém o número de polígonos.
A partir da terceira linha o seguinte padrão se repete:
Linha contendo um comentário, informando que as duas linhas abaixo apresentam os valores de x e os valores de y, respectivamente.
Linha contendo as coordenadas X do polígono.
Linha contendo as coordenadas Y do polígono.

:warning: **OBS:** Não altere esse nome ("map-nfz.sgl") do arquivo de mapa.
:warning: **OBS:** Repare que o arquivo "map-nfz.sgl" está em coordenadas cartesianas.

### Entrada da Missão

O seu planejador deverá utilizar também como entrada um arquivo de missão chamado "waypointsMission.txt".

Abaixo um exemplo de arquivo de missão: "waypointsMission.txt"

```
36.00252853997811 1.510573225910600 0.000000000000000
34.62763097628212 35.74463456964809 0.000000000000000
3.656086252145222 2.618458547258856 0.000000000000000
36.00252853997811 1.510573225910600 0.000000000000000
```

O arquivo possui o seguinte padrão para cada linha: "coordenadaX coordenadaY coordenadaZ"

:warning: **OBS:** A coordenadaZ poderá/deverá ser ignorada não é necessário utilizá-la.

Deve-se pensar no valor `36.00252853997811 1.510573225910600` como sendo as coordenadas x, y do primeiro ponto. Chamarei ele de A para exemplificação.
Deve-se pensar no valor `34.62763097628212 35.74463456964809` como sendo as coordenadas x, y do segundo ponto. Chamarei ele de B para exemplificação.
Deve-se pensar no valor `3.656086252145222 2.618458547258856` como sendo as coordenadas x, y do terceiro ponto. Chamarei ele de C para exemplificação.
Deve-se pensar no valor `36.00252853997811 1.510573225910600` como sendo as coordenadas x, y do quarto ponto. Chamarei ele de D para exemplificação.

Dessa forma essa missão possui quatro waypoints objetivos. Logo, três rotas deverão ser calculada. Uma rota entre A e B. Uma rota entre B e C. E uma última rota entre C e D.
Após calcular estas três rotas uma arquivo de saída com as três rotas deverá ser impresso (veja especificação abaixo).

:warning: **OBS:** Repare que o arquivo "waypointsMission.txt" está em coordenadas cartesianas. 

### Arquivos de Saída

O Path Planner implementado deverá imprimir sua rota em um arquivo com o nome "output.txt". 
O arquivo deverá ter o seguinte formato para cada linha: "coordenadaX coordenadaY"

Abaixo um exemplo de arquivo de rota: "output.txt"

```
36.00252853997811 1.51057322591060
36.00252853997811 2.13557322591060
36.00252853997811 3.69807322591060
36.00252853997811 5.70345977458058
36.00252853997811 7.89903959758554
36.00252853997811 10.18432950908802
35.97628863095608 12.51447446483926
35.90567876742306 14.86704694271489
35.80787383565654 17.23083318165270
35.69647136977328 19.60022630112160
35.57827013683166 21.97242286085605
35.45666952036084 24.34602114072328
35.33336921212543 26.72032028065689
35.20921905800773 29.06371985062370
```

:warning: **OBS:** Deve-se utilizar o separador de espaço ou ainda o separador de tabulação (tab) ("\t").
:warning: **OBS:** O arquivo deverá ter necessariamente o nome "output.txt".
:warning: **OBS:** Repare que o arquivo "output.txt" está em coordenadas cartesianas. O sistema UAV-Toolkit irá converter a sua rota posteriormente para coordenadas geográficas.
