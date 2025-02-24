package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.DTO.ReassignmentDTO;
import org.example.Service.ReassignmentService;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reassignments")
@AllArgsConstructor
public class ReassignmentController {

    private final ReassignmentService reassignmentService;

    @PostMapping("add/{studentId}")
    public ResponseEntity<ReassignmentDTO> addReassignment(@PathVariable Long studentId,
                                                           @RequestBody ReassignmentDTO reassignmentDTO) throws IOException {
        return ResponseEntity.status(201).body(reassignmentService.addReassignment(studentId, reassignmentDTO)) ;
    }

    @GetMapping("/all")
    public List<ReassignmentDTO> getAllReassignments(){return reassignmentService.getAllReassignment();}

    @GetMapping("/{id}")
    public ReassignmentDTO getReassignmentById(@PathVariable Long id){return reassignmentService.getReassignmentById(id);}

    @GetMapping
    public Slice<ReassignmentDTO> getReassignmentsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return reassignmentService.getReassignmentsWithPagination(page, size);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ReassignmentDTO> patchUpdateReassignment(@PathVariable Long id,
                                                                   @RequestBody Map<String, Object> updates) throws IOException {
        try {
            return ResponseEntity.ok().body(reassignmentService.updateReassignment(id, updates)) ;
        }
        catch (RuntimeException e){
            return ResponseEntity.status(404).body(null);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReassignment(@PathVariable  Long id) throws IOException {
        reassignmentService.deleteReassignment(id);
        return ResponseEntity.status(204).build();
    }

}
