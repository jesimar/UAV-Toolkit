# Modules-MOSA

Este diretório é responsável por agrupar um conjunto de algoritmos usados pelo sistema *Mission Oriented Sensors Array* (MOSA). 

Os algoritmos seguintes estão sendo utilizados como algoritmos de planejamento de rotas pelo MOSA.

* **HGA4m** ->  *Hybrid Genetic Algorithm for mission* - Utiliza um algoritmo genético híbrido (com resolução de modelo matemático) para encontrar a rota.
* **CCQSP4m** -> *Chance Constraint Qualitative State Plan for mission* - Utiliza um algoritmo baseado em Programação Linear Inteira-Mista para encontrar a rota.

O diretório seguinte contém missões pré-planejadas usadas pelo sistema MOSA.

* **Fixed-Route4m** -> *Fixed Route for mission* - Arquivos com missões completas pré-definidas. 

Os seguites métodos podem ser utilizados para controlar o drone:

* **Keyboard-Commands** -> Utiliza comandos do teclado para controlar a aeronave (somente SITL).
* **Voice-Commands** -> Utiliza comandos de voz para controlar a aeronave (somente SITL).

O diretório a seguir contém códigos utilizados pelo sistema MOSA.

* **Buzzer4m** -> Código responsável por acionar o buzzer do drone após um waypoint específico ter sido alcançado. 
