# TO DO

A seguir encontra-se diversas atividades para serem feitas no projeto.

## Drone - iDroneAlpha:

* Incorporar hardware de leds no drone.
* Incorporar hardware de sonar no drone apontado para baixo.
* Incorporar hardware de sonar no drone apontado para frente.
* Incorporar hardware da câmera no drone.
* Incorporar hardware de disparo de paraquedas.
* Trocar o hardware do piloto automático APM para Pixhawk.
* Trocar o hardware da Intel Edison pela Raspberry Pi 2 Modelo B.
* Desenvolver aplicação básica na Edison para acender os leds.
* Desenvolver aplicação básica na Edison para bater fotos e fazer filmagem com a câmera.
* Desenvolver aplicação básica na Edison para ler informações do sonar.
* Desenvolver aplicação básica na Edison para efetuar o disparo do paraquedas.

## Sistem IFA:

* Colocar no MOSA e IFA um objeto com todos os waypoints da rota.
* Implementar um sistema de reação do IFA quando a aeronave começar a perder altitude: por exemplo se a altura for menor que 1 metro o drone pousa (mas tem que ser a altura do sonar medida por alguns instantes para não pegar ruído).

## Sistema MOSA:

* Incorporar planejador de missão CCQSP4m no MOSA.

## Sistema UAV-GCS

* Desenvolver no UAV-GCS as seguintes funcionalidades: 
	+ Comandos possíveis: HOVER, STOP_MISSION, SUBIR_1METRO, DESCER_1METRO.
* Executar scripts automaticamente
* Colocar recurso para plotar a rota do drone em tempo real
* Colocar recurso para plotar a rota calculada pelo drone HGA4m e MPGA4s (no google maps).
* Colocar recurso para mapear obstáculos (definir regiões bonificadores, penalizadores, e nfz).

## Sistema UAV-SOA-Interface:

* Criar função para pairar o drone após chegar a uma waypoint final e não ter nenhuma missão para executar.
    + Observar: O porque o drone esta pousando no final da missão.
* Verificar se a função que verifica overhead está correta. O ideal é medir o overhead apenas do AP, pois talvez o gargalo seja a comunicação entre a minha aplicação em java e a aplicação em python o que eu dúvido que seja isso, mas é bom testar. Dessa forma, é interessante capturar os instantes de tempo inicial e final dentro do código em python.
* Diagramar as dependências entre os arquivos .py do sistema UAV-SOA-Interface com suas respectivas funções.
* Criar comando para desarmar o motor (mesmo que a aeronave esteja no ar). Usado para abrir paraquedas.

## Modules-MOSA:

* Colocar rotas standard na pasta Fixed-Route4m: Circle, Triangle, Rectangle, Infinity, Star, etc.
* Colocar para chamar as rotas standard dentro do sistema MOSA a partir do ponto onde o drone está.

## UAV-Mission-Creator:

* Definir no Google Earth um waypoint específico para TAKEOFF.
* Definir no Google Earth um waypoint específico para LAND_VERTICAL.
* Definir no Google Earth um padrão para missão com foto.
* Definir no Google Earth um padrão para missão com vídeo.
* Definir no Google Earth um padrão para missão com pulverização.

## GitHub e Documentação UAV-Toolkit

* Melhorar a descrição do projeto no github.
* Traduzir a descrição do projeto no github para o Inglês.
* Melhorar as descrições através de links, figuras, diagramas e vídeos.
* Colocar informações sobre como configurar os arquivos de properties.
* Futuro: Fazer um diagrama do hardware completo e colocar no UAV-Toolkit e Github.

## Legislação

* Ler documentação e leis sobre voos de drone na ANAC e ANATEL. 
* Fazer seguro de voo quando for voar.

## Geral

* Documentar todos os códigos em java usando javadoc.
* Fazer voo com drone pairando em uma altitude constante para capturar o erro do barômetro.
* Fazer de papelão objetos que representam as regiões bonificadoras e regiões penalizadoras.
* Fazer diagrama do sistema IFA e MOSA colocando a frequência de operação de cada uma das threads.
* Discutir com verônica se nossa simulação SITL-Edison não é na verdade HITL. Pois quem esta controlando o drone é na verdade o AP ou não?
* Melhorar o código evitando acoplamento e coesão do código em Java (trabalhar com interfaces e abstrações).

