# Detalhamento do Desenvolvimento
Como comentado durante a entrevista, eu tenho uma preferência pessoal no desenvolvimento de uma **API**, eu começo pelo desenvolvimento das **entidades/dominios**, 
seguido de seu **DTO** e **repositorio**.  

  
  Feito isso, eu sigo para a construção do esqueleto dos métodos na **camada de serviço** e na **camada de controle**, e então eu faço a implementação dos métodos mais simples, 
  que geralmente são, não necessariamente nessa ordem:
- Pesquisa Geral (getAll)
- Pesquisa Especifica (getById)
- Exclusão (delete)  
  
Então, eu sigo para os métodos de **inserção** e **atualização**, que geralmente acabam pedindo DTO's "diferenciados" - segue exemplo do codigo que consta na **BeneficiarioController**:  
  
``public ResponseEntity<BeneficiarioDTO> updateBeneficiario(@RequestParam("id") Long id, @RequestBody BeneficiarioUpdateDTO beneficiarioUpdateDTO) throws ResourceNotFoundException {``  
  
O Método recebe um **BeneficiarioUpdateDTO**, envia para a camada de serviço um **BeneficiarioInputDTO** e retorna um **BeneficiarioDTO**, e a diferença entre eles? 
BeneficiarioDTO é um "espelho"  da entididade **Benficiario**, **BeneficiarioInputDTO** não tem os campos **Data de Inserção** e nem **Data de Atualização**, 
enquanto o **BeneficiarioUpdateDTO** além de não ter esses dois campos, não tem o campo referente a lista de documentos que um beneficiario tem - 
eu criei ele para poder colocar ele na chamada do endpoint de alteração, para que não apareça no swagger a entrada da lista, em um método aonde ela não é alterada. Eu poderia ter tentado
alterar a visualização dos atributos em alguns momentos especificos, mas achei mais produto criar os DTO's adicionais. 
  
Continuando com a explicação, ao finalizar de construir os métodos da **camada de serviço** e da **camada de controle** eu segui para a implementação do **swagger**, que acabou sendo o segundo maior desafio que eu encontrei durante o desenvolvimento deste teste, e eu explico o porque: Eu já havia mexido com swagger outras vezes, mas quando gerei meu projeto pelo **Spring Initializr** o projeto foi criado em Java 17 e a versão 3.2 do Spring, e depois de horas (pelo menos umas 5) eu não estava conseguindo de jeito nenhum fazer o swagger funcionar, e depois de muita tentativa, erro e pesquisa, eu descobri que é por uma inconsitência que existe entre o **jakarta** que consta nas versões mais recentes do spring, eu tentei mudar as versões para ver se conseguia fazer funcionar mas não obtive sucesso, até que eu criei um projeto do zero com versões mais antigas tanto do Java como do Spring e então consegui ele fazer funcionar.  

Terminando esse "calvario" eu segui para os **testes de usuario** nos endpoints para ver se eles estavam funcionando corretamente, essa fase me levou a implementar mais codigo para lidar com situações diferentes do **"caminho feliz"**, e assim que eu finalizei esse desenvolvimento, eu decidi então seguir para os **testes unitários** - por mais que eles não tenham sido solicitados, eu tinha tempo, e não teve uma vez que eu não tive que fazer alterações no codigo devido aos testes unitarios, e dessa vez não foi diferente, eu dei preferência em fazer os testes unitarios dos itens mais importantes da aplicação (Camada de Serviço e de Controle) e não me preocupei em fazer teste unitario de DTO's/Getters/Setters e derivados. Eu também não utilizei a annotation **@Data** do lombok para ele não gerar mais "buracos" no relatorio de testes do jacoco. 
  
 Durante os **testes unitarios** eu efetuei alterações no codigo como: 
 - Método sem tratativa de erro (Por exemplo: Os métodos de exclusão, eles excluiam e pronto, lembrei então de considerar que se isso fosse uma aplicação produtiva de uma empresa, 
 poderia dar falha na internet, no servidor, e etc, então o certo seria fazer uma tratativa de erro)
 - Métodos que tinham mais de um caminho (Sucesso/Erro), mas que não enviavam "mensagem customizavel" para o usuario final - como por exemplo o metodo de exclusão de beneficiario.  
   
Pode parecer pouco, mas eu sou bem meticuloso e paciente, não tento resolver tudo de uma vez, ao invés disso eu sigo o fluxo do desenvolvimento, e vou deixando que em cada etapa me ajude 
a entregar um projeto final mais robusto, já que durante o desenvolvimento, a parte mais abstrata do projeto (especificação) vai se tornando mais concreta, o que no meu caso pessoalmente falando, facilita o entendimento como um todo, e me ajuda a perceber os pontos de melhoria até a entrega do projeto. 
  
Finalizado as etapas anteriores, deixei a **autenticação** por ultimo, eu até hoje só tinha trabalhado em projetos que já tinham um sistema de autenticaçao implementado, e eu achei que não seria algo tããooo complicado, mas eu me enganei, eu tentei e muito tentar implementar uma autenticação com **JWT** mas não obtive sucesso, o tópico se mostrou muito complexo pela quantidade de tempo que eu tive para o teste, eu não me arrependo da ordem que segui no desenvolvimento, porque se eu tivesse parado qualquer etapa de desenvolvimento para fazer a implementação do **JWT**, eu não teria o código na qualidade e com as implementações que eu fiz, então para conseguir completar o objetivo do desafio, eu fiz uma implementação de autenticação simples pelo **Spring Security. (O usuario para acesso da aplicação é "ekanavaliacao" e a senha é "candidatoaprovado)"** 
  
Durante o desenvolvimento, eu me deparei com duvidas referente ao que foi solicitado na avaliação, não sei se foi falha minha de entendimento, se realmente faltou a informação 
ou se no final era uma pegadinha, ainda sim eu anotei esses pontos para deixar em evidência, e em caso de feedback tanto positivo como negativo, eu adoraria saber qual das opções que é, seguem os itens:
- Não ter um campo "Chave Estrangeira" na tabela "Documento" referenciando a tabela "Beneficiario"
- Existir um campo "DataAtualizacao" em "Documento", dado que não existe feature que atualiza
- Não foi informado os "Tipo Documentos" aceitos, caso tivesse sido especificado, eu teria implementado um ENUM
- Não foi informado se na função "Listar todos os beneficiários cadastrados" deveria-se trazer os documentos cadastrados (Na implementação eu considerei que sim)
- Como só existe a funcionalidade de excluir "Beneficiario" e não "Excluir documentos", eu fiz uma tratativa para apagar os documentos também 
- Não foi informado o resultado esperado na funcionalidade de "Inserir Beneficiario" e "Atualizar Beneficiario", retornei então o Beneficiario com seus devidos documentos
  
  
  
##  
## Instruções para Executar a Build  
Estou considerando que a pessoa que irá rodar o teste já tem um ambiente de desenvolvimento instalado na maquina com as variaveis de ambiente devidamente configuradas.  
Para subir a aplicação você pode seguir qualquer um dos trës passos a seguir: 
- Executar o arquivo **"avaliacao-1.0.jar"** que consta dentro da pasta **/target** / Clicar nesse link: [avaliacao-1.0.jar](target/avaliacao-1.0.jar)
- Executar o arquivo **"avaliacao-run.bat"** que consta dentro da pasta **/target** / Clicar nesse link: [avaliacao-run.bat](target/avaliacao-run.bat)
- Importar o projeto em uma IDE, e subir ela como **Java Application** não esquecendo de apontar a classe **com.ekan.avaliacao.AvaliacaoApplication.java** 
  
(Por algum motivo não consegui fazer os links tanto do jar como do bat manteem o cmd aberto, e finalizarem a aplicação ao encerrar)  
(Caso queira refazer o build, executar como um **Maven Project** apontando para a pasta do projeto e colocando como goals o seguinte texto: clean install package 
  
O caminho do swagger para teste dos endpoints está em: [Swagger](http://localhost:8080/swagger-ui/) ou [Swagger Index](http://localhost:8080/swagger-ui/index.html)
  
Você pode ver o relatório de cobertura do Jacoco no link a seguir: [Jacoco Report](/avaliacao/target/site/jacoco/index.html)

