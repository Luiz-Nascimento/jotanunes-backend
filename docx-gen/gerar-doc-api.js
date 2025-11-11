import fs from "fs";
import path from "path";
import PizZip from "pizzip";
import Docxtemplater from "docxtemplater";

const inputJsonPath = process.argv[2];
const outputDocxPath = process.argv[3];

if (!inputJsonPath || !outputDocxPath) {
    console.error("❌ Erro: Caminhos de entrada e saída são obrigatórios.");
    process.exit(1);
}

try {

    const jsonRaw = fs.readFileSync(inputJsonPath, "utf-8");
    const data = JSON.parse(jsonRaw);

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

    doc.render(data);

    const buffer = doc.getZip().generate({ type: "nodebuffer" });
    fs.writeFileSync(outputDocxPath, buffer);

    console.log("✅ DOCX gerado com sucesso!");

} catch (error) {
    console.error("❌ Erro fatal no Node.js:");

    if (error.properties && error.properties.errors) {
        error.properties.errors.forEach(e => console.error(e));
    } else {
        console.error(error);
    }
    process.exit(1);
}