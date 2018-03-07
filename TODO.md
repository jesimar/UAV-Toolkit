# TO DO

A seguir encontra-se diversas atividades para serem feitas no projeto.

## Drone - iDroneAlpha:

* Incorporar hardware de leds no drone.
* Incorporar hardware de sonar no drone apontado para baixo.
* Incorporar hardware de sonar no drone apontado para frente.
* Incorporar hardware da câmera no drone.
* Trocar o hardware do piloto automático APM para Pixhawk.
* Trocar o hardware da Intel Edison pela Raspberry Pi 2 Modelo B.
* Desenvolver aplicação básica na Edison para acender os leds.
* Desenvolver aplicação básica na Edison para bater fotos e fazer filmagem com a câmera.
* Desenvolver aplicação básica na Edison para ler informações do sonar.
* Melhorar a calibração do sensor de alarme de bateria, pois ele está apintando apenas quando a bateria esta terminando.
* Melhorar a calibração do power module.

## Sistem IFA:

* Incorporar funcionalidade no monitoramento do IFA as seguintes leituras: 
    + Armazenar o tempo atual do Sistema Operacional.
    + Armazenar o tempo transcorrido do Sistema Operacional.
* Gravar todos os prints na tela em um arquivo de log para posterir análise. 
* Colocar no arquivo de properties o número do pino que liga o buzzer e o alarm (deixar isso genérico).
* Colocar no MOSA e IFA um objeto com todos os waypoints da rota.

## Sistema MOSA:

* Incorporar funcionalidade de recalculo de rota após um terminado tempo (feito).
* Incorporar funcionalidade de recalculo de rota após atingir um determinado waypoint (fazer).
* Incorporar planejador de missão CCQSP4m no MOSA.
* Gravar todos os prints na tela em um arquivo de log para posterir análise.

## Sistema UAV-SOA-Interface:

* Corrigir problema na função setHomeLocation(). Problema no cmds.download() e cmds.wait_ready().
* Corrigir problema nas funções appendWaypoint() e appendMission(). Problema no cmds.download() e cmds.wait_ready().
* OBS: Os problemas com cmds.download() e cmds.wait_ready() não ocorrem sempre. 
* Criar função para pairar o drone após chegar a uma waypoint final e não ter nenhuma missão para executar.
    + Observar: O porque o drone esta pousando no final da missão.
* Verificar se a função que verifica overhead está correta. O ideal é medir o overhead apenas do piloto automático, pois talvez o gargalo seja a comunicação entre a minha aplicação em java e a aplicação em python o que eu dúvido que seja isso, mas é bom testar. Dessa forma, é interessante capturar os instantes de tempo inicial e final dentro do código em python.

## QGroundControl:

* Corrigir problema na mensagem no inicio do programa, quando começo uma missão e fala que PreArm 3D Fix.
* Tentar descobrir como limpar a antiga missão do qgroundcontrol.

## UAV-Mission-Creator:

* Definir no Google Earth o Home-Location da missão.
* Definir no Google Earth um padrão para missão com foto.
* Definir no Google Earth um padrão para missão com vídeo.
* Definir no Google Earth um waypoint específico para TAKEOFF.
* Definir no Google Earth um waypoint específico para LAND_VERTICAL.

## GitHub

* Melhorar a descrição do projeto no github.
* Melhorar as descrições através de links, figuras, diagramas e vídeos.
* Colocar informações sobre como configurar os arquivos de properties.

## Documentação UAV-Toolkit

* Fazer um diagrama do hardware completo e colocar no UAV-Toolkit e Github.

## Geral

* Criar uma logo para o projeto.
* Documentar todos os códigos em java usando javadoc.
* Documentar todos os códigos em python e C.
* Colocar uma descrição em todos os scripts sobre o que eles fazem.
* Colocar uma descrição em todos os arquivos de properties e o que eles fazem.
* Medir usando o software Wicd a porcentagem de alcance das redes do Notebook, Celular e Roteador Wifi.
* Fazer experimento de payload do drone tentando levantar: 200g, 400g, 600g, 800g e 1000g.
* Fazer de papelão objetos que representam as regiões bonificadoras e regiões penalizadoras.
