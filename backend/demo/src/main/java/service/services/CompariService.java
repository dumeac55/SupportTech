package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import service.WebScraper.Service.CelService;
import service.WebScraper.Service.EmagService;
import service.WebScraper.Service.EvomagService;
import service.dto.CompariDto;
import service.entity.Compari;
import service.repository.CompariRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompariService {
    @Autowired
    private CompariRepository compariRepository;

    @Autowired
    private EvomagService evomagService;

    @Autowired
    private CelService celService;

    @Autowired
    private EmagService emagService;

    @Scheduled(cron = " 0 27 16 * * ?")
    public void getEvomag(){
        evomagService.fetchData();
    }
    @Scheduled(cron = " 0 59 18 * * ?")
    public void getCel(){ celService.fetchData();}
    @Scheduled(cron = " 0 27 16 * * ?")
    public void getEmag(){ emagService.fetchData();}

    public ResponseEntity<?> getEvomagProducts(String wordSearch, String infRange, String supRange, String domain){
        List<CompariDto> compariDtoLista = new ArrayList<>();
        List<Compari> lista = compariRepository.findByNameProducttEvomag(wordSearch, infRange, supRange, domain);
        for(Compari list:lista){
            CompariDto compariDto = new CompariDto();
            compariDto.setIdProduct(list.getId());
            compariDto.setCompany(list.getCompany());
            compariDto.setPrice(list.getPrice());
            compariDto.setNameProduct(list.getNameProduct());
            compariDto.setLinkImage(list.getImageLink());
            compariDto.setLinkProduct(list.getLink());
            compariDtoLista.add(compariDto);
        }
        return new ResponseEntity<>(compariDtoLista, HttpStatus.OK);
    }

    public ResponseEntity<?> getEmagProducts(String wordSearch, String infRange, String supRange, String domain){
        List<CompariDto> compariDtoLista = new ArrayList<>();
        List<Compari> lista = compariRepository.findByNameProducttEmag(wordSearch, infRange, supRange, domain);
        for(Compari list:lista){
            CompariDto compariDto = new CompariDto();
            compariDto.setIdProduct(list.getId());
            compariDto.setCompany(list.getCompany());
            compariDto.setPrice(list.getPrice());
            compariDto.setNameProduct(list.getNameProduct());
            compariDto.setLinkImage(list.getImageLink());
            compariDto.setLinkProduct(list.getLink());
            compariDtoLista.add(compariDto);
        }
        return new ResponseEntity<>(compariDtoLista, HttpStatus.OK);
    }

    public ResponseEntity<?> getCelProducts(String wordSearch, String infRange, String supRange, String domain){
        List<CompariDto> compariDtoLista = new ArrayList<>();
        List<Compari> lista = compariRepository.findByNameProducttCel(wordSearch, infRange, supRange, domain);
        for(Compari list:lista){
            CompariDto compariDto = new CompariDto();
            compariDto.setIdProduct(list.getId());
            compariDto.setCompany(list.getCompany());
            compariDto.setPrice(list.getPrice());
            compariDto.setNameProduct(list.getNameProduct());
            compariDto.setLinkImage(list.getImageLink());
            compariDto.setLinkProduct(list.getLink());
            compariDtoLista.add(compariDto);
        }
        return new ResponseEntity<>(compariDtoLista, HttpStatus.OK);
    }
}
