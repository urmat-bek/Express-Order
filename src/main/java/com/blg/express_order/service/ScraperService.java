package com.blg.express_order.service;

import com.blg.express_order.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperService {
    public List<ProductDTO> searchAmazonProducts(String query) throws IOException {
        String url = "https://www.amazon.com/s?k=" + query;
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .get();
        Elements items = doc.select(".s-result-item");

        List<ProductDTO> products = new ArrayList<>();

        for (Element item : items) {
            String title = item.select(".a-text-normal").text();
            String price = item.select(".a-price .a-offscreen").text();
            String rating = item.select(".a-icon-alt").text();
            String productUrl = "https://www.amazon.com" + item.select(".a-link-normal").attr("href");
            String imageUrl = item.select(".s-image").attr("src");

            if (!title.isEmpty() && !price.isEmpty() && !productUrl.isEmpty() && !imageUrl.isEmpty()) {
                products.add(new ProductDTO(title, price, rating, productUrl, imageUrl, "Amazon"));
            }
        }

        return products;
    }

    public List<ProductDTO> searchEbayProducts(String query) throws IOException {
        String url = "https://www.ebay.com/sch/i.html?_nkw=" + query;
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .get();

        Elements items = doc.select(".s-item");

        List<ProductDTO> products = new ArrayList<>();

        for (Element item : items) {
            String title = item.select(".s-item__title").text();
            String price = item.select(".s-item__price").text();
            String productUrl = item.select(".s-item__link").attr("href");
            Element imageElement = item.select(".s-item__image-wrapper img").first();
            String imageUrl = imageElement != null ? imageElement.attr("src") : "Нет изображения";

            if (!title.isEmpty() && !price.isEmpty() && !productUrl.isEmpty() && !imageUrl.isEmpty()) {
                products.add(new ProductDTO(title, price, productUrl, imageUrl, "Ebay"));
            }
        }

        return products;
    }

    public List<ProductDTO> searchProducts(String query) throws IOException {
        List<ProductDTO> amazonProducts = searchAmazonProducts(query);
        List<ProductDTO> ebayProducts = searchEbayProducts(query);

        List<ProductDTO> allProducts = new ArrayList<>();
        allProducts.addAll(amazonProducts);
        allProducts.addAll(ebayProducts);

        return allProducts;
    }
}
