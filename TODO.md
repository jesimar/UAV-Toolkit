# TO DO

A seguir encontra-se diversas atividades para serem feitas no projeto.

## Drone - iDroneAlpha:

* Incorporar hardware da câmera no drone.
* Incorporar hardware de leds no drone.
* Incorporar hardware de sonar no drone apontado para baixo.
* Desenvolver aplicação básica na Edison e Raspberry Pi para acender os leds.
* Desenvolver aplicação básica na Edison e Raspberry Pi para bater fotos e fazer filmagem com a câmera.
* Desenvolver aplicação básica na Edison e Raspberry Pi para ler informações do sonar.

## Sistem IFA:

* Incluir MultiStart no IFA
* Incluir MILP4s no IFA
* Implementar um sistema de reação do IFA quando a aeronave começar a perder altitude: por exemplo, se a altura for menor que 1 metro o drone pousa (mas tem que ser a altura do sonar medida por alguns instantes para não pegar ruído).
* Adicionar campos no arquivo de log como altitude do sonar, ligou led, bateu foto, etc.
* IFA deve encerrar sua execução quando o drone levantar o voo e depois pousar.
* IFA recebe a missão via socket e retorna uma mensagem se aceitou ou não a rota (motivo)
* IFA deve verificar se o arquivo geobase.txt encontra-se no diretório, pois irei abortar a missão caso não esteja.
* Adicionar simplificador de rotas.
* Fazer rota fixa para testar o IFA. Estressar o IFA para verificar reações.
* Definir melhor o home da missão e o launch da missão.
* IFA deu errado RTL -> colocar em uma thread um verificador de RTL o tempo todo.
1. Verificar bateria, GPS
2. Verificar missão antes de enviar ao AP, caso seja inviável não enviá-la.
3. Executa a missão.

## Sistema MOSA:

* Incorporar planejador de missão CCQSP4m no MOSA.
* MOSA deve encerrar sua execução quando o drone levantar o voo e depois pousar.
* MOSA deve tomar cuidado com o Exit(0), pois ele precisa avisar ao IFA antes de sair.
* MOSA deve atua direto na câmera (sem perguntar nada ao IFA)
* MOSA não atua direto com a missão (todas as informações devem ser passadas ao IFA)
* Definir melhor o home da missão e o launch da missão.
* Adicionar simplificador de rotas.

## Sistema UAV-GCS

* Desenvolver no UAV-GCS as seguintes funcionalidades: HOVER, STOP_MISSION
* Melhorar os aspectos da interface gráfica do UAV-GCS.
* Fazer planejamento de caminho na GCS (GROUND_AIR e apenas AIR).
* Só habilitar recurso se tiver sensor (atuador) no arquivo de config-global.properties.
* Renomear: Forward -> Norte, Right -> Leste, Left -> Oeste, etc.
* Fazer programa em Java que plot mapa e rota somente (coordenadas cartesianas) (passar esse código para Rafael Doutorando).
* Usar o programa acima dentro do UAV-GCS.

## Sistema UAV-SOA-Interface:

* Corrigir problema na função setHomeLocation(). Problema no cmds.download() e cmds.wait_ready().
* Corrigir problema nas funções appendWaypoint() e appendMission(). Problema no cmds.download() e cmds.wait_ready().
* OBS: Os problemas com cmds.download() e cmds.wait_ready() não ocorrem sempre.

## UAV-Mission-Creator:

## Legislação

* Ler documentação e leis sobre voos de drone na ANAC e ANATEL. 
* Fazer seguro de voo quando for voar.

## Geral

