package newproject.controller;

import newproject.domain.Problems;
import newproject.domain.ProblemSet;
import newproject.service.ScrapingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By Alireza Dolatabadi
 * Date: 7/4/2022
 * Time: 8:00 PM
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Controller {
    private  final ScrapingService scrapingService;

    public Controller(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GetMapping()
    public ResponseEntity startLeetCode() throws IOException {
        List<ProblemSet> leetCode = scrapingService.getLeetCode();
        List<Problems> codeChet = scrapingService.getCodeChet();
        List<Object> objects = new ArrayList<>(codeChet);
        objects.addAll(leetCode);

        return ResponseEntity.ok(objects);
    }

    @PostMapping()
    public ResponseEntity createKeySpace() throws IOException {

        scrapingService.createKeySpace();

        return ResponseEntity.ok("done");
    }
}
