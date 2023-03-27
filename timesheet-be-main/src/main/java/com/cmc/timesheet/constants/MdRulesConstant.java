package com.cmc.timesheet.constants;

public class MdRulesConstant {
    /* Columns */

    /* md_rules */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_WORKING_TIME_IN = "working_time_in";
    public static final String COLUMN_WORKING_TIME_OUT = "working_time_out";
    public static final String COLUMN_LATE_LIMIT = "late_limit";
    public static final String COLUMN_MINUTE_LIMIT = "minute_limit";
    public static final String COLUMN_TOTAL_MINUTE_LIMIT = "total_minute_limit";
    public static final String COLUMN_RULE_TYPE = "rule_type";
    public static final String COLUMN_BLOCK_ID = "block_id";

    /* md_rules_block */
    public static final String COLUMN_START_MINUTE = "start_minute";
    public static final String COLUMN_BLOCK_MINUTE = "block_minute";
    public static final String COLUMN_CALC_MINUTE = "calc_minute";

    /* Rule Type */
    public static final int RULE_TYPE_MINUTE = 0;
    public static final int RULE_TYPE_TOTAL_MINUTE = 1;
    public static final int RULE_TYPE_BLOCK = 2;

    /* Messages */
    public static final String MSG_RULE_EMPTY = "Rule does not exists";
    public static final String MSG_INVALID_TIME_IN_OUT = "Time In or Time Out are invalid";
    public static final String MSG_RULE_BLOCK_EMPTY = "Rule block does not exists";
    public static final String MSG_INVALID_CONVERT_TIME = "Conversion of Time in or Time out is invalid";

}
