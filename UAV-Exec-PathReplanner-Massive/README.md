# UAV-Exec-PathReplanner-Massive

Este projeto em Java é o responsável por fazer a execução massiva dos algoritmos replanejadores de rotas de forma a validá-los para diversos pontos da rota.
Dessa forma, pode-se mostrar que o algoritmo de replanejamento retorna ou não boas rotas para diversos pontos de falha simulados.
Para executar esse sistema deve-se entrar na pasta "execute/" e executar o script (exec-massive.sh).
Algumas configurações podem ser feitas nesse script, como por exemplo, selecionar qual replanejador de rotas você gostaria de executar, quantos pontos tem os estados do veículo, etc.

Na pasta "execute/" tem-se um gerador de estados do drone no formato desejado chamado "generator-states-drone.ods".
Após usar esse gerador você deve salvar o arquivo de forma semelhante ao arquivo "states-drone.txt". Observe que o separador de casas decimais é o ponto "." não a vírgula ",".

Após executar esse script um conjunto de arquivos de imagens e com a rota do drone é salvo dentro da pasta "execute/Massive-Routes/METHOD/".

Uma outra utilidade desse algoritmo é a sua utilização junto com o método UAV-Fixed-Route4s em que a melhor rota de pouso emergencial é retornado. O UAV-Fixed-Route4s é usado pelo sistema IFA.
