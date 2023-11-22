package com.cda.controllers.TipoDocumento;

import com.cda.models.TipoDocumento;
import com.cda.services.TipoDocumento.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/tipo-documento")
@CrossOrigin(origins = "*")
public class TipoDocumentoController {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody TipoDocumento tipoDocumento){
        return new ResponseEntity(tipoDocumentoService.Add(tipoDocumento), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody TipoDocumento tipoDocumento){
        return new ResponseEntity(tipoDocumentoService.Update(id, tipoDocumento), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(tipoDocumentoService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(tipoDocumentoService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(tipoDocumentoService.GetAll(), HttpStatus.OK);
    }

}
