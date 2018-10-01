# Scripts

Este diretório contém um conjunto de scripts utilizados para facilitar a execução de experimentos. 

Os scripts mais importantes e utilizados são:

* **clear-simulations.sh** -> Limpa um conjunto de arquivos gerado durante a simulação e o voo.
* **dir** -> Script para facilitar a navegação no CC (Intel Edison, Raspberry Pi, BeagleBone)  até o diretório de scripts.
* **exec-copy-files-results.sh** -> Copia os arquivos de resultados para a pasta ../Results/.
* **exec-fgfs.sh** -> Executa o simulador de voo FlightGear.
* **exec-gcs.sh** -> Executa o sistema UAV-GCS para acompanhamento/intervenção da missão.
* **exec-ifa.sh** -> Executa o sistema IFA para gerenciamento da segurança em voo.
* **exec-login-bbb.sh** -> Efetua o login na BeagleBone Black de forma automática. Nota: o computador e a BBB devem estar na mesma rede. OBS: deve-se atualizar o IP do script.
* **exec-login-edison.sh** -> Efetua o login na Edison de forma automática. Nota: o computador e a Intel Edison devem estar na mesma rede. OBS: deve-se atualizar o IP do script.
* **exec-login-rpi.sh** -> Efetua o login na Raspberry Pi de forma automática. Nota: o computador e a Raspberry Pi devem estar na mesma rede. OBS: deve-se atualizar o IP do script.
* **exec-mavproxy-sitl.sh** -> Executa o MAVProxy na máquina local. Usado em experimentos SITL.
* **exec-mavproxy-hitl.sh** -> Executa o MAVProxy no companion computer. Usado em experimentos HITL.
* **exec-mavproxy-real-edison.sh** -> Executa o MAVProxy na Intel Edison. Usado em experimentos com o drone real.
* **exec-mavproxy-real-rpi.sh** -> Executa o MAVProxy na Raspberry Pi. Usado em experimentos com o drone real.
* **exec-monitoring.sh** -> Executa o sistema UAV-Monitoring para monitoramento dos sensores do drone.
* **exec-mosa.sh** -> Executa o sistema MOSA para gerenciamento da missão em voo.
* **exec-pos-analyser.sh** -> Executa o sistema UAV-PosAnalyser para análise da posição do drone.
* **exec-s2dk.sh** -> Executa um serviço para prover informações sobre o drone.
* **exec-sitl.sh** -> Executa o Dronekit-SITL no computador local. Usado em experimentos SITL.
* **exec-swap-mission.sh** -> Faz a troca da missão a ser executada. Copia um conjunto de arquivos de configuração da missão para as pastas dos sistema IFA e MOSA.
* **exec-tests.sh** -> Executa o sistema UAV-Tests para testar as funcionalidades do UAV-S2DK.
* **exec-turn-off-buzzer.sh** -> Executa um código em python que desliga o buzzer, caso o mesmo esteja apitando.
* **exec-uav-manager.sh** -> Executa o sistem UAV-Manager para instalar e executar o sistema UAV-Toolkit.
* **kill-all-processes.sh** -> Mata (kill) todos os processos do sistema UAV-Toolkit.
* **list-info-hd.sh** -> Lista informações do HD.
* **list-ips-in-use.sh** -> Lista todos os IPs que estão em uso na rede (todos os computadores que estão na rede).
* **list-ports-in-use.sh** -> Lista todas as portas de rede que estão em uso.
* **show-all-processes.sh** -> Mostra todos os processos do sistema UAV-Toolkit.
* **show-my-ip.sh*** -> Mostra o IP do meu computador.
* **show-my-linux.sh** -> Mostra a versão detalhada do meu sistema operacional.

Os scripts a seguir necessitam ser configurados para serem utilizados:

* **exec-sitl.sh** -> Necessita trocar o local de lançamento do drone.
* **exec-login-bbb.sh** -> Necessita configurar o IP da BeagleBone Black.
* **exec-login-edison.sh** -> Necessita configurar o IP da Intel Edison.
* **exec-login-rpi.sh** -> Necessita configurar o IP da Raspberry Pi.
* **exec-mavproxy-hitl.sh** -> Necessita configurar o IP da Estação de Controle de Solo.
* **exec-mavproxy-real-edison.sh** -> Necessita configurar o IP da Estação de Controle de Solo.
* **exec-mavproxy-real-rpi.sh** -> Necessita configurar o IP da Estação de Controle de Solo e o device usado.
* **exec-swap-mission.sh** -> Necessita trocar o diretório em que encontra-se os arquivos da nova missão.
* **list-ips-in-use.sh** -> Necessita configurar o IP base da rede em que o notebook está logado.
