package com.cda.controllers.Sede;

import com.cda.models.Sede;
import com.cda.services.Sede.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/sede")
@CrossOrigin(origins = "*")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Sede sede){
        return new ResponseEntity(sedeService.Add(sede), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Sede sede){
        return new ResponseEntity(sedeService.Update(id, sede), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(sedeService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(sedeService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(sedeService.GetAll(), HttpStatus.OK);
    }

}
