package com.blg.express_order.controller;

import com.blg.express_order.dto.ProductDTO;
import com.blg.express_order.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    @GetMapping("/search")
    public List<ProductDTO> search(@RequestParam String query) throws IOException {
        return scraperService.searchProducts(query);
    }

}
