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
public class EmagService implements IWebScraper {
    @Autowired
    private CompariRepository compariRepository;

    @Override
    public void getMemoryRAM() {
        String url = "https://www.emag.ro/memorii/c?ref=hp_menu_quick-nav_23_12&type=category";
        scrapeDataFromEmag(url,"RAM");
    }

    @Override
    public void getMemoryROM() {
        String urlHDD = "https://www.emag.ro/hard_disk-uri/c?ref=hp_menu_quick-nav_23_11&type=category";
        String urlSDD = "https://www.emag.ro/solid-state_drive_ssd_/c?ref=hp_menu_quick-nav_23_10&type=category";
        scrapeDataFromEmag(urlSDD, "ROM");
        scrapeDataFromEmag(urlHDD, "ROM");
    }

    @Override
    public void getProcessor() {
        String urlIntel = "https://www.emag.ro/procesoare/brand/intel/c?ref=lst_leftbar_6415_12";
        String urlAmd = "https://www.emag.ro/procesoare/brand/amd/c?ref=delete_filter_left_bar";
        scrapeDataFromEmag(urlAmd, "Processor");
        scrapeDataFromEmag(urlIntel, "Processor");
    }

    @Override
    public void getVideoCard() {
        String urlAsus = "https://www.emag.ro/placi_video/brand/asus/c?ref=lst_leftbar_6415_18";
        String urlGigabyte = "https://www.emag.ro/placi_video/brand/gigabyte/c?ref=delete_filter_left_bar";
        String urlMSI = "https://www.emag.ro/placi_video/brand/msi/c?ref=delete_filter_left_bar";
        scrapeDataFromEmag(urlMSI, "Video Card");
        scrapeDataFromEmag(urlAsus, "Video Card");
        scrapeDataFromEmag(urlGigabyte, "Video Card");
    }

    @Override
    public void getMotherboard() {
        String urlAsus = "https://www.emag.ro/placi_baza/brand/asus/c?ref=lst_leftbar_6415_18";
        String urlGigabyte = "https://www.emag.ro/placi_baza/brand/gigabyte/c?ref=lst_leftbar_6415_19";
        String urlMSI = "https://www.emag.ro/placi_baza/brand/msi/c?ref=lst_leftbar_6415_48";
        scrapeDataFromEmag(urlMSI, "Motherboard");
        scrapeDataFromEmag(urlAsus, "Motherboard");
        scrapeDataFromEmag(urlGigabyte, "Motherboard");
    }

    @Override
    public void getMouse() {
        String url  = "https://www.emag.ro/mouse/c?ref=hp_menu_quick-nav_23_22&type=category";
        scrapeDataFromEmag(url, "Mouse");
    }

    @Override
    public void getKeyboard() {
        String url = "https://www.emag.ro/tastaturi/c?ref=hp_menu_quick-nav_23_23&type=category";
        scrapeDataFromEmag(url, "Keyboard");
    }

    @Override
    public void getCooler() {
        String url = "https://www.emag.ro/coolere_procesor/c?ref=hp_menu_quick-nav_23_14&type=category";
        scrapeDataFromEmag(url, "Cooler");
    }

    @Override
    public void getSource() {
        String url = "https://www.emag.ro/surse-pc/c?ref=hp_menu_quick-nav_23_16&type=category";
        scrapeDataFromEmag(url, "Source");
    }

    @Override
    public void getOpticalDrive() {

    }

    @Override
    public void getHeadphone() {
        String url = "https://www.emag.ro/casti-pc/c?ref=hp_menu_quick-nav_23_27&type=category";
        scrapeDataFromEmag(url, "Headphone");
    }

    @Override
    public void getMonitor(){
        String urlPhilips = "https://www.emag.ro/monitoare-lcd-led/brand/philips/c?ref=lst_leftbar_6415_43";
        String urlAsus = "https://www.emag.ro/monitoare-lcd-led/brand/asus/c?ref=delete_filter_left_bar";
        String urlSamsung = "https://www.emag.ro/monitoare-lcd-led/brand/samsung/c?ref=delete_filter_left_bar";
        String urlDell = "https://www.emag.ro/monitoare-lcd-led/brand/dell/c?ref=delete_filter_left_bar";
        String urlLG= "https://www.emag.ro/monitoare-lcd-led/brand/lg/c?ref=delete_filter_left_bar";
        scrapeDataFromEmag(urlPhilips, "Monitor");
        scrapeDataFromEmag(urlAsus, "Monitor");
        scrapeDataFromEmag(urlDell, "Monitor");
        scrapeDataFromEmag(urlSamsung, "Monitor");
        scrapeDataFromEmag(urlLG, "Monitor");
    }

    private void scrapeDataFromEmag(String url, String domain) {
        String html = new RestTemplate().getForObject(url, String.class);
        Document document = Jsoup.parse(html);
        Elements productElements = document.select(".card-item.card-standard.js-product-data");
        if (!productElements.isEmpty()) {
            for (Element productElement : productElements) {
                String productName = productElement.select(".card-v2-title-wrapper").text().trim();
                String productPrice = productElement.select(".product-new-price").text().trim();
                String productImageLink = productElement.select(".card-v2-thumb-inner img").attr("src");
                String productPageLink = productElement.select(".card-v2-info a").attr("href");
                Optional<Compari> existingProduct = compariRepository.findByNameProduct(productName);
                if (existingProduct.isPresent()) {
                    Compari existing = existingProduct.get();
                    if (!existing.getPrice().equals(productPrice)) {
                        existing.setPrice(productPrice);
                        compariRepository.save(existing);
                    }
                } else {
                    Compari result = new Compari();
                    result.setCompany("EMAG");
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
