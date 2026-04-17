CREATE TABLE subscription_usage (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    subscription_id BIGINT NOT NULL,
    usage_month VARCHAR(7) NOT NULL,
    lead_trigger_count INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT fk_subscription_usage_subscription_id FOREIGN KEY (subscription_id) REFERENCES subscriptions (id),
    CONSTRAINT uk_subscription_usage_subscription_month UNIQUE (subscription_id, usage_month)
);

CREATE INDEX idx_subscription_usage_subscription_id ON subscription_usage (subscription_id);
CREATE INDEX idx_subscription_usage_usage_month ON subscription_usage (usage_month);
