package com.careydevelopment.nativeadbrowser.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.careydevelopment.nativeadbrowser.jpa.entity.DomainAd;
import com.careydevelopment.nativeadbrowser.jpa.entity.NativeAd;

public interface DomainAdRepository extends BaseRepository<DomainAd,Long>{

    @Query("SELECT da.nativeAd FROM DomainAd da order by da.lastSeen desc")
    Page<NativeAd> findNativeAds(Pageable page);
}
