package com.jotanunes.especificacoes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jotanunes.especificacoes.dto.empreendimento.EspecificacaTecnicaDTO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Service
public class DocGenerateService {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(DocGenerateService.class);

    @Value("${app.docs.project-path:./docx-gen}")
    private String nodeProjectPath;
    @Value("${app.docs.script-name:gerar-doc-api.js}")
    private String nodeScriptname;
    @Value("${app.docs.node-command:node}")
    private String nodeCommand;
    @Value("${app.docs.timeout-seconds:30}")
    private int timeoutSeconds;

    @Value("${app.docs.max-concurrent:5}")
    private int maxConcurrent;

    private Semaphore semaphore;

    @PostConstruct
    public void init() {
        this.semaphore = new Semaphore(maxConcurrent);
        logger.info("DocGenerateService inicializado com {} permissões de concorrência", maxConcurrent);
    }




    public byte[] gerarDocx(EspecificacaTecnicaDTO dados) throws IOException, InterruptedException {

        logger.debug("Aguardando permissao para gerar DOCX... {} permissoes disponiveis", semaphore.availablePermits());

        semaphore.acquire();

        logger.debug("Permissão adquirida. Iniciando geração do DOCX.");
        try {
            Path nodeDirectory = Paths.get(nodeProjectPath).toAbsolutePath().normalize();
            if (!Files.isDirectory(nodeDirectory)) {
                logger.error("Diretório do projeto node não encontrado: {}", nodeDirectory);
                throw new IllegalStateException("Pasta '" + nodeProjectPath + "' não encontrada.");
            }

            Path tempJson = Files.createTempFile("dados-", ".json");
            Path tempDocx = Files.createTempFile("especificacao-", ".docx");

            logger.debug("Arquivos temporários criados: JSON={}, DOCX={}", tempJson, tempDocx);

            try {
                Files.write(tempJson, objectMapper.writeValueAsBytes(dados));

                ProcessBuilder pb = new ProcessBuilder(
                        nodeCommand,
                        nodeScriptname,
                        tempJson.toString(),
                        tempDocx.toString()
                );

                pb.directory(nodeDirectory.toFile());
                pb.redirectErrorStream(true);

                logger.info("Iniciando processo Node.js para gerar DOCX...");
                Process process = pb.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        logger.debug("[NodeJS]: {}", line);
                    }
                }

                boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);

                if (!finished || process.exitValue() != 0) {
                    if (!finished) {
                        process.destroyForcibly();
                        logger.error("Time ao gerar DOCX após {}s", timeoutSeconds);
                    } else {
                        logger.error("Processo Node falhou com código de saída: {}", process.exitValue());
                    }
                    throw new RuntimeException("Falha na geração do DOCX. Verifique os logs para detalhes");
                }
                logger.info("DOCX gerado com sucesso!");
                return Files.readAllBytes(tempDocx);

            } finally {
                try {
                    Files.deleteIfExists(tempJson);
                    Files.deleteIfExists(tempDocx);
                } catch (IOException e) {
                    logger.warn("Não foi possível limpar alguns arquivos temporários: {}", e.getMessage());
                }
            }
        } finally {
            semaphore.release();
            logger.debug("Permissao liberada. {} permissoes disponiveis", semaphore.availablePermits());
        }
    }
}