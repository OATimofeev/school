SELECT s.name,
       s.age,
       f.name as faculty
FROM student s
         FULL JOIN faculty f
                   ON s.FACULTY_ID = f.ID;

SELECT s.*
FROM student s
         JOIN avatar a ON a.student_id = s.id;