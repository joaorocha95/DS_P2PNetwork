# Trabalho Prático de Sistemas Distribuidos
Grupo: 

Eduardo Amaral - up201805189

João Rocha - up201805199

## Parte 1 - Token Ring

Instruções de compilação:

1. Correr o comando "javac *.java && java Peer 'IP da Máquina atual' 'Porta da Máquina atual'.
2. Inserir o IP da máquina a que se pretende ligar.
3. Inserir a Porta da máquina a que se pretende ligar.
4. Fazer os passos 2 e 3 até fechar o Token Ring.
5. Escrever qualquer coisa na shell das máquinas que não irão começar com o token.
6. Escrever Start na máquina que pretende que comece com o token.
7. Após o inicio da execução pode correr os comandos lock() e unlock() para parar ou libertar o token da maquina pretendida.


##  Parte 2 - Rede P2P

Instruções de compilação:

1. Correr o comando "javac *.java && java Peer 'IP da Máquina atual' 'Porta da Máquina atual'.
2. Para interagir com outras máquinas use os comandos definidos:
    * Register('IP da Máquina Desejada':'Porta da Máquina Desejada') - é usado para registar uma máquina, a máquina que efetua o pedido é automaticamente registada na máquina com o IP escrito.
    * push('IP da Máquina Desejada':'Porta da Máquina Desejada') - Envia as informações presentes no seu dicionário para a máquina com o IP escrito.
    * pull('IP da Máquina Desejada':'Porta da Máquina Desejada') - Pede as informações presentes no seu dicionário da máquina com o IP escrito.
    * pushpull('IP da Máquina Desejada':'Porta da Máquina Desejada') - Pede as informações presentes no seu dicionário da máquina com o IP escrito, e envia as suas informações para esta.
    * show() - Mostra as palavras presentes no dicionário da máquina atual.

Nota: Os pedidos GET à API usada por vezes podem falhar devido à natureza da API usada.

##  Parte 3 - Reliable Totally Ordered Multicast

Instruções de compilação:

1. Correr o comando "javac *.java && java Peer 'IP da Máquina atual' 'Porta da Máquina atual'.
2. Ligar todas as máquinas através do comando "join('IP da máquina desejada':'PORTA da máquina desejada')", a máquina que efetua o pedido é automaticamente registada na máquina com o IP escrito.
3. Digitar a mensagem que pretende enviar as máquinas ligadas.


Qualquer dúvida através do email up201805189@up.pt e up201805199@up.pt