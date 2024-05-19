package com.JRMRod.carMatch.demo.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

public record DadosMarcas(
    @JsonAlias("codigo") String codigo,
    @JsonAlias("nome") String nome
) {
}
