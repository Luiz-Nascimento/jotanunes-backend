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

    // === CORREÇÃO PRINCIPAL AQUI ===
    // Usa caminho relativo. O "." representa a raiz do projeto onde o Java está rodando.
    // Funciona localmente e no Heroku.
    private static final String NODE_PROJECT_PATH = "./docx-gen";
    private static final String NODE_SCRIPT_NAME = "gerar-doc-api.js";
    private static final String NODE_COMMAND = "node";

    public byte[] gerarDocx(EspecificacaTecnicaDTO dados) throws IOException, InterruptedException {
        // Validação de segurança para não ficar batendo cabeça se a pasta não existir
        File nodeDir = new File(NODE_PROJECT_PATH);
        if (!nodeDir.exists() || !nodeDir.isDirectory()) {
            throw new IllegalStateException("Pasta '" + NODE_PROJECT_PATH + "' não encontrada na raiz do projeto. Verifique se ela existe.");
        }

        File tempJson = File.createTempFile("dados-", ".json");
        File tempDocx = File.createTempFile("especificacao-", ".docx");

        try {
            objectMapper.writeValue(tempJson, dados);

            ProcessBuilder pb = new ProcessBuilder(
                    NODE_COMMAND,
                    NODE_SCRIPT_NAME, // Usa apenas o nome do script
                    tempJson.getAbsolutePath(),
                    tempDocx.getAbsolutePath()
            );

            // Define que o comando 'node' vai rodar DENTRO da pasta 'docx-gen'
            pb.directory(nodeDir);
            pb.redirectErrorStream(true);

            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Dica: Em produção, use um Logger (ex: slf4j) em vez de System.out
                    System.out.println("[NodeJS]: " + line);
                }
            }

            boolean finished = process.waitFor(30, TimeUnit.SECONDS);
            if (!finished || process.exitValue() != 0) {
                // Se falhar, destrói o processo para não ficar pendurado
                if (!finished) process.destroyForcibly();
                throw new RuntimeException("Erro na geração do DOCX. Código de saída: " + (finished ? process.exitValue() : "TIMEOUT"));
            }

            return Files.readAllBytes(tempDocx.toPath());

        } finally {
            // Tenta deletar, mas não falha o request se não conseguir (apenas loga o erro se quiser)
            if (tempJson.exists()) tempJson.delete();
            if (tempDocx.exists()) tempDocx.delete();
        }
    }
}