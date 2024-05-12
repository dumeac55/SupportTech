package service.WebScraper.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.WebScraper.Interface.IWebScraper;
import service.entity.Compari;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.repository.CompariRepository;

import java.util.Optional;

@Service
public class EvomagService implements IWebScraper {

    @Autowired
    private CompariRepository compariRepository;

    @Override
    public void getMemoryRAM() {
        String urlCorsair = "https://www.evomag.ro/componente-pc-gaming-memorii/corsair/";
        String urlKingston = "https://www.evomag.ro/componente-pc-gaming-memorii/kingston/";
        String urlGSkill = "https://www.evomag.ro/componente-pc-gaming-memorii/g.skill/";
        String urlAdata = "https://www.evomag.ro/componente-pc-gaming-memorii/adata/";
        scrapeDataFromEvomag(urlCorsair, "RAM");
        scrapeDataFromEvomag(urlKingston, "RAM");
        scrapeDataFromEvomag(urlGSkill, "RAM");
        scrapeDataFromEvomag(urlAdata, "RAM");
    }

    @Override
    public void getMemoryROM() {
        String urlSSD = "https://www.evomag.ro/componente-pc-gaming-solid-state-drive-ssd/";
        String urlHDD = "https://www.evomag.ro/componente-pc-gaming-hard-disk-drive-hard-disk-uri-hdd-desktop/";
        scrapeDataFromEvomag(urlHDD, "ROM");
        scrapeDataFromEvomag(urlSSD, "ROM");
    }

    @Override
    public void getProcessor() {
        String urlIntel = "https://www.evomag.ro/componente-pc-gaming-procesoare/intel/";
        String urlAmd ="https://www.evomag.ro/componente-pc-gaming-procesoare/amd/";
        scrapeDataFromEvomag(urlIntel, "Processor");
        scrapeDataFromEvomag(urlAmd, "Processor");
    }

    @Override
    public void getVideoCard() {
        String urlMSI ="https://www.evomag.ro/componente-pc-gaming-placi-video/msi/";
        String urlGigabyte = "https://www.evomag.ro/componente-pc-gaming-placi-video/gigabyte/";
        String urlAsus = "https://www.evomag.ro/componente-pc-gaming-placi-video/asus/";
        scrapeDataFromEvomag(urlMSI, "Video Card");
        scrapeDataFromEvomag(urlGigabyte, "Video Card");
        scrapeDataFromEvomag(urlAsus, "Video Card");
    }

    @Override
    public void getMotherboard() {
        String urlAsus ="https://www.evomag.ro/componente-pc-gaming-placi-de-baza/asus/";
        String urlGigabyte = "https://www.evomag.ro/componente-pc-gaming-placi-de-baza/gigabyte/";
        String urlMSI = "https://www.evomag.ro/componente-pc-gaming-placi-de-baza/msi/";
        scrapeDataFromEvomag(urlAsus, "Motherboard");
        scrapeDataFromEvomag(urlGigabyte, "Motherboard");
        scrapeDataFromEvomag(urlMSI, "Motherboard");
    }

    @Override
    public void getMouse() {
        String url = "https://www.evomag.ro/componente-pc-gaming-mouse-pc-gaming/";
        scrapeDataFromEvomag(url, "Mouse");
    }

    @Override
    public void getKeyboard() {
        String url = "https://www.evomag.ro/componente-pc-gaming-tastaturi-pc/";
        scrapeDataFromEvomag(url, "Keyboard");
    }

    @Override
    public void getCooler() {
        String url = "https://www.evomag.ro/componente-pc-gaming-coolere-coolere-cpu/";
        scrapeDataFromEvomag(url, "Cooler");
    }

    @Override
    public void getSource() {
        String url = "https://www.evomag.ro/componente-pc-gaming-surse/";
        scrapeDataFromEvomag(url, "Source");
    }

    @Override
    public void getOpticalDrive() {
        String url = "https://www.evomag.ro/componente-pc-gaming-unitati-optice/";
        scrapeDataFromEvomag(url, "Optical Drive");
    }

    @Override
    public void getHeadphone() {
        String url = "https://www.evomag.ro/componente-pc-gaming-casti-microfoane//";
        scrapeDataFromEvomag(url, "Headphone");
    }

    @Override
    public void getMonitor(){
        String urlPhilips = "https://www.evomag.ro/monitoare-monitoare-led/philips/";
        String urlLG = "https://www.evomag.ro/monitoare-monitoare-led/lg/";
        String urlAsus = "https://www.evomag.ro/monitoare-monitoare-led/asus/";
        String urlSamsung = "https://www.evomag.ro/monitoare-monitoare-led/samsung/";
        String urlDell = "https://www.evomag.ro/monitoare-monitoare-led/dell/";
        scrapeDataFromEvomag(urlPhilips, "Monitor");
        scrapeDataFromEvomag(urlLG, "Monitor");
        scrapeDataFromEvomag(urlAsus, "Monitor");
        scrapeDataFromEvomag(urlSamsung, "Monitor");
        scrapeDataFromEvomag(urlDell, "Monitor");
    }

    private void scrapeDataFromEvomag(String url, String domain) {
        String html = new RestTemplate().getForObject(url, String.class);
        Document document = Jsoup.parse(html);
        Elements productElements = document.select(".nice_product_item");
        if (!productElements.isEmpty()) {
            for (Element productElement : productElements) {
                String productName = productElement.select(".npi_name").text().trim();
                String productPrice = productElement.select(".real_price").text().trim();
                String productImageLink = productElement.select(".npi_image img").attr("src");
                String productPageLink = productElement.select(".npi_image a").attr("href");
                Optional<Compari> existingProduct = compariRepository.findByNameProduct(productName);
                if (existingProduct.isPresent()) {
                    Compari existing = existingProduct.get();
                    if (!existing.getPrice().equals(productPrice)) {
                        existing.setPrice(productPrice);
                        compariRepository.save(existing);
                    }
                } else {
                    Compari result = new Compari();
                    result.setCompany("EVOMAG");
                    result.setPrice(productPrice);
                    result.setNameProduct(productName);
                    result.setLink("https://www.evomag.ro" + productPageLink);
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

