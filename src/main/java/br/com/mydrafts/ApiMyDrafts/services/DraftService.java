package br.com.mydrafts.ApiMyDrafts.services;

import br.com.mydrafts.ApiMyDrafts.dto.DraftDTO;
import br.com.mydrafts.ApiMyDrafts.dto.DraftFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DraftService {

    DraftDTO save(DraftFormDTO body);

    Page<DraftDTO> getDrafts(Pageable page, String userID);

    DraftDTO searchDraft(String id);

    DraftDTO updateDraft(String id, DraftFormDTO body);

    void deleteDraft(String id);

}
