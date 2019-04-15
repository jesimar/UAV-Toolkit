# EXPERIMENTOS

A seguir serão apresentados um conjunto de tabelas contendo os resultados obtidos durante a minha tese de doutorado. A primeira tabela mostra algumas aeronaves avaliadas.

| Tipo de Aeronave   | Aeronave                  | Construída por UAV-Team   | Avaliada em Voo Real     | Avaliada em Voo Inteligente |
|--------------------|---------------------------|---------------------------|--------------------------|-----------------------------|
| Quadricóptero X4   | iDroneAlpha               | Sim                       | Sim                      | Sim                         |
| Quadricóptero X4   | iDroneBeta                | Sim                       | Sim                      | Sim                         |
| Quadricóptero X4   | iDroneGamma               | Sim                       | Sim                      | Não                         |
| Quadricóptero X4   | iDroneDelta               | Sim                       | Não                      | Não                         |
| Octocóptero X8+    | ARTI-15                   | Não                       | Sim                      | Não                         |
| Octocóptero X8+    | ARTI-40                   | Não                       | Sim                      | Não                         |
| Hexacóptero        | DroneSimões               | Não                       | Sim                      | Não                         |
| Asa Fixa           | Ararinha                  | Não                       | Não                      | Não                         |

A segunda tabela mostra alguns componentes de hardware avaliados.

| Tipo de Componente | Componentes de Hardware   | Avaliado em SITL          | Avaliado em HITL         | Avaliado em Voo Real     |
|--------------------|---------------------------|---------------------------|--------------------------|--------------------------|
| AutoPilot          | APM                       | N/A                       | N/A                      | Sim                      |
| AutoPilot          | Pixhawk                   | N/A                       | N/A                      | Sim                      |
| Companion Computer | Intel Edison              | N/A                       | Sim                      | Sim                      |
| Companion Computer | Raspberry Pi 2            | N/A                       | Sim                      | Sim                      |
| Companion Computer | Raspberry Pi 3            | N/A                       | Sim                      | Sim                      |
| Companion Computer | BeagleBone Black Wireless | N/A                       | Sim                      | Não                      |
| Companion Computer | BeagleBone Green          | N/A                       | Não                      | Não                      |
| Companion Computer | BeagleBone Blue           | N/A                       | Não                      | Não                      |
| Companion Computer | Odroid C1                 | N/A                       | Não                      | Não                      |
| Companion Computer | Raspberry Pi Zero         | N/A                       | Não                      | Não                      |
| Companion Computer | Intel Galileo             | N/A                       | Não                      | Não                      |
| Sensor             | Câmera                    | Sim (Simulado)            | Sim                      | Sim                      |
| Sensor             | Sonar                     | Sim (Simulado)            | Sim                      | Não                      |
| Sensor             | Temperatura               | Sim (Simulado)            | Sim                      | Não                      |
| Sensor             | Power Module              | Sim (Simulado)            | Sim                      | Sim                      |
| Atuador            | Buzzer                    | Sim (Simulado)            | Sim                      | Sim                      |
| Atuador            | LED                       | N/A                       | Sim                      | Não                      |
| Atuador            | Parachute                 | N/A                       | Não                      | Não                      |
| Atuador            | Spraying                  | N/A                       | Não                      | Não                      |

A terceira tabela mostra alguns componentes de software avaliados.