* Documentação: Melhorar a descrição do projeto no github.
* Documentação: Traduzir a descrição do projeto no github para o Inglês.
* Documentação: Melhorar as descrições do github através de links, figuras, diagramas e vídeos.
* Documentação: Colocar informações sobre como configurar os arquivos de properties no github.
* Documentação: Fazer diagrama do sistema IFA e MOSA colocando a frequência de operação de cada uma das threads.
* Documentar todos os códigos em java usando javadoc.
* Fazer voo com drone pairando em uma altitude constante para capturar o erro do barômetro.
* Fazer de papelão objetos que representam as regiões bonificadoras e regiões penalizadoras.
* Melhorar o código evitando acoplamento e coesão do código em Java (trabalhar com interfaces e abstrações).

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
4. Print do Erro:
```
127.0.0.1 - - [22/May/2018 10:49:32] "GET /get-home-location/ HTTP/1.1" 500 -
Traceback (most recent call last):
  File "/media/jesimar/Workspace/Work/UAV/UAV-SOA-Interface/server.py", line 25, in do_GET
    response = GET_URLS[self.path](request)
  File "/media/jesimar/Workspace/Work/UAV/UAV-SOA-Interface/views.py", line 161, in getHomeLocation
    'home-location': [vehicle.home_location.lat, vehicle.home_location.lon, vehicle.home_location.alt]
AttributeError: 'NoneType' object has no attribute 'lat'
```

## Alguns Dilemas


## Trabalho Alunos de IC:

* Instalar/Configurar SO Yocto na Intel Galileo (Feito)
* Instalar/Configurar SO de tempo real na Intel Galileo 
* Fazer planejador baseado em A* (Fazendo)
* Fazer planejador baseado em Campos Potenciais
* Fazer planejador baseado em AG Puro
* Fazer simplificador de rotas baseado em derivadas (Feito)
* Sistema de voz da Google
* Trabalhar com sistema de paraquedas
* Trabalhar com sistema de câmera
* Trabalhar com sistema sonar + edison
* Trabalhar com sistema sonar + raspberry pi
* Fazer programa inicilizar junto com o sistema operacional (ubuntu, raspbian, yocto linux)
* Aprender a trabalhar com xterminal usando a raspberry pi (terminal para acessar raspberry via ambiente grafico)
* Sistema de comunicação entre telemetria (air <-> ground).
* Sistema de comunicação entre rádio controle (rádio controle -> receptor de rádio controle)
* UAV-Tests colocar ele para trabalhar e validar o sistema.

## Trabalho Doutorado Rafael:

* Fazer tradução de código de replanejador AG para linguagem C (Fazendo)

## Trabalhos Futuros ou Não Fazer Mais

* Colocar no arquivo de properties o número do pino que liga o buzzer e o alarm (deixar isso genérico).
* Gravar todos os prints na tela em um arquivo de log para posterir análise (MOSA e IFA).
* Criar uma pasta de examples em meu projeto em que consiga testar o meu ambiente com baixíssimo nível de modificação. 
* Criar ambiente de testes da funções do UAV-SOA-Interface independente da linguagem Java em python. 
* Trabalhar melhor no getHomeLocation esta função está dando problemas com a placa Pixhawk (com a APM funciona perfeitamente).
* Documentação: Fazer um diagrama do hardware completo e colocar no UAV-Toolkit e Github.

* QGroundControl: Tentar descobrir como limpar a antiga missão do QGroundControl.
* QGroundControl: Corrigir problema na mensagem no início do programa, quando começo uma missão e fala que PreArm 3D Fix.

* Hardware: Melhorar forma de alimentação da Intel Edison, ao invés de ligar os pinos do ESC (pinos de alimentação positivo e GND) na edison. Ligar os pinos Positivo e GND em um conector padrão de alimentação do Breakeout da Edison.
* Hardware: Incorporar hardware/software de disparo de paraquedas.
* Hardware: Incorporar hardware/software de sonar no drone apontado para frente.
* Hardware: Incorporar hardware/software de pulverização de plantações.


* Drone: Melhorar a calibração do sensor de alarme de bateria, pois ele está apintando apenas quando a bateria está terminando.
* Drone: Melhorar a calibração do power module no piloto automático.
* Drone: Calibrar o meu drone melhor (PIDs), segundo o Onofre eles estão descalibrados.
* Drone: Corrigir problema em que o AP mostra status informando CRITICAL, acredito que tenha algum problema de hardware.

