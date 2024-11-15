DROP
DATABASE IF EXISTS `blog`;
CREATE
DATABASE IF NOT EXISTS `blog`
USE `blog`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`
(
    `id`
    bigint
(
    20
) NOT NULL AUTO_INCREMENT,
    `firstName` varchar
(
    50
) DEFAULT NULL,
    `middleName` varchar
(
    50
) DEFAULT NULL,
    `lastName` varchar
(
    50
) DEFAULT NULL,
    `mobile` varchar
(
    15
) DEFAULT NULL,
    `email` varchar
(
    50
) DEFAULT NULL,
    `passwordHash` varchar
(
    32
) NOT NULL,
    `registeredAt` datetime NOT NULL,
    `lastLogin` datetime DEFAULT NULL,
    `intro` tinytext DEFAULT NULL,
    `profile` text DEFAULT NULL,
    PRIMARY KEY
(
    `id`
),
    UNIQUE KEY `uq_mobile`
(
    `mobile`
),
    UNIQUE KEY `uq_email`
(
    `email`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE =latin1_swedish_ci;


DROP TABLE IF EXISTS `post`;
CREATE TABLE IF NOT EXISTS `post`
(
    `id`
    bigint
(
    20
) NOT NULL AUTO_INCREMENT,
    `authorId` bigint
(
    20
) NOT NULL,
    `parentId` bigint
(
    20
) DEFAULT NULL,
    `title` varchar
(
    75
) NOT NULL,
    `metaTitle` varchar
(
    100
) DEFAULT NULL,
    `summary` tinytext DEFAULT NULL,
    `published` tinyint
(
    1
) NOT NULL DEFAULT 0,
    `createdAt` datetime NOT NULL,
    `updatedAt` datetime DEFAULT NULL,
    `publishedAt` datetime DEFAULT NULL,
    `content` text DEFAULT NULL,
    PRIMARY KEY
(
    `id`
),
    KEY `idx_post_user`
(
    `authorId`
),
    KEY `idx_post_parent`
(
    `parentId`
),
    CONSTRAINT `fk_post_parent` FOREIGN KEY
(
    `parentId`
) REFERENCES `post`
(
    `id`
) ON DELETE NO ACTION
  ON UPDATE NO ACTION,
    CONSTRAINT `fk_post_user` FOREIGN KEY
(
    `authorId`
) REFERENCES `user`
(
    `id`
)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE =latin1_swedish_ci;


DROP TABLE IF EXISTS `post_comment`;
CREATE TABLE IF NOT EXISTS `post_comment`
(
    `id`
    bigint
(
    20
) NOT NULL AUTO_INCREMENT,
    `postId` bigint
(
    20
) NOT NULL,
    `parentId` bigint
(
    20
) DEFAULT NULL,
    `title` varchar
(
    100
) NOT NULL,
    `published` tinyint
(
    1
) NOT NULL DEFAULT 0,
    `createdAt` datetime NOT NULL,
    `publishedAt` datetime DEFAULT NULL,
    `content` text DEFAULT NULL,
    PRIMARY KEY
(
    `id`
),
    KEY `idx_comment_post`
(
    `postId`
),
    KEY `idx_comment_parent`
(
    `parentId`
),
    CONSTRAINT `fk_comment_parent` FOREIGN KEY
(
    `parentId`
) REFERENCES `post_comment`
(
    `id`
) ON DELETE NO ACTION
  ON UPDATE NO ACTION,
    CONSTRAINT `fk_comment_post` FOREIGN KEY
(
    `postId`
) REFERENCES `post`
(
    `id`
)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE =latin1_swedish_ci;

INSERT INTO `user` (`first_name`, `middle_name`, `last_name`, `mobile`, `email`, `password_hash`, `registered_at`,
                    `last_login`, `intro`, `profile`)
VALUES ('John', 'A', 'Doe', '1234567890', 'john.doe@example.com', MD5('password1'), '2024-11-01 10:00:00',
        '2024-11-12 08:00:00', 'Hi, I am John.', 'Profile of John Doe'),
       ('Jane', 'B', 'Smith', '2345678901', 'jane.smith@example.com', MD5('password2'), '2024-11-02 11:30:00',
        '2024-11-13 09:00:00', 'Hello, I am Jane.', 'Profile of Jane Smith'),
       ('Alice', 'C', 'Johnson', '3456789012', 'alice.johnson@example.com', MD5('password3'), '2024-11-03 12:45:00',
        '2024-11-14 10:00:00', 'Alice here!', 'Profile of Alice Johnson'),
       ('Bob', 'D', 'Brown', '4567890123', 'bob.brown@example.com', MD5('password4'), '2024-11-04 09:20:00',
        '2024-11-12 11:00:00', 'Bob\'s intro', 'Profile of Bob Brown'),
('Charlie', 'E', 'Davis', '5678901234', 'charlie.davis@example.com', MD5('password5'), '2024-11-05 15:00:00', '2024-11-13 12:00:00', 'This is Charlie.', 'Profile of Charlie Davis');



INSERT INTO `post` (`author_id`, `parent_id`, `title`, `meta_title`, `summary`, `published`, `created_at`, `updated_at`, `published_at`, `content`) VALUES
(1, NULL, 'First Post', 'Intro to Blogging', 'This is the first post summary.', 1, '2024-11-01 10:30:00', '2024-11-01 11:00:00', '2024-11-01 11:00:00', 'Content of the first post.'),
(2, NULL, 'Second Post', 'About Programming', 'This is the second post summary.', 1, '2024-11-02 11:45:00', '2024-11-02 12:15:00', '2024-11-02 12:15:00', 'Content of the second post.'),
(3, NULL, 'Third Post', 'Guide to Databases', 'This is the third post summary.', 1, '2024-11-03 13:00:00', '2024-11-03 13:30:00', '2024-11-03 13:30:00', 'Content of the third post.'),
(4, 1, 'First Post - Follow Up', 'Deep Dive into Blogging', 'This is a follow-up to the first post.', 0, '2024-11-04 10:00:00', NULL, NULL, 'Content of the follow-up post to the first post.'),
(5, NULL, 'Fifth Post', 'Introduction to SQL', 'This is the fifth post summary.', 1, '2024-11-05 14:00:00', '2024-11-05 15:00:00', '2024-11-05 15:00:00', 'Content of the fifth post.');

INSERT INTO `post_comment` (`post_id`, `user_id`, `parent_id`, `title`, `published`, `created_at`, `published_at`, `content`)
VALUESpost_comment
(1, 1, NULL, 'Great post!', 1, '2024-11-01 12:00:00', '2024-11-01 12:30:00', 'This is a great post, thanks for sharing.'),
(2, 2, NULL, 'Informative Post', 1, '2024-11-02 13:00:00', '2024-11-02 13:30:00', 'Very informative, I learned a lot.'),
(1, 3, 1, 'Reply to Great post!', 1, '2024-11-01 12:45:00', '2024-11-01 13:00:00', 'I agree, it\'s a great post.'),
       (3, 4, NULL, 'Question about Databases', 0, '2024-11-03 14:00:00', NULL,
        'I have a question about the database section.'),
       (5, 5, NULL, 'Useful Information', 1, '2024-11-05 16:00:00', '2024-11-05 16:30:00',
        'Thanks, this post was really useful.');

