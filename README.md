# Desafio IBM: Criar uma aplicação Web que simule transações financeiras de um banco.

Dados técnicos:
<UL TYPE="disc">
<LI>Linguagem: Java</LI>
<LI>IDE: Eclipse</LI>
<LI>Banco de dados utilizado: MySQL</LI>
<LI>Framework front-end: Vaadin</LI>
<LI>Framework back-end: EclipseLink</LI>
<LI>Servidor de aplicação: WildFly</LI>
</UL><BR>
	
	

No sistema implementado existem dois tipos de usuários: Clientes e Gerentes.
Para realizar o login no sistema é necessário utilizar seu CPF e senha já cadastrados por um Gerente.




Funções do Cliente:
<UL TYPE="disc">
<LI>Visualizar seu saldo e número da conta</LI>
<LI>Gerar extrato do período que selecionar</LI>
<LI>Realizar saques, depósitos e transferências para outras contas.</LI>
</UL>	




Operações do Cliente:
<UL TYPE="disc">
<LI>Saque: Um cliente pode realizar saque contanto que o valor não exceda o saldo da conta.</LI>
<LI>Depósito: É possível depositar qualquer valor (dentro dos limites de um Double).</LI>
<LI>Transferência: Um cliente pode efetuar uma transferência para a conta de qualquer cliente que não seja ele mesmo. Obs: contanto que o valor não exceda o saldo da conta.</LI>
<LI>Extrato:
<UL TYPE="circle">
<LI>É necessário escolher as datas e clicar no botão de gerar extrato.</LI>
<LI>O extrato pode ser ordenado e filtrado por campos das colunas</LI>
<LI>Por padrão um extrato da última semana é gerado.</LI>
<LI>No extrato, transações onde o dinheiro "saiu" da conta do cliente ficam em vermelhas e aquelas que o dinheiro "entrou" ficam verdes.</LI>
</UL></LI>
<LI>Alteração de dados: os clientes podem alterar seu endereço, nome, e-mail, data de nascimento e senha clicando no símbolo de engrenagem no canto superior direito.</LI>
</UL>
Obs: O tipo de usuário (cliente/gerente), CPF e conta não podem ser alterados.




Funções do Gerente:
<UL TYPE="disc">
<LI>Realizar as mesmas funções do cliente, tendo em vista que ele também é um.</LI>
<LI>Acessar o relatório contendo as transações de todos os clientes pelo período que selecionar.</LI>
<LI>Visualizar o saldo da conta de todos os clientes.</LI>
<LI>Gerenciar usuários.</LI>
</UL>
	
	
	
	
Operações do Gerente:
<UL TYPE="disc">
<LI>Relatório de transações: O Gerente pode visualizar todas as transferências selecionando o período e clicando no botão "Gerar relatórios".</LI>
<LI>Para o relatório de transações, as cores definem o que aquela transação significa para o banco, sendo:
<UL TYPE="circle">
<LI>Vermelha -> Dinheiro saiu do banco</LI>
<LI>Verde -> Dinheiro entrou no banco</LI>
<LI>Amarela -> O dinheiro mudou de cliente mas continua no banco</LI>
</UL></LI>
<LI>Saldos de contas: Consultar o saldo de todas as contas.</LI>
<LI>Gerenciar Usuários: Nesta tela o Gerente é capaz de criar novos usuários (tanto clientes quanto gerentes). Consultar as informações de cada usuário e realizar o reset da senha de cada usuário para a padrão, "abc123" sem aspas;</LI>
</UL></LI>
		
	
	
	
Considerações:
<UL TYPE="disc">
<LI>Os campos foram verificados para evitar erros, a maioria dos campos do sistema possui um "formatter" evitando digitação de caracteres não esperados.</LI>
<LI>A única tela existente antes da realização do login é a própria tela de login, as outras são criadas no momento que o login é realizado e são destruídas após o logout evitando acessos indevidos.</LI>
<LI>AS Views de gerência não podem ser acessadas por clientes pois não existem em seu contexto.</LI>
<LI>As senhas são armazenadas em formato hash utilizando o MD5.</LI>
<LI>Para o login é verificado se o hash da senha digitada coincide com o armazenado para o usuário.</LI>
</UL>
