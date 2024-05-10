package service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.services.CompariService;

@RestController
@RequestMapping("api")
public class CompariController {
    @Autowired
    private CompariService compariService;

    @GetMapping
    private void get(){
        compariService.scrapeEVOMAG();
    }
}
