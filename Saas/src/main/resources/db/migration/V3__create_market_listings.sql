CREATE TABLE market_listings (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    company_id BIGINT NOT NULL,
    product_id BIGINT,
    side VARCHAR(50),
    min_quantity NUMERIC(18,2),
    max_quantity NUMERIC(18,2),
    unit_text VARCHAR(50),
    rate_hint NUMERIC(18,2),
    city VARCHAR(100),
    state VARCHAR(100),
    is_active BOOLEAN,
    remarks VARCHAR(255),
    CONSTRAINT fk_market_listings_company_id FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT fk_market_listings_product_id FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE INDEX idx_market_listings_product_id ON market_listings (product_id);
CREATE INDEX idx_market_listings_side ON market_listings (side);
CREATE INDEX idx_market_listings_is_active ON market_listings (is_active);
CREATE INDEX idx_market_listings_city_state ON market_listings (city, state);
