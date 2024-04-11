package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.TypeDto;
import service.entity.Type;
import service.repository.TypeRepository;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public ResponseEntity<?> getTypes(){
        List<Type> types = typeRepository.findAll();
        if(types.isEmpty()){
            return new ResponseEntity<>("Types not found", HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(types, HttpStatus.OK);
        }
    }
    public ResponseEntity<?> addType(TypeDto typeDto){
        if(typeDto == null || !idDigit(typeDto.getPrice())){
            return new ResponseEntity<>("Error Type", HttpStatus.BAD_REQUEST);
        }
        else{
            Type type = new Type();
            type.setNameType(typeDto.getNameType());
            type.setPrice(typeDto.getPrice());
            typeRepository.save(type);
            return new ResponseEntity<>(type, HttpStatus.CREATED);
        }
    }

    public boolean idDigit(int num) {
        String numStr = Integer.toString(num);
        for (int i = 0; i < numStr.length(); i++) {
            if (!Character.isDigit(numStr.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
