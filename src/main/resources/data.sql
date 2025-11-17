INSERT INTO categories (name, created_at, updated_at)
VALUES ('Programming', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users (name, email, role, created_at, updated_at)
VALUES
    ('Alice Teacher', 'alice.teacher@example.com', 'TEACHER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Bob Student', 'bob.student@example.com', 'STUDENT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO courses (title, description, category_id, teacher_id, created_at, updated_at)
VALUES (
        'Java Basics',
        'Introductory Java course',
        (SELECT id FROM categories WHERE name = 'Programming'),
        (SELECT id FROM users WHERE email = 'alice.teacher@example.com'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO modules (title, order_index, course_id, created_at, updated_at)
VALUES (
        'Getting Started',
        1,
        (SELECT id FROM courses WHERE title = 'Java Basics'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO lessons (title, content, video_url, module_id, created_at, updated_at)
VALUES (
        'Hello World',
        'Welcome to Java Basics!',
        'https://example.com/hello-world',
        (SELECT id FROM modules WHERE title = 'Getting Started'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO quizzes (title, module_id, created_at, updated_at)
VALUES (
        'Module 1 Quiz',
        (SELECT id FROM modules WHERE title = 'Getting Started'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO questions (text, type, quiz_id, created_at, updated_at)
VALUES (
        'What does JVM stand for?',
        'SINGLE_CHOICE',
        (SELECT id FROM quizzes WHERE title = 'Module 1 Quiz'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO answer_options (text, is_correct, question_id, created_at, updated_at)
VALUES
    (
        'Java Virtual Machine',
        TRUE,
        (SELECT id FROM questions WHERE text = 'What does JVM stand for?'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Java Valuable Mechanism',
        FALSE,
        (SELECT id FROM questions WHERE text = 'What does JVM stand for?'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO enrollments (user_id, course_id, status, enroll_date, created_at, updated_at)
VALUES (
        (SELECT id FROM users WHERE email = 'bob.student@example.com'),
        (SELECT id FROM courses WHERE title = 'Java Basics'),
        'ACTIVE',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO course_reviews (course_id, student_id, rating, comment, created_at, updated_at)
VALUES (
        (SELECT id FROM courses WHERE title = 'Java Basics'),
        (SELECT id FROM users WHERE email = 'bob.student@example.com'),
        9,
        'Great introduction!',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

