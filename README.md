# 🗺️ Calculadora de Distâncias entre Cidades

Este projeto é uma aplicação em Java que calcula a distância mais curta entre duas cidades usando o algoritmo de Dijkstra. Além disso, permite criar e listar cidades utilizando estruturas de dados como pilha e fila.

## 📚 Sobre o projeto

O sistema carrega um arquivo `.txt` contendo as distâncias entre cidades e constrói um grafo com essas informações.

A partir disso, o usuário pode:

• Calcular a distância entre duas cidades  
• Criar e listar uma pilha de cidades  
• Criar e listar uma fila de cidades  
• Encerrar o programa

Para o cálculo da menor distância, foi utilizado o algoritmo de Dijkstra.

## 🚀 Tecnologias utilizadas

• Java  
• Estruturas de dados (Pilha e Fila)  
• Algoritmo de Dijkstra

## 🔍 Funcionamento

O arquivo de entrada (`distancias_cidades.txt`) deve conter as informações no seguinte formato:

```
CidadeOrigem;CidadeDestino;Distancia
```

Exemplo:

```
Blumenau;Gaspar;20
Gaspar;Itajaí;40
```

Durante a execução, o programa solicita ao usuário a cidade de origem e a cidade de destino, realizando o cálculo da menor distância.  
Também é possível criar e listar as cidades utilizando pilha e fila.

Obs: O programa remove os acentos das palavras para evitar inconsistências nas buscas.

## 🏁 Resultados finais

• Grafo dinâmico construído a partir de arquivo externo  
• Cálculo eficiente das distâncias utilizando o algoritmo de Dijkstra  
• Implementação de pilha e fila para manipulação dos dados  
• Tratamento de erros para entradas inválidas e leitura de arquivos
