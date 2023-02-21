package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;
import br.com.mydrafts.apimydrafts.dto.DraftUpdateFormDTO;

public interface DraftService {

    DraftDTO save(DraftFormDTO body);

    DraftDTO searchDraft(String id);

    DraftDTO updateDraft(String id, DraftUpdateFormDTO body);

    void deleteDraft(String id);

}
