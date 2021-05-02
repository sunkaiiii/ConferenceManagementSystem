package org.openjfx.controllers.page;

import org.openjfx.controllers.page.interfaces.PageNameDescriber;

enum PageNames implements PageNameDescriber {
    LOG_IN("log_in.fxml"),
    SIGN_UP("sign_up.fxml"),
    CONFERENCE_MANAGEMENT("conference_management.fxml"),
    CREATE_CONFERENCE("create_conference.fxml"),
    CONFERENCE_CELL("conference_cell.fxml"),
    AVAILABLE_CONFERENCE_CELL("paper_page_available_conference_cell.fxml"),
    PAPER_SUBMISSION_LIST("paper_management.fxml"),
    SUBMIT_PAPER("submit_paper.fxml"),
    PAPER_MANAGEMENT_MY_PAPERS("paper_management_my_papers")
    ;
    private final String pageName;

    public String getPageName() {
        return pageName;
    }

    PageNames(String pageName) {
        this.pageName = pageName;
    }
}
