package com.proyecto.excelupload.api.controller;

import com.proyecto.excelupload.api.dto.ClienteConfirmacionDto;
import com.proyecto.excelupload.aplication.ExcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/")
public class ExcelUploadController {

    private final ExcelService excelService;

    public ExcelUploadController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/excel")
    public ResponseEntity<?>getEcxel(
            @RequestParam("file")MultipartFile file
            )
    {
        try{

            return ResponseEntity.ok(excelService.readExcel(file));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }


    }

    @PostMapping("/confirmar")
    public ResponseEntity<String> confirmarClientes(
            @RequestBody List<ClienteConfirmacionDto> confirmaciones) {

        long confirmados = confirmaciones.stream()
                .filter(ClienteConfirmacionDto::isConfirmado)
                .count();

        return ResponseEntity.ok(
                "Clientes confirmados para guardar: " + confirmados
        );
    }

}
