package com.emresahna.rsstojson.service;

import com.emresahna.rsstojson.model.MediumPostModel;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RssService {

    @Value("${medium.username}")
    private String mediumUsername;

    private final RestTemplate restTemplate;

    public RssService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<SyndEntry> getRss() {
        String rssUrl = "https://medium.com/feed/@" + mediumUsername;
        String rssContent = restTemplate.getForObject(rssUrl, String.class);
        InputStream is = new ByteArrayInputStream(rssContent.getBytes());
        SyndFeed feed = null;
        try {
            feed = new SyndFeedInput().build(new XmlReader(is));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return feed.getEntries();
    }

    public List<MediumPostModel> getUsersMediumPosts() {
        List<SyndEntry> entries = getRss();
        return entries.stream().map(
                entry -> MediumPostModel.builder()
                        .title(entry.getTitle())
                        .link(entry.getLink())
                        .pubDate(entry.getPublishedDate().toString())
                        .build()
        ).collect(Collectors.toList());
    }
}
