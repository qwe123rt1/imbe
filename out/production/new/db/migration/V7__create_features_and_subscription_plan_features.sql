CREATE TABLE features (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    feature_code VARCHAR(100) NOT NULL,
    feature_name VARCHAR(255) NOT NULL,
    feature_type VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    is_active BOOLEAN,
    CONSTRAINT uk_features_feature_code UNIQUE (feature_code)
);

CREATE TABLE subscription_plan_features (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    plan_id BIGINT NOT NULL,
    feature_id BIGINT NOT NULL,
    is_enabled BOOLEAN,
    feature_limit INTEGER,
    CONSTRAINT fk_subscription_plan_features_plan_id FOREIGN KEY (plan_id) REFERENCES subscription_plans (id),
    CONSTRAINT fk_subscription_plan_features_feature_id FOREIGN KEY (feature_id) REFERENCES features (id),
    CONSTRAINT uk_subscription_plan_features_plan_feature UNIQUE (plan_id, feature_id)
);

CREATE INDEX idx_features_is_active ON features (is_active);
CREATE INDEX idx_subscription_plan_features_plan_id ON subscription_plan_features (plan_id);
CREATE INDEX idx_subscription_plan_features_feature_id ON subscription_plan_features (feature_id);
CREATE INDEX idx_subscription_plan_features_is_enabled ON subscription_plan_features (is_enabled);
