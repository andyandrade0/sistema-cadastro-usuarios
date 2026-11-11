-- cria o banco de dados
CREATE DATABASE bd_cadastrousuarios;

-- seleciona o banco
USE bd_cadastrousuarios;

-- cria a tabela de usuários (senha guardada em MD5)
CREATE TABLE usuario (
    usuario VARCHAR(10) PRIMARY KEY,
    nome VARCHAR(30) DEFAULT '',
    senha CHAR(32) DEFAULT ''
);
