# FAQ

A seguir estão listadas algumas respostas para perguntas frequentes.

**Pergunta 1:** Estou tentando fazer um voo autônomo, com meu drone, e aparece a mensagem "low batery" no momento de armar no software UAV-S2DK. O que devo fazer?

**Resposta 1:** Caso isso aconteça, muito provavelmente, você possui um sistema de Power Module instalado. Para solucionar isso você deve armar o drone de forma manual, utilizando o rádio controle. Após fazer isso o drone irá assumir o total controle do drone automaticamente. Lembre-se de verificar se o parâmetro do piloto automático BATT_CAPACITY está configurado corretamente. 

**Pergunta 2:** O meu drone voa para frente, porém está orientado para o lado. Por que isso acontence?

**Resposta 2:** As vezes percebemos esse efeito em que o drone voa para um lado, mas está orientado para outro. Isso ocorre aparentemente porque foram passados muitos waypoints (com pouco espaço entre eles) e não deu tempo do drone consertar a sua orientação, o drone primeiro preocupa-se em seguir o waypoint não importando a orientação.

**Pergunta 3:** O que devo fazer caso o drone decole, todavia não muda do modo GUIDED para o modo AUTO?

**Resposta 3:** Primeiro, você precisa aguardar um tempo, caso nada acontença faça o seguinte. Você poderá fazer isso via estação de base ou rádio controle, chaveando o modo que está em GUIDED para AUTO. E então o drone passa a seguir a rota especificada. No entanto, pode ocorrer do drone não faça o pouso final, dessa forma, basta você trocar na estação de base para o modo LAND.

**Pergunta 4:** O drone está fazendo uma missão e ele não atinje a altura correta no momento da decolagem. O que devo fazer?

**Resposta 4:** Pela estação de controle, por exemplo o QGroundControl, mude para LOITER. Em seguida, dê throttle pelo controle e então volte pela estação de controle para o modo AUTO.

**Pergunta 5:** Um conjunto de arquivos são gerados pela plataforma nas simulações ou voos reais. Quais são os principais arquivos que devo baixar após fazer uma missão para posterior análise e escrita de artigos científicos?

**Resposta 5:** Abaixo estão listados alguns arquivos que podem ser interessantes fazer o download.

* routeGeo.txt -> Caso tenha utilizado CCQSP4m. Encontrado na pasta /UAV-Toolkit/Modules-MOSA/CCQSP4m/
* routeGeo*.txt -> Caso tenha utilizado HGA4m. Encontrado na pasta /UAV-Toolkit/Modules-MOSA/HGA4m/.
* routeGeo.txt -> Caso tenha utilizado HGA4m. Encontrado na pasta /UAV-Toolkit/Modules-IFA/MPGA4s/.
* log-aircraft*.csv -> arquivo de log do sistema UAV-Tookit. Encontrado na pasta /UAV-Toolkit/UAV-IFA/.
* log-overhead-ifa*.csv - > arquivo de log de overhead do sistema IFA. Encontrado na pasta /UAV-Toolkit/UAV-IFA/.
* log-overhead-mosa*.csv -> arquivo de log de overhead do sistema MOSA. Encontrado na pasta /UAV-Toolkit/UAV-MOSA/.
* .h264 -> se fez vídeo. Encontrado na pasta /UAV-Toolkit/Modules-Global/Camera/videos/.
* .jpg -> se retirou foto. Encontrado na pasta /UAV-Toolkit/Modules-Global/Camera/pictures/.
* .jpg -> se retirou fotos em sequência. Encontrado na pasta /UAV-Toolkit/Modules-Global/Camera/photos-in-sequence/.
* config-global.properties -> arquivo de properties com dados de configuração utilizados no voo real. Encontrado na pasta /UAV-Toolkit/Modules-Global/.
* config-param.properties -> arquivo de properties com dados dos parâmetros utilizados no voo real. Encontrado na pasta /UAV-Toolkit/Modules-Global/.
* .tlog -> arquivo com o log do voo. Encontrado na pasta /UAV-Toolkit/Scripts/MyCopter/logs/DATA/.
* .tlog.raw -> arquivo com o log do voo. Encontrado na pasta /UAV-Toolkit/Scripts/MyCopter/logs/DATA/.
* .parm -> arquivo com os parâmetros do drone. Encontrado na pasta /UAV-Toolkit/Scripts/MyCopter/logs/DATA/.
* .bin -> arquivo com o log do voo. Conectar a Pixhawk no notebook via USB. Copiar utilizando APM Planner o arquivo .bin. Para isso vá na Aba Graphs -> Download Log -> Refresh -> Selecione -> Download Log(s). O arquivo irá, em geral, para a pasta ~/apmplanner2/dataflashLogs/.
