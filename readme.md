Sistema Gerencial de Estudantes!
===================

Aplicação recebe um `POST` para executar o processo de envio de e-mail para todos os alunos com notas
inferiores à 7.0 e retorna a quantidade de e-mails processados. 


Obtendo o código fonte
-------------
```
git@github.com:ttiede/sge.git
```


Configuração do ambiente de desenvolvimento
-------------
> - Apache Maven  versão 3.3.9
> - Java versão 1.8.0_151, vendor: Oracle Corporation
> - Default locale: pt_BR, platform encoding: UTF-8
> - OS name: "linux", version: "4.8.0-53-generic", arch: "amd64", family: "unix"
> - Spring boot  versão 1.4.4.RELEASE  

Requisitos
-------------
> - Apache Maven  versão 3.3.9
> - Java versão 1.8.0_151, vendor: Oracle Corporation
> - Default locale: pt_BR, platform encoding: UTF-8

Gerando Build do Projeto
-------------

Recomendo o build seguindo os seguintes comandos:

``` 
// BUILD

mvn clean
mvn install
```

Executando a aplicação
-------------

Para executar a aplicação recomendo o comando abaixo:

``` 
// run
java -jar target/sge-1.0.0.jar
```

Quando a aplicação for executada,  ela pode ser acessada pela URL ```http://localhost:8080```
A aplicação receber um POST contendo os dados dos alunos possíveis para o recebimento da mensagem.

Exemplo de POST:

```
//CURL

curl -X POST \
  http://localhost:8080/maladireta \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: d953cb04-6b1e-fd4a-3539-2034ec9b7b62' \
  -d ' [
 	{
 		"nome": "Jose da Silva",
 		"endereco": "Rua Silvio Santos, 55",
 		"cep": "99999-929",
 		"mensagem": "Texto referente a mensagem para o aluno"
 		},
 	{
 		"nome": "Tiago Tiede",
 		"endereco": "Rua Maestro Zeferino Santa, 132, apto 6",
 		"cep": "18040-011",
 		"mensagem": "Texto referente a mensagem para o aluno"
 		}
]'
```


O desenvolvimento utilizou o `Mockit`  para asr espostas `API's` de retorno das URL  `/alunos` e `/alunos/#{cpf}/notas `


```
GET: /alunos
GET: /alunos/#{cpf}/notas

```  

As chamadas dessas URL também sendo requisitadas na porta `8080`


Caso seja necessário modificações no consumo dessas `API's`,  recomendo que essa a alteração seja feita nas seguintes classes e métodos

``` 
// GET: /alunos
// Class: StudentServiceImpl
// Method: getAllStudents() 
```


``` 
// GET: /alunos/#{cpf}/notas
// Class: CourseServiceImpl
// Method: getCourseByStudent(String studentDocument) 
```


Dentro dessas `Classes`, existe um sobrescrita de `content`, comentada conforme o exemplo enviado para o exercício `API's`


``` 
// GET: /alunos
// Class: StudentServiceImpl
// Method: getAllStudents() 
// lines: 60,61
```


``` 
// GET: /alunos/#{cpf}/notas
// Class: CourseServiceImpl
// Method: getCourseByStudente() 
// lines: 67, 68
```


Quando descomentado essa sobre escrita do valor `content`, pode ser alterado conforme desejado. Caso seja alterado reptir o processo de build novamente.
