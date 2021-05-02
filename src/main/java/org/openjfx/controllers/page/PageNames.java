package org.openjfx.controllers.page;

import org.openjfx.controllers.page.interfaces.PageNameDescriber;

enum PageNames implements PageNameDescriber {
    LOG_IN("log_in.fxml"),
    SIGN_UP("sign_up.fxml"),
    CONFERENCE_MANAGEMENT("conference_management.fxml"),
    CREATE_CONFERENCE("create_conference.fxml"),
    CONFERENCE_CELL("conference_cell.fxml"),
    PAPER_SUBMISSION_LIST("paper_submission_List.fxml"),
    PAPER_MANAGEMENT_AVAILABLE_CONFERENCE("paper_management_available_conference.fxml"),
    PAPER_MANAGEMENT_MY_PAPERS("paper_management_my_papers")
    ;
    private String pageName;

    public String getPageName() {
        return pageName;
    }

    PageNames(String pageName) {
        this.pageName = pageName;
    }
}
