package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.dto.TypeDto;
import service.entity.TechnicianProfile;
import service.entity.Type;
import service.repository.TechnicianProfileRepository;
import service.repository.TypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private TechnicianProfileRepository technicianProfileRepository;

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
            Type type = typeRepository.findById(typeDto.getIdType());
            if(type!=null){
                TechnicianProfile technicianProfile = technicianProfileRepository.findById(typeDto.getTechnicianId());
                type.setNameType(typeDto.getNameType());
                type.setPrice(typeDto.getPrice());
                type.setTechnicianProfile(technicianProfile);
                typeRepository.save(type);
                return new ResponseEntity<>(type, HttpStatus.OK);
            }
            else {
                Type newType = new Type();
                TechnicianProfile technicianProfile = technicianProfileRepository.findById(typeDto.getTechnicianId());
                newType.setNameType(typeDto.getNameType());
                newType.setPrice(typeDto.getPrice());
                newType.setTechnicianProfile(technicianProfile);
                typeRepository.save(newType);
                return new ResponseEntity<>(newType, HttpStatus.CREATED);
            }
        }
    }

    public ResponseEntity<?> getTypesByIdTechnician(int idTechnician){
        TechnicianProfile technicianProfile = technicianProfileRepository.findById(idTechnician);
        if( technicianProfile == null){
            return new ResponseEntity<>("Technician not found", HttpStatus.NOT_FOUND);
        }
        else{
            List<Type> typeList = new ArrayList<>();
            List<TypeDto> typeDtoList = new ArrayList<>();
            typeList = typeRepository.findByTechnicianProfile_IdTechnician(idTechnician);
            for(Type type :typeList){
                TypeDto typeDto = new TypeDto();
                typeDto.setNameType(type.getNameType());
                typeDto.setIdType(type.getId());
                typeDto.setPrice(type.getPrice());
                typeDtoList.add(typeDto);
            }
            return new ResponseEntity<>(typeDtoList, HttpStatus.OK);

        }
    }

    public ResponseEntity<?> deleteType(int id){
        Type type = typeRepository.findById(id);
        if(type!= null){
            typeRepository.deleteById(id);
            return new ResponseEntity<>("Type delete successfull", HttpStatus.OK);
        }
        return new ResponseEntity<>("Type not found", HttpStatus.NOT_FOUND);
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
