# G_PATH_REPLANNER4s

Abaixo serão descritas as especificações de entrada e saída do replanejador, chamado G_PATH_REPLANNER4s.

## Arquivos de Entradas

### Entrada do Mapa

O seu replanejador deverá utilizar como entrada um arquivo de mapa chamado "map". Atualmente, existem três versões/formatos disponíveis de mapas que o .sgl, .json e .xml.
Os três arquivos possuem o mesmo conteúdo, você poderá escolher o que você achar melhor. 

O arquivo .sgl possui o seguinte padrão: "map.sgl"

```
<number of polygons>
8
<number of zona n>
6
<number of zona p>
0
<number of zona b>
2
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
<x..., y..., n = 4, id = 0, type = b>
5.217207841852747,5.2651545384500755,3.300233886413994,3.2062236495879737
-5.235328792096436,-7.220231231242693,-7.277442485252136,-5.235324554850292
<x..., y..., n = 4, id = 0, type = b>
32.14638032476888,32.07322980969037,29.93355621932995,30.089646780698367
-5.5912675314779126,-7.676106710825428,-7.789440323181001,-5.591271982323588
```

A primeira linha contém um comentário, informando que a linha de baixo representa o "number of polygons"

A segunda linha contém o número de polígonos.

A terceira linha contém um comentário, informando que a linha de baixo representa o "number of zona n" (n representa região não navegável).

A quarta linha contém o número de regiões do tipo n.

A quinta linha contém um comentário, informando que a linha de baixo representa o "number of zona p" (p representa região penalizadora).

A sexta linha contém o número de regiões do tipo p.

A sétima linha contém um comentário, informando que a linha de baixo representa o "number of zona b" (b representa região bonificadora).

A oitava linha contém o número de regiões do tipo b.

A partir da nona linha o seguinte padrão se repete:

Linha contendo um comentário, informando que as duas linhas abaixo apresentam os valores de x e os valores de y, respectivamente.

Linha contendo as coordenadas X do polígono.

Linha contendo as coordenadas Y do polígono.

:warning: **OBS:** Não altere esse nome ("map.sgl") do arquivo de mapa.

:warning: **OBS:** Repare que o arquivo "map.sgl" está em coordenadas cartesianas.

:warning: **OBS:** Dê uma olhada nos arquivos "map.xml" e "map.json". Eles podem ser mais legíveis para você uma vez que seu formato é mais conhecido.

### Entrada do Estado do VANT

O seu replanejador deverá utilizar também como entrada um arquivo de configuração "config.sgl".

Abaixo um exemplo de arquivo de configuração: "config.sgl"

```
# Initial State: positionX positionY velocity angle
35.69719083150005 1.8037666763287938 1.5 6.265732014659643
```

A primeira linha contém um comentário sobre quem são os valores lidos abaixo. 

A segunda linha contém os valores da posiçãoX, posiçãoY, velocidade e orientação do drone. 

:warning: **OBS:** A posição do drone é medida em metros, a velocidade em m/s e a orientação em radianos.

:warning: **OBS:** Repare que o arquivo "config.sgl" está em coordenadas cartesianas. 

### Arquivos de Saída

O Path Replanner implementado deverá imprimir sua rota em um arquivo com o nome "output.txt". 
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

:warning: **OBS:** A rota de saída deve ser apenas 2D.

:warning: **OBS:** Deve-se utilizar o separador de espaço ou ainda o separador de tabulação (tab) ("\t").

:warning: **OBS:** O arquivo deverá ter necessariamente o nome "output.txt".

:warning: **OBS:** Repare que o arquivo "output.txt" está em coordenadas cartesianas. O sistema UAV-Toolkit irá converter a sua rota posteriormente para coordenadas geográficas.

## Códigos de Exemplo

Na pasta /UAV-Toolkit/Modules-IFA/G-Path-Replanner4s/Example/ contém quatro códigos de exemplo que são: 

* **replanner-making-line-c.c** -> Código em C que produz como saída uma linha reta.
* **replanner-making-line-cpp.cpp** -> Código em C++ que produz como saída uma linha reta.
* **replanner-making-line-python.py** -> Código em Python que produz como saída uma linha reta.
* **replanner-making-line-java.jar** -> Código em Java que produz como saída uma linha reta.

:warning: **OBS:** Repare que estes códigos são chamados/executados pelo script: "exec-replanner.sh".

:warning: **OBS:** Você pode/deve escolher qual código será executado usando este script. Abra-o e o edit. 
