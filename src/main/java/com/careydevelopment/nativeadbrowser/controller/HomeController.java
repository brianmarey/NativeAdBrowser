package com.careydevelopment.nativeadbrowser.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.nativeadbrowser.jpa.entity.NativeAd;
import com.careydevelopment.nativeadbrowser.jpa.repository.DomainAdRepository;

@Controller
public class HomeController {

	@Autowired
	DomainAdRepository domainAdRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
    @RequestMapping("/")
    public String home(Model model) {    	    
    	model.addAttribute("homeActive", "active");
    	
    	Pageable topTwelve = new PageRequest(0,12);
    	Page<NativeAd> ads = domainAdRepository.findNativeAds(topTwelve);
    	
    	List<NativeAd> nads = new ArrayList<NativeAd>();
    	for (NativeAd ad : ads) {
    		LOGGER.info(ad.getHeadline());
    		nads.add(ad);
    	}
    	
    	
    	model.addAttribute("nativeAds",nads);
    	
        return "index";
    }
}
