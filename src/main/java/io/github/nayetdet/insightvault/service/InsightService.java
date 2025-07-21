package io.github.nayetdet.insightvault.service;

import io.github.nayetdet.insightvault.exception.InsightModificationForbiddenException;
import io.github.nayetdet.insightvault.exception.InsightNotFoundException;
import io.github.nayetdet.insightvault.mapper.InsightMapper;
import io.github.nayetdet.insightvault.model.Insight;
import io.github.nayetdet.insightvault.payload.dto.InsightDTO;
import io.github.nayetdet.insightvault.payload.dto.simplified.SimplifiedInsightDTO;
import io.github.nayetdet.insightvault.payload.query.InsightQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.payload.request.InsightRequest;
import io.github.nayetdet.insightvault.repository.InsightRepository;
import io.github.nayetdet.insightvault.security.provider.UserContextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InsightService {

    private final RagService ragService;
    private final InsightRepository insightRepository;
    private final InsightMapper insightMapper;

    @Transactional(readOnly = true)
    public ApplicationPage<SimplifiedInsightDTO> search(InsightQuery query) {
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), query.getSort());
        Page<Insight> page = insightRepository.findAll(query.getSpecification(), pageable);
        return new ApplicationPage<>(page.map(insightMapper::toSimplifiedDTO));
    }

    @Transactional(readOnly = true)
    public Optional<InsightDTO> find(Long id) {
        return insightRepository.findById(id).map(insightMapper::toDTO);
    }

    @Transactional
    public InsightDTO create(InsightRequest request) {
        UserContextProvider.assertAuthorizedContext(request.getUsername(), InsightModificationForbiddenException.class);
        Insight insight = insightMapper.toModel(request, ragService.query(request.getQuestion()));
        return insightMapper.toDTO(insightRepository.save(insight));
    }

    @Transactional
    public void delete(Long id) {
        Insight insight = insightRepository
                .findById(id)
                .orElseThrow(InsightNotFoundException::new);

        UserContextProvider.assertAuthorizedContext(insight.getUser().getUsername(), InsightModificationForbiddenException.class);
        insightRepository.delete(insight);
    }

}
