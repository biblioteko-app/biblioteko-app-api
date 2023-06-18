--usuarios
INSERT INTO USERS(ID, EMAIL, NAME, PASSWORD, ROLE) VALUES ('c9bf1a69-062e-41de-8c35-21343d660fae', 'prof@gmail.com', 'Prof. Profeta', '$2a$10$1vfIh3eRrjtZL.8oensmkO6FBfUhVqJIkpDkMxCRdS1d3eHQzcGWO', 'PROFESSOR'); -- 123

INSERT INTO USERS(ID, EMAIL, NAME, PASSWORD, ROLE) VALUES ('2a52adb1-615d-4c8b-8ab1-520a9828bce5', 'alunoaluado@gmail.com', 'Aluno Aluado', '$2a$10$.dNFjex2xKXiEDpDdwd.beidsAuUR1GLcrKA4rAfGQ7CD7HGdrnri', 'ALUNO'); -- abc

INSERT INTO USERS(ID, EMAIL, NAME, PASSWORD, ROLE) VALUES ('dfcefbff-8b80-433b-8638-950c694a5875', 'aluadoaluno2@gmail.com', 'Aluno Aluado II', '$2a$10$p/rQnswKpYXzNEPfNka95.aK5vPM6DLeOCuRHKvQLa0SHJNK6pPr2', 'ALUNO'); -- 000

INSERT INTO USERS(ID, EMAIL, NAME, PASSWORD, ROLE) VALUES ('d107c54e-fd06-4b49-8a96-f2d21334155b', 'aluadoaluno3@gmail.com', 'Aluno Aluado III', '$2a$10$a/poizAYjeavKyLYtOfJDeEmpYFcZtzgtoPIKuHekev3/wAilv8bG', 'ALUNO'); -- 111

INSERT INTO USERS(ID, EMAIL, NAME, PASSWORD, ROLE) VALUES ('9f6681ae-be52-4f98-82a7-18b2300dbcac', 'aluadoaluno4@gmail.com', 'Aluno Aluado IV', '$2a$10$8YmKNIMrkYBP2z66hQweeebC8TkYo3Bx17GDCLCsQnpUVeoPuBA12	', 'ALUNO'); -- 222

--turma
INSERT INTO STUDENT_CLASS(ID, CLASS_YEAR, NAME, PHOTO, SCHOOL_SUBJECT, OWNER_ID) VALUES ('9e0eaacb-e486-49ff-b5f4-72405ed4d792', 2023, 'Turma Turmalina', '', 'Topologia Diferencial', 'c9bf1a69-062e-41de-8c35-21343d660fae');

INSERT INTO CLASS_STUDENTS(STUDENT_CLASS_ID, USER_ID) VALUES ('9e0eaacb-e486-49ff-b5f4-72405ed4d792', '2a52adb1-615d-4c8b-8ab1-520a9828bce5');

INSERT INTO CLASS_STUDENTS(STUDENT_CLASS_ID, USER_ID) VALUES ('9e0eaacb-e486-49ff-b5f4-72405ed4d792', 'dfcefbff-8b80-433b-8638-950c694a5875');

INSERT INTO CLASS_STUDENTS(STUDENT_CLASS_ID, USER_ID) VALUES ('9e0eaacb-e486-49ff-b5f4-72405ed4d792', 'd107c54e-fd06-4b49-8a96-f2d21334155b');

--outra turma do mesmo prof
INSERT INTO STUDENT_CLASS(ID, CLASS_YEAR, NAME, PHOTO, SCHOOL_SUBJECT, OWNER_ID) VALUES ('7faa7b51-0785-455a-8e59-21ef3d7b8cb7', 2023, 'Turma do Prof. Profeta', '', 'Teoria Algébrica dos Números', 'c9bf1a69-062e-41de-8c35-21343d660fae');

INSERT INTO CLASS_STUDENTS(STUDENT_CLASS_ID, USER_ID) VALUES ('7faa7b51-0785-455a-8e59-21ef3d7b8cb7', '9f6681ae-be52-4f98-82a7-18b2300dbcac');

INSERT INTO CLASS_STUDENTS(STUDENT_CLASS_ID, USER_ID) VALUES ('7faa7b51-0785-455a-8e59-21ef3d7b8cb7', '2a52adb1-615d-4c8b-8ab1-520a9828bce5');


