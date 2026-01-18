package com.proyecto.excelupload.aplication;
import com.proyecto.excelupload.api.dto.ClienteDto;
import com.proyecto.excelupload.api.dto.ProductoDto;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    public List<ClienteDto> readExcel(MultipartFile file) throws Exception{
        InputStream is = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        Map<String, ClienteDto> cliente = new LinkedHashMap<>();

        boolean firstRow = true;

        for (Row row : sheet) {

            if (firstRow) {
                firstRow = false;
                continue;
            }

            String nombreCliente = getCellValue(row.getCell(0));
            String tipoProducto = getCellValue(row.getCell(1));
            String bolso = getCellValue(row.getCell(2));
            String ropa = getCellValue(row.getCell(3));

            cliente.putIfAbsent(nombreCliente, crearCliente(nombreCliente));
            ClienteDto clienteDTO = cliente.get(nombreCliente);

            ProductoDto productos = clienteDTO.getProductos();

            if ("bolsos".equalsIgnoreCase(tipoProducto)) {
                productos.setBolsos(
                        agregarProducto(productos.getBolsos(), "bolso", bolso)
                );
            }

            if ("ropa".equalsIgnoreCase(tipoProducto)) {
                productos.setRopa(
                        agregarProducto(productos.getRopa(), "prenda", ropa)
                );
            }
        }

        workbook.close();
        return new ArrayList<>(cliente.values());
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }

        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        }

        return "";
    }

    private ClienteDto crearCliente(String nombre) {
        ClienteDto dto = new ClienteDto();
        dto.setCliente(nombre);
        dto.setProductos(new ProductoDto());
        return dto;
    }

    private Map<String, String> agregarProducto(
            Map<String, String> productos,
            String prefijo,
            String valor) {

        if (productos == null) {
            productos = new LinkedHashMap<>();
        }

        int index = productos.size() + 1;
        productos.put(prefijo + index, valor.isEmpty() ? null : valor);
        return productos;
    }
}
