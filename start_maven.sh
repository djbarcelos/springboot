#!/bin/bash
# ./mvnw clean install
java -jar target/springboot-0.0.1-SNAPSHOT.jar

# # Configuração
# PROJECT_DIR="target/"
# JAR_FILE="springboot-0.0.1-SNAPSHOT.jar"

# # Navega até o diretório do projeto
# cd "$(dirname "$0")"

# # Verifica se o arquivo JAR existe
# if [ ! -f "${PROJECT_DIR}${JAR_FILE}" ]; then
#     echo "Arquivo JAR não encontrado: ${PROJECT_DIR}${JAR_FILE}"
#     exit 1
# fi

# # Inicia a aplicação
# echo "Iniciando aplicação Spring Boot..."
# java -jar "${PROJECT_DIR}${JAR_FILE}"

# # Mensagem após a execução
# echo "Aplicação iniciada."
