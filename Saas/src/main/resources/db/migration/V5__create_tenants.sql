CREATE TABLE tenants (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    business_name VARCHAR(255) NOT NULL,
    owner_name VARCHAR(255),
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    match_limit INTEGER,
    user_limit INTEGER,
    CONSTRAINT uk_tenants_phone UNIQUE (phone),
    CONSTRAINT uk_tenants_email UNIQUE (email)
);

CREATE INDEX idx_tenants_status ON tenants (status);
