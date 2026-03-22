CREATE TABLE ft_dev.trades
(
    id         VARCHAR(255) NOT NULL,
    amount     DECIMAL(20, 8),
    trade_type VARCHAR(255),
    date       date,
    user_id    VARCHAR(255),
    asset_name VARCHAR(255),
    asset_type VARCHAR(255),
    CONSTRAINT pk_trades PRIMARY KEY (id)
);

ALTER TABLE ft_dev.trades
    ADD CONSTRAINT FK_TRADES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);