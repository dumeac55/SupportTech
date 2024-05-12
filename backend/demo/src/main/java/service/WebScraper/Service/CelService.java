package service.WebScraper.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.WebScraper.Interface.IWebScraper;
import service.entity.Compari;
import service.repository.CompariRepository;

import java.util.Optional;

@Service
public class CelService implements IWebScraper {

    @Autowired
    private CompariRepository compariRepository;

    @Override
    public void getMemoryRAM() {
        String url = "https://www.cel.ro/memorii/";
        scrapeDataFromCel(url, "RAM");
    }

    @Override
    public void getMemoryROM() {
        String urlSSD = "https://www.cel.ro/ssd-uri/";
        String urlHDD = "https://www.cel.ro/hard-disk-uri/";
        scrapeDataFromCel(urlSSD, "ROM");
        scrapeDataFromCel(urlHDD, "ROM");
    }

    @Override
    public void getProcessor() {
        String urlAmd = "https://www.cel.ro/procesoare/amd/1a-1";
        String urlIntel = "https://www.cel.ro/procesoare/intel/1a-1";
        scrapeDataFromCel(urlAmd, "Processor");
        scrapeDataFromCel(urlIntel, "Processor");
    }

    @Override
    public void getVideoCard() {
        String urlMSI = "https://www.cel.ro/placi-video/msi/1a-1";
        String urlGigabyte = "https://www.cel.ro/placi-video/gigabyte/1a-1";
        String urlAsus = "https://www.cel.ro/placi-video/asus/1a-1";
        scrapeDataFromCel(urlMSI, "Video Card");
        scrapeDataFromCel(urlGigabyte, "Video Card");
        scrapeDataFromCel(urlAsus, "Video Card");
    }

    @Override
    public void getMotherboard() {
        String urlMSI = "https://www.cel.ro/placi-de-baza/msi/1a-1";
        String urlGigabyte = "https://www.cel.ro/placi-de-baza/gigabyte/1a-1";
        String urlAsus = "https://www.cel.ro/placi-de-baza/asus/1a-1";
        scrapeDataFromCel(urlMSI, "Motherboard");
        scrapeDataFromCel(urlGigabyte, "Motherboard");
        scrapeDataFromCel(urlAsus, "Motherboard");
    }

    @Override
    public void getMouse() {
        String url = "https://www.cel.ro/mouse/";
        scrapeDataFromCel(url, "Mouse");
    }

    @Override
    public void getKeyboard() {
        String url = "https://www.cel.ro/tastaturi/";
        scrapeDataFromCel(url, "Keyboard");
    }

    @Override
    public void getCooler() {
        String url = "https://www.cel.ro/coolere-componente/";
        scrapeDataFromCel(url, "Cooler");
    }

    @Override
    public void getSource() {
        String url = "https://www.cel.ro/surse/";
        scrapeDataFromCel(url, "Source");
    }

    @Override
    public void getOpticalDrive() {
        String url = "https://www.cel.ro/unitati-optice/";
        scrapeDataFromCel(url, "OpticalDrive");
    }

    @Override
    public void getHeadphone() {
        String url = "https://www.cel.ro/casti/";
        scrapeDataFromCel(url, "Headphone");
    }

    @Override
    public void getMonitor(){
        String urlPhilips = "https://www.cel.ro/monitoare-lcd-led/lenovo/1a-1";
        String urlDell = "https://www.cel.ro/monitoare-lcd-led/dell/1a-1";
        String urlSamsung = "https://www.cel.ro/monitoare-lcd-led/samsung/1a-1";
        String urlLG = "https://www.cel.ro/monitoare-lcd-led/lg/1a-1";
        String urlAsus = "https://www.cel.ro/monitoare-lcd-led/asus/1a-1";
        scrapeDataFromCel(urlPhilips, "Monitor");
        scrapeDataFromCel(urlDell, "Monitor");
        scrapeDataFromCel(urlSamsung, "Monitor");
        scrapeDataFromCel(urlLG, "Monitor");
        scrapeDataFromCel(urlAsus, "Monitor");

    }

    private void scrapeDataFromCel(String url, String domain) {
        String html = new RestTemplate().getForObject(url, String.class);
        Document document = Jsoup.parse(html);
        Elements productElements = document.select(".product_data");
        if (!productElements.isEmpty()) {
            for (Element productElement : productElements) {
                String productName = productElement.select(".productTitle").text().trim();
                String productPrice = productElement.select(".price").text().trim();
                String productImageLink = productElement.select(".productListing-poza img").attr("src");
                String productPageLink = productElement.select(".productListing-poza a").attr("href");
                Optional<Compari> existingProduct = compariRepository.findByNameProduct(productName);
                if (existingProduct.isPresent()) {
                    Compari existing = existingProduct.get();
                    if (!existing.getPrice().equals(productPrice)) {
                        existing.setPrice(productPrice);
                        compariRepository.save(existing);
                    }
                } else {
                    Compari result = new Compari();
                    result.setCompany("CEL");
                    result.setPrice(productPrice);
                    result.setNameProduct(productName);
                    result.setLink(productPageLink);
                    result.setImageLink(productImageLink);
                    result.setDomain(domain);
                    compariRepository.save(result);
                }
            }
        } else {
            System.out.println("No products found");
        }
    }

    public void fetchData(){
        Thread threadMotherboard = new Thread(() -> getMotherboard());
        Thread threadMemoryRAM = new Thread(() -> getMemoryRAM());
        Thread threadMemoryROM = new Thread(() -> getMemoryROM());
        Thread threadProcesor = new Thread(() -> getProcessor());
        Thread threadVideoCard = new Thread(() -> getVideoCard());
        Thread threadMouse = new Thread(() -> getMouse());
        Thread threadCooler = new Thread(() -> getCooler());
        Thread threadHeadphone = new Thread(() -> getHeadphone());
        Thread threadKeyboard = new Thread(() -> getKeyboard());
        Thread threadOpticalDrive = new Thread(() -> getOpticalDrive());
        Thread threadSource = new Thread(() -> getSource());
        Thread threadMonitor = new Thread(() -> getMonitor());

        threadMotherboard.start();
        threadMemoryRAM.start();
        threadMemoryROM.start();
        threadProcesor.start();
        threadVideoCard.start();
        threadMouse.start();
        threadCooler.start();
        threadHeadphone.start();
        threadKeyboard.start();
        threadOpticalDrive.start();
        threadSource.start();
        threadMonitor.start();

        try {
            threadMotherboard.join();
            threadMemoryRAM.join();
            threadMemoryROM.join();
            threadProcesor.join();
            threadVideoCard.join();
            threadMouse.join();
            threadCooler.join();
            threadHeadphone.join();
            threadKeyboard.join();
            threadOpticalDrive.join();
            threadSource.join();
            threadMonitor.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
