package com.cda.controllers.Visitante;

import com.cda.models.Visitante;
import com.cda.services.Visitante.VisitanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/visitante")
@CrossOrigin(origins = "*")
public class VisitanteController {

    @Autowired
    private VisitanteService visitanteService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Visitante visitante){
        return new ResponseEntity(visitanteService.Add(visitante), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Visitante visitante){
        return new ResponseEntity(visitanteService.Update(id, visitante), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(visitanteService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(visitanteService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(visitanteService.GetAll(), HttpStatus.OK);
    }

}
