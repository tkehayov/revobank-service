CREATE TABLE accounts
(
    id               serial4            NOT NULL PRIMARY KEY,
    name             varchar(255)       NOT NULL,
    iban             varchar(34) UNIQUE NOT NULL,
    "status"         bool               NOT NULL,
    available_amount numeric,
    "created"        timestamp          NOT NULL,
    modified         timestamp NULL
);

CREATE TABLE transfers
(
    id                     serial4   NOT NULL PRIMARY KEY,
    account_id             int4,
    beneficiary_account_id int4,
    "type"                 varchar(255)   NOT NULL,
    amount                 numeric,
    "created"              timestamp NOT NULL,
    modified               timestamp NULL
);

ALTER TABLE transfers
    ADD CONSTRAINT account_id_fk FOREIGN KEY (account_id) REFERENCES accounts (id);
ALTER TABLE transfers
    ADD CONSTRAINT beneficiary_account_id_fk FOREIGN KEY (beneficiary_account_id) REFERENCES accounts (id);