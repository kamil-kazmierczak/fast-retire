CREATE TABLE ft_dev.history
(
    id             VARCHAR(255) NOT NULL,
    asset          VARCHAR(255),
    register_date  date,
    price_value    DECIMAL,
    price_currency VARCHAR(255),
    CONSTRAINT pk_history PRIMARY KEY (id)
);