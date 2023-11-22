package com.cda.controllers.Programa;

import com.cda.models.Programa;
import com.cda.services.Programa.ProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/programa")
@CrossOrigin(origins = "*")
public class ProgramaController {

    @Autowired
    private ProgramaService programaService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Programa programa){
        return new ResponseEntity(programaService.Add(programa), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Programa programa){
        return new ResponseEntity(programaService.Update(id, programa), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(programaService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(programaService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(programaService.GetAll(), HttpStatus.OK);
    }

}
