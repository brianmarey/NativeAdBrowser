package com.careydevelopment.nativeadbrowser.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.nativeadbrowser.jpa.entity.DomainAd;
import com.careydevelopment.nativeadbrowser.jpa.repository.DomainAdRepository;
import com.careydevelopment.nativeadbrowser.util.Constants;

@Controller
public class HottestController implements Constants {

	@Autowired
	DomainAdRepository domainAdRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HottestController.class);
	
	private static final int MAX_PAGE_NUM = 9;
	private static final int RESULTS_PER_PAGE = 12;
	
    @RequestMapping("/hottest")
    public String hottest(Model model, @RequestParam(value="pageNum", required=false) String pageNum) {    	    
    	int page = getPage(pageNum);
    	
    	List<DomainAd> ads = domainAdRepository.findTop120NativeAds();
    	
    	Collections.sort(ads,new Comparator<DomainAd>() {
    	    @Override
    	    public int compare(DomainAd a, DomainAd b) {
    	        return b.getDaysRunning().compareTo(a.getDaysRunning());
    	    }
    	});

    	List<DomainAd> nads = new ArrayList<DomainAd>();
    	for (int i=page * RESULTS_PER_PAGE;i<(page*RESULTS_PER_PAGE) + RESULTS_PER_PAGE;i++) {
    		nads.add(ads.get(i));
    	}
    	
    	setPagination(ads,model,page);
    	
    	model.addAttribute("nativeAds",nads);
    	
        return "hottest";
    }
    
    
    private void setPagination (List<DomainAd> ads, Model model, int page) {
    	model.addAttribute("isFirst", (page == 0));
    	model.addAttribute("isLast", (page == 9));
    	
    	if (page<9) {
    		model.addAttribute("nextPageNum", page+1);
    	}
    	
    	if (page==0) {
    		model.addAttribute("previousPageNum", page-1);
    	}	
    	
    	model.addAttribute("showingResults",getShowingResults(ads,page));
    }
    
    
    private String getShowingResults(List<DomainAd> ads,int page) {
    	StringBuilder builder = new StringBuilder();
    	
    	int start = (page * RESULTS_PER_PAGE) + 1;
    	int end = start + 12 - 1;
    	
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
