## Configurando as Coisas (feito para Windows)
### Configurando o Container
Após instalar o Docker no seu sistema operacional.
Vá no diretório `database-docker` e execute `docker-compose up -d` para iniciar o container, `-d` te deixa usar o mesmo terminal.

### Configurando o Banco de Dados (Postgres SQL)
Utilize o arquivo init.sql localizado em `database-docker`, é um script do PostgreSQL para criar as tabelas no gerenciador de banco de dados que você preferir.

### Outros
Não se esqueça de carregar as dependências do Maven.


-----
## Endpoints
#### GET - /carModel
Lista todos os modelos.

#### GET - /carModel/model/{nome_modelo}
Busca pelo nome de uma modelo sem case match podendo ser parte do nome.

#### POST - /carModel
Para upload de arquivo
params: fileType -> String (xlsx), fileBase64 -> String Base64 do arquivo.

#### PUT - /carModel
Atualiza modelo

#### DELEETE - /carModel/{id_modelo}
Deleta modelo

-----





