# Modules-IFA

Este diretório é responsável por agrupar um conjunto de algoritmos usados pelo sistema *In-Flight Awareness* (IFA). 

Os algoritmos seguintes estão sendo utilizados como algoritmos de replanejamento de rotas pelo IFA.

* **DE4s** -> *Differential Evolution for security*  [[Link em Breve]()]

* **GH4s** -> *Greedy Heuristic for security* (Artigo IJAIT 2017) [[Link](http://www.worldscientific.com/doi/abs/10.1142/S0218213017600089)]

* **GA4s** -> *Genetic Algorithm for security* (Artigo IJAIT 2017) [[Link](http://www.worldscientific.com/doi/abs/10.1142/S0218213017600089)]

* **MS4s** ->  *MultiStart for security* [[Link em Breve]()]

* **MILP4s** ->  *Mixed Integer Linear Programming for security* (Artigo Journal PLIM 2018) [[Link em Breve]()]

* **MPGA4s** ->  *Multi-Population Genetic Algorithm for security* (Artigo ICTAI 2015) [[Link](http://ieeexplore.ieee.org/document/7372174/)]

* **GA-GA-4s** -> *Two Genetic Algorithms for security executed in parallel* (Artigo ICTAI 2017) [[Link](https://ieeexplore.ieee.org/document/8372047/)]

* **GA-GH-4s** -> *One Genetic Algorithm and one Greedy Heuristic for security executed in parallel* (Artigo ICTAI 2017) [[Link](https://ieeexplore.ieee.org/document/8372047/)]

* **Pre-Planned4s** -> *Route pre-planned for security* (Artigo Journal PLIM 2018) [[Link em Breve]()]

* **Fixed-Route4s** -> *Fixed Route for security*

* **G-Path-Replanner4s** -> *Generic Path Replanner for security* - Replanejador de rotas genérico para segurança. 

## Arquivos dos Métodos

Em cada uma das pastas dos métodos citados acima (DE4s, GH4s, GA4s, MPGA4s), tem-se um conjunto de arquivos explicados a seguir. 

* method4s.jar -> Arquivo que executa o respectivo método (sem plotar a imagem da rota).
* method4s-plot.jar -> Arquivo que executa o respectivo método (com a plotagem da imagem da rota) (usado em geral para debug).
* exec-replanner.sh -> Comando shell script para executar o replanejador (sem plot).
* exec-replanner-plot.sh -> Comando shell script para executar o replanejador (com plot) (usado em geral para debug).
* instance -> Arquivo de instância do método. Este arquivo é um arquivo de entrado gerado pelo framework ProOF. Arquivo a ser substituído pelo IFA e usado pelo método.
* instance-base -> Arquivo de instância do método. Este arquivo é um arquivo de entrado gerado pelo framework ProOF. Arquivo base de utilização pelo IFA.
* config.sgl -> Arquivo de configuração do método. Arquivo a ser substituído pelo IFA e usado pelo método.
* config-base.sgl -> Arquivo de configuração do método. Arquivo base de utilização pelo IFA.
* map.sgl -> Arquivo contendo o mapa da missão.

# Método Fixed-Route4s

Para utilizar esse método basta adicionar um arquivo com os waypoints a serem seguidos caso haja falha crítica. 

Um arquivo com rota fixa possui o seguinte formato para cada linha: "latitude;longitude;altitude"

```
-22.005933559084500;-47.898553832061054;3.0
-22.005947900466417;-47.898552639339780;2.7
-22.005952070247055;-47.898568058483870;2.3
-22.005954544760815;-47.898583391395460;2.0
-22.005956014172074;-47.898597887173686;1.7
-22.005957386402432;-47.898611513844690;1.4
-22.005958656052947;-47.898624371205635;1.1
-22.005959858216720;-47.898636542582280;0.8
-22.005960999192517;-47.898648106455894;0.5
-22.005961585877234;-47.898659167468140;0.2
-22.005962146467080;-47.898669722712306;0.0
```

## Síntese: 

Abaixo encontram-se duas tabela sintetizando os principais módulos do IFA e suas características.

| Característica             | MPGA4s                    | GA4s                     | DE4s                     | GH4s                     | MS4s                     |
|----------------------------|---------------------------|--------------------------|--------------------------|--------------------------|--------------------------|
| Estratégia                 | Metaheurística            | Metaheurística           | Metaheurística           | Heurística               | Heurística               |
| Módelo PLIM                | Não                       | Não                      | Não                      | Não                      | Não                      |
| Núcleos da CPU usados      | Um                        | Um                       | Um                       | Um                       | Um                       |
| Tempo de Processamento     | Rápido (~0.5 a 2 seg)     | Rápido (~0.5 a 2 seg)    | Rápido (~0.5 a 2 seg)    | Rápido (~0.5 a 2 seg)    | Rápido (~0.5 a 2 seg)    |
| Dependência de Bibliotecas | Não tem                   | Não tem                  | Não tem                  | Não tem                  | Não tem                  |
| Dependência de Arquitetura | Todas que rodam Java      | Todas que rodam Java     | Todas que rodam Java     | Todas que rodam Java     | Todas que rodam Java     |
| Faz o desvio de obstáculos | Sim                       | Sim                      | Sim                      | Sim                      | Sim                      |
| Alocação do Risco          | Sim                       | Sim                      | Sim                      | Não                      | Não                      |
| Linguagem                  | Java                      | Java                     | Java                     | Java                     | Java                     |
| Local de Execução          | Onboard/Offboard          | Onboard/Offboard         | Onboard/Offboard         | Onboard/Offboard         | Onboard/Offboard         |

| Característica             | GA-GA-4s                   | GA-GH-4s                 | Pre-Planned4s            | Fixed-Route4s            | MILP4s                   | G-Path-Replanner4s 
|----------------------------|----------------------------|--------------------------|--------------------------|--------------------------|--------------------------|--------------------------|
| Estratégia                 | Metaheurística             | Metaheurística           | Determinística           | Determinística           | Programação Matemática   | Qualquer Estratégia      |
| Módelo PLIM                | Não                        | Não                      | Não                      | Não                      | Sim                      | N/A                      |
| Núcleos da CPU usados      | Dois                       | Dois                     | Um                       | Um                       | Um                       | N/A                      |
| Tempo de Processamento     | Rápido (~0.5 a 2 seg)      | Rápido (~0.5 a 2 seg)    | Muito Rápido (~0.1 a 0.5 seg) | Super Rápido (<0.01 seg)| Super Lento (>30 seg até 10 minutos) | N/A                      |
| Dependência de Bibliotecas | Não tem                    | Não tem                  | Não tem                  | Não tem                  | CPLEX                    | N/A                      |
| Dependência de Arquitetura | Todas que rodam Java       | Todas que rodam Java     | Todas que rodam Java     | Todas                    | Apenas x86 e x64         | N/A                      |
| Faz o desvio de obstáculos | Sim                        | Sim                      | Sim                      | Não                      | Sim                      | N/A                      |
| Alocação do Risco          | Sim                        | Sim                      | Sim                      | Não                      | Sim                      | N/A                      |
| Linguagem                  | Java                       | Java                     | Java                     | N/A                      | Java                     | Qualquer Linguagem (Tem Exemplos em C, C++, Java e Python) |
| Local de Execução          | Onboard                    | Onboard                  | Onboard                  | Onboard                  | Onboard                  | Onboard/Offboard         |
