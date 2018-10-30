# Modules-Global

Este diretório é responsável por agrupar um conjunto de algoritmos e módulos de hardware usados pelo sistema IFA e MOSA. 

Os diretórios a seguir contém códigos utilizados pelo sistema IFA.

* **Buzzer** -> Código responsável por acionar o alarme do drone após ocorrer uma falha crítica. 
* **Parachute** -> Código responsável por acionar o paraquedas do drone após ocorrer uma falha crítica. 
* **Sonar** -> Código responsável por fazer leituras de distância do drone até o solo.
* **LED** -> Código responsável por acionar ou apagar alguns LEDs do drone.
* **Temperature** -> Código responsável por pegar dados do sensor de temperatura para verificação de superaquecimento do drone.

Os diretórios a seguir contém códigos utilizados pelo sistema MOSA.

* **Buzzer** -> Código responsável por acionar o buzzer do drone após um waypoint específico ter sido alcançado. 
* **Camera** -> Código responsável por fazer a retirada de fotografia e vídeos conforme acionado pelo sistema MOSA.
* **LED** -> Código responsável por acionar ou apagar alguns LEDs do drone.
* **Spraying** -> Código responsável por abrir ou fechar o sistema de pulverização do drone.
* **Route-Simplifier** -> Código responsável por fazer a simplificação de waypoints de forma a tornar mais leve para o piloto automático a execução da rota.

O diretório Files contém arquivos utilizados pelo sistema MOSA e IFA.

* **geoBase.txt** -> Arquivo com as coordenadas de longitude, latitude e altitude usado na transformação para coordenadas cartesianas.
* **featureMission.txt** -> Arquivo que contém as coordenadas de latitude, longitude e altitude, em que deve-se acionar o buzzer, câmera foto, câmera vídeo e spraying.
* **map-full.sgl** -> Arquivo de mapa completo (contendo obstáculos, região penalizadora e região bonificadora) usado pelo sistema.
* **map-nfz.sgl** -> Arquivo de mapa com apenas os obstáculos usado pelo sistema.

## Arquivos de configuração

Este diretório também possui dois arquivos de configuração que são:

* **config-global.properties** -> Arquivo usado pelos sistemas MOSA, IFA, GCS, S2DK para descrever os aspectos gerais da missão/segurança.
* **config-param.properties** -> Arquivo usado pelo sistema IFA para calibrar os parâmetros de voo do Piloto Automático.

## Síntese: 

Abaixo encontra-se uma tabela sintetizando os principais módulos presentes nessa pasta e suas características.

| Característica              | Sonar          | Temperature         | Câmera           | Buzzer        | LED           | Parachute     | Spraying      | Route-Simplifier    |
|-----------------------------|----------------|---------------------|------------------|---------------|---------------|---------------|---------------|---------------------|
| Tipo                        | Sensor         | Sensor              | Sensor           | Atuador       | Atuador       | Atuador       | Atuador       | Otimizador de Rotas |
| Marca/Modelo                | HC-SR04        | MAX6675             | Camera RPi v1    | Buzzer 12V    |               |               |               | N/A                 |
| Dependência de Bibliotecas  | RPi.GPIO       | RPi.GPIO e WiringPi | picamera         | mraa          |               |               |               | Não tem             |
| Linguagem                   | Python         | Python ou C         | Python           | Python        |               |               |               | Python              |
| Aplicação que Utiliza       | IFA            | IFA                 | MOSA             | IFA/MOSA      | IFA/MOSA      | IFA           | MOSA          | MOSA                |
| Tem versão PC               | Sim (Simulado) | Sim (Simulado)      | Sim              | Sim           | Não           | Não           | Não           | N/A                 |
| Imagem                      | ![](../Figures/sonar.png) | ![](../Figures/temperature.png)| ![](../Figures/camera.png) | ![](../Figures/buzzer.png) | ![](../Figures/led.png) | ![](../Figures/parachute.png) | ![](../Figures/spraying.png) | ![](../Figures/route-simplifier.png) |

