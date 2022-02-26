package com.complexas.controller;

import com.complexas.EnrichDto;
import com.complexas.service.EnrichService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Deprecated(since = "Composing process reworked")
public class ComposerController {

    private final EnrichService enrichService;

    public ComposerController(EnrichService enrichService) {
        this.enrichService = enrichService;
    }

    @PostMapping("/push")
    public List<EnrichDto> pushToKafka() {
        return enrichService.pushToKafka();
    }

    @GetMapping("/dto")
    public List<EnrichDto> getAllDto() {
        return enrichService.getAllDto();
    }
}
