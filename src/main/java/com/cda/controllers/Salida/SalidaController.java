package com.cda.controllers.Salida;

import com.cda.models.Salida;
import com.cda.services.Salida.SalidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/salida")
@CrossOrigin(origins = "*")
public class SalidaController {

    @Autowired
    private SalidaService salidaService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Salida salida){
        return new ResponseEntity(salidaService.Add(salida), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Salida salida){
        return new ResponseEntity(salidaService.Update(id, salida), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(salidaService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(salidaService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(salidaService.GetAll(), HttpStatus.OK);
    }

}
