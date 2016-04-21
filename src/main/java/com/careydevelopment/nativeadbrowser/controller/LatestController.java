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
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.nativeadbrowser.jpa.entity.NativeAd;
import com.careydevelopment.nativeadbrowser.jpa.repository.DomainAdRepository;
import com.careydevelopment.nativeadbrowser.util.Constants;

@Controller
public class LatestController implements Constants {

	@Autowired
	DomainAdRepository domainAdRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LatestController.class);
	
    @RequestMapping("/latest")
    public String latest(Model model, @RequestParam(value="pageNum", required=false) String pageNum) {    	    
    	model.addAttribute("homeActive", "active");
 
    	int page = getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page,RESULTS_PER_PAGE);
    	Page<NativeAd> ads = domainAdRepository.findNativeAds(pageable);
    	
    	List<NativeAd> nads = new ArrayList<NativeAd>();
    	for (NativeAd ad : ads) {
    		nads.add(ad);
    	}
    	
    	setPagination(ads,model,page);
    	
    	model.addAttribute("nativeAds",nads);
    	
    	
        return "latest";
    }
    
    
    private void setPagination (Page<NativeAd> ads, Model model, int page) {
    	model.addAttribute("isFirst", ads.isFirst());
    	model.addAttribute("isLast", ads.isLast());
    	
    	if (!ads.isLast()) {
    		model.addAttribute("nextPageNum", page+1);
    	}
    	
    	if (!ads.isFirst()) {
    		model.addAttribute("previousPageNum", page-1);
    	}	
    	
    	model.addAttribute("showingResults",getShowingResults(ads,page));
    }
    
    
    private String getShowingResults(Page<NativeAd> ads,int page) {
    	StringBuilder builder = new StringBuilder();
    	
    	int start = (page * RESULTS_PER_PAGE) + 1;
    	int end = start + ads.getSize() - 1;
    	
    	builder.append("Showing Results ");    	    	
    	builder.append(start);
    	builder.append(" - ");
    	builder.append(end);
    	
    	return builder.toString();
    }
    
    
    private int getPage(String pageNum) {
    	int page = 0;
    	
    	if (pageNum != null) {
    		try {
    			page = new Integer(pageNum);
    		} catch (NumberFormatException ne) {
    			LOGGER.warn("Page number isn't a number! " +pageNum);
    		}
    	}
    	
    	return page;
    }
}
