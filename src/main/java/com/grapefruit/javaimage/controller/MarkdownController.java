/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.javaimage.controller;

import com.grapefruit.javaimage.entity.Markdown;
import com.grapefruit.javaimage.http.req.Req;
import com.grapefruit.javaimage.repo.MarkdownRepo;
import com.grapefruit.javaimage.service.MarkdownService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-11-16 7:52 下午
 */
@RestController
public class MarkdownController {

    @Autowired
    private MarkdownService markdownService;

    @Autowired
    private MarkdownRepo markdownRepo;

    private Map<String, String> markdown = new HashMap();

    @PostMapping("/downloadMarkdown")
    public String downloadRichText(HttpServletRequest request, HttpServletResponse response) {
        String content = "<p><em>1</em></p><p><em>2</em></p><p><em>3</em></p><p><em><span " +
                "class=\"ql-cursor\">\uFEFF</span></em><img src=\"data:image/png;base64," +
                "iVBORw0KGgoAAAANSUhEUgAAAJoAAADECAYAAABneziQAAAJ3ElEQVR4Ae2dj5NVUxzA" +
                "+5MwfozfhIQkCRsKSaL0Q8qWn1uYjGLIjybEJJVEhCEGzVTDsqFI5Ef0a23t9kPbjzWzHfO9da7neu+c1X7PvnOez5m5c9595" +
                "+z3nfs5nz3v3nffO6eXKaSmpiYjGwkCmgR6FYMhWpEI" +
                "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\"></p>";
        return content;
    }

    @PostMapping("/downloadAllMd")
    public List<Markdown> downloadAllMd() {
        return markdownService.selectAll();
    }

    @PostMapping("/downloadMdById")
    public Markdown downloadMdById(@RequestBody Req req) {
        Markdown byId = markdownService.findById(req.getId());
        String tags = byId.getTags();
        if (StringUtils.isEmpty(tags)) {
            byId.setTagArray(new ArrayList<>());
        } else {
            String[] split = tags.split(",");
            byId.setTagArray(Arrays.asList(split));
        }
        return byId;
    }

    @PostMapping("/saveMarkdown")
    public Markdown saveMd(@RequestBody Markdown markdown) {
        if (StringUtils.isEmpty(markdown.getTitle())) {
            markdown.setTitle("Markdown");
        }
        markdown.setModifier("grape");
        markdown.setModifyTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        Markdown save = markdownRepo.save(markdown);
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        String tags = save.getTags();
        if (StringUtils.isEmpty(tags)) {
            save.setTagArray(new ArrayList<>());
        } else {
            String[] split = tags.split(",");
            save.setTagArray(Arrays.asList(split));
        }
        return save;
    }
}



/*
```java
@PostMapping("/saveMarkdown")
public String saveMd(@RequestBody Markdown markdown){
    markdown.setTitle("Markdown");
    markdown.setModifier("grape");
    markdown.setModifyTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

    markdownService.save(markdown);
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
```
*/
