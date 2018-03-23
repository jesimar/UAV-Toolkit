# Modules-Global

Este diretório é responsável por agrupar um conjunto de algoritmos e modulos de hardware usados pelo sistema IFA e MOSA. 

Os diretórios a seguir contém códigos utilizados pelo sistema IFA.

* **Buzzer** -> Código responsável por acionar o alarme do drone após ocorrer uma falha crítica. 

* **Parachute** -> Código responsável por acionar o paraquedas do drone após ocorrer uma falha crítica. 

Os diretórios a seguir contém códigos utilizados pelo sistema MOSA.

* **Buzzer** -> Código responsável por acionar o buzzer do drone após um waypoint específico ter sido alcançado. 

* **Camera** -> Código responsável por fazer a retirada de fotografia e vídeos conforme acionado pelo sistema MOSA.

## Arquivos de configuração

Este diretório possui dois arquivos de configuração que são:

* **config-global.properties** -> Arquivo usado pelos sistemas MOSA e IFA para descrever os aspectos gerais da missão.
* **config-param.properties** -> Arquivo usado pelo sistema IFA para calibrar os parâmetros do Piloto Automático.