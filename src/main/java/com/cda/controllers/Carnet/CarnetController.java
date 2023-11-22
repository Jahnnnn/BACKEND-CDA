package com.cda.controllers.Carnet;

import com.cda.models.Carnet;
import com.cda.services.Carnet.CarnetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/carnet")
@CrossOrigin(origins = "*")
public class CarnetController {

    @Autowired
    private CarnetService carnetService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Carnet carnet){
        return new ResponseEntity(carnetService.Add(carnet), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Carnet carnet){
        return new ResponseEntity(carnetService.Update(id, carnet), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(carnetService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(carnetService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(carnetService.GetAll(), HttpStatus.OK);
    }

}
