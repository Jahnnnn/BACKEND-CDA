package com.cda.controllers.PermisoModulo;

import com.cda.models.PermisoModulo;
import com.cda.services.PermisoModulo.PermisoModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/permiso-modulo")
@CrossOrigin(origins = "*")
public class PermisoModuloController {

    @Autowired
    private PermisoModuloService permisoModuloService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody PermisoModulo permisoModulo){
        return new ResponseEntity(permisoModuloService.Add(permisoModulo), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody PermisoModulo permisoModulo){
        return new ResponseEntity(permisoModuloService.Update(id, permisoModulo), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(permisoModuloService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(permisoModuloService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(permisoModuloService.GetAll(), HttpStatus.OK);
    }

}
