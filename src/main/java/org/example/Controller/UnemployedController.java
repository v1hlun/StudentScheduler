package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.DTO.UnemployedDTO;
import org.example.Service.UnemployedService;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/unemployed")
@AllArgsConstructor
public class UnemployedController {
    private final UnemployedService unemployedService;

    @PostMapping("/add/{studentId}")
    public ResponseEntity<UnemployedDTO> addUnemployedById(@PathVariable Long studentId,
                                                           @RequestBody UnemployedDTO unemployedDTO) throws IOException {
        return ResponseEntity.status(201).body(unemployedService.addUnemployed(studentId,unemployedDTO));

    }

    @GetMapping("/all")
    public List<UnemployedDTO> getAllUnemployed(){return unemployedService.getAllUnemployed();}

    @GetMapping("/{id}")
    public UnemployedDTO getUnemployedById(@PathVariable Long id){return unemployedService.getUnemployedById(id);}

    @GetMapping
    public Slice<UnemployedDTO> getUnemployedWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return unemployedService.getUnemployedWithPagination(page, size);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<UnemployedDTO> patchUpdateUnemployed(@PathVariable Long id,
                                                               @RequestBody Map<String, Object> updates) throws IOException {
        try {
            return ResponseEntity.ok().body(unemployedService.updateUnemployed(id, updates));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUnemployed(@PathVariable Long id) throws IOException {
        unemployedService.deleteUnemployed(id);
        return ResponseEntity.status(204).build();
    }
}

