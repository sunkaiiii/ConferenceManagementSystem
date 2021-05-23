package org.openjfx.controllers;

import org.openjfx.controllers.page.interfaces.PageNameDescriber;

public enum PageNames implements PageNameDescriber {
    LOG_IN("log_in.fxml"),
    SIGN_UP("sign_up.fxml"),
    CONFERENCE_MANAGEMENT("conference_management.fxml"),
    CREATE_CONFERENCE("create_conference.fxml"),
    EDIT_CONFERENCE("edit_conference.fxml"),
    CONFERENCE_CELL("conference_cell.fxml"),
    AVAILABLE_CONFERENCE_CELL("paper_page_available_conference_cell.fxml"),
    PAPER_MANAGEMENT("paper_management.fxml"),
    SUBMIT_PAPER("submit_paper.fxml"),
    PRE_DEFINE_KEYWORD_CELL("pre_define_list_cell.fxml"),
    PAPER_MANAGEMENT_MY_PAPERS("paper_management_my_papers"),
    MY_PAPER_LIST_CELL("my_paper_list_cell.fxml"),
    CONFERENCE_PAPER("conference_paper.fxml"),
    ADMIN_PAGE("admin_page.fxml"),
    ADMIN_CONFERENCE_CELL("admin_conference_cell.fxml"),
    ADMIN_USER_CELL("admin_user_cell.fxml"),
    REVIEWER_ASSIGNMENT("reviewer_assignment.fxml"),
    ASSIGN_REVIEWER_REVIEW_LIST_CELL("assign_reviewer_reviewer_list_cell.fxml"),
    SELECTED_REVIEWER_CELL("selected_reviewer_cell.fxml"),
    REVIEW_MANAGEMENT("review_management.fxml"),
    REVIEW_PAGE_CELL("review_page_cell.fxml"),
    WRITE_REVIEW_PAGE("write_review_page.fxml"),
    WRITE_REVIEW_PAGE_FILE_LIST_CELL("write_review_page_file_list_cell.fxml"),
    PAPER_SUBMIT_FILE_LIST_CELL("paper_submit_file_list_cell.fxml"),
    PAPER_FINAL_DECISION_PAGE("paper_final_decision_page.fxml"),
    FINAL_DECISION_REVIEW_LIST_CELL("final_decision_review_list_cell.fxml"),
    GENERAL_ALERT_VIEW("general_alert_view.fxml"),
    CREATE_CONFERENCE_ADD_TIME_DIALOG("/org/openjfx/controllers/dialog/create_conference_select_time_dialog.fxml"),
    ADD_INTERSTING_AREA_DIALOG("/org/openjfx/controllers/dialog/add_interesting_area_dialog.fxml"),
    ADD_KEYWORD_DIALOG("/org/openjfx/controllers/dialog/add_keyword_dialog.fxml"),
    ;
    private final String pageName;

    public String getPageName() {
        return pageName;
    }

    PageNames(String pageName) {
        this.pageName = pageName;
    }
}
