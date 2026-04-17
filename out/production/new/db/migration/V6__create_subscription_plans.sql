CREATE TABLE subscription_plans (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    plan_code VARCHAR(100) NOT NULL,
    plan_name VARCHAR(255) NOT NULL,
    monthly_price NUMERIC(18,2) NOT NULL,
    lead_limit INTEGER,
    match_limit INTEGER,
    user_limit INTEGER,
    is_active BOOLEAN,
    CONSTRAINT uk_subscription_plans_plan_code UNIQUE (plan_code)
);

CREATE INDEX idx_subscription_plans_is_active ON subscription_plans (is_active);
