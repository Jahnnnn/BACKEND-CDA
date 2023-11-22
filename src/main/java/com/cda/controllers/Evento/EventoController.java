package com.cda.controllers.Evento;

import com.cda.models.Evento;
import com.cda.services.Evento.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/evento")
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Evento evento){
        return new ResponseEntity(eventoService.Add(evento), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam("id") String id, @RequestBody Evento evento){
        return new ResponseEntity(eventoService.Update(id, evento), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id){
        return new ResponseEntity(eventoService.Delete(id), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity getById(@RequestParam("id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(eventoService.GetById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() throws ExecutionException, InterruptedException {
        return new ResponseEntity(eventoService.GetAll(), HttpStatus.OK);
    }

}
