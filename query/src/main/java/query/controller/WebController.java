package query.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/p2p")
    public String p2pQueryView() {
        return "p2p.html";
    }
    
    @GetMapping("/subscription")
    public void subscriptionQueryView() {};
}
