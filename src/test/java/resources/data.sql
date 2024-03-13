CREATE SCHEMA IF NOT EXISTS papel;

CREATE SEQUENCE papel.user_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE papel.post_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE papel.users (
    user_id BIGINT DEFAULT nextval('papel.user_sequence') PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE papel.posts (
    post_id BIGINT DEFAULT nextval('papel.post_sequence') PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES papel.users(user_id) ON DELETE CASCADE
);

ALTER TABLE papel.users
ADD COLUMN version INT DEFAULT 0 NOT NULL;
