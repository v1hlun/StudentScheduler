-- Добавление случайных студентов
INSERT INTO student (full_name, address, tel_number, speciality, code, faculty, form_education, paid_education, step_education, year_of_graduation, consolidation)
VALUES
('Иван Иванов', 'ул. Ленина, 12', '123456789', 'Программист', '001', 'ФКТИ', 'Очное', 'Нет', 'Бакалавр', '2023', 'Да'),
('Мария Петрова', 'ул. Пушкина, 5', '987654321', 'Экономист', '002', 'ФЭУ', 'Очно-заочное', 'Да', 'Магистр', '2024', 'Нет');

-- Добавление случайных распределенных студентов
INSERT INTO distribution (student_id, profiling, name_company, job_title, consolidation)
VALUES
(1, 'IT', 'Google', 'Software Engineer', 'Да');

-- Добавление случайных перераспределенных студентов
INSERT INTO reassignment (student_id, profiling, name_old_company, name_new_company, job_title, consolidation)
VALUES
(1, 'IT', 'Google', 'Microsoft', 'Senior Developer', 'Да');

-- Добавление случайных нетрудоустроенных студентов
INSERT INTO unemployed (student_id, profiling, name_company, direction_returned, consolidation)
VALUES
(2, 'Экономика', 'Нет', 'Вернулся в универ', 'Нет');
