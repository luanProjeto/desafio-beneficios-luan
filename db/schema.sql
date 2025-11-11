-- Schema base
CREATE TABLE IF NOT EXISTS beneficio (
    id BIGSERIAL PRIMARY KEY,
    titular VARCHAR(120) NOT NULL,
    saldo NUMERIC(18,2) NOT NULL DEFAULT 0,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS transferencia (
    id BIGSERIAL PRIMARY KEY,
    origem_id BIGINT NOT NULL REFERENCES beneficio(id),
    destino_id BIGINT NOT NULL REFERENCES beneficio(id),
    valor NUMERIC(18,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
