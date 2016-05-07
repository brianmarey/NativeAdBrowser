package com.careydevelopment.nativeadbrowser.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.careydevelopment.nativeadbrowser.jpa.entity.DomainAd;

public interface DomainAdRepository extends BaseRepository<DomainAd,Long>{

    @Query("SELECT da FROM DomainAd da order by da.lastSeen desc")
    Page<DomainAd> findNativeAds(Pageable page);
    
    @Query("SELECT da FROM DomainAd da order by da.lastSeen desc")
    List<DomainAd> findTop120NativeAds();
}
