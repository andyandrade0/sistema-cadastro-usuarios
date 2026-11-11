<h1 align="center">Sistema de Cadastro de Usuários</h1>

<p align="center">◇ ◆ ◇ ◆ ◇ ◆ ◇</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-D4608B?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Swing-8E3B5B?style=for-the-badge" alt="Swing">
  <img src="https://img.shields.io/badge/MySQL-D4608B?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/Status-Estudo-F3C1D3?style=for-the-badge&labelColor=8E3B5B" alt="Status">
</p>

<p align="center">◇ ◆ ◇ ◆ ◇ ◆ ◇</p>

Projeto desenvolvido em Java para praticar o desenvolvimento de sistemas com interface gráfica e acesso a banco de dados. O sistema permite cadastrar, autenticar, alterar e excluir usuários.

---

## ◆ Tecnologias

* Java
* Java Swing (interface gráfica)
* MySQL
* JDBC (mysql-connector-j 9.6.0)

## ◆ Funcionalidades

* Cadastro de novos usuários
* Login com usuário e senha
* Alteração de nome e senha
* Exclusão da própria conta
* Validação dos campos em todas as telas

## ◆ Conceitos praticados

* Classes e objetos
* Encapsulamento (getters e setters)
* Sobrecarga de métodos
* Tratamento de exceções
* Eventos com ActionListener
* Conexão com banco de dados via JDBC
* Consultas parametrizadas (PreparedStatement)
* CRUD (criar, consultar, alterar e excluir)

## ◆ Estrutura do projeto

```
src/cadastrausuario
├── Conexao.java         conexão com o MySQL
├── Usuario.java         dados do usuário e operações no banco
├── TelaLogin.java       tela de login
├── TelaCadastro.java    tela de cadastro
├── TelaInicio.java      menu inicial
├── TelaAlteracao.java   alteração de dados
├── TestaConexao.java    teste da conexão
└── TestaTela.java       inicia o sistema
banco_de_dados.sql       script de criação do banco
```

## ◆ Como executar

1. Execute o script `banco_de_dados.sql` no MySQL para criar o banco e a tabela.
2. Confira os dados de acesso em `Conexao.java` (por padrão, usuário `root` e senha `root` em `127.0.0.1:3306`).
3. Adicione o `mysql-connector-j-9.6.0.jar` às bibliotecas do projeto.
4. Execute a classe `TestaTela`.

## ◆ Status

Projeto desenvolvido para fins de estudo.
by:andy andrade
---

<p align="center">◇ ◆ ◇ ◆ ◇ ◆ ◇</p>
