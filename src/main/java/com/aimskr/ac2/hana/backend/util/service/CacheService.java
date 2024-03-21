package com.aimskr.ac2.hana.backend.util.service;

import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneAccd;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneAccdRepository;
import com.aimskr.ac2.hana.backend.util.config.EhcacheConfig;
import com.aimskr.ac2.hana.backend.vision.domain.DocKeyword;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.hana.backend.vision.domain.DetailKeyword;
import com.aimskr.ac2.hana.backend.vision.domain.DetailKeywordRepository;
import com.aimskr.ac2.hana.backend.vision.domain.DocKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CacheService {

    private final DocKeywordRepository docKeywordRepository;
    private final DetailKeywordRepository detailKeywordRepository;
    private final PhoneAccdRepository phoneAccdRepository;

    public Map<ItemType, List<String>> getDetailKeywords(){
        List<DetailKeyword> detailKeywords = detailKeywordRepository.findAll();
        detailKeywords.sort(Comparator.comparingInt(DetailKeyword::getPriority));

        return detailKeywords.stream()
                .collect(Collectors.groupingBy(DetailKeyword::getDetailKeywordCategory,
                        Collectors.mapping(DetailKeyword::getDetailKeyword, Collectors.toList())));
    }

    @Cacheable(value= EhcacheConfig.DOCKEYWORD_CACHE)
    public List<DocKeyword> getDocKeywords() { return docKeywordRepository.findAll(); }

    @Cacheable(value= EhcacheConfig.ACCD_CACHE)
    public List<PhoneAccd> getPhoneAccd() { return phoneAccdRepository.findAll(); }


}
