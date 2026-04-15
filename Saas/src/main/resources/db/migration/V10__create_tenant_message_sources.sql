CREATE TABLE tenant_message_sources (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    tenant_id BIGINT NOT NULL,
    source_type VARCHAR(100) NOT NULL,
    source_label VARCHAR(255),
    phone_number VARCHAR(50),
    permission_status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_tenant_message_sources_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

CREATE INDEX idx_tenant_message_sources_tenant_id ON tenant_message_sources (tenant_id);
CREATE INDEX idx_tenant_message_sources_phone_number ON tenant_message_sources (phone_number);
CREATE INDEX idx_tenant_message_sources_permission_status ON tenant_message_sources (permission_status);
