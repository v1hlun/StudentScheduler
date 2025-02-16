package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.Service.ReassignmentService;
import org.example.model.Reassignment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reassignments")
@AllArgsConstructor
public class ReassignmentController {

    private final ReassignmentService reassignmentService;

    @PostMapping("add/{studentId}")
    public Reassignment addReassignment(@PathVariable Long studentId,
                                        @RequestBody Reassignment reassignment){
        return reassignmentService.addReassignment(studentId, reassignment);
    }

    @GetMapping
    public List<Reassignment> getAllReassignments(){return reassignmentService.getAllReassignment();}

    @GetMapping("/{id}")
    public Reassignment getReassignmentById(Long id){return reassignmentService.getReassignmentById(id);}

    @PatchMapping("/update/{id}")
    public Reassignment patchUpdateReassignment(@PathVariable Long id,
                                          @RequestBody Map<String, Object> updates){
        return reassignmentService.updateReassignment(id, updates);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReassignment(Long id){reassignmentService.deleteReassignment(id);}

}
