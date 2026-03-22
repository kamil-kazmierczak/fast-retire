CREATE TABLE ft_dev.portfolio_eod
(
    id             VARCHAR(255) NOT NULL,
    date           date,
    amount         DECIMAL,
    asset_name     VARCHAR(255),
    asset_type     VARCHAR(255),
    computed_value DECIMAL,
    currency       VARCHAR(255),
    user_id        VARCHAR(255),
    CONSTRAINT pk_portfolio_eod PRIMARY KEY (id)
);

ALTER TABLE ft_dev.portfolio_eod
    ADD CONSTRAINT FK_PORTFOLIO_EOD_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);