--livros
INSERT INTO BOOKS (ID, ACCESS_LINK, AUTHOR, EDITION, GENRE, NUM_REVIEW, PAGES, PHOTO, RATING, SYNOPSIS, TITLE, OWNER_ID) VALUES ('203f4eb0-9837-4041-b521-bbc382210e85', 'https://drive.google.com/file/d/1YN849mYvQ-SBVwZ_CbkpcBgbpX-voK4c/view?usp=drive_link', 'Rohit Ghey', 2, 'Filosofia', 0, 404, '', 4.0, 'A existência de uma bala de prata é uma questão que intriga filósofos desde a Antiguidade. Neste ensaio, o professor Rohit Ghey emprega o mais preciso silogismo Aristotélico e responde de maneira inconteste: não há bala de prata. Leia e descubra por quê.', 'A Bala de Prata', 'c9bf1a69-062e-41de-8c35-21343d660fae');

INSERT INTO BOOKS (ID, ACCESS_LINK, AUTHOR, EDITION, GENRE, NUM_REVIEW, PAGES, PHOTO, RATING, SYNOPSIS, TITLE, OWNER_ID) VALUES ('7b796335-7b7e-48da-8eb7-d41239dd55f8', 'https://drive.google.com/file/d/1YN849mYvQ-SBVwZ_CbkpcBgbpX-voK4c/view', 'Rohit Ghey', 4, 'Educacional', 0, 403, '', 4.0, 'Este livro, escrito pelo especialista Rohit Ghey, é seu magnum-opus. Descubra como mitigar riscos de maneira sistemática.', 'Mitigando Riscos', 'c9bf1a69-062e-41de-8c35-21343d660fae');

INSERT INTO BOOKS (ID, ACCESS_LINK, AUTHOR, EDITION, GENRE, NUM_REVIEW, PAGES, PHOTO, RATING, SYNOPSIS, TITLE, OWNER_ID) VALUES ('f8499f7e-2af0-411d-9f56-b0352ed28355', 'https://drive.google.com/file/d/1YN849mYvQ-SBVwZ_CbkpcBgbpX-voK4c/view', 'Rohit Ghey', 4, 'Suspense', 0, 403, '', 4.0, 'Uma obra-prima do suspense psicológico.', 'Onde está a ata?', 'c9bf1a69-062e-41de-8c35-21343d660fae');

INSERT INTO BOOKS (ID, ACCESS_LINK, AUTHOR, EDITION, GENRE, NUM_REVIEW, PAGES, PHOTO, RATING, SYNOPSIS, TITLE, OWNER_ID) VALUES ('67260c15-dc6d-4d66-94da-1056c4b4eeb5', 'https://drive.google.com/file/d/1YN849mYvQ-SBVwZ_CbkpcBgbpX-voK4c/view', 'Rohit Ghey', 4, 'Suspense', 0, 403, '', 4.0, 'Uma obra-prima do suspense psicológico.', 'Onde está a ata?', 'c9bf1a69-062e-41de-8c35-21343d660fae');

INSERT INTO BOOKS (ID, ACCESS_LINK, AUTHOR, EDITION, GENRE, NUM_REVIEW, PAGES, PHOTO, RATING, SYNOPSIS, TITLE, OWNER_ID) VALUES ('34de3367-5090-4e0c-be41-edf291de229c', 'https://drive.google.com/file/d/1YN849mYvQ-SBVwZ_CbkpcBgbpX-voK4c/view?usp=drive_link', 'Rohit Ghey', 2, 'Filosofia', 0, 404, '', 4.0, 'A existência de uma bala de prata é uma questão que intriga filósofos desde a Antiguidade. Neste ensaio, o professor Rohit Ghey emprega o mais preciso silogismo Aristotélico e responde de maneira inconteste: não há bala de prata. Leia e descubra por quê.', 'A Bala de Prata', 'c9bf1a69-062e-41de-8c35-21343d660fae');

INSERT INTO BOOKS (ID, ACCESS_LINK, AUTHOR, EDITION, GENRE, NUM_REVIEW, PAGES, PHOTO, RATING, SYNOPSIS, TITLE, OWNER_ID) VALUES ('50f753fa-71b6-4fad-af39-e11503764b9c', 'https://drive.google.com/file/d/1YN849mYvQ-SBVwZ_CbkpcBgbpX-voK4c/view', 'Rohit Ghey', 4, 'Educacional', 0, 403, '', 4.0, 'Este livro, escrito pelo especialista Rohit Ghey, é seu magnum-opus. Descubra como mitigar riscos de maneira sistemática.', 'Mitigando Riscos', 'c9bf1a69-062e-41de-8c35-21343d660fae');