## Problemas:

Drone:
* Melhorar a calibração do sensor de alarme de bateria, pois ele está apintando apenas quando a bateria está terminando.
* Melhorar a calibração do power module.
* Verificar/corrigir problema da alimentação do ESC (não esta mais conseguindo alimentar a Intel Edison), medir a tensão do ESC.
* Calibrar o meu drone melhor (PIDs), segundo onofre eles estão descalibrados.
* Corrigir problema em que o AP mostra status informando CRITICAL, acredito que tenha algum problema de hardware.

Sistema UAV-SOA-Interface:
* Corrigir problema na função setHomeLocation(). Problema no cmds.download() e cmds.wait_ready().
* Corrigir problema nas funções appendWaypoint() e appendMission(). Problema no cmds.download() e cmds.wait_ready().
* OBS: Os problemas com cmds.download() e cmds.wait_ready() não ocorrem sempre. 

Sistema IFA:
* Discutir com Claudio uma forma fácil de fazer a projeção da aeronave no futuro após ocorrer a falha crítica.

Sistema SOA:
* Corrigir problema do drone em que fica informando mensagem na momento de armar (low battery) (verificar se é algo com power module) (nem sempre é necessário armar de forma manual)

QGroundControl:
* Corrigir problema na mensagem no início do programa, quando começo uma missão e fala que PreArm 3D Fix.
* Tentar descobrir como limpar a antiga missão do qgroundcontrol.


## Documentação Formal das Falhas no Sistema

* Falha no appendMission(MOSA) (SOA)
1. Falha em wait_ready
2. Não tem solução
3. Drone pode cair

* Falha no getHomeLocation (IFA) (SOA)
1. Falha em wait_ready
2. Não tem solução
3. Drone não cai

* Falha em getHomeLocation (IFA) (SOA)
1. Falha em attribute lat
2. Não tem solução (mas acredito que é só esperar mais 5 segundos entre a execução do SOA e o IFA)
3. Drone não cai

## Alguns Dilemas

## Trabalhos Futuros ou Não Fazer Mais

* Colocar no arquivo de properties o número do pino que liga o buzzer e o alarm (deixar isso genérico).
* Gravar todos os prints na tela em um arquivo de log para posterir análise (MOSA e IFA).
* Criar uma pasta de examples em meu projeto em que consiga testar o meu ambiente com baixíssimo nível de modificação. 
* Criar ambiente de testes da funções do UAV-SOA-Interface independente da linguagem Java em python. 
* Incorporar funcionalidade de recalculo de rota após atingir um determinado waypoint (no MOSA).
* Melhorar forma de alimentação da Intel Edison, ao invés de ligar os pinos do ESC (pinos de alimentação positivo e GND) na edison. Ligar os pinos Positivo e GND em um conector padrão de alimentação do Breakeout da Edison.
* Medir usando o software Wicd a porcentagem de alcance das redes do Notebook, Celular e Roteador Wifi.
* Fazer experimento de payload do drone tentando levantar: 200g, 400g, 600g, 800g e 1000g. Para descobrir 
* Incluir no IFA sistema para verificação de aeronaves intrusas incluindo informações como número de aeronaves intrusas, rotas percorridas por tais aeronaves, distância da aeronave intrusa mais próxima, projeção futura da aeroanve para verificar chance de colisão, conforme descrito em Mattei 2015.
* Fazer experimentos em que o IFA tomar o controle humano caso o mesmo coloque a aeronave em risco, por exemplo, ser humano aproxime demais o VANT de uma NFZ.

MOSA:
* Path Planner:
	* Integrar o módulo do HISA4m (Márcio).
	* Integrar o módulo do CSA4m (Márcio).
	* Baseado em Campos Potenciais.
	* Baseado em A*.