A tabela abaixo sintetiza algumas informações sobre o sensor câmera.

| Característica           | Câmera                  | Câmera                       | Câmera                 | Observação                               |
|--------------------------|-------------------------|------------------------------|------------------------|------------------------------------------|
| Tipo de Ação             | Retirar Foto            | Retirar Fotos em Sequência   | Fazer Vídeo            |                                          |
| Código para Raspberry Pi | picture-rpi.py          | photo-in-sequence-rpi.py     | video-rpi.py           |                                          |
| Código para PC           | picture-pc.jar          | photo-in-sequence-pc.jar     | video-pc.jar           | Print screen, print screens e grava tela |

A tabela abaixo sintetiza algumas informações sobre o atuador buzzer.

| Característica           | Buzzer                  | Buzzer                       | Observação             |
|--------------------------|-------------------------|------------------------------|------------------------|
| Tipo de Ação             | Aciona um Beep (Buzzer) | Aciona vários Beeps (Alarme) |                        |
| Código para Edison       | buzzer-edison.py        | alarm-edison.py              |                        |
| Código para PC           | buzzer-pc.jar           | alarm-pc.jar                 | Aciona o buzzer do pc  |

A tabela abaixo sintetiza algumas informações sobre o sensor sonar.

| Característica           | Sonar                 | Observação             |
|--------------------------|-----------------------|------------------------|
| Tipo de Ação             | Calcula Distância     |                        |
| Código para Raspberry Pi | sonar-rpi.py          |                        |
| Código para PC           | sonar-pc.jar          | Dados apenas simulados |

A tabela abaixo sintetiza algumas informações sobre o sensor de temperatura.

| Característica           | Temperature           | Observação             |
|--------------------------|-----------------------|------------------------|
| Tipo de Ação             | Calcula a Temperatura |                        |
| Código para Raspberry Pi | temperature-rpi.py    |                        |
| Código para Raspberry Pi | temperature-rpi.c     |                        |
| Código para PC           | temperature-pc.jar    | Dados apenas simulados |

A tabela abaixo sintetiza alguns detalhes sobre a implementação do simplificador de rotas. Pode-se perceber nessa tabela que os métodos do MOSA (HGA4m e CCQSP4m) suportam a simplificação de rotas, sejam executando de forma onboard ou offboard. Nota-se também que todos os métodos do IFA (como, MPGA4s, GA4s, DE4s, etc) não suportam esse recurso (por enquanto, esse recurso será adicionado em breve), execuntando onboard ou offboard. Por fim, pode-se perceber que apenas o método CCQSP4m (executando onboard) suporta o simplificador de rotas de forma automática. Isso significa que mesmo que eu não ative explicitamente o simplificador, o mesmo será executado caso o tamanho da rota ultrapasse o tamanho máximo suportado pelo piloto. Vale lembrar que o número máximo de waypoints suportados pela APM v2.8 é 166 e a Pixhawk v1.0 é 718. Vamos supor então que o planejador CCQSP4m (executando onboard) gerou uma rota com 200 waypoints e estamos usando a APM, se tentarmos passar tal rota para o piloto automático irá dar um erro, mas foi adicionado um recurso, onde o simplificador de rotas automaticamente será chamado para previnir tais erros de lógica.

| Característica                                 | HGA4m Onboard   | HGA4m Offboard  | CCQSP4m Onboard | CCQSP4m Offboard | Métodos-IFA Onboard | Métodos-IFA Offboard |
|------------------------------------------------|-----------------|-----------------|-----------------|------------------|---------------------|----------------------|
| Suporta Simplificador de Rotas                 | Sim             | Sim             | Sim             | Sim              | Não                 | Não                  |
| Suporta Simplificador de Rotas Automaticamente | Não             | Não             | Sim             | Não              | Não                 | Não                  |
