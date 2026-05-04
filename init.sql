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
    id                     serial4      NOT NULL PRIMARY KEY,
    account_id             int4,
    beneficiary_account_id int4,
    "type"                 varchar(255) NOT NULL,
    amount                 numeric,
    "created"              timestamp    NOT NULL,
    modified               timestamp NULL
);

ALTER TABLE transfers
    ADD CONSTRAINT account_id_fk FOREIGN KEY (account_id) REFERENCES accounts (id);
ALTER TABLE transfers
    ADD CONSTRAINT beneficiary_account_id_fk FOREIGN KEY (beneficiary_account_id) REFERENCES accounts (id);

INSERT INTO public.accounts (name, iban, status, available_amount, created, modified)
VALUES ('Petra', 'BG12STSA93000012245611', true, 100, '2026-05-01 19:30:08.323', '2026-05-03 19:40:16.552'),
       ('Maria', 'BG19STSA93000012245611', false, 20, '2026-05-03 13:41:35.200', '2026-05-03 19:40:16.553');
