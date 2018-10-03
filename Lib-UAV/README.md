# Lib-UAV

Biblioteca em Java que contém estruturas genéricas ao sistema UAV-MOSA, UAV-IFA, UAV-GCS e UAV-Mission-Creator.

Para ser usado este projeto deve ser importado pelos sistemas UAV-MOSA, UAV-IFA, UAV-GCS, UAV-Mission-Creator, UAV-Tests, UAV-Monitoring e UAV-PosAnalyser.

Neste projeto temos classes que armazenam informações das Aeronaves disponíveis (iDroneAlpha e Ararinha), dos sensores da aeronave (GPS, Atitude, Bateria, Velocidade, etc), dos dados, como por exemplo, parâmetros e home-location.

Tem-se classes que gerencia a aquisição de dados da aeronave através de requisições GET e POST. Tem-se classes genéricas também sobre estrutura de dados genéticas aos sistemas MOSA e IFA. Classes para leituras de arquivos de instâncias, etc. Classes para conversões de entre coordenadas cartesianas para coordenadas geográficas e vice-versa.
