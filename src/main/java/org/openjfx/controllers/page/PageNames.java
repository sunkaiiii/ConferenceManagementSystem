package org.openjfx.controllers.page;

import org.openjfx.controllers.page.interfaces.PageNameDescriber;

enum PageNames implements PageNameDescriber {
    LOG_IN("log_in.fxml"),
    SIGN_UP("sign_up.fxml"),
    CONFERENCE_MANAGEMENT("conference_management.fxml"),
    CREATE_CONFERENCE("create_conference.fxml"),
    CONFERENCE_CELL("conference_cell.fxml"),
    AVAILABLE_CONFERENCE_CELL("paper_page_available_conference_cell.fxml"),
    PAPER_MANAGEMENT("paper_management.fxml"),
    SUBMIT_PAPER("submit_paper.fxml"),
    PRE_DEFINE_KEYWORD_CELL("pre_define_list_cell.fxml"),
    PAPER_MANAGEMENT_MY_PAPERS("paper_management_my_papers"),
    MY_PAPER_LIST_CELL("my_paper_list_cell.fxml"),
    CONFERENCE_PAPER("conference_paper.fxml")
    ;
    private final String pageName;

    public String getPageName() {
        return pageName;
    }

    PageNames(String pageName) {
        this.pageName = pageName;
    }
}
