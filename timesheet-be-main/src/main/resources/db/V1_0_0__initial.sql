drop table if exists public.project_emp;
drop table if exists public.timesheet;
drop table if exists public.employee;
drop table if exists public.project;
drop table if exists public.md_rules;
drop table if exists public.md_rules_block;
-- drop table if exists division;

-- CREATE TABLE division
-- (
--   id SERIAL PRIMARY KEY,
--   name character varying(50) NOT NULL,
--   group character varying(50),
--   created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
--   deleted_at timestamp without time zone,
-- );
CREATE TABLE public.md_rules_block
(
    id                  SERIAL PRIMARY KEY,
    start_minute        smallint                NOT NULL,
    block_minute        smallint                NOT NULL,
    calc_minute         smallint                NOT NULL
);

CREATE TABLE public.md_rules
(
    id                  SERIAL PRIMARY KEY,
    name                character varying(255) NOT NULL,
    description         character varying(255),
    working_time_in     time                   NOT NULL,
    working_time_out    time                   NOT NULL,
    late_limit          smallint,
    minute_limit        smallint,
    total_minute_limit  smallint,
    block_id            smallint,
    rule_type           smallint               NOT NULL default 0,
    created_at          timestamp without time zone     DEFAULT CURRENT_TIMESTAMP,
    deleted_at          timestamp without time zone
);

CREATE TABLE public.project
(
    id          SERIAL PRIMARY KEY,
    name        character varying(255) NOT NULL,
    description character varying(255),
    rule_id     integer                NOT NULL,
    created_at  timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at  timestamp without time zone,
    CONSTRAINT fk_project_rules FOREIGN KEY (rule_id) REFERENCES md_rules (id)
);

CREATE TABLE public.employee
(
    id              integer                NOT NULL PRIMARY KEY,
    name            character varying(255) NOT NULL,
    knox_id         character varying(255),
    full_name       character varying(255) NOT NULL,
    ldap            character varying(50),
    email           character varying(255),
    du              character varying(50)  NOT NULL,
    project_id      integer,
    rule_id         integer,
    leave_remaining smallint,
    created_at      timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    deleted_at      timestamp without time zone,
    CONSTRAINT fk_employee_project FOREIGN KEY (project_id) REFERENCES project (id),
    CONSTRAINT fk_employee_rules FOREIGN KEY (rule_id) REFERENCES md_rules (id)
);

CREATE TABLE public.project_emp
(
    project_id   integer NOT NULL,
    employee_id  integer NOT NULL,
    joining_date timestamp without time zone,
    leaving_date timestamp without time zone,
    CONSTRAINT pk_project_emp PRIMARY KEY (project_id, employee_id),
    CONSTRAINT fk_project_emp_project FOREIGN KEY (project_id) REFERENCES project (id),
    CONSTRAINT fk_project_emp_employee FOREIGN KEY (employee_id) REFERENCES employee (id)
);

CREATE TABLE public.timesheet
(
    id              character varying(255) NOT NULL,
    working_date    date                   NOT NULL,
    working_type    smallint               NOT NULL,
    time_in         time,
    time_out        time,
    employee_id     integer                NOT NULL,
    regular_hours   time,
    overtime_hours  time,
    overtime_from   time,
    overtime_to     time,
    missing_minutes smallint,
    notes           character varying(1000),
    attachments     text[],
    created_at      timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_timesheet PRIMARY KEY (id),
    CONSTRAINT fk_timesheet_employee FOREIGN KEY (employee_id) REFERENCES employee (id)
);

-- -- ALL the Indexes
-- CREATE UNIQUE INDEX idx_emp_date ON timesheet(working_date, employee_id);

CREATE TABLE public.email
(
    id              character varying(255) NOT NULL primary key,
    to_mail         character varying(512),
    cc_mail         character varying(512),
    content         character varying(10000),
    subject         character varying(512),
    attachment      text[],
    month           smallint,
    user_id          int,
    is_sent         boolean,
    created_at      timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);