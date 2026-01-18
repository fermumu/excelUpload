package com.proyecto.excelupload.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class ExcelUploadController {

    @PostMapping("/excel")
    public ResponseEntity<Object>getEcxel(
            @RequestParam("file")MultipartFile file
            ){
        return ResponseEntity.ok("ENVIAR RESPUESTA");
    }

}
