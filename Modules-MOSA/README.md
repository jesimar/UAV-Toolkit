# Modules-MOSA

Este diretório é responsável por agrupar um conjunto de algoritmos usados pelo sistema *Mission Oriented Sensors Array* (MOSA). 

Os algoritmos seguintes estão sendo utilizados como algoritmos de planejamento de rotas pelo MOSA.

* **HGA4m** ->  *Hybrid Genetic Algorithm for mission* - Utiliza um algoritmo genético híbrido com grafo de visibilidade e resolução de modelo de Programação Linear Inteira-Mista (PLIM) para encontrar a rota. Necessita do IBM CPLEX instalado para utilizá-lo. (Artigo GECCO 2016) [[Link](https://dl.acm.org/citation.cfm?id=2908919)]

* **CCQSP4m** -> *Chance Constraint Qualitative State Plan for mission* - Utiliza um algoritmo baseado em Programação Linear Inteira-Mista (PLIM) para encontrar a rota. Necessita do IBM CPLEX instalado para utilizá-lo. [[Link](http://www.teses.usp.br/teses/disponiveis/55/55134/tde-05122017-083420/pt-br.php)] 

* **A-Star4m** -> Algoritmo A* para missão. 

* **G-Path-Planner4m** -> *Generic Path Planner for mission* - Planejador de rotas genérico para missão. 

* **Route-Standard4m** -> Algoritmo para trocar o comportamento de voo do drone por alguma rota default (circulo, triângulo ou retângulo). Dessa forma, esse algoritmo gera uma dessas possíveis rotas baseado na posição do drone capturada em tempo real. 

O diretório seguinte contém missões pré-planejadas usadas pelo sistema MOSA.

* **Fixed-Route4m** -> *Fixed Route for mission* - Arquivos com missões completas pré-definidas. 

Um arquivo com rota fixa possui o seguinte formato para cada linha: "latitude;longitude;altitude"

```
-22.0060105801367;-47.8987005643903;3.00
-22.0059047401367;-47.8986661843903;3.00
-22.0058394001367;-47.8985761843903;3.00
-22.0058394001367;-47.8984649443903;3.00
-22.0059047401367;-47.8983749443903;3.00
-22.0060105801367;-47.8983405643903;3.00
-22.0061164201367;-47.8983749443903;3.00
-22.0061817601367;-47.8984649443903;3.00
-22.0061817601367;-47.8985761843903;3.00
-22.0061164201367;-47.8986661843903;3.00
-22.0060105801367;-47.8987005643903;3.00
```

Este diretório (/UAV-Toolkit/Modules-MOSA/Fixed-Route4m/) possui três subdiretórios que são: /circle/, /triangle/, /rectangle/.
Nestes diretórios estão contidos um conjunto de rotas circulares/triangulares/retangulares variando a quantidade de waypoints utilizados para gerá-los.
Estas rotas default (circle, triangle, rectangle) foram geradas pelo algoritmo Route-Standard4m e são bastante úteis em análises em que a rota a ser percorrida pelo drone não é importante.

## Síntese: 

Abaixo encontra-se uma tabela sintetizando os principais módulos do MOSA e suas características.

| Característica             | HGA4m                    | CCQSP4m                  | A-Star4m                 | Route-Standard4m         | Fixed-Route4m            | G-Path-Planner4m         |
|----------------------------|--------------------------|--------------------------|--------------------------|--------------------------|--------------------------|--------------------------|
| Estratégia                 | Metaheurística + Grafo Visibilidade + Programação Matemática | Programação Matemática  | Heurística               | Determinística           | Não tem                  | Qualquer Estratégia      |
| Módelo PLIM                | Sim                      | Sim                      | Não                      | Não                      | Não                      | N/A                      |
| Tempo de Processamento     | Lento (~10 a 50 seg)     | Médio (~2 a 10 seg)      | Rápido (~0.1 a 2 seg)    | Muito Rápido (<0.1 seg)  | Super Rápido (<0.01 seg) | N/A                      |
| Dependência de Bibliotecas | CPLEX                    | CPLEX                    | Não tem                  | Não tem                  | Não tem                  | N/A                      |
| Dependência de Arquitetura | Apenas x86 e x64         | Apenas x86 e x64         | Todas que rodam C        | Todas que rodam C        | Todas                    | N/A                      |
| Faz o desvio de obstáculos | Sim                      | Sim                      | Sim                      | Não                      | Não                      | N/A                      |
| Alocação do Risco          | Sim                      | Sim                      | Não                      | Não                      | Não                      | N/A                      |
| Linguagem                  | Java                     | Java                     | C                        | C                        | N/A                      | Qualquer Linguagem (Tem Exemplos em C, C++, Java e Python) |
| Local de Execução          | Onboard/Offboard         | Onboard/Offboard         | Onboard/Offboard         | Onboard                  | Onboard                  | Onboard/Offboard         |
