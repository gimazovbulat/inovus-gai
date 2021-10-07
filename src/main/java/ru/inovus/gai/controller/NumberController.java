package ru.inovus.gai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inovus.gai.service.NumberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/number")
public class NumberController {

    private final NumberService numberService;

    @GetMapping("/random")
    public String getRandom(){
        return numberService.getRandom().getVal();
    }

    @GetMapping("/next")
    public String getNext(){
        return numberService.getNext().getVal();
    }
}
