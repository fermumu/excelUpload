package com.proyecto.excelupload.aplication;
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

    public List<Map<String, Object>> readExcel(MultipartFile file) throws Exception{
        InputStream is = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        Map<String, Map<String, Object>> clientes = new LinkedHashMap<>();

        boolean firstRow = true;

        for (Row row : sheet) {

            if (firstRow) {
                firstRow = false;
                continue;
            }

            String cliente = getCellValue(row.getCell(0));
            String tipoProducto = getCellValue(row.getCell(1));
            String bolso = getCellValue(row.getCell(2));
            String ropa = getCellValue(row.getCell(3));

            clientes.putIfAbsent(cliente, new LinkedHashMap<>());
            Map<String, Object> clienteMap = clientes.get(cliente);

            clienteMap.putIfAbsent("cliente", cliente);
            clienteMap.putIfAbsent("productos", new LinkedHashMap<>());

            Map<String, Map<String, String>> productos =
                    (Map<String, Map<String, String>>) clienteMap.get("productos");

            productos.putIfAbsent(tipoProducto, new LinkedHashMap<>());
            Map<String, String> listaProductos = productos.get(tipoProducto);

            String valor = tipoProducto.equals("bolsos") ? bolso : ropa;

            int index = listaProductos.size() + 1;
            listaProductos.put(tipoProducto.substring(0, tipoProducto.length() - 1) + index,
                    valor.isEmpty() ? null : valor);
        }

        workbook.close();
        return new ArrayList<>(clientes.values());
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
}
