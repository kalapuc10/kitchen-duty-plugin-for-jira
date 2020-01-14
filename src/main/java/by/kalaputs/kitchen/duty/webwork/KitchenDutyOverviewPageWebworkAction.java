package by.kalaputs.kitchen.duty.webwork;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class KitchenDutyOverviewPageWebworkAction extends JiraWebActionSupport {
    private static final Logger log = LoggerFactory.getLogger(KitchenDutyOverviewPageWebworkAction.class);

    private PageBuilderService pageBuilderService;

    @Override
    public String execute() {
        pageBuilderService.assembler().resources()
            .requireWebResource("by.kalaputs.kitchen-duty-plugin:kitchen-duty-plugin-resources")
            .requireWebResource("by.kalaputs.kitchen-duty-plugin:kitchen-duty-plugin-resources--overview-page");

        return "kitchen-duty-overview-page-success";
    }

    @Inject
    public void setPageBuilderService(PageBuilderService pageBuilderService) {
        this.pageBuilderService = pageBuilderService;
    }
}
