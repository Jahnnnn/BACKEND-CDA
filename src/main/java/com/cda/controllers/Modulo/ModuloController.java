package com.cda.controllers.Modulo;

import com.cda.models.Modulo;
import com.cda.services.Modulo.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/modulo")
@CrossOrigin(origins = "*")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Modulo modulo){
        return new ResponseEntity(moduloService.Add(modulo), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Modulo modulo){
        return new ResponseEntity(moduloService.Update(id, modulo), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(moduloService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(moduloService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(moduloService.GetAll(), HttpStatus.OK);
    }

}
