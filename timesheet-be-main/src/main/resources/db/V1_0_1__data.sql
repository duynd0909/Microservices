-- Default rules
INSERT INTO public.md_rules("name", description, working_time_in, working_time_out, total_minute_limit, minute_limit, late_limit, rule_type, block_id)
values
('Cloud App', 'Giờ làm việc cho các dự án thuộc khối Cloud Application', '08:00', '17:00', 240, null, null, 2, null),
('GDC Rules', 'Giờ làm việc cho các dự án của GDC', '08:00', '17:00', null, null, null, 2, 1)
;


INSERT INTO public.md_rules_block(start_minute, block_minute, calc_minute)
values (15, 30, 30);

INSERT INTO public.project("name", description, rule_id)
values ('DU6 Pool', 'Pool Team', 1);

INSERT INTO public.master_data(created_at, deleted_at, code, name, type)
VALUES
('2023-02-21', NULL , 'NORMAL', 'Normal', 'working_type'),
('2023-02-21', NULL , 'LATE_COMING', 'Late coming', 'working_type'),
('2023-02-21', NULL , 'EARLY_LEAVING', 'Early leaving', 'working_type'),
('2023-02-21', NULL , 'ABSENT', 'Absent', 'working_type'),
('2023-02-21', NULL , 'LATE_COMING_EARLY_LEAVING', 'Late coming,Early Leaving', 'working_type'),
('2023-02-21', NULL , 'WFH', 'Work from home','working_type'),
('2023-02-21', NULL , 'COMPENSATORY_LEAVE', 'Compensatory leave', 'working_type')

insert into employee (id, name, knox_id, full_name, ldap, email, du, project_id, rule_id, leave_remaining)
VALUES (1, 'MrAN', '006', 'Mac Thanh An', 'mtan1', 'mtan1@cmcglobal.vn', '6', 1, 1, 1);
insert into employee (id, name, knox_id, full_name, ldap, email, du, project_id, rule_id, leave_remaining)
VALUES (2, 'Duy', '007', 'Mr Duy', 'ndduy2', 'ndduy2@cmcglobal.vn', '6', 1, 1, 1);
--insert into employee (id, name, knox_id, full_name, ldap, email, du, project_id, rule_id, leave_remaining)
INSERT INTO public.timesheet (id, working_date, working_type, time_in, time_out, employee_id, regular_hours, overtime_hours)
VALUES ('229f6f9f-7724-43b1-b951-43ee5475ac93', '2023-02-01', 1, '08:00:00.05', '17:00:00.052', 1, '17:00:00.052', '00:00:00');
INSERT INTO public.timesheet (id, working_date, working_type, time_in, time_out, employee_id, regular_hours, overtime_hours)
VALUES ('0a811cb7-60a3-4b89-afc1-582f324a4e97', '2023-02-28', 1, '07:00:00', '17:00:00.009', 1, '17:00:00.009', '00:00:00');
INSERT INTO public.timesheet (id, working_date, working_type, time_in, time_out, employee_id, regular_hours, overtime_hours)
VALUES ('66c143d1-496b-413e-aed4-fb8ca7f07558', '2023-02-01', 1, '07:00:00.015', '17:00:00.025', 2, '17:00:00.025', '00:00:00');
INSERT INTO public.timesheet (id, working_date, working_type, time_in, time_out, employee_id, regular_hours, overtime_hours)
VALUES ('42d4f250-6c04-4296-9170-1e2fd9b113d4', '2023-02-28', 2, '08:05:00.054', '17:00:00.029', 2, '17:00:00.029', '00:00:00');
INSERT INTO public.timesheet (id, working_date, working_type, time_in, time_out, employee_id, regular_hours, overtime_hours)
VALUES ('a986eace-9b96-4852-a3b8-f36ef96f04fb', '2023-02-01', 4, '09:00:00.044', '10:00:00.007', 3, '10:00:00.007', '00:00:00');
INSERT INTO public.timesheet (id, working_date, working_type, time_in, time_out, employee_id, regular_hours, overtime_hours)
VALUES ('5951f40b-e0fa-4cc6-99be-0dcd6740efd1', '2023-02-28', 4, '09:00:00.008', '16:00:00.019', 3, '17:00:00.019', '00:00:00');