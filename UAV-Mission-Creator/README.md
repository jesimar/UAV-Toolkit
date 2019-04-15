# UAV-Mission-Creator

O projeto UAV-Mission-Creator foi desenvolvido para auxiliar a projetar missões e mapas usando o [Google Earth](https://www.google.com/earth/index.html). Este projeto foi desenvolvido em Java usando o IDE [Netbeans](https://netbeans.org/).

## Visão Geral
 
Esse projeto conta com um conjunto de ferramentas para conversões de rotas em coordenadas cartesianas em coordenadas geográficas (e vice-versa), conta também com conversões de mapas projetados no Google Earth para mapas em coordenadas cartesianas. Dentre inúmeras outras funcionalidades.

Este sistema é o responsável por a partir de um arquivo de mapa geográfico (kml) (feito no Google Earth) transformar esse arquivo em um conjunto de arquivos necessários aos algoritmos de planejamento de caminho (HGA4m, CCQSP4m, AStar4m, G-Path-Planner4m) e replanejamento de caminho (MPGA4s, GA4s, GH4s, DE4s, MS4s, G-Path-Replanner4s).

A ideia geral do sistema pode ser vista na figura abaixo.

![](../Figures/sw-mission-creator.png)

Os arquivos de entrada/saída desse sistema ficam dentro do diretório: /UAV-Toolkit/Missions/

## Criando o arquivo KML

Para a criação do arquivo KML basta abrir o Google Earth. E criar um mapa, na criação desse mapa as seguinte tags devem ser utilizadas.

As seguintes notações foram definidas afim de mapear as regiões em que ocorrerá a missão:

* **map_nfz**: também chamada, região não navegável, esta região define as áreas que o VANT está estritamente proibido de sobrevoar ou pousar, podendo representar, por exemplo, bases militares, aeroportos, entre outras.
* **map_obstacle**: também chamada, região de obstáculo, esta região define as áreas que o VANT está estritamente proibido de sobrevoar ou pousar, podendo representar, por exemplo, edifícios, casas, entre outras.
* **map_penalty**: também chamada, região penalizadora, esta região define as áreas que o VANT pode sobrevoar, mas não deve pousar. Caso pouse, uma penalização será aplicada. Isso porque nessa área encontram-se estruturas de tamanho mediano como as árvores, lagos, montanhas, etc.
* **map_bonus**: também chamada, região bonificadora, esta região define as áreas que o VANT pode sobrevoar e pousar. Essa região pode representar a pista de pouso do VANT, planícies, entre outras.
* **map_scenic**: também chamada, região cênica, esta região define áreas que a aeronave deve sobrevoar, representa uma região de interesse.
* **map_frontier**: também chamada, região remanescente, define a fronteira da missão, ou seja, uma região a qual o VANT jamais deve sobrevoar fora da mesma, delimitando assim o espaço de voo/missão.

As seguintes notações foram definidas afim de registrar as atividades relacionadas a missão:

* **geo_base**: define um ponto de referência para a transformação entre os sistemas de coordenadas cartesianas e geográficas.
* **waypoint**: define um ponto alvo que a aeronave deve cumprir durante a sua missão. Os waypoints definidos serão utilizados pelo MOSA para estabelecer a rota a ser seguida pela aeronave.
* **cmd_picture**: define um ponto alvo em que uma fotografia deve ser retirada. O sistema MOSA ao atingir esse ponto efetua a retirada da fotografia.
* **cmd_photo_seq**: define um ponto alvo em que um conjunto de fotografias em sequência devem ser retiradas. O sistema MOSA ao atingir esse ponto irá iniciar a retirada da(s) fotografia(s) em sequência.
* **cmd_video**: define um ponto alvo a partir do qual se iniciara a filmagem da região.
* **cmd_buzzer**: define um ponto alvo em que um disparo de apito do buzzer é efetuado.
* **cmd_spraying_begin**: define um ponto alvo a partir do qual se iniciara a pulverização da região.
* **cmd_spraying_end**: define um ponto alvo indicando o término da pulverização.

## Arquivos de Entrada

* **config-mission.properties**: um arquivo de configuração para definição de um conjunto de propriedades importantes internas do UAV-Mission-Creator.

* **mission.kml**: um arquivo com o mapa e plano da missão planejada feito usando o Google Earth. Este arquivo pode ter qualquer nome, mas deve possuir a extensão kml. Extensões kmz ainda não são suportadas.

## Arquivos de Saída

* **map-nfz.sgl** -> especificação do mapa contendo as regiões de obstáculos em formato .SGL. Usado pelo algoritmo HGA4m.
* **map-nfz.json** -> especificação do mapa contendo as regiões de obstáculos em formato .JSON. Usado pelo algoritmo G-Path-Planner4m.
* **map-nfz.xml** -> especificação do mapa contendo as regiões de obstáculos em formato .XML. Usado pelo algoritmo G-Path-Planner4m.
* **map-nfz-astar.sgl** -> especificação do mapa contendo as regiões de obstáculos em formato .SGL. Usado pelo algoritmo A-Star4m.
* **map-full.sgl** -> especificação do mapa contendo as regiões de obstáculos, bonificadores e penalizadores em formato .SGL. Usado pelos algoritmos GA4s, DE4s, MPGA4s, GH4s, MS4s, GA-GA-4s e GA-GH-4s.
* **map-full.json** -> especificação do mapa contendo as regiões de obstáculos, bonificadores e penalizadores em formato .JSON. Usado pelo algoritmo G-Path-Replanner4s.
* **map-full.xml** -> especificação do mapa contendo as regiões de obstáculos, bonificadores e penalizadores em formato .XML. Usado pelo algoritmo G-Path-Replanner4s.
* **waypointsMission.txt** -> especificação do plano da missão, ou seja, conjunto de waypoints onde deseja-se sobrevoar com o VANT. Usado pelo algoritmo HGA4m.
* **mission-ccqsp.sgl** -> especificação do mapa e da missão em formato .SGL. Usado pelo algoritmo CCQSP4m.
* **geoBase.txt** -> especificação de um ponto base em coordenadas geográficas usado para conversão entre os sistemas de coordenadas cartesianas e geográficas e vice-versa. Usado pelos sistemas IFA e MOSA.
* **featureMission.txt** -> especificações de ações a serem executadas durante a realização da missão como: retirada de fotografias, filmagem, pulverização, acionamento do buzzer, etc. Usado pelo sistema MOSA.

## Como Usar

Abaixo encontra-se alguns vídeos mostrando um pouco como projetar os mapas usando o Google Earth.

[![](https://img.youtube.com/vi/UpTqucMuJt8/0.jpg)](https://www.youtube.com/watch?v=UpTqucMuJt8 "Criando Missões com Google Earth")

[![](https://img.youtube.com/vi/JgspSf9rvio/0.jpg)](https://www.youtube.com/watch?v=JgspSf9rvio "Criando Missões com Google Earth")

[![](https://img.youtube.com/vi/u5QslNeOEYs/0.jpg)](https://www.youtube.com/watch?v=u5QslNeOEYs "Criando Missões com Google Earth")

## Problemas

:warning: **OBS:** De acordo com algumas medidas minhas a imagem do Google Earth (Google Satélites) e a imagem de fundo do QGroundControl (Bing Satélites) possuem um deslocamento de entre 5,0 metros a 7,0 metros. 
Isso pode impactar e muito durante o planejamento da missão e fazer o drone colidir com algum obstáculo. Pois, o meu planejamento é feito baseado em waypoints e obstáculos e eles são definidos
baseados na imagem obtida. Eu costumo colocar uma margem de erro nos obstáculos, mas de acordo com tais medidas obtidas preciso aumentar ainda mais essa margem para pelo menos 7 metros, afim de garantir uma segurança mínima.
Nessa figura abaixo pode-se considerar as rotas na cor vermelha e verde como iguais, observando assim o erro de medida das imagens.

![](../Figures/error-image-satellites.png)

A Figura abaixo mostra a mesma rota obtida no software QGroundControl apenas mudando o tipo de mapa de Bing Satélities para Google Satélities. Nessa imagem foi ressaltado o deslocamento dos obstáculos em relação as duas rotas.

![](../Figures/error-image-satellites2.png)
