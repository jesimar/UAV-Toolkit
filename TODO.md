# TO DO 

A seguir encontra-se diversas atividades para serem feitas no projeto.

## Drone - iDroneAlpha:

* Desabilitar o failsafe do iDroneBeta, iDroneGamma, iDroneDelta

* Compilar todos os códigos em C para as suas respectivas arquiteturas.
1. RouteStandard4m-PC -> Sim
2. RouteStandard4m-Edison -> Não
3. RouteStandard4m-RPi -> Não
4. RouteStandard4m-BBB -> Não
5. RouteStandard4m-Odroid -> Não

## Sistema IFA:

* Trocar velocidade de subida e decida se a bateria estiver acabando (automaticamente no IFA).
* Trocar velocidade de navegação se bateria estiver acabando (automaticamente no IFA).

## Sistema MOSA:

* MOSA Adaptativo.
* Colocar missão no Route-Standard de zig zag para pulverização.
* Incluir tempo-maximo para processamento no arquivo de config-global.properties e alterar no arquivo instance-base do modulo CCQSP4m.

## Sistema UAV-GCS

* Calcular overhead de enviar a rota do MOSA para a GCS e do IFA para a GCS e vice-versa.
* Plotar na GCS as rotas do Behavior
* Plotar na GCS as rotas calculadas nas placas do MOSA.
* Definir no google maps pontos para trocar velocidade atualizar isso também no protocolo. 
		Isso será util para fazer missões que necessitam ser realizadas com duas velocidades uma velocidade mais lenta, 
		por exemplo, para etapa de pulverização. (behavior diferente).

## Sistema UAV-S2DK:

* Corrigir problema nas funções appendWaypoint() e appendMission(). Problema no cmds.download() e cmds.wait_ready().
* OBS: Os problemas com cmds.download() e cmds.wait_ready() não ocorrem sempre.

## Geral

* Documentação: Fazer um diagrama do hardware completo e colocar no UAV-Toolkit e Github.
* Documentação: Fazer diagrama do sistema IFA e MOSA colocando a frequência de operação de cada uma das threads.

## Documentação Formal das Falhas no Sistema

* Falha no appendMission(MOSA) (S2DK)
1. Falha em wait_ready
2. Não tem solução
3. Drone pode cair

* Falha na decolagem: o drone durante a decolagem enquanto ganha altitude, do nada troca o modo de voo para LAND, abortando assim a decolagem e o restante da missão que ainda não foi setado (essa falha ocorreu apenas na Intel Edison). Não sei porque ainda.

## Trabalho Alunos de IC:

* Fazer simplificador de rotas baseado em derivadas (Feito)
* Fazer planejador baseado em A* (Feito - Pequeno defeito - desistimos de procurar o erro)
* Estudar FlytOS (Feito - problemas com instalação/configuração - desistimos de tentar instalar e usar)
* Instalar/Configurar SO Yocto na Intel Galileo (Feito)
* Estudar ROS (Fazendo)
* Instalar/Configurar SO na Raspberry Pi Zero e habilitar o SSH (Fazendo)

## Trabalho Doutorado Rafael:

* Fazer tradução de código de replanejador AG para linguagem C (Fazendo)
* Fazer iniciador automático do sistema para inclusão de path planner (Fazendo)
