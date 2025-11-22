package com.jotanunes.especificacoes.mapper;

import com.jotanunes.especificacoes.dto.CombinacaoEMM.MaterialMarcasDocResponse;
import com.jotanunes.especificacoes.dto.ambiente.AmbienteDocResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoRequest;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoResponse;
import com.jotanunes.especificacoes.dto.empreendimento.EmpreendimentoUpdate;
import com.jotanunes.especificacoes.dto.empreendimento.EspecificacaTecnicaDTO;
import com.jotanunes.especificacoes.enums.TipoAmbiente;
import com.jotanunes.especificacoes.model.Ambiente;
import com.jotanunes.especificacoes.model.CombinacaoEMM;
import com.jotanunes.especificacoes.model.Empreendimento;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// MUDANÇA 1: Alterado de 'interface' para 'abstract class'
@Mapper(componentModel = "spring", uses = {AmbienteMapper.class})
public abstract class EmpreendimentoMapper {

    // MUDANÇA 2: Injeção real do Spring.
    // O MapStruct vai gerar uma subclasse que usa este campo.
    @Autowired
    protected AmbienteMapper ambienteMapper;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDto(EmpreendimentoUpdate dto, @MappingTarget Empreendimento empreendimento);

    @Mapping(source = "criadoPor.nome", target = "criadoPor")
    public abstract EmpreendimentoResponse toDto(Empreendimento empreendimento);

    @Mapping(source = "empreendimento.criadoPor.nome", target = "criadoPor")
    @Mapping(target = "totalAmbientes", source = "totalAmbientes")
    @Mapping(target = "totalItens", source = "totalItens")
    @Mapping(target = "totalMarcas", source = "totalMarcas")
    @Mapping(target = "totalMateriais", source = "totalMateriais")
    public abstract EmpreendimentoResponse toDtoDetalhado(Empreendimento empreendimento,
                                                          Integer totalAmbientes,
                                                          Integer totalItens,
                                                          Integer totalMarcas,
                                                          Integer totalMateriais);


    public abstract List<EmpreendimentoResponse> toDtoList(List<Empreendimento> entities);

    public abstract Empreendimento requestToEntity(EmpreendimentoRequest empreendimentoRequest);

    // Método base auxiliar (mantém como abstract para o MapStruct gerar)
    @Mapping(target = "privativos", ignore = true)
    @Mapping(target = "areaComum", ignore = true)
    @Mapping(target = "marcasMaterial", ignore = true)
    protected abstract EspecificacaTecnicaDTO toBaseEspecificacaoTecnica(Empreendimento empreendimento);

    // --- MÉTODO PRINCIPAL ---
    // Agora é um método público normal (não 'default')
    public EspecificacaTecnicaDTO toEspecificacaoTecnica(Empreendimento empreendimento) {
        if (empreendimento == null) {
            return null;
        }

        EspecificacaTecnicaDTO base = toBaseEspecificacaoTecnica(empreendimento);

        List<AmbienteDocResponse> privativos = new ArrayList<>();
        List<AmbienteDocResponse> areaComum = new ArrayList<>();

        if (empreendimento.getAmbientes() != null) {
            for (Ambiente amb : empreendimento.getAmbientes()) {
                // MUDANÇA 3: Usamos o mapper injetado explicitamente.
                // Isso garante que as regras de mapeamento do AmbienteMapper sejam usadas.
                AmbienteDocResponse doc = ambienteMapper.toDocResponse(amb);

                if (TipoAmbiente.PRIVATIVO.equals(amb.getCatalogoAmbiente().getTipo())) {
                    privativos.add(doc);
                } else {
                    areaComum.add(doc);
                }
            }
        }

        List<MaterialMarcasDocResponse> marcasAgrupadas = agruparMarcas(empreendimento.getMateriaisPorMarca());

        return new EspecificacaTecnicaDTO(
                base.nome(),
                base.localizacao(),
                base.descricao(),
                privativos,
                areaComum,
                marcasAgrupadas,
                base.observacoes()
        );
    }

    // Método auxiliar de agrupamento (pode ser protegido)
    protected List<MaterialMarcasDocResponse> agruparMarcas(Collection<CombinacaoEMM> combinacoes) {
        if (combinacoes == null || combinacoes.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, List<String>> agrupado = combinacoes.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getMaterial().getNome(),
                        Collectors.mapping(c -> c.getMarca().getNome(), Collectors.toList())
                ));

        List<MaterialMarcasDocResponse> resultado = new ArrayList<>();

        agrupado.forEach((material, marcasLista) -> {
            // --- O TRUQUE ESTÁ AQUI ---
            // 1. String.join(", ", marcasLista) -> junta todos com vírgula e espaço
            // 2. + "." -> adiciona o ponto final que você queria
            String formatado = String.join(", ", marcasLista) + ".";

            resultado.add(new MaterialMarcasDocResponse(material, formatado));
        });

        return resultado;
    }
}