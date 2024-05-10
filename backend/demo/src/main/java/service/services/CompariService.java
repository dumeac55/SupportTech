package service.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    private final CompariRepository compariRepository;

    public CompariService(CompariRepository compariRepository) {
        this.compariRepository = compariRepository;
    }

    @Scheduled(cron = " 0 12 19 * * ?")
    public List<Compari> scrapeCEL() {
        List<Compari> procesorList = new ArrayList<>();
        String searchKeyword = "memorii/ram+ddr4"; // Parametrul de căutare

        String celURL = "https://www.cel.ro/cauta/memorie+ddr4/";
        String html = new RestTemplate().getForObject(celURL, String.class);

        Document document = Jsoup.parse(html);
        Elements productElements = document.select(".product_data");

        if (!productElements.isEmpty()) {
            for (Element productElement : productElements) {
                String productName = productElement.select(".productTitle").text().trim();
                String productPrice = productElement.select(".price").text().trim();
                String productImageLink = productElement.select(".productListing-poza img").attr("src");
                String productPageLink = productElement.select(".productListing-poza a").attr("href");
                Compari result = new Compari();
                result.setCompany("CEL.RO");
                result.setPrice(productPrice);
                result.setNameProduct(productName);
                result.setLink(productPageLink);
                result.setImageLink(productImageLink);
                procesorList.add(result);
                // Dacă dorești să salvezi fiecare produs într-o bază de date, utilizează metoda save aici
                compariRepository.save(result);
            }
            return procesorList;
        } else {
            System.out.println("No products found");
            return Collections.emptyList(); // Return
        }
    }

    @Scheduled(cron = " 0 25 19 * * ?")
    public List<Compari> scrapeEMAG() {
        List<Compari> procesorList = new ArrayList<>();
        String searchKeyword = "memorii/ram+ddr4"; // Parametrul de căutare

        String emagURL = "https://www.emag.ro/search/memorie%20ram%20ddr4?ref=effective_search";
        String html = new RestTemplate().getForObject(emagURL, String.class);

        Document document = Jsoup.parse(html);
        Elements productElements = document.select(".card-item.card-standard.js-product-data");

        if (!productElements.isEmpty()) {
            for (Element productElement : productElements) {
                String productName = productElement.select(".card-v2-title-wrapper").text().trim();
                String productPrice = productElement.select(".product-new-price").text().trim();
                String productImageLink = productElement.select(".card-v2-thumb-inner img").attr("src");
                String productPageLink = productElement.select(".card-v2-info a").attr("href");
                Compari result = new Compari();
                result.setCompany("EMAG");
                result.setPrice(productPrice);
                result.setNameProduct(productName);
                result.setLink(productPageLink);
                result.setImageLink(productImageLink);
                procesorList.add(result);
                // Dacă dorești să salvezi fiecare produs într-o bază de date, utilizează metoda save aici
                compariRepository.save(result);
            }
            return procesorList;
        } else {
            System.out.println("No products found");
            return Collections.emptyList(); // Return
        }
    }

//    @Scheduled(cron = " 0 31 19 * * ?")
    public List<Compari> scrapeEVOMAG() {
        List<Compari> procesorList = new ArrayList<>();
        String searchKeyword = "memorii/ram+ddr4"; // Parametrul de căutare

        String EvoMagURL = "https://www.evomag.ro/componente-pc-gaming-memorii/";
        String html = new RestTemplate().getForObject(EvoMagURL, String.class);

        Document document = Jsoup.parse(html);
        Elements productElements = document.select(".nice_product_item");
        if (!productElements.isEmpty()) {
            for (Element productElement : productElements) {
                String productName = productElement.select(".npi_name").text().trim();
                String productPrice = productElement.select(".real_price").text().trim();
                String productImageLink = productElement.select(".npi_image img").attr("src");
                String productPageLink = productElement.select(".npi_image a").attr("href");
                Compari result = new Compari();
                result.setCompany("EVOMAG");
                result.setPrice(productPrice);
                result.setNameProduct(productName);
                result.setLink("https://www.evomag.ro"+productPageLink);
                result.setImageLink(productImageLink);
                procesorList.add(result);

                compariRepository.save(result);
            }
            return procesorList;
        } else {
            System.out.println("No products found");
            return Collections.emptyList(); // Return
        }
    }
}
