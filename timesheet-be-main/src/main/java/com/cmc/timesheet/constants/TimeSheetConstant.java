package com.cmc.timesheet.constants;

public class TimeSheetConstant {

    public static final String TABLE_NAME = "timesheet";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WORKING_DATE = "working_date";
    public static final String COLUMN_WORKING_TYPE = "working_type";
    public static final String COLUMN_TIME_IN = "time_in";
    public static final String COLUMN_TIME_OUT = "time_out";
    public static final String COLUMN_EMPLOYEE_ID = "employee_id";
    public static final String COLUMN_REGULAR_HOURS = "regular_hours";
    public static final String COLUMN_OT_FROM = "overtime_from";
    public static final String COLUMN_OT_TO = "overtime_to";
    public static final String COLUMN_MISSING_MINUTES = "missing_minutes";
    public static final String COLUMN_LATE_COMING = "late_coming_minutes";
    public static final String COLUMN_EARLY_LEAVING = "early_leaving_minutes";

    public static final String COLUMN_OVERTIME_HOURS = "overtime_hours";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_ATTACHMENTS = "attachments";

    public static final String ADMIN = "admin";

    public static final String STATUS_INSERT_SUCCESS = "Data inserted successfully!";
    public static final String STATUS_INSERT_FAILED = "Data insert failed!";
    public static final String TIME_FORMAT_REGEX = "H:mm:ss";
    public static final String ERROR_VIEW_DETAIL_DATA = "An unexpected error occurred";

    public static final String DELETE_RECORD_TIMESHEET_SUCCESS = "This record have deleted!";
    public static final String DELETE_RECORD_TIMESHEET_ERROR = "An unexpected error occurred";
    public static final String VIEW_DETAIL_NOT_ID = "Employee Id must not be null";

}
