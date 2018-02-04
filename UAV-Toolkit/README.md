# UAV-Toolkit

A presente toolkit para VANTs foi desenvolvida para auxiliar a projetar rotas, missões e mapas, pré-processar os dados obtidos pelo [FligthGear](http://www.flightgear.org/), [Google Earth](https://www.google.com/earth/index.html) etc. Os projetos ReaderKML, ManagerSITL e ProcessDataFG foram desenvolvidos em Java usando o IDE [Netbeans](https://netbeans.org/). Já o projeto UAV-Routes foi desenvolvido em C usando o IDE [Geany](https://www.geany.org/).

## ReaderKML
 
 Esse projeto conta com um conjunto de ferramentas para conversões de rotas em coordenadas cartesianas em coordenadas geograficas (e vice-versa), conta também com conversões de mapas projetados no Google Earth para mapas em coordenadas cartesianas. Dentre inumeras outras funcionalidades.
 
## ManagerSITL
 
 Esse projeto auxilia na automatização de simulações Software-in-the-Loop (SILT) usando o [Ardupilot](http://ardupilot.org/). Um arquivo de propriedades encontra-se disponível para se efetuar as alterações devidas sobre a localização do ardupilot, tempo de simulação etc.
 
## ProcessDataFG

 Esse projeto auxilia no pré-processamento de informações obtidas pelo software AutoFG, onde o AutoFG foi executado dezenas de vezes e esses dados precisam ser combinados afim de se extrair informações (através de imagens). Esse projeto foi feito 
usando o IDE Netbeans. Os resultados das simulações do AutoFG devem estar na pasta "results" nomeados da seguinte maneira sim0, sim1, ... simN. Os dados de saída desse programa encontra-se na pasta "out". O arquivo "plot-sim-graficos.gplot" dentro da pasta "out" captura os dados gerados e efetua a plotagem de todas as rotas obtidas usando o software [gnuplot](http://www.gnuplot.info/). 
 
## UAV-Routes
 
 Esse projeto auxilia a projetar rotas para VANT com formatos regulares como circulos, quadrados e triangulos. De acordo com uma discretização especificada (quantidade de waypoints). As rotas são projetadas sempre em uma altitude de voo cruzeiro especificada e os dados são em coordenadas cartesianas (x, y, z) e não em coordenadas geográficas (lat, lng, alt). As dimensões (tamanhos) assumidos na rota também podem ser alteradas. 
 
 Para compilar o arquivo uav-routes.c use a seguinte linha no terminal.
 <pre>
 \>\> gcc uav-routes.c -o uav-routes -lm
 </pre>
 
 As funções que criam as rotas de circulo, retângulo e triângulo são respectivamente: 
 
 ```c
 void createRouteCircle(int discretization, char name[]);
 void createRouteRectangle(int discretization, char name[]);
 void createRouteTriangle(int discretization, char name[]);
 ```
 As pastas Circle, Rectangle, Triangle contém algumas rotas já calculadas em coordenadas cartesianas. 
 
## License 

Esse projeto esta distribuido pela [GNU - GENERAL PUBLIC LICENSE](LICENSE)
 
## Getting Started
 TODO...
