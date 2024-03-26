package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import service.dto.TypeDto;
import service.entity.Type;
import service.services.TypeService;

import java.util.List;


@RestController
@RequestMapping("api/utils")
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    private ResponseEntity<?> getTypes(){
        List<Type> allTypes = typeService.getTypes();
        return new ResponseEntity<>(allTypes, HttpStatus.OK);
    }

    @PostMapping("/type")
    private ResponseEntity<?> addType(@RequestBody TypeDto typeDto){
        boolean isDigit = typeService.idDigit(typeDto.getPrice());
        if(isDigit){
            Type newType = new Type(typeDto.getNameType(), typeDto.getPrice());
            typeService.addType(newType);
            return new ResponseEntity<>("New type succesfull added", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Erroare pret", HttpStatus.BAD_REQUEST);
        }
    }
}
