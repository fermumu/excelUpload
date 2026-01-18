package com.proyecto.excelupload.aplication;

import com.proyecto.excelupload.api.dto.ClienteConfirmacionDto;
import com.proyecto.excelupload.api.dto.ProductoDto;
import com.proyecto.excelupload.domain.model.entities.Cliente;
import com.proyecto.excelupload.domain.model.entities.Producto;
import com.proyecto.excelupload.domain.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public int guardarConfirmados(List<ClienteConfirmacionDto> confirmaciones) {

        int totalGuardados = 0;

        for (ClienteConfirmacionDto c : confirmaciones) {

            if (!c.isConfirmado()) continue;

            Cliente cliente = new Cliente();
            cliente.setNombre(c.getCliente().getCliente());

            List<Producto> productos = new ArrayList<>();

            ProductoDto dto = c.getCliente().getProductos();

            guardarProductos(dto.getBolsos(), "bolso", cliente, productos);
            guardarProductos(dto.getRopa(), "ropa", cliente, productos);

            cliente.setProductos(productos);
            clienteRepository.save(cliente);

            totalGuardados++;
        }

        return totalGuardados;
    }

    private void guardarProductos(
            Map<String, String> mapa,
            String tipo,
            Cliente cliente,
            List<Producto> productos) {

        if (mapa == null) return;

        mapa.forEach((k, v) -> {
            Producto p = new Producto();
            p.setTipo(tipo);
            p.setNombre(v);
            p.setCliente(cliente);
            productos.add(p);
        });
    }
}
