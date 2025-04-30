package com.example.projetemploiexamen.niveau;

import com.example.projetemploiexamen.niveau.DTO.CreateNiveauDTO;
import com.example.projetemploiexamen.niveau.DTO.NiveauDTO;
import com.example.projetemploiexamen.niveau.DTO.UpdateNiveauDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NiveauService {

    private final NiveauRepository niveauRepository;

    public NiveauService(NiveauRepository niveauRepository) {
        this.niveauRepository = niveauRepository;
    }

    public ResponseEntity<ApiResponse<NiveauDTO>> createNiveau(CreateNiveauDTO niveauDTO) {
        try {
            Niveau niveau = new Niveau(niveauDTO);
            niveauRepository.save(niveau);
            return ResponseEntity.ok(ApiResponse.success("Niveau created successfully", new NiveauDTO(niveau)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create niveau: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<NiveauDTO>> updateNiveau(Long id, UpdateNiveauDTO updateNiveauDTO) {
        try {
            Niveau niveau = niveauRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Niveau not found"));

            niveau.setName(updateNiveauDTO.getName());
            niveau.setSubjects(updateNiveauDTO.getSubjects());
            niveau.setNbrStudents(updateNiveauDTO.getNbrStudents());
            niveau.setTd(updateNiveauDTO.getTd());
            niveauRepository.save(niveau);

            return ResponseEntity.ok(ApiResponse.success("Niveau updated successfully", new NiveauDTO(niveau)));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error updating niveau"));
        }
    }


    public ResponseEntity<ApiResponse<String>> deleteNiveau(Long id) {
        return niveauRepository.findById(id)
                .map(niveau -> {
                    niveauRepository.deleteById(id);
                    return ResponseEntity.ok(ApiResponse.success("Niveau deleted successfully", "Niveau ID: " + id));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Niveau not found")));
    }

    public ResponseEntity<ApiResponse<List<NiveauDTO>>> getAllNiveaux() {
        List<NiveauDTO> niveaux = niveauRepository.findAll().stream()
                .map(NiveauDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("List of all niveaux", niveaux));
    }

    public ResponseEntity<ApiResponse<NiveauDTO>> getNiveauById(Long id) {
        return niveauRepository.findById(id)
                .map(niveau -> ResponseEntity.ok(ApiResponse.success("Niveau retrieved successfully", new NiveauDTO(niveau))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Niveau not found")));
    }


    public ResponseEntity<ApiResponse<NiveauDTO>> getNiveauByName(String niveauName) {
        Optional<Niveau> optionalNiveau = niveauRepository.findFirstByName(niveauName);

        return optionalNiveau.map(niveau -> ResponseEntity.ok(ApiResponse.success("Niveau retrieved successfully", new NiveauDTO(niveau))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Niveau not found")));
    }
}
