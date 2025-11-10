# --- Estágio 1: Construção (Opcional) ---
# FROM maven:3.9-eclipse-temurin-21 AS build  <-- AJUSTADO PARA JAVA 21
# WORKDIR /app
# COPY pom.xml .
# COPY src ./src
# RUN mvn clean package -DskipTests

# --- Estágio Final: A Imagem que vai rodar ---
# Usa a imagem base do Java 21 (JRE é suficiente para rodar e é menor que o JDK)
FROM eclipse-temurin:21-jre-jammy

# 1. Instalação do Node.js (v20 LTS) no Linux
# (Os comandos continuam os mesmos, pois o Linux base 'jammy' é o Ubuntu 22.04)
RUN apt-get update && apt-get install -y curl gnupg \
    && curl -fsSL https://deb.nodesource.com/gpgkey/nodesource-repo.gpg.key | gpg --dearmor -o /usr/share/keyrings/nodesource.gpg \
    && echo "deb [signed-by=/usr/share/keyrings/nodesource.gpg] https://deb.nodesource.com/node_20.x nodistro main" | tee /etc/apt/sources.list.d/nodesource.list \
    && apt-get update && apt-get install -y nodejs \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Verifica as versões instaladas (para debug no log do build)
RUN java -version && node -v && npm -v

# 2. Definindo o diretório de trabalho
WORKDIR /app

# 3. Copia o .jar da aplicação
COPY target/*.jar app.jar

# 4. Copia a pasta 'docx-gen' com o script e o template
COPY docx-gen ./docx-gen

# 5. Instala as dependências do Node.js
# Entra na pasta, roda o npm install e volta para a raiz /app
RUN cd docx-gen && npm ci --only=production && cd ..

# 6. (Opcional) Cria usuário não-root para segurança
# RUN useradd -m myuser
# USER myuser

# 7. Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]