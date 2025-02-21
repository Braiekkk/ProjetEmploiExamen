package com.example.projetemploiexamen.niveau;

import com.example.projetemploiexamen.niveau.DTO.CreateNiveauDTO;
import com.example.projetemploiexamen.niveau.DTO.UpdateNiveauDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NiveauService {

    private final NiveauRepository niveauRepository;

    public NiveauService(NiveauRepository niveauRepository) {
        this.niveauRepository = niveauRepository;
    }

    public ResponseEntity<ApiResponse<CreateNiveauDTO>> createNiveau(CreateNiveauDTO niveauDTO) {
        try {
            Niveau niveau = new Niveau(niveauDTO.getId(), niveauDTO.getName(), niveauDTO.getSubjects(), niveauDTO.getNbrStudents(), niveauDTO.getTd());
            niveauRepository.save(niveau);
            return ResponseEntity.ok(ApiResponse.success("Niveau created successfully", new CreateNiveauDTO(niveau)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create niveau: " + e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse<UpdateNiveauDTO>> updateNiveau(Long id, UpdateNiveauDTO niveauDTO) {
        return niveauRepository.findById(id)
                .map(niveau -> {
                    niveau.setName(niveauDTO.getName());
                    niveau.setSubjects(niveauDTO.getSubjects());
                    niveau.setNbrStudents(niveauDTO.getNbrStudents());
                    niveau.setTd(niveauDTO.getTd());
                    niveauRepository.save(niveau);
                    return ResponseEntity.ok(ApiResponse.success("Niveau updated successfully", new UpdateNiveauDTO(niveau)));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Niveau not found")));
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

    public ResponseEntity<ApiResponse<List<CreateNiveauDTO>>> getAllNiveaux() {
        List<CreateNiveauDTO> niveaux = niveauRepository.findAll().stream()
                .map(CreateNiveauDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("List of all niveaux", niveaux));
    }

    public ResponseEntity<ApiResponse<CreateNiveauDTO>> getNiveauById(Long id) {
        Optional<Niveau> optionalNiveau = niveauRepository.findById(id);

        return optionalNiveau.map(niveau -> ResponseEntity.ok(ApiResponse.success("Niveau retrieved successfully", new CreateNiveauDTO(niveau))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Niveau not found")));
    }
}
