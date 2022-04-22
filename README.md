# Trabalho Prático SO - Etapa 01

Realizamos testes com o objetivo de analisar qual seria a melhor forma de organização dos pedidos, utilizando arquivos .txt disponíveis no repositório.

- minimizar o tempo médio gasto para atender cada pedido;
- maximizar a quantidade de pedidos produzidos antes de meio dia, quando sai a primeira van para entrega - SJF (Shortest Job First) é a melhor opção, já que o tempo necessário para a conclusão de cada pedido é menor, maximizando a quantidade de pedidos produzidos em um menor tempo.
- atender a prazos estritos de clientes prioritários, que pagam a mais por isso - Escalonamento por prioridades considerando como prioritário os pedidos que têm menor prazo
- verificar se vale a pena comprar uma segunda esteira para ajudar no empacotamento - Depende da quantidade de pedidos e pacotes por dia. Por exemplo, nos arquivos de testse utilizados, com 300 pedidos e entre 1 e 1000 pacotes, seria interessante comprar uma segunda esteira para cumprir todos os pedidos no mesmo dia. Caso os prazos sejam maiores, não é necessário comprar uma nova esteira

Considerando uma situação em que não existam prazos a serem cumpridos, recomendamos a aplicação do algoritmo SJR, que maximiza a quantidade de pedidos entregues em um determinado tempo em comparação aos outros algoritmos.
