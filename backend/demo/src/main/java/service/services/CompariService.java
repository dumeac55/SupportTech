package service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.WebScraper.Service.CelService;
import service.WebScraper.Service.EmagService;
import service.WebScraper.Service.EvomagService;
import service.dto.CompariDto;
import service.entity.Compari;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.repository.CompariRepository;

import java.util.ArrayList;
import java.util.Collections;
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

    @Scheduled(cron = " 0 56 12 * * ?")
    public void getEvomag(){
        evomagService.fetchData();
    }
    public void getCel(){ celService.fetchData();}
    public void getEmag(){ emagService.fetchData();}

    public ResponseEntity<?> testCompariEmag(){
        List<CompariDto> compariDtoLista = new ArrayList<>();
        List<Compari> lista = compariRepository.findByNameProducttEmag("0", "6000");
        for(Compari list:lista){
            CompariDto compariDto = new CompariDto();
            compariDto.setCompany(list.getCompany());
            compariDto.setPrice(list.getPrice());
            compariDto.setNameProduct(list.getNameProduct());
            compariDto.setLinkImage(list.getImageLink());
            compariDto.setLinkProduct(list.getLink());
            compariDtoLista.add(compariDto);
        }
        return new ResponseEntity<>(compariDtoLista, HttpStatus.OK);
    }

    public ResponseEntity<?> testCompariCel(){
        List<CompariDto> compariDtoLista = new ArrayList<>();
        List<Compari> lista = compariRepository.findByNameProducttCel("0", "6000");
        for(Compari list:lista){
            CompariDto compariDto = new CompariDto();
            compariDto.setCompany(list.getCompany());
            compariDto.setPrice(list.getPrice());
            compariDto.setNameProduct(list.getNameProduct());
            compariDto.setLinkImage(list.getImageLink());
            compariDto.setLinkProduct(list.getLink());
            compariDtoLista.add(compariDto);
        }
        return new ResponseEntity<>(compariDtoLista, HttpStatus.OK);
    }

    public ResponseEntity<?> testCompariEvomag(){
        List<CompariDto> compariDtoLista = new ArrayList<>();
        List<Compari> lista = compariRepository.findByNameProducttEvomag("0", "6000");
        for(Compari list:lista){
            CompariDto compariDto = new CompariDto();
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