| Tipo de Componente | Componentes de Software              | Avaliado em SITL          | Avaliado em HITL         | Avaliado em Voo Real     |
|--------------------|--------------------------------------|---------------------------|--------------------------|--------------------------|
| Global/Geral       | Route Simplifier                     | Sim                       | Sim                      | Sim                      |
| Global/Geral       | Change Behavior - Route Circle       | Sim                       | Sim                      | Sim                      |
| Global/Geral       | Change Behavior - Route Triangle     | Sim                       | Sim                      | Não                      |
| Global/Geral       | Change Behavior - Route Rectangle    | Sim                       | Sim                      | Não                      |
| Global/Geral       | Integração com Oracle Drone          | Sim                       | Não                      | Não                      |
| Mapa               | Instâncias Reais                     | Sim                       | Sim                      | Sim                      |
| Mapa               | Instâncias Artificiais               | Sim                       | Não                      | Não                      |
| UAV-GCS            | Integração com Mapa (Graphics2D)     | Sim                       | Sim                      | Sim                      |
| UAV-GCS            | Integração com Google Maps           | Sim                       | Sim                      | Sim                      |
| UAV-GCS            | Tipo de Inserção de Falha - GCS      | Sim                       | Sim                      | Sim                      |
| IFA                | Fixed Route                          | Sim                       | Sim                      | Sim                      |
| IFA                | Controller - Baseado no Teclado      | Sim                       | Sim                      | Sim                      |
| IFA                | Controller - Baseado em Voz          | Sim                       | Sim                      | Não                      |
| IFA                | Replanner                            | Sim                       | Sim                      | Sim                      |
| IFA                | Replanner - Onboard                  | Sim                       | Sim                      | Sim                      |
| IFA                | Replanner - Offboard                 | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - MPGA4s - Onboard         | Sim                       | Sim                      | Sim                      |
| IFA                | Replanner - MPGA4s - Offboard        | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - GA4s - Onboard           | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - GA4s - Offboard          | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - GH4s - Onboard           | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - GH4s - Offboard          | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - DE4s - Onboard           | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - DE4s - Offboard          | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - MS4s - Onboard           | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - MS4s - Offboard          | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - GA-GA-4s - Onboard       | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - GA-GA-4s - Offboard      | Não                       | Não                      | Não                      |
| IFA                | Replanner - GA-GH-4s - Onboard       | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - GA-GH-4s - Offboard      | Não                       | Não                      | Não                      |
| IFA                | Replanner - Pre-Planned4s - Onboard  | Sim                       | Não                      | Não                      |
| IFA                | Replanner - Pre-Planned4s - Offboard | Não                       | Não                      | Não                      |
| IFA                | Replanner - G_PATH_REP.4s - Onboard  | Sim                       | Não                      | Não                      |
| IFA                | Replanner - G_PATH_REP.4s - Offboard | Sim                       | Não                      | Não                      |
| IFA                | Tipo de Inserção de Falha - NONE     | Sim                       | Sim                      | Sim                      |
| IFA                | Tipo de Inserção de Falha - WAYPOINT | Sim                       | Sim                      | Sim                      |
| IFA                | Tipo de Inserção de Falha - TIME     | Sim                       | Não                      | Não                      |
| IFA                | Tipo de Inserção de Falha - POSITION | Sim                       | Não                      | Não                      |
| MOSA               | Fixed Route                          | Sim                       | Sim                      | Sim                      |
| MOSA               | Fixed Route - Dinâmico               | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner                              | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - Onboard                    | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - Offboard                   | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - HGA4m - Onboard            | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - HGA4m - Offboard           | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - CCQSP4m - Onboard          | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - CCQSP4m - Offboard         | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - M-Adaptive4m - Onboard     | Sim                       | Não                      | Não                      |
| MOSA               | Planner - M-Adaptive4m - Offboard    | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - A_STAR4m - Onboard         | Sim                       | Não                      | Não                      |
| MOSA               | Planner - A_STAR4m - Offboard        | Sim                       | Não                      | Não                      |
| MOSA               | Planner - G_PATH_PLANNER4m - Onboard | Sim                       | Não                      | Não                      |
| MOSA               | Planner - G_PATH_PLANNER4m - Offboard| Sim                       | Não                      | Não                      |

A quarta tabela mostra as simulações de HITL feitos para a tese.

