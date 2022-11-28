DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users(
    user_No BIGINT  AUTO_INCREMENT,
    user_Id  VARCHAR(30) NOT NULL ,
    user_Pwd VARCHAR(100) NOT NULL,
    user_Name VARCHAR(30) NOT NULL,
    user_Point  INT DEFAULT 0,
    PRIMARY KEY(user_No)
);
CREATE UNIQUE INDEX IDX_USER_USER_NO ON users ( user_No );
CREATE UNIQUE INDEX IDX_USER_USER_ID ON users ( user_Id );

DROP TABLE IF EXISTS article CASCADE;
CREATE TABLE article (
    article_No BIGINT AUTO_INCREMENT,
    article_Title VARCHAR(50) NOT NULL,
    article_Contents VARCHAR(100) NOT NULL,
    user_No BIGINT NOT NULL,
    PRIMARY KEY (article_No),
    FOREIGN KEY (user_No) REFERENCES users(user_No)
);
CREATE INDEX IDX_ARTICLE_USER_NO ON article ( user_No );

DROP TABLE IF EXISTS comment CASCADE;
CREATE TABLE comment (
    comment_No BIGINT AUTO_INCREMENT,
    comment_Contents VARCHAR(100),
    user_No BIGINT NOT NULL,
    article_No BIGINT NOT NULL,
    PRIMARY KEY (comment_No),
    FOREIGN KEY (user_No) REFERENCES users(user_No),
    FOREIGN KEY (article_No) REFERENCES article(article_No)
);
CREATE INDEX IDX_COMMENT_USER_NO ON comment ( user_No );
CREATE INDEX IDX_COMMENT_ARTICLE_NO ON comment ( article_No );