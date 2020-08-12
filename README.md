# Voting
Projeto Voting foi desenvolvido para mostrar e testar habilidades em Java usando [spring-boot](https://spring.io/projects/spring-boot). 

O projeto consiste em disponibilizar uma WebApi com funções de votação, onde é possível:

    - Cadastrar pautas
    - Abrir sessões de votação em uma pauta (default 1 minuto, ou determinar o tempo)
    - Receber votos nas pautas com sessões abertas
    - Contabilizar os votos e dar o resultado da votação da pauta

## Teste online

- O projeto fui disponibilizado online e a API pode ser testado através do [Swagger](https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api) no endereço [https://votingomr.herokuapp.com/swagger-ui.html](https://votingomr.herokuapp.com/swagger-ui.html) 

## Instalação Local

- Necessário ter instalado e configurado o [Apache Maven](https://maven.apache.org/);
- Necessário ter instalado um banco de dados, pode ser [postgresql](https://www.postgresql.org/) ou [mysql](https://www.mysql.com/);
- Para conectar no banco de dados troque o conteúdo da configuração do arquivo [application.properties](https://github.com/osmar85/voting/blob/master/src/main/resources/application.properties) por algum desses 
[application-mysql.properties](https://github.com/osmar85/voting/blob/master/src/main/resources/application-mysql.properties)
[application-postgres.properties](https://github.com/osmar85/voting/blob/master/src/main/resources/application-postgres.properties) adaptando usuário e senha.
- Nos exemplos está configurado para a porta 8000.
- Na raiz do projeto execute:
```bash
mvn clean package
```
- O Maven irá baixar as bibliotecas necessarias e gerar o executável na pasta target do projeto, basta executar o comando:
```bash
java -jar .\target\voting-1.0.0-SNAPSHOT.jar
```

- Testes Unitarios, na raiz do projeto execute:
```bash
mvn test
```
## Qualidade de software

- Foi utilizado o framework [JaCoCo](https://www.jacoco.org/jacoco/trunk/doc/maven.html) para verificar a cobertura de codigo fonte nos Testes Unitarios. Ao rodar os testes unitarios sempre será gerado um relatorio em [RaizProjeto]/target/site/jacoco/index.html
![cobertura de teste unitario](https://github.com/osmar85/voting/blob/master/img/q1.png)

- Foi utilizado o ferramenta [SonarQube](https://www.sonarqube.org/) para identificar bugs e codigos ruins no projeto
![SonarQube](https://github.com/osmar85/voting/blob/master/img/q2.png)
