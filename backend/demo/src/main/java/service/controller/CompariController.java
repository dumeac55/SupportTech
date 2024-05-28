package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.services.CompariService;

@RestController
@RequestMapping("api/compari")
@CrossOrigin(origins = "http://localhost:4200/")
public class CompariController {
    @Autowired
    private CompariService compariService;

    @GetMapping("/evomag")
    private ResponseEntity<?> getEvomagProducts(@RequestParam(name = "wordSearch") String wordSearch,
                                                @RequestParam(name = "infRange") String infRange,
                                                @RequestParam(name = "supRange") String supRange,
                                                @RequestParam(name = "domain") String domain){

        return compariService.getEvomagProducts(wordSearch, infRange, supRange, domain);
    }

    @GetMapping("/emag")
    private ResponseEntity<?> getEmagProducts(@RequestParam(name = "wordSearch") String wordSearch,
                                                @RequestParam(name = "infRange") String infRange,
                                                @RequestParam(name = "supRange") String supRange,
                                                @RequestParam(name = "domain") String domain) {

        return compariService.getEmagProducts(wordSearch, infRange, supRange, domain);

    }

    @GetMapping("/cel")
    private ResponseEntity<?> getCelProducts(@RequestParam(name = "wordSearch") String wordSearch,
                                                @RequestParam(name = "infRange") String infRange,
                                                @RequestParam(name = "supRange") String supRange,
                                                @RequestParam(name = "domain") String domain) {

        return compariService.getCelProducts(wordSearch, infRange, supRange, domain);
    }
}
