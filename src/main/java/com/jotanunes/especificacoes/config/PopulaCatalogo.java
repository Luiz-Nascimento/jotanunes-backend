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
                    new Material("Madeira"),
                    new Material("Aço Inox"),
                    new Material("Vidro Temperado"),
                    new Material("Alumínio"),
                    new Material("Cimento")
            ));
            System.out.println("✅ Materiais populados com sucesso!");
        }

        if (marcaRepository.count() == 0) {
            marcaRepository.saveAll(List.of(
                    new Marca("Tigre"),
                    new Marca("Deca"),
                    new Marca("Amanco"),
                    new Marca("Portobello"),
                    new Marca("Eliane")
            ));
            System.out.println("✅ Marcas populadas com sucesso!");
        }
    }
}
