package com.s14ittalents.insta.search;

import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController extends AbstractController {
    @Autowired
    SearchService searchService;

    @GetMapping()
    @ResponseBody
    SearchResultDTO search(@RequestParam String query) {
//        getLoggedUserId();
        return searchService.search(query);
    }
}
