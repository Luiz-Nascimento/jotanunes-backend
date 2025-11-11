FROM eclipse-temurin:21-jre-jammy

RUN apt-get update && apt-get install -y curl gnupg \
    && curl -fsSL https://deb.nodesource.com/gpgkey/nodesource-repo.gpg.key | gpg --dearmor -o /usr/share/keyrings/nodesource.gpg \
    && echo "deb [signed-by=/usr/share/keyrings/nodesource.gpg] https://deb.nodesource.com/node_20.x nodistro main" | tee /etc/apt/sources.list.d/nodesource.list \
    && apt-get update && apt-get install -y nodejs \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

RUN java -version && node -v && npm -v

WORKDIR /app

COPY target/*.jar app.jar

COPY docx-gen ./docx-gen

RUN cd docx-gen && npm ci --only=production && cd ..

ENTRYPOINT ["java", "-jar", "app.jar"]
