package query.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import query.service.QueryService;

@RestController
@RequiredArgsConstructor
public class QueryController {
    private final QueryService queryService;
    
    @PostMapping("/reset")
    public void reset() {
        queryService.reset();
    }
}