* UAV-IFA: Incluir verificação de aeronaves intrusas incluindo informações como número de aeronaves intrusas, rotas percorridas por tais aeronaves, distância da aeronave intrusa mais próxima, projeção futura da aeroanve para verificar chance de colisão, conforme descrito em Mattei 2015.
* UAV-IFA: Abrir o paraquedas somente se der 2Dfix ou 1Dfix ou 0Dfix, por mais de 2 segundos.
* UAV-IFA deve verificar a rota antes de enviar para o PA. Verificar se existe mais de um comando de TAKEOFF. Só pode haver um comando desse tipo.
* UAV-IFA deve verificar se existe mais de um comando do tipo LAND, LAND_VERTICAL ou RTL. Só pode haver um comando desse tipo.
* UAV-IFA deve ter um objeto com todos os waypoints da rota.
* UAV-IFA discutir uma forma fácil de fazer a projeção da aeronave no futuro após ocorrer a falha crítica.

* UAV-MOSA: Incorporar funcionalidade de recalculo de rota após atingir um determinado waypoint.
* UAV-MOSA deve ter um objeto com todos os waypoints da rota.
* UAV-MOSA deve aguardar até que o modo seja STANDBY, antes disso nao adianta calcular nada.
* UAV-MOSA deve chamar as rotas standard a partir do ponto onde o drone está localizado.

* UAV-GCS: Executar scripts automaticamente
* UAV-GCS: Colocar recurso para plotar a rota do drone em tempo real
* UAV-GCS: Colocar recurso para plotar a rota calculada pelo drone HGA4m e MPGA4s (no google maps).
* UAV-GCS: Colocar recurso para mapear obstáculos (definir regiões bonificadores, penalizadores, e nfz).

* UAV-SOA-Interface: Criar função para pairar o drone após chegar a uma waypoint final e não ter nenhuma missão para executar. 
* UAV-SOA-Interface: Observar o porquê o drone esta pousando no final da missão.
* UAV-SOA-Interface: Corrigir problema do drone em que fica informando mensagem na momento de armar (low battery). Verificar se é algo com power module. Nem sempre é necessário armar de forma manual.
* UAV-SOA-Interface: Criar comando para desarmar o motor (mesmo que a aeronave esteja no ar). Usado para abrir paraquedas.
* UAV-SOA-Interface: Verificar se a função que verifica overhead está correta. O ideal é medir o overhead apenas do AP, pois talvez o gargalo seja a comunicação entre a minha aplicação em java e a aplicação em python o que eu dúvido que seja isso, mas é bom testar. Dessa forma, é interessante capturar os instantes de tempo inicial e final dentro do código em python.

* Experimento: Medir usando o software Wicd a porcentagem de alcance das redes do Notebook, Celular e Roteador Wifi.
* Experimento: Fazer experimento de payload do drone tentando levantar: 200g, 400g, 600g, 800g e 1000g. Para descobrir 
* Experimento: em que o IFA tomar o controle humano caso o mesmo coloque a aeronave em risco, por exemplo, ser humano aproxime demais o VANT de uma NFZ.
* Aplicação: aplicação básica na Edison e Raspberry Pi para efetuar o disparo do paraquedas.
* Aplicação: que captura os comandos digitados no controle de vídeo game B-MAX para controlar o drone.
* Aplicação: em que tenho além do iDroneAlpha, em solo um computador com GPS, a cada segundo eu passo para a aeronave as minhas coordenadas GPS e a aeronave me segue, Follow-me. (Drone Seguindo Notebook)
* Aplicação: em que o meu drone segue não o meu notebook, mas outro drone em que um drone passa para o outro as coordenadas GPS, Follow-Drone. (Drone Seguindo drone)
* MOSA - Path Planner:
1. Integrar o módulo do HISA4m (Márcio).
2. Integrar o módulo do CSA4m (Márcio).
3. Integrar o módulo do HCCQSP (Márcio).
4. Baseado em Campos Potenciais.
5. Baseado em A*.

* Criar um UAV-Text-Commands: Colocar em um arquivo de texto uma lista de comandos e mandar o drone seguir a missão.

-----Exemplo 1 (primeiro tipo de arquivo )------
| takeoff                                      |
| forward                                      |
| forward                                      |
| right                                        |
| back                                         |
| land                                         |
------------------------------------------------

------Exemplo 2 (segundo tipo de arquivo):------
| takeoff                                      |
| forward 10                                   |   (metros)
| rigth 4                                      |   (metros)
| forward                                      |   (se não escrito a metragem o drone move-se 3 metros)
| left 5                                       |
| land                                         |
------------------------------------------------
