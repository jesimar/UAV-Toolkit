# Scripts

Este diretório contém um conjunto de scripts utilizados para facilitar a execução de experimentos. 

Os scripts mais importantes e utilizados são:

* **exec-copy-files.sh** -> Copia um conjunto de arquivos de configuração da missão para as pastas dos sistema IFA e MOSA.
* **exec-sitl.sh** -> Executa o Dronekit-SITL no computador local. Usado em experimentos SITL.
* **exec-mavproxy-local.sh** -> Executa o MAVProxy na máquina local. Usado em experimentos SITL.
* **exec-mavproxy-edison.sh** -> Executa o MAVProxy na máquina local. Usado em experimentos com o drone real.
* **exec-services-dronekit.sh** -> Executa um serviço para prover informações sobre o drone.
* **exec-ifa.sh** -> Executa o sistema IFA para gerenciamento da segurança em voo.
* **exec-insert-failure.sh** -> Executa o sistema UAV-Insert-Failure para inserção de falha na aeronave no momento desejado.
* **exec-mosa.sh** -> Executa o sistema MOSA para gerenciamento da missão em voo.
* **exec-tests.sh** -> Executa o sistema UAV-Tests para testar as funcionalidades do UAV-Services-Dronekit.
* **exec-monitoring.sh** -> Executa o sistema UAV-Monitoring para monitoramento dos sensores do drone.
* **exec-pos-analyser.sh** -> Executa o sistema UAV-PosAnalyser para análise da posição do drone.
* **login-edison.sh** -> Efetua o login na Edison de forma automática. Nota: o computador e a Intel Edison devem estar na mesma rede. OBS: deve-se atualizar o IP do script.
