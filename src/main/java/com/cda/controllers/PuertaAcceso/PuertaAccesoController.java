package com.cda.controllers.PuertaAcceso;

import com.cda.models.PuertaAcceso;
import com.cda.services.PuertaAcceso.PuertaAccesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/puerta-acceso")
@CrossOrigin(origins = "*")
public class PuertaAccesoController {

    @Autowired
    private PuertaAccesoService puertaAccesoService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody PuertaAcceso puertaAcceso){
        return new ResponseEntity(puertaAccesoService.Add(puertaAcceso), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody PuertaAcceso puertaAcceso){
        return new ResponseEntity(puertaAccesoService.Update(id, puertaAcceso), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(puertaAccesoService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(puertaAccesoService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(puertaAccesoService.GetAll(), HttpStatus.OK);
    }

}
