package com.cda.controllers.Rol;

import com.cda.models.Rol;
import com.cda.services.Rol.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/rol")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Rol rol){
        return new ResponseEntity(rolService.Add(rol), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Rol rol){
        return new ResponseEntity(rolService.Update(id, rol), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(rolService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(rolService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(rolService.GetAll(), HttpStatus.OK);
    }

}
