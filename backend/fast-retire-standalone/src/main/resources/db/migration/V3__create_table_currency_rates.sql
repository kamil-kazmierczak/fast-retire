CREATE TABLE ft_dev.currency_rates
(
    id              VARCHAR(255) NOT NULL,
    base_currency   VARCHAR(255),
    target_currency VARCHAR(255),
    date            date,
    amount          DECIMAL,
    CONSTRAINT pk_currency_rates PRIMARY KEY (id)
);