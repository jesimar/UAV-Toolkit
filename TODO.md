# TO DO

A seguir encontra-se diversas atividades para serem feitas no projeto.

## Drone - iDroneAlpha:

* Incorporar hardware de leds no drone.
* Incorporar hardware de sonar no drone apontado para baixo.
* Desenvolver aplicação básica na Edison e Raspberry Pi para acender os leds.
* Desenvolver aplicação básica na Edison e Raspberry Pi para ler informações do sonar.
* Construir uma placa com: 2 leds (azul (indica led), vermelho (indica paraquedas)), dois sonares, um termometro, um buzzer. (comprar sensor temperatura).
* Parametrizar o número de sensores (sonares que temos em arquivo properties) e quais pinos utilizar.
* Ler parametro pin echo e trig do sonar do arquivo de properties.

## Sistema IFA:

* Refatorar todo o Código do IFA, MOSA, GCS, S2DK.
* Criar versão simplificada do clear simulations dentro do IFA.
* Implementar um sistema de reação do IFA quando a aeronave começar a perder altitude: por exemplo, se a altura for menor que 1 metro o drone pousa (mas tem que ser a altura do sonar medida por alguns instantes para não pegar ruído).
* Adicionar simplificador de rotas.
* Melhorar o código evitando acoplamento e coesão do código em Java (trabalhar com interfaces e abstrações).
* Deu errado RTL -> colocar em uma thread um verificador de RTL o tempo todo.

## Sistema MOSA:

* Adicionar simplificador de rotas.
* Adicionar A*.
* Melhorar o código evitando acoplamento e coesão do código em Java (trabalhar com interfaces e abstrações).
* Deve chamar as rotas standard a partir do ponto onde o drone está localizado.

## Sistema UAV-GCS

* Fazer um método na GCS para obter a Rota (mission) (getMission) e outro para obter um waypoint (acho que talvez seja desnecessário). Em seguida, plotar isso na GCS.
* Fazer um método na GCS que para a missão e executa um comportamento de andar em circulo (behavior) (definir tal behavior no arquivo de properties (circulo, quadrado ou triangulo) definir também o rario no arquivo). Plotar isso na GCS.
* Definir no google maps pontos para trocar velocidade atualizar isso também no protocolo. Isso será util para fazer missões que necessitam ser realizadas com duas velocidades uma velocidade mais lenta, por exemplo, para etapa de pulverização. (behavior diferente).
* Colocar um botão para fazer o clear e copy file.
* Renomear: Forward -> Norte, Right -> Leste, Left -> Oeste, etc.
* Calcular overhead de enviar a rota do MOSA para a GCS e do IFA para a GCS e vice-versa.
* Colocar marcadores especiais nos Plots (google maps e Plot Mission Márcio) para fotos, vídeo, buzzer, pulverização, waypoints.
* Pesquisar como executar UAV-GCS (google maps) sem acesso a internet.

## Sistema UAV-S2DK:

* Criar função para pairar o drone após chegar a uma waypoint final e não ter nenhuma missão para executar. 
* Observar o porquê o drone esta pousando no final da missão.
* Corrigir problema na função setHomeLocation(). Problema no cmds.download() e cmds.wait_ready().
* Corrigir problema nas funções appendWaypoint() e appendMission(). Problema no cmds.download() e cmds.wait_ready().
* OBS: Os problemas com cmds.download() e cmds.wait_ready() não ocorrem sempre.

## Geral

* Diagramar toda a máquina de estados dos modos de voo do drone (Guided, Auto, Stabilized, etc).
* Criar uma pasta de examples em meu projeto em que consiga testar o meu ambiente com baixíssimo nível de modificação. 
* Documentação: Melhorar a descrição do projeto no github.
    Usar os termos: 
        x86 para 32 bits
        x64 para 64 bits
        ARM32 para 32 bits
        ARM64 para 64 bits
* Documentação: Traduzir a descrição do projeto no github para o Inglês.
* Documentação: Melhorar as descrições do github através de links, figuras, diagramas e vídeos.
* Documentação: Fazer um diagrama do hardware completo e colocar no UAV-Toolkit e Github.
* Documentação: Colocar informações sobre como configurar os arquivos de properties no github.
* Documentação: Fazer diagrama do sistema IFA e MOSA colocando a frequência de operação de cada uma das threads.
* Documentar todos os códigos em java usando javadoc.
* Fazer voo com drone pairando em uma altitude constante para capturar o erro do barômetro.
* Fazer de papelão objetos que representam as regiões bonificadoras e regiões penalizadoras.
* Trocar licença de GPL 3.0 para MIT (UAV-Toolkit, UAV-Tools, Path-Replanning).
* Criar um github com somente os .jar que irá ficar no drone afim ter um ambiente executável apenas no drone.
* Estudar os dois canais de comunicação do AP (canal 1 e canal 2). Ver as características de ambos.

## Documentação Formal das Falhas no Sistema

* Falha no appendMission(MOSA) (S2DK)
1. Falha em wait_ready
2. Não tem solução
3. Drone pode cair

* Falha no getHomeLocation (IFA) (S2DK)
1. Falha em wait_ready
2. Não tem solução
3. Drone não cai

* Falha em getHomeLocation (IFA) (S2DK)
1. Falha em attribute lat
2. Não tem solução (mas acredito que é só esperar mais 5 segundos entre a execução do S2DK e o IFA)
3. Drone não cai
4. Print do Erro:
```
127.0.0.1 - - [22/May/2018 10:49:32] "GET /get-home-location/ HTTP/1.1" 500 -
Traceback (most recent call last):
  File "/media/jesimar/Workspace/Work/UAV/UAV-S2DK/server.py", line 25, in do_GET
    response = GET_URLS[self.path](request)
  File "/media/jesimar/Workspace/Work/UAV/UAV-S2DK/views.py", line 161, in getHomeLocation
    'home-location': [vehicle.home_location.lat, vehicle.home_location.lon, vehicle.home_location.alt]
AttributeError: 'NoneType' object has no attribute 'lat'
```

## Trabalho Alunos de IC:

* Fazer simplificador de rotas baseado em derivadas (Feito)
* Fazer planejador baseado em A* (Feito)
* Sistema de comunicação entre telemetria (air <-> ground) (Feito)
* Instalar/Configurar SO Yocto na Intel Galileo (Feito)
* Estudar ROS (Fazendo)
* Estudar FlytOS (Fazendo)

* Instalar/Configurar SO de tempo real na Intel Galileo 
* Fazer planejador baseado em Campos Potenciais
* Fazer planejador baseado em AG Puro
* Sistema de voz da Google
* Sistema de comunicação entre rádio controle (rádio controle -> receptor de rádio controle)
* UAV-Tests colocar ele para trabalhar e validar o sistema.

## Trabalho Doutorado Rafael:

* Fazer tradução de código de replanejador AG para linguagem C (Fazendo)
