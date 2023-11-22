package com.cda.controllers.Foto;

import com.cda.models.Foto;
import com.cda.services.Foto.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/foto")
@CrossOrigin(origins = "*")
public class FotoController {

    @Autowired
    private FotoService fotoService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Foto foto){
        return new ResponseEntity(fotoService.Add(foto), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Foto foto){
        return new ResponseEntity(fotoService.Update(id, foto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(fotoService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(fotoService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(fotoService.GetAll(), HttpStatus.OK);
    }

}