INSERT INTO BOOKS (ID, ACCESS_LINK, AUTHOR, EDITION, GENRE, NUM_REVIEW, PAGES, PHOTO, RATING, SYNOPSIS, TITLE, OWNER_ID) VALUES ('ad01c6d5-03d1-4c82-96e3-b41b5d662a9d', 'https://drive.google.com/file/d/1YN849mYvQ-SBVwZ_CbkpcBgbpX-voK4c/view', 'Rohit Ghey', 4, 'Aventura Fantástica', 0, 403, '', 4.0, 'Uma obra-prima.', 'A saga da Ata', 'c9bf1a69-062e-41de-8c35-21343d660fae');

--sugerindo livros a uma turma
INSERT INTO CLASS_SUGGESTED_BOOKS(STUDENT_CLASS_ID, BOOK_ID) VALUES('7faa7b51-0785-455a-8e59-21ef3d7b8cb7', '203f4eb0-9837-4041-b521-bbc382210e85');

INSERT INTO CLASS_SUGGESTED_BOOKS(STUDENT_CLASS_ID, BOOK_ID) VALUES('7faa7b51-0785-455a-8e59-21ef3d7b8cb7', 'ad01c6d5-03d1-4c82-96e3-b41b5d662a9d');

INSERT INTO CLASS_SUGGESTED_BOOKS(STUDENT_CLASS_ID, BOOK_ID) VALUES('7faa7b51-0785-455a-8e59-21ef3d7b8cb7', 'f8499f7e-2af0-411d-9f56-b0352ed28355');

INSERT INTO CLASS_SUGGESTED_BOOKS(STUDENT_CLASS_ID, BOOK_ID) VALUES('7faa7b51-0785-455a-8e59-21ef3d7b8cb7', '67260c15-dc6d-4d66-94da-1056c4b4eeb5');

--sugerindo livros a outra turma

INSERT INTO CLASS_SUGGESTED_BOOKS(STUDENT_CLASS_ID, BOOK_ID) VALUES('9e0eaacb-e486-49ff-b5f4-72405ed4d792', '203f4eb0-9837-4041-b521-bbc382210e85');

INSERT INTO CLASS_SUGGESTED_BOOKS(STUDENT_CLASS_ID, BOOK_ID) VALUES('9e0eaacb-e486-49ff-b5f4-72405ed4d792', '34de3367-5090-4e0c-be41-edf291de229c');

INSERT INTO CLASS_SUGGESTED_BOOKS(STUDENT_CLASS_ID, BOOK_ID) VALUES('9e0eaacb-e486-49ff-b5f4-72405ed4d792', 'f8499f7e-2af0-411d-9f56-b0352ed28355');

INSERT INTO CLASS_SUGGESTED_BOOKS(STUDENT_CLASS_ID, BOOK_ID) VALUES('9e0eaacb-e486-49ff-b5f4-72405ed4d792', '67260c15-dc6d-4d66-94da-1056c4b4eeb5');

--criando leituras

INSERT INTO READ(ID, READ_PAGES, BOOK_ID, USER_ID) VALUES ('0a2dca7d-b9e7-461b-95e0-6cc56425b1d6', 100, '203f4eb0-9837-4041-b521-bbc382210e85', '2a52adb1-615d-4c8b-8ab1-520a9828bce5');

INSERT INTO READ(ID, READ_PAGES, BOOK_ID, USER_ID) VALUES ('85918fb7-a524-456d-b164-e9ff6922ba0a', 0, '34de3367-5090-4e0c-be41-edf291de229c', '2a52adb1-615d-4c8b-8ab1-520a9828bce5');

INSERT INTO READ(ID, READ_PAGES, BOOK_ID, USER_ID) VALUES ('1a9a4f8f-8c0b-4e1a-901e-2a284d3a70da', 10, '67260c15-dc6d-4d66-94da-1056c4b4eeb5', 'dfcefbff-8b80-433b-8638-950c694a5875');

--favoritando livros

INSERT INTO USER_STARRED_BOOKS(USER_ID, BOOK_ID) VALUES('2a52adb1-615d-4c8b-8ab1-520a9828bce5', '203f4eb0-9837-4041-b521-bbc382210e85');

INSERT INTO USER_STARRED_BOOKS(USER_ID, BOOK_ID) VALUES('dfcefbff-8b80-433b-8638-950c694a5875', '67260c15-dc6d-4d66-94da-1056c4b4eeb5');
