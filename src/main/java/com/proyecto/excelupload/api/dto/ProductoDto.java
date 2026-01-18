package com.proyecto.excelupload.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoDto {
    private Map<String, String> bolsos;
    private Map<String, String> ropa;

    public ProductoDto() {
        //CONSTRUCTOR EMPTY
    }

    public Map<String, String> getBolsos() {
        return bolsos;
    }

    public void setBolsos(Map<String, String> bolsos) {
        this.bolsos = bolsos;
    }

    public Map<String, String> getRopa() {
        return ropa;
    }

    public void setRopa(Map<String, String> ropa) {
        this.ropa = ropa;
    }
}
