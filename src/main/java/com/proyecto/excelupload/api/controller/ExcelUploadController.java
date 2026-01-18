package com.proyecto.excelupload.api.controller;

import com.proyecto.excelupload.aplication.ExcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

}