| Cenário     | CC               | Altura    | Path Planner | Path Replanner | Tipo de Falha     | Tipo Ação | Filmou o Voo | Status | OBS             |
|-------------|------------------|-----------|--------------|----------------|-------------------|-----------|--------------|--------|-----------------|
| Cenário I   | Raspberry Pi 3   | 5 m       | FixedRoute4m |                | WithoutFailure    |           | Sim          | FEITO  |                 |
| Cenário I   | Raspberry Pi 3   | 5 m       | FixedRoute4m |                | BadWeather        | RTL       | Sim          | FEITO  |                 |
| Cenário I   | Raspberry Pi 3   | 5 m       | FixedRoute4m | DE4s           | LowBattery-1      | Replanner | Sim          | FEITO  | Pousou em bonificadora     |
| Cenário I   | Raspberry Pi 3   | 5 m       | FixedRoute4m | DE4s           | LowBattery-2      | Replanner | Sim          | FEITO  | Pousou em bonificadora     |
| Cenário I   | Raspberry Pi 3   | 5 m       | FixedRoute4m | DE4s           | LowBattery-3      | Replanner | Sim          | FEITO  | Não pousou em bonificadora |
| Cenário I   | Intel Edison     | 5 m       | FixedRoute4m |                | WithoutFailure    |           | Não          | FEITO  |                 |
| Cenário I   | Intel Edison     | 5 m       | FixedRoute4m |                | BadWeather        | RTL       | Não          | FEITO  |                 |
| Cenário I   | Intel Edison     | 5 m       | FixedRoute4m | DE4s           | LowBattery-1      | Replanner | Não          | FEITO  | Pousou em bonificadora     |
| Cenário I   | Intel Edison     | 5 m       | FixedRoute4m | DE4s           | LowBattery-2      | Replanner | Não          | FEITO  | Pousou em bonificadora     |
| Cenário I   | Intel Edison     | 5 m       | FixedRoute4m | DE4s           | LowBattery-3      | Replanner | Não          | FEITO  | Pousou em bonificadora     |
| Cenário II  | Raspberry Pi 3   | 15 m      | CCQSP4m      |                | WithoutFailure    |           | Sim          | FEITO  |                 |
| Cenário II  | Raspberry Pi 3   | 15 m      | CCQSP4m      |                | AP-Failure-1      | VertLand  | Sim          | FEITO  |                 |
| Cenário II  | Raspberry Pi 3   | 15 m      | CCQSP4m      |                | AP-Failure-2      | VertLand  | Sim          | FEITO  |                 |
| Cenário II  | Raspberry Pi 3   | 15 m      | CCQSP4m      |                | AP-Failure-3      | VertLand  | Sim          | FEITO  |                 |
| Cenário II  | Raspberry Pi 3   | 15 m      | HGA4m        |                | WithoutFailure    |           | Sim          | FEITO  |                 |
| Cenário II  | Raspberry Pi 3   | 15 m      | HGA4m        |                | AP-Failure-1      | VertLand  | Sim          | FEITO  |                 |
| Cenário II  | Raspberry Pi 3   | 15 m      | HGA4m        |                | AP-Failure-2      | VertLand  | Sim          | FEITO  |                 |
| Cenário II  | Raspberry Pi 3   | 15 m      | HGA4m        |                | AP-Failure-3      | VertLand  | Sim          | FEITO  |                 |
| Cenário II  | BeagleBone Black | 15 m      | CCQSP4m      |                | WithoutFailure    |           | Não          | FEITO  |                 |
| Cenário II  | BeagleBone Black | 15 m      | CCQSP4m      |                | AP-Failure-1      | VertLand  | Não          | FEITO  |                 |
| Cenário II  | BeagleBone Black | 15 m      | CCQSP4m      |                | AP-Failure-2      | VertLand  | Não          | FEITO  |                 |
| Cenário II  | BeagleBone Black | 15 m      | CCQSP4m      |                | AP-Failure-3      | VertLand  | Não          | FEITO  |                 |
| Cenário II  | BeagleBone Black | 15 m      | HGA4m        |                | WithoutFailure    |           | Não          | FEITO  |                 |
| Cenário II  | BeagleBone Black | 15 m      | HGA4m        |                | AP-Failure-1      | VertLand  | Não          | FEITO  |                 |
| Cenário II  | BeagleBone Black | 15 m      | HGA4m        |                | AP-Failure-2      | VertLand  | Não          | FEITO  |                 |
| Cenário II  | BeagleBone Black | 15 m      | HGA4m        |                | AP-Failure-3      | VertLand  | Não          | FEITO  |                 |
| Cenário III | Raspberry Pi 3   | 20 m      | CCQSP4m      |                | WithoutFailure    |           | Sim          | FEITO  |                 |
| Cenário III | Raspberry Pi 3   | 20 m      | CCQSP4m      | MPGA4s         | LowBattery-1      | Replanner | Sim          | FEITO  |                 |
| Cenário III | Raspberry Pi 3   | 20 m      | CCQSP4m      | MPGA4s         | LowBattery-2      | Replanner | Sim          | FEITO  |                 |
| Cenário III | Raspberry Pi 3   | 20 m      | CCQSP4m      | MPGA4s         | LowBattery-3      | Replanner | Sim          | FEITO  |                 |
| Cenário III | Raspberry Pi 3   | 20 m      | HGA4m        |                | WithoutFailure    |           | Sim          | FEITO  |                 |
| Cenário III | Raspberry Pi 3   | 20 m      | HGA4m        | MPGA4s         | LowBattery-1      | Replanner | Sim          | FEITO  |                 |
| Cenário III | Raspberry Pi 3   | 20 m      | HGA4m        | MPGA4s         | LowBattery-2      | Replanner | Sim          | FEITO  |                 |
| Cenário III | Raspberry Pi 3   | 20 m      | HGA4m        | MPGA4s         | LowBattery-3      | Replanner | Sim          | FEITO  |                 |
| Cenário III | Intel Edison     | 20 m      | CCQSP4m      |                | WithoutFailure    |           | Não          | FEITO  |                 |
| Cenário III | Intel Edison     | 20 m      | CCQSP4m      | MPGA4s         | LowBattery-1      | Replanner | Não          | FEITO  | Pousou em bonificadora     |
| Cenário III | Intel Edison     | 20 m      | CCQSP4m      | MPGA4s         | LowBattery-2      | Replanner | Não          | FEITO  | Pousou em bonificadora     |
| Cenário III | Intel Edison     | 20 m      | CCQSP4m      | MPGA4s         | LowBattery-3      | Replanner | Não          | FEITO  | Não pousou em bonificadora |
| Cenário III | Intel Edison     | 20 m      | HGA4m        |                | WithoutFailure    |           | Não          | FEITO  |                 |
| Cenário III | Intel Edison     | 20 m      | HGA4m        | MPGA4s         | LowBattery-1      | Replanner | Não          | FEITO  | Pousou em bonificadora     |
| Cenário III | Intel Edison     | 20 m      | HGA4m        | MPGA4s         | LowBattery-2      | Replanner | Não          | FEITO  | Pousou em bonificadora     |
| Cenário III | Intel Edison     | 20 m      | HGA4m        | MPGA4s         | LowBattery-3      | Replanner | Não          | FEITO  | Pousou em bonificadora     |
| Cenário IV  | Raspberry Pi 3   | 12 m      | HGA4m        |                | WithoutFailure    |           | Sim          | FEITO  |                 |
| Cenário IV  | Raspberry Pi 3   | 12 m      | HGA4m        | GA4s           | MOSA-Failure      | Replanner | Sim          | FEITO  |                 |
| Cenário IV  | Raspberry Pi 3   | 12 m      | HGA4m        |                | AP-Failure        | VertLand  | Sim          | FEITO  |                 |
| Cenário IV  | Raspberry Pi 3   | 12 m      | HGA4m        |                | BadWeather        | RTL       | Sim          | FEITO  |                 |
| Cenário IV  | BeagleBone Black | 12 m      | HGA4m        |                | WithoutFailure    |           | Não          | FEITO  |                 |
| Cenário IV  | BeagleBone Black | 12 m      | HGA4m        | GA4s           | MOSA-Failure      | Replanner | Não          | FEITO  |                 |
| Cenário IV  | BeagleBone Black | 12 m      | HGA4m        |                | AP-Failure        | VertLand  | Não          | FEITO  |                 |
| Cenário IV  | BeagleBone Black | 12 m      | HGA4m        |                | BadWeather        | RTL       | Não          | FEITO  |                 |

