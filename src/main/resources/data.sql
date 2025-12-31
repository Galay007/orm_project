-- 1. Пользователи (users)
INSERT INTO users ( first_name, last_name, email, role) VALUES
  ( 'Анна', 'Смирнова', 'anna@example.com', 'STUDENT'),
  ( 'Иван', 'Петров', 'ivan@example.com', 'TEACHER'),
  ( 'Елена', 'Кузнецова', 'elena@example.com', 'ADMIN');

-- 2. Профили (profiles)
INSERT INTO profiles ( user_id, bio, avatar_url) VALUES
  (1, 'Студент, учусь в МИФИ', 'https://example.com/avatars/anna.jpg'),
  (2, 'Преподаю Java в университете', 'https://example.com/avatars/ivan.jpg');

-- 3. Категории (categories)
INSERT INTO categories ( name) VALUES
  ( 'Программирование'),
  ( 'Математика');

-- 4. Теги (tags)
INSERT INTO tags ( name) VALUES
  ( 'IT'),
  ( 'Java'),
  ( 'Spring'),
  ( 'Алгоритмы');

-- 5. Курсы (courses)
INSERT INTO courses ( title, description, duration, start_date, teacher_id, category_id) VALUES
  ( 'Java для начинающих', 'Базовый курс по Java SE', '3 месяца', '2025-09-01', 2, 1);

-- 6. Связь курс-теги (course_tags — many-to-many)
INSERT INTO course_tags (course_id, tag_id) VALUES
  (1, 1), (1, 2), (1, 3);

-- 7. Модули (modules)
INSERT INTO modules ( title, description, course_id) VALUES
  ('Основы Spring Boot', 'Введение в Spring Boot, DI, REST', 1);

-- 8. Уроки (lessons)
INSERT INTO lessons ( title, content, module_id) VALUES
  ( 'Введение в Spring Data JPA', 'Разбор аннотаций @Entity, @Repository, CrudRepository...', 1);

-- 9. Записи на курсы (enrollments)
INSERT INTO enrollments ( student_id, course_id, enroll_date) VALUES
  ( 1, 1, '2025-12-30');

-- 10. Задания (assignments)
INSERT INTO assignments ( lesson_id, description) VALUES
  ( 1, 'Создать CourseController с CRUD-операциями');

-- 11. Тесты (test_cases)
INSERT INTO tests ( title, module_id) VALUES
  ( 'Тест по Spring Boot', 1);

-- 12. Вопросы (questions)
INSERT INTO questions ( test_case_id, text) VALUES
  ( 1, 'TEST TEXT');

-- 13. Варианты ответов (answer_options)
INSERT INTO answer_options ( question_id, text) VALUES
  ( 1, '@RestController'),
  ( 1, '@Service'),
  ( 1, '@Component');

-- 14. Результаты прохождения тестов (test_submissions)
INSERT INTO test_submissions ( test_case_id, student_id, score, taken_at) VALUES
  ( 1, 1, 50, '2025-12-30');

-- 15. Решения заданий (submissions)
INSERT INTO submissions ( assignment_id, student_id, content, submitted_at) VALUES
  ( 1, 1, 'public class Solution { public int add(int a, int b) { return a + b; } }', '2025-12-30');

-- 16. Отзывы (reviews)
INSERT INTO reviews ( course_id, student_id, rating, created_at) VALUES
  ( 1, 1, 5, '2025-12-31');