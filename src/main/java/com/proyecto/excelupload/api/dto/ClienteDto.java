package com.proyecto.excelupload.api.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDto {
    private String cliente;
    private ProductoDto productos;

    public ClienteDto() {
        //CONSTRUCTOR EMPTY
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public ProductoDto getProductos() {
        return productos;
    }

    public void setProductos(ProductoDto productos) {
        this.productos = productos;
    }
}