A quinta tabela mostra os experimentos reais feitos para a tese.

| Cenário     | Drone       | AP      | CC               | Altura    | Path Planner | Path Replanner | Tipo de Falha     | Tipo Ação | Filmou o Voo | Status | OBS             |
|-------------|-------------|---------|------------------|-----------|--------------|----------------|-------------------|-----------|--------------|--------|-----------------|
| Cenário I   | iDroneAlpha | APM     | Raspberry Pi 3   | 5 m       | FixedRoute4m |                | WithoutFailure    |           | Sim          | FEITO  |                 |
| Cenário I   | iDroneAlpha | APM     | Intel Edison     | 5 m       | FixedRoute4m |                | BadWeather        | RTL       | Não          | FEITO  | Somente não subiu a 15 metros, fez o RTL sem sair dos 5 metros (estranho) |
| Cenário II  | iDroneAlpha | APM     | Raspberry Pi 3   | 15 m      | HGA4m        |                | WithoutFailure    |           | Sim          | FEITO  |                 |
| Cenário II  | iDroneBeta  | Pixhawk | Raspberry Pi 3   | 15 m      | CCQSP4m      |                | AP-Failure        | VertLand  | Sim          | FEITO  |                 |
| Cenário III | iDroneBeta  | Pixhawk | Raspberry Pi 3   | 20 m      | CCQSP4m      |                | WithoutFailure    |           | Sim          | FEITO  | Troquei a velocidade de 300cm/s para 200cm/s |
| Cenário III | iDroneBeta  | Pixhawk | Raspberry Pi 3   | 20 m      | HGA4m        | MPGA4s         | Low-Battery       | MPGA4s    | Sim          | FEITO  | Troquei a velocidade de 300cm/s para 200cm/s. Pousou em bonificadora |
| Cenário IV  | iDroneBeta  | Pixhawk | Raspberry Pi 3   | 12 m      | M-Adaptive4m |                | WithoutFailure    |           | Sim          | FEITO  |                 |
| Cenário IV  | iDroneBeta  | Pixhawk | Intel Edison     | 12 m      | M-Adaptive4m |                | BadWeather        | RTL       | Não          | FEITO  |                 |

