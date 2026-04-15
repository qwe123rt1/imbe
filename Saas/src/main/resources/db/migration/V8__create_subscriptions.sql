CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    tenant_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    billing_cycle VARCHAR(50),
    CONSTRAINT fk_subscriptions_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants (id),
    CONSTRAINT fk_subscriptions_plan_id FOREIGN KEY (plan_id) REFERENCES subscription_plans (id)
);

CREATE INDEX idx_subscriptions_tenant_id ON subscriptions (tenant_id);
CREATE INDEX idx_subscriptions_plan_id ON subscriptions (plan_id);
CREATE INDEX idx_subscriptions_status ON subscriptions (status);
CREATE INDEX idx_subscriptions_end_date ON subscriptions (end_date);
