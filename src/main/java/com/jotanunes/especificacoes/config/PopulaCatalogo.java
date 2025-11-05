package com.jotanunes.especificacoes.config;

import com.jotanunes.especificacoes.model.Material;
import com.jotanunes.especificacoes.model.Marca;
import com.jotanunes.especificacoes.repository.MaterialRepository;
import com.jotanunes.especificacoes.repository.MarcaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PopulaCatalogo implements CommandLineRunner {

    private final MaterialRepository materialRepository;
    private final MarcaRepository marcaRepository;

    public PopulaCatalogo(MaterialRepository materialRepository, MarcaRepository marcaRepository) {
        this.materialRepository = materialRepository;
        this.marcaRepository = marcaRepository;
    }

    @Override
    public void run(String... args) {
        if (materialRepository.count() == 0) {
            materialRepository.saveAll(List.of(
                    new Material("Cerâmica"),
                    new Material("Laminado"),
                    new Material("Esquadria"),
                    new Material("Ferragem"),
                    new Material("Inst. Elétrica"),
                    new Material("Metal Sanitário"),
                    new Material("Louças"),
                    new Material("Porta (alumínio)"),
                    new Material("Cuba (inox)"),
                    new Material("Porcenalato"),
                    new Material("Cuba (louça)")
            ));
            System.out.println("Materiais populados com sucesso!");
        }

        if (marcaRepository.count() == 0) {
            marcaRepository.saveAll(List.of(
                    new Marca("Aliança"),
                    new Marca("Alumasa"),
                    new Marca("Arouca"),
                    new Marca("Alumbra"),
                    new Marca("Arielle"),
                    new Marca("Atlantica"),
                    new Marca("Biancogrês"),
                    new Marca("Camelo Fior"),
                    new Marca("Celite"),
                    new Marca("Ceusa"),
                    new Marca("Deca"),
                    new Marca("Durafloor"),
                    new Marca("Docol"),
                    new Marca("Eliane"),
                    new Marca("Elizabeth"),
                    new Marca("Esaf"),
                    new Marca("Esteves"),
                    new Marca("Eucatex"),
                    new Marca("Espaçofloor"),
                    new Marca("Fabrimar"),
                    new Marca("Fame"),
                    new Marca("Forusi"),
                    new Marca("Frank"),
                    new Marca("Ghel Plus"),
                    new Marca("Icasa"),
                    new Marca("Ilumi"),
                    new Marca("Incepa"),
                    new Marca("Incesa"),
                    new Marca("Imab"),
                    new Marca("Kelly"),
                    new Marca("La Fonte"),
                    new Marca("Lef"),
                    new Marca("Logasa"),
                    new Marca("Margirius"),
                    new Marca("Mari Louças Icasa"),
                    new Marca("Mari Louças Ideal"),
                    new Marca("Meber"),
                    new Marca("Mgm"),
                    new Marca("Pado"),
                    new Marca("Pamesa"),
                    new Marca("Papaiz"),
                    new Marca("Peesa"),
                    new Marca("Pianox"),
                    new Marca("Pointer"),
                    new Marca("Portinari"),
                    new Marca("Portobello"),
                    new Marca("Ramassol"),
                    new Marca("Roca"),
                    new Marca("Schneider"),
                    new Marca("Silvana"),
                    new Marca("Soprano"),
                    new Marca("Stam"),
                    new Marca("Steck"),
                    new Marca("Talita"),
                    new Marca("Tecnocuba"),
                    new Marca("Tecnogres"),
                    new Marca("Tramontina"),
                    new Marca("Una Max"),
                    new Marca("Unicasa"),
                    new Marca("Villagres")
            ));
            System.out.println("Marcas populadas com sucesso!");
        }
    }
}
