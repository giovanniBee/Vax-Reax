package com.vaxreact;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Operation {
    void updateTables() throws SQLException;
    ArrayList<String> retriveReportInfo(String report, String patient, String doctor, String adReactionDate, String adverseReaction, String reportDate);
}
