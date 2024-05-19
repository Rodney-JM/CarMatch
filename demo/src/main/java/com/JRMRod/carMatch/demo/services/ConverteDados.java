package com.JRMRod.carMatch.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) throws JsonProcessingException {
        try{
            return mapper.readValue(json, classe);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) throws JsonProcessingException {
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, classe); //Transformo em uma lista generics

        return mapper.readValue(json, lista);
    }
}
