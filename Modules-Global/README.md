# Modules-Global

Este diretório é responsável por agrupar um conjunto de algoritmos e módulos de hardware usados pelo sistema IFA e MOSA. 

Os diretórios a seguir contém códigos utilizados pelo sistema IFA.

* **Buzzer** -> Código responsável por acionar o alarme do drone após ocorrer uma falha crítica. 
* **Parachute** -> Código responsável por acionar o paraquedas do drone após ocorrer uma falha crítica. 
* **Sonar** -> Código responsável por fazer leituras de distância do drone até o solo.
* **LED** -> Código responsável por acionar ou apagar alguns LEDs do drone.
* **TemperatureSensor** -> Código responsável por pegar dados do sonar para correção da altitude do drone em caso de baixa altitude.

Os diretórios a seguir contém códigos utilizados pelo sistema MOSA.

* **Buzzer** -> Código responsável por acionar o buzzer do drone após um waypoint específico ter sido alcançado. 
* **Camera** -> Código responsável por fazer a retirada de fotografia e vídeos conforme acionado pelo sistema MOSA.
* **LED** -> Código responsável por acionar ou apagar alguns LEDs do drone.
* **Spraying** -> Código responsável por abrir ou fechar o sistema de pulverização do drone.
* **Route-Simplifier** -> Código responsável por fazer a simplificação de waypoints de forma a tornar mais leve para o piloto automático a execução da rota.

O diretório Files contém arquivos utilizados pelo sistema MOSA e IFA.

* **geoBase.txt** -> Arquivo com as coordenadas de longitude, latitude e altitude usado na transformação para coordenadas cartesianas.
* **featureMission.txt** -> Arquivo que contém as coordenadas de latitude, longitude e altitude, em que deve-se acionar o buzzer, câmera foto, câmera vídeo e spraying.
* **map-full.sgl** -> Arquivo de mapa completo (contendo obstáculos, região penalizadora e região bonificadora) usado pelo sistema.
* **map-nfz.sgl** -> Arquivo de mapa com apenas os obstáculos usado pelo sistema.

## Arquivos de configuração

Este diretório também possui dois arquivos de configuração que são:

* **config-global.properties** -> Arquivo usado pelos sistemas MOSA, IFA, GCS, S2DK para descrever os aspectos gerais da missão/segurança.
* **config-param.properties** -> Arquivo usado pelo sistema IFA para calibrar os parâmetros de voo do Piloto Automático.
