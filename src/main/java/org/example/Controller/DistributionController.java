package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.Service.DistributionService;
import org.example.model.Distribution;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/distributions")
@AllArgsConstructor
public class DistributionController {

    private final DistributionService distributionService;

    @PostMapping("/add/{studentId}")
    public Distribution addDistributionById(@PathVariable Long studentId,
                                        @RequestBody Distribution distribution){
        return distributionService.addDistribution(studentId,distribution);

    }

    @GetMapping("/all")
    public List<Distribution> getAllDistributions(){return distributionService.getAllDistribution();}

    @GetMapping("/{id}")
    public Distribution getDistributionById(@PathVariable Long id){return distributionService.getDistributionById(id);}

    @GetMapping
    public Slice<Distribution> getDistributionsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return distributionService.getDistributionsWithPagination(page, size);
    }

    @PatchMapping("/update/{id}")
    public Distribution patchUpdateDistribution(@PathVariable Long id,
                                                @RequestBody Map<String, Object> updates){
        return  distributionService.updateDistribution(id, updates);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDistribution(@PathVariable Long id){distributionService.deleteDistribution(id);}

}
