package com.cda.controllers.Bloque;

import com.cda.models.Bloque;
import com.cda.services.Bloque.BloqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/bloque")
@CrossOrigin(origins = "*")
public class BloqueController {

    @Autowired
    private BloqueService bloqueService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Bloque bloque){
        return new ResponseEntity(bloqueService.Add(bloque), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Bloque bloque){
        return new ResponseEntity(bloqueService.Update(id, bloque), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(bloqueService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(bloqueService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(bloqueService.GetAll(), HttpStatus.OK);
    }

}
