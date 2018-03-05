# UAV-Toolkit

Conjunto de ferramentas desenvolvidas para automatização de voos de Veículos Aéreos Não-Tripulados (VANTs) ou *Unmanned Aerial Vehicles* (UAVs).

------

## Visão Geral

Nesse projeto podemos encontrar os seguintes diretórios:

* **Docs** -> Documentação escrita sobre esse projeto. Dissertação, Qualificação e Tutorial.
* **Libs** -> Bibliotecas utilizadas nos projetos aqui descritos.
* **Missions-Ardupilot-SITL** -> Agrupa um conjunto de missões para serem simuladas no ardupilot SITL.
* **Missions-Google-Earth** -> Agrupa um conjunto de missões feitas usando o software Google Earth.
* **Modules-IFA** -> Agrupa um conjunto de algoritmos usados pelo sistema IFA.
* **Modules-MOSA** -> Agrupa um conjunto de algoritmos usados pelo sistema MOSA.
* **Scripts** -> Agrupa um conjunto de scripts utilizados para facilitar a execução de experimentos.
* **UAV-Generic** -> Projeto em Java que contém estruturas genéricas ao sistema MOSA e IFA.
* **UAV-IFA** -> Projeto em Java para gerenciamento da segurança em voo.
* **UAV-Keyboard-Commands** -> Projeto em Java para controlar o drone utilizando comandos de teclado.
* **UAV-MOSA** -> Projeto em Java para gerenciamento da missão em voo.
* **UAV-Mission-Creator** -> Projeto em Java que auxilia a criar missões e mapas usando o Google Earth.
* **UAV-Monitoring** -> Projeto em Java para monitoramento dos sensores e informações da aeronave.
* **UAV-PosAnalyser** -> Projeto em Java para monitoramento da posição da aeronave.
* **UAV-Services-DroneKit** -> Código em python que provê serviços de acesso a informações do drone através do dronekit.
* **UAV-Tests** -> Projeto em Java para execução de testes das funcionalidades do UAV-Services-Dronekit.
* **UAV-Toolkit-C** -> Conjunto de códigos em C para gerenciamento do drone.
* **UAV-Voice-Commands** -> Projeto em Java para controlar o drone utilizando comandos de voz.

------

## Instalação

### Pré-Requisitos de Instação:

É necessário possuir instalado em seu computador os seguintes softwares/pacotes: 

* python 2.7 [[Link](https://www.python.org/)]
* dronekit [[Link](http://python.dronekit.io/)]
* dronekit-sitl [[Link](http://python.dronekit.io/)]
* Mavproxy [[Link](http://ardupilot.github.io/MAVProxy/html/index.html)]
* QGroundControl [[Link](http://qgroundcontrol.com/)] 
ou 
* APM Planner 2.0 [[Link](http://ardupilot.org/planner2/index.html)] 
ou 
* Mission Planner [[Link](http://ardupilot.org/planner/docs/mission-planner-overview.html)]

### Instação Caso I:

Caso você deseje modificar o projeto atual incluindo melhorias, então deve-se ter instalado em seu computador a IDE Netbeans ou alguma outra IDE de seu interesse.

Logo em seguida, você deverá clonar o nosso repositório:

`git clone https://github.com/jesimar/UAV-Toolkit.git`

Uma vez terminado, navegue até o diretório RAIZ. Neste caso você estará em ./UAV-Toolkit:

Então você poderá importar cada um dos projetos que você deseja melhorar. 

### Instação Caso II:

Caso você deseje apenas utilizar o projeto atual sem incluir melhorias, então não é necessária a instação de nenhuma IDE para edição dos projetos desenvolvidos.

Logo em seguida, você deverá fazer o download do último release do nosso projeto:

`https://github.com/jesimar/UAV-Toolkit/releases`

Uma vez terminado, navegue até o diretório RAIZ. Neste caso você estará em ./UAV-Toolkit:

Pronto o projeto já está pronto para o uso.

------

## Lista de Softwares Estáveis

A seguir estão listados os projetos de software do UAV-Toolkit que estão estáveis.

1. **UAV-Monitoring** [[Link](./UAV-Monitoring/)]
2. **UAV-PosAnalyser** [[Link](./UAV-PosAnalyser/)]
3. **UAV-Toolkit-C** [[Link](./UAV-Toolkit-C/)]

------

## Configurando o Ambiente de Trabalho

TODO

------

## Citação

Se você usar o UAV-Toolkit, por favor, cite minha Qualificação de Doutorado [[PDF](./Docs/Qualificação-Jesimar-2017.pdf)].

@PhdThesis{ArantesJS2017,
  title = {Sistema autônomo para supervisão de missão e segurança de voo em VANTs},
  author = {Jesimar da Silva Arantes},
  School = {Universidade de São Paulo (USP)},
  Year = {2017},
  Month = {ago},
  pages = {1--140},
  Note = {São Carlos, SP},
  Type = {Qualificação de Doutorado}
}

------

## Licença

UAV-Toolkit está disponível sobre código aberto com permissões [GNU General Public License v3.0](https://github.com/jesimar/UAV-Toolkit/blob/master/LICENSE). 

------

Copyright 2018 - Jesimar da Silva Arantes.
