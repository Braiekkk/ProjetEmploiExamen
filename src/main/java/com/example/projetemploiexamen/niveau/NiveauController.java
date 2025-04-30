package com.example.projetemploiexamen.niveau;
import com.example.projetemploiexamen.niveau.DTO.CreateNiveauDTO;
import com.example.projetemploiexamen.niveau.DTO.NiveauDTO;
import com.example.projetemploiexamen.niveau.DTO.UpdateNiveauDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/niveau")
public class NiveauController {
    private final NiveauService niveauService;

    public NiveauController(NiveauService niveauService) {
        this.niveauService = niveauService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NiveauDTO>> createNiveau(@RequestBody CreateNiveauDTO niveauDTO) {
        return niveauService.createNiveau(niveauDTO);
    }
    @GetMapping("/specific")
    public ResponseEntity<ApiResponse<NiveauDTO>> getNiveauByName(@RequestParam String niveauName) {
        return niveauService.getNiveauByName(niveauName);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NiveauDTO>> updateNiveau(@PathVariable Long id, @RequestBody UpdateNiveauDTO niveauDTO) {
        return niveauService.updateNiveau(id, niveauDTO);
    }

    @GetMapping("/specific")
    public ResponseEntity<ApiResponse<NiveauDTO>> getNiveauByName(@RequestParam String niveauName) {
        return niveauService.getNiveauByName(niveauName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNiveau(@PathVariable Long id) {
        return niveauService.deleteNiveau(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NiveauDTO>>> getAllNiveaux() {
        return niveauService.getAllNiveaux();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NiveauDTO>> getNiveauById(@PathVariable Long id) {
        return niveauService.getNiveauById(id);
    }

}