Experimentos que deram errado.

| Cenário     | Drone       | AP      | CC               | Altura    | Path Planner | Path Replanner | Tipo de Falha     | Tipo Ação | Filmou o Voo | Status | OBS             |
|-------------|-------------|---------|------------------|-----------|--------------|----------------|-------------------|-----------|--------------|--------|-----------------|
| Cenário I   | iDroneAlpha | APM     | Intel Edison     | 5 m       | FixedRoute4m |                | BadWeather        | RTL       | Não          | Fazer  | Feito, mas o drone pousou antes da hora, pois cliquei no botão sem querer |
| Cenário II  | iDroneBeta  | Pixhawk | Raspberry Pi 3   | 15 m      | HGA4m        |                | WithoutFailure    |           | Sim          | Fazer  | Feito, mas o drone seguiu apenas as duas primeiras partes da rota |
| Cenário II  | iDroneAlpha | APM     | BeagleBone Black | 15 m      | HGA4m        |                | WithoutFailure    |           | Não          | Fazer  | Não funciona BBB com APM |
| Cenário IV  | iDroneBeta  | Pixhawk | Raspberry Pi 3   | 12 m      | M-Adaptive4m |                | WithoutFailure    |           | Sim          | Fazer  | Feito, mas o drone apresentou um comportamento não esperado |
| Cenário IV  | iDroneBeta  | Pixhawk | BeagleBone Black | 12 m      | M-Adaptive4m |                | WithoutFailure    |           | Não          | Fazer  | Não funciona BBB com Pixhawk | 

## Peças Trocadas

A seguir serão listados todos os equipamentos que foram trocados durante o doutorado, por terem sido queimados, estragados, quebrados, etc.
 
**Siglas**

* **RC** -> Rádio Controlado (Manual)
* **TeS** -> Teste em Solo
* **N/A** -> Não se Aplica ou Não disponível

