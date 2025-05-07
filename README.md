# zapja-api
Api para o produto zapja, envio de mensagens via whatsapp automaticamente.

## DOCKER

-- Autenticação do Docker
  > gcloud auth configure-docker

-- Compilando
  > mvnw clean package -Dmaven.test.skip=true OU mvn -N io.takari:maven:wrapper
  > .\mvnw clean package --% -Dmaven.test.skip=true

-- Gerando Imagem Docker
  > docker build --build-arg JAR_FILE=target/*.jar -t asv-zapja-api .

-- Image to Google Cloud
  > docker tag  asv-zapja-api gcr.io/infra-assovio-1/asv-zapja-api
  > docker push gcr.io/infra-assovio-1/asv-zapja-api

## EXECUTAR PROJETO NO VSCODE

    1 - Rodar `mvn clean install` para instalar as dependências

    2 - Rodar `mvn spring-boot:run` para subir aplicação