CREATE TABLE ft_dev.users
(
    id                VARCHAR(255) NOT NULL,
    login             VARCHAR(255),
    email             VARCHAR(255),
    registration_date date,
    CONSTRAINT pk_users PRIMARY KEY (id)
);