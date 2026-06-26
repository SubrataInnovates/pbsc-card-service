CREATE TABLE cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    card_number VARCHAR(16) NOT NULL,

    card_holder_name VARCHAR(100) NOT NULL,

    expiry_month INT NOT NULL,

    expiry_year INT NOT NULL,

    cvv VARCHAR(3) NOT NULL,

    balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,

    currency VARCHAR(3) NOT NULL,

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT uk_cards_card_number UNIQUE (card_number)
);