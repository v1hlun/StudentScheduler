package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.DTO.DistributionDTO;
import org.example.Service.DistributionService;
import org.example.model.Distribution;
import org.springframework.data.domain.Slice;
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
    public DistributionDTO addDistributionById(@PathVariable Long studentId,
                                               @RequestBody DistributionDTO distributionDTO) throws IOException {
        return distributionService.addDistribution(studentId,distributionDTO);

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
    public DistributionDTO patchUpdateDistribution(@PathVariable Long id,
                                                @RequestBody Map<String, Object> updates) throws IOException {
        return  distributionService.updateDistribution(id, updates);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDistribution(@PathVariable Long id) throws IOException {distributionService.deleteDistribution(id);}

}
