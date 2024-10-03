CREATE TABLE public.exchange_rates
(
    id            SERIAL PRIMARY KEY,
    currency_code VARCHAR(3)     NOT NULL,
    rate          NUMERIC(10, 4) NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create sequence current_rate_seq;
