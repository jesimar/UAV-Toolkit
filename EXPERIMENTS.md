# EXPERIMENTOS

A seguir serão apresentados um conjunto de tabelas contendo os resultados obtidos durante a minha tese de doutorado. A primeira tabela mostra algumas aeronaves avaliadas.

| Tipo de Aeronave   | Aeronave                  | Construída por UAV-Team   | Avaliada em Voo Real     | Avaliada em Voo Inteligente |
|--------------------|---------------------------|---------------------------|--------------------------|-----------------------------|
| Quadricóptero      | iDroneAlpha               | Sim                       | Sim                      | Sim                         |
| Quadricóptero      | iDroneBeta                | Sim                       | Sim                      | Não                         |
| Quadricóptero      | iDroneGamma               | Sim                       | Sim                      | Não                         |
| Quadricóptero      | iDroneDelta               | Sim                       | Não                      | Não                         |
| Quadricóptero      | DroneOnofre               | Não                       | Não                      | Não                         |
| Hexacóptero        | DroneSimões               | Não                       | Sim                      | Não                         |
| Asa Fixa           | Ararinha                  | Não                       | Não                      | Não                         |

A segunda tabela mostra alguns componentes de hardware avaliados.

| Tipo de Componente | Componentes de Hardware   | Avaliado em SITL          | Avaliado em HITL         | Avaliado em Voo Real     |
|--------------------|---------------------------|---------------------------|--------------------------|--------------------------|
| AutoPilot          | APM                       | N/A                       | N/A                      | Sim                      |
| AutoPilot          | Pixhawk                   | N/A                       | N/A                      | TODO                     |
| Companion Computer | Intel Edison              | N/A                       | Sim                      | Sim                      |
| Companion Computer | Raspberry Pi 2            | N/A                       | Sim                      | Sim                      |
| Companion Computer | Raspberry Pi 3            | N/A                       | Sim                      | Sim                      |
| Companion Computer | BeagleBone Black Wireless | N/A                       | Sim                      | TODO                     |
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
| Atuador            | LED                       | Não                       | Sim                      | Não                      |
| Atuador            | parachute                 | Não                       | Não                      | Não                      |
| Atuador            | Spraying                  | Não                       | Não                      | Não                      |

A terceira tabela mostra alguns componentes de software avaliados.

| Tipo de Componente | Componentes de Software              | Avaliado em SITL          | Avaliado em HITL         | Avaliado em Voo Real     |
|--------------------|--------------------------------------|---------------------------|--------------------------|--------------------------|
| Global/Geral       | Route Simplifier                     | Sim                       | Sim                      | Sim                      |
| Global/Geral       | Change Behavior - Route Circle       | Sim                       | TODO                     | Não                      |
| Global/Geral       | Change Behavior - Route Triangle     | Sim                       | TODO                     | Não                      |
| Global/Geral       | Change Behavior - Route Rectangle    | Sim                       | TODO                     | Não                      |
| Global/Geral       | Integração com Mapa (Graphics2D)     | Sim                       | Sim                      | Sim                      |
| Global/Geral       | Integração com Google Maps           | Sim                       | Sim                      | Sim                      |
| Global/Geral       | Integração com Oracle Drone          | Sim                       | Não                      | Não                      |
| Mapa               | Instâncias Reais                     | Sim                       | Sim                      | Sim                      |
| Mapa               | Instâncias Artificiais               | Sim                       | Não                      | Não                      |
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
| IFA                | Replanner - DE4s - Onboard           | Sim                       | Não                      | Não                      |
| IFA                | Replanner - DE4s - Offboard          | Sim                       | Não                      | Não                      |
| IFA                | Replanner - MS4s - Onboard           | Sim                       | Não                      | Não                      |
| IFA                | Replanner - MS4s - Offboard          | Sim                       | Não                      | Não                      |
| IFA                | Replanner - GA-GA-4s - Onboard       | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - GA-GA-4s - Offboard      | Não                       | Não                      | Não                      |
| IFA                | Replanner - GA-GH-4s - Onboard       | Sim                       | Sim                      | Não                      |
| IFA                | Replanner - GA-GH-4s - Offboard      | Não                       | Não                      | Não                      |
| IFA                | Replanner - Pre-Planned4s - Onboard  | Sim                       | Não                      | Não                      |
| IFA                | Replanner - Pre-Planned4s - Offboard | Não                       | Não                      | Não                      |
| IFA                | Replanner - G_PATH_REP.4s - Onboard  | Sim                       | Não                      | Não                      |
| IFA                | Replanner - G_PATH_REP.4s - Offboard | Sim                       | Não                      | Não                      |
| MOSA               | Fixed Route                          | Sim                       | Sim                      | Sim                      |
| MOSA               | Fixed Route - Dinâmico               | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner                              | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - Onboard                    | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - Offboard                   | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - HGA4m - Onboard            | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - HGA4m - Offboard           | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - CCQSP4m - Onboard          | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - CCQSP4m - Offboard         | Sim                       | Sim                      | Sim                      |
| MOSA               | Planner - A_STAR4m - Onboard         | Sim                       | Não                      | Não                      |
| MOSA               | Planner - A_STAR4m - Offboard        | Sim                       | Não                      | Não                      |
| MOSA               | Planner - PATH_PLANNER4m - Onboard   | Sim                       | Não                      | Não                      |
| MOSA               | Planner - PATH_PLANNER4m - Offboard  | Sim                       | Não                      | Não                      |

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
| Cenário I   | iDroneAlpha | APM     | Raspberry Pi 3   | 5 m       | FixedRoute4m |                | WithoutFailure    |           | Sim          | FEITO  |                 | ok
| Cenário I   | iDroneAlpha | APM     | Intel Edison     | 5 m       | FixedRoute4m |                | BadWeather        | RTL       | Não          | Fazer  | Feito, mas o drone pousou antes da hora, pois cliquei no botão sem querer |
| Cenário II  | iDroneAlpha | APM     | Raspberry Pi 3   | 15 m      | CCQSP4m      |                | WithoutFailure    |           | Sim          | Fazer  |                 | ok
| Cenário II  | iDroneAlpha | APM     | BeagleBone Black | 15 m      | HGA4m        |                | AP-Failure        | VertLand  | Não          | Fazer  |                 | ok
| Cenário III | iDroneBeta  | Pixhawk | Raspberry Pi 3   | 20 m      | CCQSP4m      |                | WithoutFailure    |           | Sim          | Fazer  |                 | ok
| Cenário III | iDroneBeta  | Pixhawk | Intel Edison     | 20 m      | HGA4m        |                | WithoutFailure    |           | Não          | Fazer  |                 | ok
| Cenário IV  | iDroneBeta  | Pixhawk | Raspberry Pi 3   | 12 m      | HGA4m        |                | WithoutFailure    |           | Sim          | Fazer  |                 | ok
| Cenário IV  | iDroneBeta  | Pixhawk | BeagleBone Black | 12 m      | HGA4m        |                | BadWeather        | RTL       | Não          | Fazer  |                 | ok
