-- Создание таблицы студентов
CREATE TABLE student (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    tel_number VARCHAR(20),
    speciality VARCHAR(255),
    code VARCHAR(50),
    faculty VARCHAR(255),
    form_education VARCHAR(50),
    paid_education VARCHAR(50),
    step_education VARCHAR(50),
    year_of_graduation VARCHAR(10),
    consolidation VARCHAR(255)
);

-- Создание таблицы распределенных студентов
CREATE TABLE distribution (
    id SERIAL PRIMARY KEY,
    student_id INT UNIQUE REFERENCES student(id) ON DELETE CASCADE,
    profiling VARCHAR(255),
    name_company VARCHAR(255),
    name_company_obl VARCHAR(255),
    name_company_gorod VARCHAR(255),
    name_company_rajon VARCHAR(255),
    based_name_company VARCHAR(255),
    other_organization VARCHAR(255),
    writed_request_of_distribution VARCHAR(255),
    writed_request_of_distribution_ip VARCHAR(255),
    target_agreement VARCHAR(255),
    olympiad VARCHAR(255),
    confirmation_arrival_enterprise VARCHAR(255),
    job_title VARCHAR(255),
    working VARCHAR(255),
    serves_army VARCHAR(255),
    on_maternity_leave VARCHAR(255),
    worked VARCHAR(255),
    date_letter VARCHAR(50),
    re_distributed VARCHAR(255),
    notes TEXT,
    period_compulsory_service VARCHAR(50),
    self_care VARCHAR(255),
    consolidation VARCHAR(255)
);

-- Создание таблицы перераспределенных студентов
CREATE TABLE reassignment (
    id SERIAL PRIMARY KEY,
    student_id INT UNIQUE REFERENCES student(id) ON DELETE CASCADE,
    profiling VARCHAR(255),
    name_old_company VARCHAR(255),
    name_new_company VARCHAR(255),
    job_title VARCHAR(255),
    notes TEXT,
    consolidation VARCHAR(255)
);

-- Создание таблицы нетрудоустроенных студентов
CREATE TABLE unemployed (
    id SERIAL PRIMARY KEY,
    student_id INT UNIQUE REFERENCES student(id) ON DELETE CASCADE,
    profiling VARCHAR(255),
    name_company VARCHAR(255),
    direction_returned VARCHAR(255),
    admission_to_higher_level VARCHAR(255),
    company_notified VARCHAR(255),
    data_notification VARCHAR(50),
    conscription_employment VARCHAR(255),
    serves_army VARCHAR(255),
    payment_of_tuition_fees VARCHAR(255),
    notes TEXT,
    consolidation VARCHAR(255)
);

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
