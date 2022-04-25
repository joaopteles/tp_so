# Trabalho Prático SO - Etapa 01

Realizamos testes com o objetivo de analisar qual seria a melhor forma de organização dos pedidos, utilizando arquivos .txt disponíveis no repositório.

- Minimizar o tempo médio gasto para atender cada pedido - Como todos os pedidos chegam no mesmo horário, o tempo médio de produção é o mesmo com a aplicação de qualquer algoritmo. Apenas se houver uma quantidade de pedidos que não pode ser atendida em um único dia, o método SJF minimiza o tempo gasto em cada pedido por priorizar menores pedidos.
- Maximizar a quantidade de pedidos produzidos antes de meio dia, quando sai a primeira van para entrega - SJF (Shortest Job First) é a melhor opção, já que o tempo necessário para a conclusão de cada pedido é menor, maximizando a quantidade de pedidos produzidos em um menor tempo.
- Atender a prazos estritos de clientes prioritários, que pagam a mais por isso - Escalonamento por prioridades considerando como prioritário os pedidos que têm menor prazo
- Verificar se vale a pena comprar uma segunda esteira para ajudar no empacotamento - Isso depende da quantidade de pedidos e pacotes por dia. Por exemplo, nos arquivos de teste utilizados, com 300 pedidos e entre 1 e 1000 pacotes, seria interessante comprar uma segunda esteira para cumprir todos os pedidos no mesmo dia. Caso os prazos sejam maiores, não é necessário comprar uma nova esteira

Considerando uma situação em que não existam prazos a serem cumpridos, recomendamos a aplicação do algoritmo SJR, que maximiza a quantidade de pedidos entregues em um determinado tempo em comparação aos outros algoritmos.


Output utilizando o arquivo de testes 1:

##### RELATÓRIO FCFS #####
Total de pedidos empacotados: 116
Tempo total: 538.7833333333333 minutos
Hora início: 08:00
Hora Fim: 16:58:47
Tempo médio para empacotar cada pedido: 278 segundos
Pedidos produzidos até 12H: 57
Não houve priorização de pedidos

##### RELATORIO SJF #####
Total de pedidos: 194
Tempo total: 538.0 minutos
Hora inicio: 08:00
Hora Fim: 16:58:37
Tempo medio para empacotar cada pedido: 166 segundos
Pedidos produzidos ate 12H: 109


##### RELATÓRIO PRIORIDADES #####
Total de pedidos: 106
Tempo total: 542.0 minutos 
Hora início: 08:00
Hora Fim: 17:02:21
Tempo médio para empacotar cada pedido: 306 segundos 
Pedidos produzidos até 12H: 50


##### RELATÓRIO ROUND ROBIN #####
Total de pedidos empacotados: 130
Tempo total: 542.0 minutos 
Hora início: 08:00
Hora Fim: 17:02:45
Tempo médio para empacotar cada pedido: 250 segundos
Pedidos produzidos até 12H: 57
Não houve priorização de pedidos

Output utilizando o arquivo de testes 2:

##### RELATÓRIO FCFS #####
Total de pedidos empacotados: 10
Tempo total: 543.7083333333334 minutos 
Hora início: 08:00
Hora Fim: 17:03:43
Tempo médio para empacotar cada pedido: 3262 segundos 
Pedidos produzidos até 12H: 9
Não houve priorização de pedidos


##### RELATORIO SJF #####
Total de pedidos: 78
Tempo total: 546.0 minutos 
Hora inicio: 08:00
Hora Fim: 17:06:25
Tempo medio para empacotar cada pedido: 420 segundos 
Pedidos produzidos ate 12H: 48


##### RELATÓRIO PRIORIDADES #####
Total de pedidos: 24
Tempo total: 546.0 minutos 
Hora início: 08:00
Hora Fim: 17:06:06
Tempo médio para empacotar cada pedido: 1365 segundos 
Pedidos produzidos até 12H: 10


##### RELATÓRIO ROUND ROBIN #####
Total de pedidos empacotados: 22
Tempo total: 550.0 minutos
Hora início: 08:00
Hora Fim: 17:10:11
Tempo médio para empacotar cada pedido: 1500 segundos
Pedidos produzidos até 12H: 9
Não houve priorização de pedidos


Output utilizando o arquivo de testes 3:

##### RELATÓRIO FCFS #####
Total de pedidos empacotados: 20
Tempo total: 37.45 minutos 
Hora início: 08:00
Hora Fim: 08:37:27
Tempo médio para empacotar cada pedido: 112 segundos 
Pedidos produzidos até 12H: 20
Não houve priorização de pedidos


##### RELATORIO SJF #####
Total de pedidos: 20
Tempo total: 37.0 minutos 
Hora inicio: 08:00
Hora Fim: 08:37:27
Tempo medio para empacotar cada pedido: 112 segundos 
Pedidos produzidos ate 12H: 20


##### RELATÓRIO PRIORIDADES #####
Total de pedidos: 20
Tempo total: 37.0 minutos
Hora início: 08:00
Hora Fim: 08:37:27
Tempo médio para empacotar cada pedido: 112 segundos
Pedidos produzidos até 12H: 20


##### RELATÓRIO ROUND ROBIN #####
Total de pedidos empacotados: 20
Tempo total: 36.0 minutos
Hora início: 08:00
Hora Fim: 08:36:50
Tempo médio para empacotar cada pedido: 110 segundos
Pedidos produzidos até 12H: 20
Não houve priorização de pedidos

