package com.jotanunes.especificacoes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jotanunes.especificacoes.dto.empreendimento.EspecificacaTecnicaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@Service
public class DocumentService {

    @Autowired
    private ObjectMapper objectMapper;

    // Ajuste este caminho para onde seu script e template estão no servidor
    private static final String NODE_PROJECT_PATH = "C:/caminho/para/sua/pasta/node";

    public byte[] gerarDocx(EspecificacaTecnicaDTO dados) throws IOException, InterruptedException {
        // 1. Cria arquivos temporários
        File tempJson = File.createTempFile("dados-", ".json");
        File tempDocx = File.createTempFile("especificacao-", ".docx");

        try {
            // 2. Escreve o DTO Java no arquivo JSON temporário
            objectMapper.writeValue(tempJson, dados);

            // 3. Prepara o comando Node.js
            ProcessBuilder pb = new ProcessBuilder(
                    "node",
                    "gerar-doc-api.js",
                    tempJson.getAbsolutePath(),
                    tempDocx.getAbsolutePath()
            );

            // Define o diretório de trabalho para onde está o script e o template .docx
            pb.directory(new File(NODE_PROJECT_PATH));
            pb.redirectErrorStream(true); // Redireciona erros para o output padrão para debug

            // 4. Executa o processo
            Process process = pb.start();

            // (Opcional) Ler logs do Node para debug no console do Java
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[NodeJS]: " + line);
                }
            }

            // Espera no máximo 30 segundos
            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished || process.exitValue() != 0) {
                throw new RuntimeException("Erro ao gerar DOCX via Node.js. Código de saída: " + (finished ? process.exitValue() : "TIMEOUT"));
            }

            // 5. Lê o arquivo DOCX gerado para um array de bytes
            return Files.readAllBytes(tempDocx.toPath());

        } finally {
            // 6. Limpeza: sempre deleta os arquivos temporários
            if (tempJson.exists()) tempJson.delete();
            if (tempDocx.exists()) tempDocx.delete();
        }
    }
}
