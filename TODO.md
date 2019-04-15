# TO DO 

A seguir encontra-se diversas atividades para serem feitas no projeto.

## Sistema IFA:

* Incluir o método MILP4s
* Adicionar simplificador de rotas.
* Trocar velocidade de subida e decida se a bateria estiver acabando (automaticamente no IFA).
* Trocar velocidade de navegação se bateria estiver acabando (automaticamente no IFA).
* Atualizar arquivo parametros (properties) RTL_ALT_FINAL tem que ser 0 para pousar. (Tem na APM e na Pixhawk)
* Atualizar arquivo parametros (properties) BATT_CAPACITY tem que ser 2200 (em meus voos) para medir porcentagem. (Tem na APM e na Pixhawk)
* Atualizar arquivo parametros (properties) BATT2_CAPACITY tem que ser 2200 (em meus voos) para medir porcentagem. (Tem somente na Pixhawk)

## Sistema MOSA:

* Integrar o módulo do HCCQSP (Tese do Márcio).
* Integrar o módulo do HISA4m (Tese do Márcio).
* Integrar o módulo do CSA4m  (Tese do Márcio).

## Sistema UAV-GCS

* Plotar na GCS as rotas calculadas pelo computador de bordo que foram executadas pelo sistema MOSA.
* Definir no google maps pontos para trocar velocidade. Atualizar isso também no protocolo. Isso será util para fazer missões que necessitam ser realizadas com duas velocidades uma velocidade mais lenta, por exemplo, para etapa de pulverização. (behavior diferente).
