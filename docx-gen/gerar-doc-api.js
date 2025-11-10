import fs from "fs";
import path from "path";
import PizZip from "pizzip";
import Docxtemplater from "docxtemplater";

// Argumentos esperados:
// node gerar-doc-api.js <CAMINHO_DO_JSON_ENTRADA> <CAMINHO_DO_DOCX_SAIDA>
const inputJsonPath = process.argv[2];
const outputDocxPath = process.argv[3];

if (!inputJsonPath || !outputDocxPath) {
    console.error("❌ Erro: Caminhos de entrada e saída são obrigatórios.");
    process.exit(1);
}

try {
    // 1) Ler o JSON que o Java preparou
    const jsonRaw = fs.readFileSync(inputJsonPath, "utf-8");
    const data = JSON.parse(jsonRaw);

    // 2) Carregar template
    // Assumindo que o template está na mesma pasta do script.
    // Ajuste 'path.resolve(__dirname, ...)' se necessário, dependendo de como você executa.
    // Para ESM (seu caso com "import"), __dirname não existe nativamente, então usamos path.resolve('.')
    // que pega o diretório de onde o comando node foi executado.
    const templatePath = path.resolve("./especificao-modelo.docx");

    if (!fs.existsSync(templatePath)) {
        throw new Error(`Template não encontrado em: ${templatePath}`);
    }
    const content = fs.readFileSync(templatePath, "binary");
    const zip = new PizZip(content);

    const doc = new Docxtemplater(zip, {
        paragraphLoop: true,
        linebreaks: true,
    });

    // 3) Renderizar
    doc.render(data);

    // 4) Salvar no caminho que o Java pediu
    const buffer = doc.getZip().generate({ type: "nodebuffer" });
    fs.writeFileSync(outputDocxPath, buffer);

    console.log("✅ DOCX gerado com sucesso!");

} catch (error) {
    console.error("❌ Erro fatal no Node.js:");
    // Se for erro do docxtemplater, mostra detalhes
    if (error.properties && error.properties.errors) {
        error.properties.errors.forEach(e => console.error(e));
    } else {
        console.error(error);
    }
    process.exit(1); // Sai com erro para o Java saber
}