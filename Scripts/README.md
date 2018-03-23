# Scripts

Este diretório contém um conjunto de scripts utilizados para facilitar a execução de experimentos. 

Os scripts mais importantes e utilizados são:

* **clear-simulations.sh** -> Limpa um conjunto de arquivos gerado durante a simulação e o voo.
* **dir** -> Script para facilitar a navegação na Intel Edison até o diretório de scripts.
* **exec-fgfs.sh** -> Executa o simulador de voo FlightGear.
* **exec-sitl.sh** -> Executa o Dronekit-SITL no computador local. Usado em experimentos SITL.
* **exec-mavproxy-local.sh** -> Executa o MAVProxy na máquina local. Usado em experimentos SITL.
* **exec-mavproxy-edison.sh** -> Executa o MAVProxy na Intel Edison. Usado em experimentos com o drone real.
* **exec-mavproxy-edison-sitl.sh** -> Executa o MAVProxy na Intel Edison para experimentos SITL. Usado em experimentos com o drone real.
* **exec-soa-interface.sh** -> Executa um serviço para prover informações sobre o drone.
* **exec-ifa.sh** -> Executa o sistema IFA para gerenciamento da segurança em voo.
* **exec-insert-failure.sh** -> Executa o sistema UAV-Insert-Failure para inserção de falha na aeronave no momento desejado.
* **exec-login-edison.sh** -> Efetua o login na Edison de forma automática. Nota: o computador e a Intel Edison devem estar na mesma rede. OBS: deve-se atualizar o IP do script.
* **exec-mosa.sh** -> Executa o sistema MOSA para gerenciamento da missão em voo.
* **exec-swap-mission.sh** -> Faz a troca da missão a ser executada. Copia um conjunto de arquivos de configuração da missão para as pastas dos sistema IFA e MOSA.
* **exec-tests.sh** -> Executa o sistema UAV-Tests para testar as funcionalidades do UAV-SOA-Interface.
* **exec-monitoring.sh** -> Executa o sistema UAV-Monitoring para monitoramento dos sensores do drone.
* **exec-pos-analyser.sh** -> Executa o sistema UAV-PosAnalyser para análise da posição do drone.
* **exec-turn-off-buzzer.sh** -> Executa um código em python que desliga o buzzer, caso o mesmo esteja apitando.
* **list-info-hd.sh** -> Lista informações do HD.
* **list-ips-in-use.sh** -> Lista todos os IPs que estão em uso na rede (todos os computadores que estão na rede).
* **list-ports-in-use.sh** -> Lista todas as portas de rede que estão em uso.
* **show-my-ip.sh*** -> Mostra o IP do meu computador.
* **show-my-linux.sh** -> Mostra a versão detalhada do meu sistema operacional.
* **exec-copy-files-results.sh** -> Copia os arquivos de resultados para a pasta ../Results/.

Os scripts a seguir necessitam ser configurados para serem utilizados:

* **exec-sitl.sh** -> Necessita trocar o local de lançamento do drone.
* **exec-login-edison.sh** -> Necessita configurar o IP da Intel Edison.
* **exec-mavproxy-edison.sh** -> Necessita configurar o IP da Estação de Controle de Solo.
* **exec-mavproxy-edison-sitl.sh** -> Necessita configurar o IP da Estação de Controle de Solo.
* **exec-swap-mission.sh** -> Necessita trocar o diretório em que encontra-se os arquivos da nova missão.
* **list-ips-in-use.sh** -> Necessita configurar o IP base da rede em que o notebook está logado.
