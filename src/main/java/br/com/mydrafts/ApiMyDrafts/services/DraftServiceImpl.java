package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.clients.TMDBClient;
import br.com.mydrafts.ApiMyDrafts.documents.Draft;
import br.com.mydrafts.ApiMyDrafts.documents.Production;
import br.com.mydrafts.ApiMyDrafts.documents.User;
import br.com.mydrafts.ApiMyDrafts.dto.DraftDTO;
import br.com.mydrafts.ApiMyDrafts.dto.DraftFormDTO;
import br.com.mydrafts.ApiMyDrafts.exceptions.BusinessException;
import br.com.mydrafts.ApiMyDrafts.repository.DraftRepository;
import br.com.mydrafts.ApiMyDrafts.repository.ProductionRepository;
import br.com.mydrafts.ApiMyDrafts.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class DraftServiceImpl implements DraftService {

    @Autowired
    private DraftRepository draftRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TMDBClient client;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DraftDTO save(DraftFormDTO body) {
        User user = this.userRepository.findById(body.getUserID())
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "User not found"));
        return null;
    }

    @Override
    public Page<DraftDTO> getDrafts(String userID) {
        return null;
    }

    @Override
    public DraftDTO searchDraft(String id) {
        Draft draft = this.draftRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "NOT FOUND", "Draft not found"));
        return modelMapper.map(draft, DraftDTO.class);
    }

    @Override
    public DraftDTO updateDraft(String id, DraftFormDTO body) {
        return null;
    }

    @Override
    public void deleteDraft(String id) {

    }
}
