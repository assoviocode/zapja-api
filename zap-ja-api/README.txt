
-- Compilando
	> mvnw clean package -Dmaven.test.skip=true

-- Gerando Imagem Docker
	> docker build --build-arg JAR_FILE=target/*.jar -t asv-zapja-api .
	
-- Rodando Docker
	> docker run -d --rm -p 8085:8080 asv-zapja-api
	
-- Image to Google Cloud
  > docker tag  asv-zapja-api gcr.io/infra-assovio-1/asv-zapja-api
  > docker push gcr.io/infra-assovio-1/asv-zapja-api