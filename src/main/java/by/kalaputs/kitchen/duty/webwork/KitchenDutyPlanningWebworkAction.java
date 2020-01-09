package by.kalaputs.kitchen.duty.webwork;

import com.atlassian.webresource.api.assembler.PageBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class KitchenDutyPlanningWebworkAction extends JiraWebActionSupport {
    private static final Logger log = LoggerFactory.getLogger(KitchenDutyPlanningWebworkAction.class);

    @Inject
    private PageBuilderService pageBuilderService;

    @Override
    public String execute() {
        pageBuilderService.assembler().resources().requireWebResource(
            "by.kalaputs.kitchen-duty-plugin:kitchen-duty-plugin-resources"
        ).requireWebResource(
            "by.kalaputs.kitchen-duty-plugin:kitchen-duty-plugin-resources--planning-page"
        ).requireWebResource("com.atlassian.auiplugin:aui-flag");

        return "kitchen-duty-planning-success";
    }

    public void setPageBuilderService(PageBuilderService pageBuilderService) {
        this.pageBuilderService = pageBuilderService;
    }
}
