# Modules-MOSA

Este diretório é responsável por agrupar um conjunto de algoritmos usados pelo sistema *Mission Oriented Sensors Array* (MOSA). 

Os algoritmos seguintes estão sendo utilizados como algoritmos de planejamento de rotas pelo MOSA.

* **HGA4m** ->  *Hybrid Genetic Algorithm for mission* - Utiliza um algoritmo genético híbrido com grafo de visibilidade e resolução de modelo de Programação Linear Inteira-Mista (PLIM) para encontrar a rota. (Artigo GECCO 2016) [[Link](https://dl.acm.org/citation.cfm?id=2908919)]
* **CCQSP4m** -> *Chance Constraint Qualitative State Plan for mission* - Utiliza um algoritmo baseado em Programação Linear Inteira-Mista (PLIM) para encontrar a rota. [[Link](http://www.teses.usp.br/teses/disponiveis/55/55134/tde-05122017-083420/pt-br.php)]

O diretório seguinte contém missões pré-planejadas usadas pelo sistema MOSA.

* **Fixed-Route4m** -> *Fixed Route for mission* - Arquivos com missões completas pré-definidas. 

Um arquivo com rota fixa possui o seguinte formato: "latitude;longitude;altitude"

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
