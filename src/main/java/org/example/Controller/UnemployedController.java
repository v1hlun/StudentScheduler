package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.Service.UnemployedService;
import org.example.model.Distribution;
import org.example.model.Unemployed;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/unemployed")
@AllArgsConstructor
public class UnemployedController {
    private final UnemployedService unemployedService;

    @PostMapping("/add/{studentId}")
    public Unemployed addUnemployedById(@PathVariable Long studentId,
                                            @RequestBody Unemployed unemployed){
        return unemployedService.addUnemployed(studentId,unemployed);

    }

    @GetMapping
    public List<Unemployed> getAllUnemployed(){return unemployedService.getAllUnemployed();}

    @GetMapping("/{id}")
    public Unemployed getUnemployedById(@PathVariable Long id){return unemployedService.getUnemployedById(id);}

    @PatchMapping("/{id}")
    public Unemployed patchUpdateUnemployed(@PathVariable Long id,
                                            @RequestBody Map<String, Object> updates) {
        return unemployedService.updateUnemployed(id, updates);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUnemployed(@PathVariable Long id){unemployedService.deleteUnemployed(id);}
}

