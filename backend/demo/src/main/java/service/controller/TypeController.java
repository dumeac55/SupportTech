package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.TypeDto;
import service.services.TypeService;

@RestController
@RequestMapping("api/utils")
@CrossOrigin(origins = "http://localhost:4200/")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    private ResponseEntity<?> getTypes(){
        return typeService.getTypes();
    }

    @PostMapping("/type")
    private ResponseEntity<?> addType(@RequestBody TypeDto typeDto){
       return typeService.addType(typeDto);
    }

    @GetMapping("/type/{id}")
    private ResponseEntity<?> getTypesByIdTechnician(@PathVariable("id") int id){
        return typeService.getTypesByIdTechnician(id);
    }

    @DeleteMapping("/type/{id}/delete")
    private ResponseEntity<?> deleteType(@PathVariable("id") int id){
        return typeService.deleteType(id);
    }
}
