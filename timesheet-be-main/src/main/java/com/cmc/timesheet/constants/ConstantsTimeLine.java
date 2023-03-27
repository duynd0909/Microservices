package com.cmc.timesheet.constants;

public class ConstantsTimeLine {

    public static final String REQUEST_MAPPING = "/api/timesheet";
    public static final String TIME_LINE_MAPPING = "/time-line";
    public static final String GET_LIST_TIME_LINE = "/get-list-time-line";

    public static final String ADD_NOTE_TIME_LINE = "/add-note-time-line";
    public static final String GET_DETAIL_NOTE_TIME_LINE = "/get-detail-note-time-line";


    //String exception
    public static final String NO_PROJECT = "Project can't be null";
    public static final String NO_DATE = "Start date and End date can't be null";

    //working type
    public static final String LEAVE_ANNUAL = "LEAVE_ANNUAL";
    public static final String ABSENT = "ABSENT";
    public static final String LEAVE_UNPAID = "LEAVE_UNPAID";
    public static final String COMPENSATORY_LEAVE = "COMPENSATORY_LEAVE";

    //STATUS TIME LINE
    public static final String AL = "AL";
    public static final String OT = "OT";
    public static final String BOT = "BOT";
    public static final String ALH = "ALH";
    public static final String NORMAL = "NORMAL";

}
