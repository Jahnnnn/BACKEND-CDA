package com.cda.controllers.Campus;

import com.cda.models.Campus;
import com.cda.services.Campus.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/campus")
@CrossOrigin(origins = "*")
public class CampusController {

    @Autowired
    private CampusService campusService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Campus campus){
        return new ResponseEntity(campusService.Add(campus), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Campus campus){
        return new ResponseEntity(campusService.Update(id, campus), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(campusService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(campusService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(campusService.GetAll(), HttpStatus.OK);
    }
}
