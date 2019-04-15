# Libs/Bibliotecas

Este diretório agrupa um conjunto de bibliotecas utilizadas neste projeto.

Lista de bibliotecas, desenvolvidas neste projeto, utilizadas neste projeto:

* **Lib-UAV.jar** -> Biblioteca genérica para manipulação e processamento de informações de VANTs. Utilizada pelos sistemas UAV-IFA, UAV-MOSA, UAV-GCS e UAV-Mission-Creator [[Link](https://github.com/jesimar/UAV-Toolkit/tree/master/Lib-UAV)] (Licença GPL 3.0).
* **LibTextColor.jar** -> Biblioteca para impressão de mensagens de texto colorida. Utilizada para impressão pelos sistemas UAV-IFA e UAV-MOSA [[Link](https://github.com/jesimar/Java-Lib-Color-Texts)] (Licença MIT).

Lista de bibliotecas, desenvolvidas por terceiros, utilizadas neste projeto:

* **jsc.jar** -> Biblioteca para manipulação estatística. Utilizada nos replanejadores de rotas (DE4s, GH4s, GA4s, MPGA4s) [[Link](https://mvnrepository.com/artifact/jsc/jsc/1.0)] (Licença N/A).
* **jmf.jar** -> Biblioteca para manipulação multimedia. Utilizada nos gravador de vídeo Video-PC [[Link](https://mvnrepository.com/artifact/javax.media/jmf)] (Licença SCSL)
* **Jama-1.0.3.jar** -> Biblioteca para minipulação de álgebra linear. Utilizada nos planejadores de rotas (HGA4m, CCQSP4m) [[Link](https://mvnrepository.com/artifact/gov.nist.math/jama/1.0.3)] (Licença Creative Commons).
* **jdom-2.0.6.jar** -> Biblioteca para acesso e manipulação de dados XML. Utilizada para leitura de arquivo em formato XML [[Link](https://mvnrepository.com/artifact/org.jdom/jdom2)] (Licença própria estilo Apache).
* **gson-2.8.2.jar** -> Biblioteca para conversão de objetos para formato JSON. Utilizada para conversão em formato JSON [[Link](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.2)] (Licença Apache 2.0).
* **mysql-connector-java-5.0.8-bin.jar** -> Biblioteca para conexão em Banco de Dados MySQL. Utilizada pelo UAV-GCS para conexão com o sistema Oracle Drone [[Link](https://mvnrepository.com/artifact/mysql/mysql-connector-java)] (Licença GPL 2.0).
* **java-swing-google-maps.jar** -> Biblioteca para navegação e plot da missão usando o Google Maps. Utilizada pelo programa UAV-GCS [[Link](https://github.com/marcio-da-silva-arantes/java-swing-google-maps)]. (Licença MIT).
* **java-swing-graphics-2d-navigation.jar** -> Biblioteca para navegação 2D usando Swing. Utilizada pelo programa UAV-GCS e UAV-Plot-Mission [[Link](https://github.com/marcio-da-silva-arantes/java-swing-graphics-2d-navigation)] (Licença MIT).
* **java-screen-recorder** -> Incluem as bibliotecas screen_recorder.jar, screen_converter.jar, screen_player.jar. Utilizada pelo programa Video-PC [[Link](https://code.google.com/archive/p/java-screen-recorder/)] (Licença MIT).
* **cplex.jar** -> Biblioteca para resolução de modelos matemáticos de Programação Linear Inteira Mista. Utilizada pelos algoritmos HGA4m e CCQSP4m [[Link](https://www.ibm.com/developerworks/br/downloads/ws/ilogcplex/index.html)] (Licença Proprietária).
* **xstream-1.4.5.jar** -> Biblioteca para conversão de objetos para formato XML. Utilizada para conversão em formato XML [[Link](https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream/1.4.5)] (Licença BSD).

:warning: **OBS:** Para utilização dos recursos do IBM ILOG CPLEX Optimization Studio [[Link](https://www.ibm.com/)], você deverá: primeiramente, instalar o CPLEX, que é suportado apenas em arquiteturas x86 e x86_64. Em seguida, trocar/copiar o arquivo cplex.jar de 
`.../IBM/ILOG/CPLEX_StudioXXXX/cplex/lib/` para todos os planejadores de rota que utilizam pragramação matemática como em: `.../UAV-Toolkit/Modules-MOSA/HGA4m/lib/`, `.../UAV-Toolkit/Modules-MOSA/CCQSP4m/lib/`
