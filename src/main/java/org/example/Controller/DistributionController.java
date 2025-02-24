package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.DTO.DistributionDTO;
import org.example.Service.DistributionService;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/distributions")
@AllArgsConstructor
public class DistributionController {

    private final DistributionService distributionService;

    @PostMapping("/add/{studentId}")
    public ResponseEntity<DistributionDTO> addDistributionById(@PathVariable Long studentId,
                                                               @RequestBody DistributionDTO distributionDTO) throws IOException {
        return ResponseEntity.status(201).body(distributionService.addDistribution(studentId,distributionDTO)) ;

    }

    @GetMapping("/all")
    public List<DistributionDTO> getAllDistributions(){return distributionService.getAllDistribution();}

    @GetMapping("/{id}")
    public DistributionDTO getDistributionById(@PathVariable Long id){return distributionService.getDistributionById(id);}

    @GetMapping
    public Slice<DistributionDTO> getDistributionsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return distributionService.getDistributionsWithPagination(page, size);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<DistributionDTO> patchUpdateDistribution(@PathVariable Long id,
                                                                   @RequestBody Map<String, Object> updates) throws IOException {
        try {
            return ResponseEntity.ok().body(distributionService.updateDistribution(id, updates)) ;
        }
        catch (RuntimeException e){
           return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDistribution(@PathVariable Long id) throws IOException {
        distributionService.deleteDistribution(id);
        return ResponseEntity.status(204).build();
    }

}
