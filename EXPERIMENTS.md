# EXPERIMENTOS

A seguir serão apresentados um conjunto de tabelas contendo os resultados obtidos durante a minha tese de doutorado. A primeira tabela mostra algumas aeronaves avaliadas.

| Tipo de Aeronave   | Aeronave                  | Construída por UAV-Team   | Avaliada em Voo Real     |
|--------------------|---------------------------|---------------------------|--------------------------|
| Quadricóptero      | iDroneAlpha               | Sim                       | Sim                      |
| Quadricóptero      | iDroneBeta                | Sim                       | Não                      |
| Quadricóptero      | iDroneGamma               | Sim                       | Não                      |
| Quadricóptero      | iDroneDelta               | Sim                       | Não                      |
| Quadricóptero      | DroneOnofre               | Não                       | Não                      |
| Hexacóptero        | DroneSimões               | Não                       | Não                      |
| Asa Fixa           | Ararinha                  | Não                       | Não                      |

A segunda tabela mostra alguns componentes de hardware avaliados.

| Tipo de Componente | Componentes de Hardware   | Avaliado em SITL          | Avaliado em HITL         | Avaliado em Voo Real     |
|--------------------|---------------------------|---------------------------|--------------------------|--------------------------|
| AutoPilot          | APM                       | N/A                       | N/A                      | Sim                      |
| AutoPilot          | Pixhawk                   | N/A                       | N/A                      | TODO                     |
| Companion Computer | Intel Edison              | N/A                       | Sim                      | Sim                      |
| Companion Computer | Raspberry Pi 2            | N/A                       | Sim                      | Sim                      |
| Companion Computer | Raspberry Pi 3            | N/A                       | Sim                      | Sim                      |
| Companion Computer | BeagleBone Black Wireless | N/A                       | Sim                      | TODO                     |
| Companion Computer | Odroid C1                 | N/A                       | TODO                     | TODO                     |
| Companion Computer | Raspberry Pi Zero         | N/A                       | Não                      | Não                      |
| Companion Computer | Intel Galileo             | N/A                       | Não                      | Não                      |
| Sensor             | Câmera                    | Sim (Simulado)            | Sim                      | Sim                      |
| Sensor             | Sonar                     | Sim (Simulado)            | Sim                      | Sim                      |
| Sensor             | Temperatura               | Sim (Simulado)            | Sim                      | Sim                      |
| Sensor             | Power Module              | Sim (Simulado)            | Sim                      | Sim                      |
| Atuador            | Buzzer                    | Sim (Simulado)            | Sim                      | Sim                      |
| Atuador            | parachute                 | Não                       | Não                      | Não                      |
| Atuador            | LED                       | TODO                      | TODO                     | Não                      |
| Atuador            | Spraying                  | Não                       | Não                      | Não                      |

A terceira tabela mostra alguns componentes de software avaliados.

| Tipo de Componente | Componentes de Software              | Avaliado em SITL          | Avaliado em HITL         | Avaliado em Voo Real     |
|--------------------|--------------------------------------|---------------------------|--------------------------|--------------------------|
| Global/Geral       | Route Simplifier                     | Sim                       | Sim                      | Sim                      |
| Global/Geral       | Change Behavior - Route Circle       | Sim                       | TODO                     | Não                      |
| Global/Geral       | Change Behavior - Route Triangle     | Sim                       | TODO                     | Não                      |
| Global/Geral       | Change Behavior - Route Rectangle    | Sim                       | TODO                     | Não                      |
| Global/Geral       | Integração com Google Maps           | Sim                       | Sim                      | Sim                      |
| Global/Geral       | Integração com Oracle Drone          | Sim                       | Não                      | Não                      |
| Mapa               | Instâncias Reais                     | Sim                       | Sim                      | Sim                      |
| Mapa               | Instâncias Artificiais               | Sim                       | Não                      | Não                      |
| IFA                | Fixed Route                          | Sim                       | Sim                      | Sim                      |
| IFA                | Controller - Baseado no Teclado      | Sim                       | Sim                      | Sim                      |
| IFA                | Controller - Baseado em Voz          | Sim                       | Não                      | Não                      |
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
