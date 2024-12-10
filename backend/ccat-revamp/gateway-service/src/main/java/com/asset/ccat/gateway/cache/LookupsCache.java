package com.asset.ccat.gateway.cache;

import java.util.HashMap;
import java.util.List;

import com.asset.ccat.gateway.models.admin.vip.PageModel;
import com.asset.ccat.gateway.models.customer_care.LkOfferModel;
import com.asset.ccat.gateway.models.shared.FootPrintPageModel;
import org.springframework.stereotype.Component;

@Component
public class LookupsCache {

//    @Autowired
//    LookupsService lookupsService;

    private HashMap<String, FootPrintPageModel> footPrintPages;
    private List<PageModel> pages;

    private List<LkOfferModel> lkOffers;

//    @PostConstruct
//    public void init() throws GatewayException {
//        try {
//            CCATLogger.DEBUG_LOGGER.debug("Start getting footprint pages...");
//            Thread.sleep(15000);
//            CCATLogger.DEBUG_LOGGER.debug("Start getting footprint pages...");
//            footPrintPages = lookupsService.getFootPrintPages();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt(); // Restore the interrupted status
//            throw new RuntimeException("Initialization was interrupted", e);
//        }
//    }

    public HashMap<String, FootPrintPageModel> getFootPrintPages() {
        return footPrintPages;
    }

    public void setFootPrintPages(HashMap<String, FootPrintPageModel> footPrintPages) {
        this.footPrintPages = footPrintPages;
    }

    public void setPages(List<PageModel> pages) {
        this.pages = pages;
    }

    public List<PageModel> getPages() {
        return pages;
    }

    public List<LkOfferModel> getLkOffers() {
        return lkOffers;
    }

    public void setLkOffers(List<LkOfferModel> lkOffers) {
        this.lkOffers = lkOffers;
    }
}
