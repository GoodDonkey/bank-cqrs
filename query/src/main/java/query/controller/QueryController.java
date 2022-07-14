package query.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import query.entity.HolderAccountSummary;
import query.service.QueryService;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class QueryController {
    private final QueryService queryService;
    
    @PostMapping("/reset")
    public void reset() {
        queryService.reset();
    }
    
    @GetMapping("/account/info/{id}")
    public ResponseEntity<HolderAccountSummary> getAccountInfo(@PathVariable(value = "id") @NonNull String holderId) {
        return ResponseEntity.ok().body(queryService.getAccountInfo(holderId));
    }
    
    @GetMapping("/account/info/subscription/{id}")
    public ResponseEntity<Flux<HolderAccountSummary>> getAccountInfoSubscription(@PathVariable(value = "id") @NonNull String holderId) {
        return ResponseEntity.ok()
                             .body(queryService.getAccountInfoSubscription(holderId));
    }
}