| Data       | Nome do Drone | Tipo de Voo   | Local   | Equipamento               | Status        | Análise                                                                | Ação                                                                | Custo Financeiro |
|------------|---------------|---------------|---------|---------------------------|---------------|------------------------------------------------------------------------|---------------------------------------------------------------------|------------------|
| ??/??/2017 | iDroneAlpha   | RC            | Campus2 | Hélices 9443              | Quebrada      | Quebrou uma das hélices em colisão com parede                          | Trocamos as quatro hélices do drone                                 | R$48,00          |
| ??/??/2017 | iDroneAlpha   | RC            | Campus2 | Pés - Canetas             | Quebrado      | Quebrou duas canetas devido a colisão com o solo (8 metros de altura)  | Trocamos as canetas quebradas por novas canetas                     | R$0,00           | 
| ??/??/2017 | iDroneAlpha   | N/A           | N/A     | Bateria 2200 mAh          | Estragada     | Desgaste da bateria, devido ao uso e carregador ser de baixa qualidade | Paramos de usar o carregador e trocamos a bateria                   | R$80,00          |
| ??/??/2017 | iDroneAlpha   | Autônomo      | Campus1 | Pés - Canetas             | Quebrado      | Quebrou uma caneta devido a colisão com o solo (3 metros de altura)    | Trocamos as canetas quebradas por novas canetas                     | R$0,00           |
| ??/??/2018 | iDroneAlpha   | Autônomo      | Campus1 | Pés - Canetas             | Quebrado      | Quebrou uma caneta devido a colisão com o solo (2 metros de altura)    | Trocamos as canetas quebradas por novas canetas                     | R$0,00           |
| ??/??/2018 | iDroneAlpha   | RC            | Campus1 | Pés - Canetas             | Quebrado      | Quebrou uma caneta devido a colisão com o solo (3 metros de altura)    | Trocamos as canetas quebradas por novas canetas                     | R$0,00           |
| ??/09/2018 | iDroneGamma   | RC            | Campus2 | Hélices 9443              | Quebrada      | Quebrou uma das hélices em colisão com parede                          | Trocamos as quatro hélices do drone                                 | R$45,00          | 
| ??/09/2018 | iDroneGamma   | RC            | Campus2 | Pés - Suporte             | Quebrado      | Quebrou um dos suportes dos pés em colisão com o solo (3 metros)       | Colocamos fita isolante remendando o estrago ocorrido               | R$0,00           | 
| ??/09/2018 | iDroneGamma   | TeS           | Campus2 | ESC 30A Simonk            | Queimado      | ESC pegou fogo, provavelmente produto comprado com defeito             | Trocamos o ESC por um novo                                          | R$42,00          |
| ??/09/2018 | iDroneBeta    | TeS           | Campus2 | Bússola do GPS Neo-M8N    | Queimada      | Queimou após conectar na porta errada devido a uma maior voltagem      | Trocamos o GPS por outro - Tentar remover a bússola e colocar outra | R$75,00          |
| ??/09/2018 | iDroneAlpha   | RC e Autônomo | Campus2 | Suporte de GPS            | Quebrado      | Quebrou a base do suporte de GPS                                       | Tiramos a base e parafusamos o suporte diretamente no frame         | R$0,00           |
| ??/10/2018 | iDroneDelta   | N/A           | N/A     | Bússola do GPS Neo-M6N    | Não Funciona  | Não está funcionando                                                   | Nada feito - Analisar se está de fato com problemas                 | Talvés R$75,00   |
| ??/11/2018 | iDroneGamma   | RC            | Campus2 | Pés - Suporte             | Quebrado      | Quebrou um dos suportes dos pés em colisão com o solo (3 metros)       | Colocamos fita isolante remendando o estrago ocorrido               | R$0,00           | 
| 19/11/2018 | N/A           | N/A           | N/A     | Conversor USB-to-TTL      | Não funciona  | Não está funcionando                                                   | Nada feito - Analisar se está de fato com problemas                 | Talvés R$24,00   |
| 26/11/2018 | iDroneBeta    | Autônomo      | Campus2 | Pés - Apenas o Encaixe    | Quebrado      | Quebrou devido a colisão com o solo (7 metros de altura)               | Trocamos o encaixe do pé do drone                                   | R$0,00           | 
| 27/11/2018 | iDroneAlpha   | RC            | Campus2 | Pés - Caneta              | Quebrado      | Quebrou devido a colisão com o solo (3 metros de altura)               | Trocamos as canetas quebradas por novas canetas                     | R$0,00           |
| 27/11/2018 | iDroneAlpha   | N/A           | N/A     | Bateria 2200 mAh          | Estragada     | Desgaste da bateria, devido ao uso e mau gerenciamente da mesma        | Trocamos a bateria e estamos analisando se é possível reciclar      | Talvés R$175,00  |

**Prejuízos**

* R$290,00 prejuízo confirmado, apesar de alguns casos gerar material sobressalente como é o caso das hélices.
* R$274,00 prejuízo em potencial, caso não se consiga resgatar o itens destacados.
