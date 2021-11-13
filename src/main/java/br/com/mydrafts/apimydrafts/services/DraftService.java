package br.com.mydrafts.apimydrafts.services;

import br.com.mydrafts.apimydrafts.dto.DraftDTO;
import br.com.mydrafts.apimydrafts.dto.DraftFormDTO;

public interface DraftService {

    DraftDTO save(DraftFormDTO body);

    DraftDTO searchDraft(String id);

    DraftDTO updateDraft(String id, DraftFormDTO body);

    void deleteDraft(String id);

}
