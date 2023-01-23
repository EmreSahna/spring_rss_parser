package com.emresahna.rsstojson.controller;

import com.emresahna.rsstojson.model.MediumPostModel;
import com.emresahna.rsstojson.service.RssService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medium")
public class MediumController {
    private final RssService rssService;

    public MediumController(RssService rssService) {
        this.rssService = rssService;
    }

    @RequestMapping("/getposts")
    public List<MediumPostModel> getRss() {
        return rssService.getUsersMediumPosts();
    }
}
