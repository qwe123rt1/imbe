ALTER TABLE whatsapp_incoming_messages
    ADD COLUMN IF NOT EXISTS tenant_id BIGINT,
    ADD COLUMN IF NOT EXISTS message_source_id BIGINT;

ALTER TABLE incoming_leads
    ADD COLUMN IF NOT EXISTS tenant_id BIGINT;

ALTER TABLE whatsapp_incoming_messages
    DROP CONSTRAINT IF EXISTS uk_whatsapp_incoming_messages_whatsapp_message_id;

ALTER TABLE whatsapp_incoming_messages
    ADD CONSTRAINT fk_whatsapp_incoming_messages_tenant_id
        FOREIGN KEY (tenant_id) REFERENCES tenants (id),
    ADD CONSTRAINT fk_whatsapp_incoming_messages_message_source_id
        FOREIGN KEY (message_source_id) REFERENCES tenant_message_sources (id);

ALTER TABLE incoming_leads
    ADD CONSTRAINT fk_incoming_leads_tenant_id
        FOREIGN KEY (tenant_id) REFERENCES tenants (id);

CREATE INDEX IF NOT EXISTS idx_whatsapp_incoming_messages_tenant_id
    ON whatsapp_incoming_messages (tenant_id);

CREATE INDEX IF NOT EXISTS idx_whatsapp_incoming_messages_message_source_id
    ON whatsapp_incoming_messages (message_source_id);

CREATE UNIQUE INDEX IF NOT EXISTS uk_whatsapp_incoming_messages_tenant_message_id
    ON whatsapp_incoming_messages (tenant_id, whatsapp_message_id)
    WHERE whatsapp_message_id IS NOT NULL;

CREATE INDEX IF NOT EXISTS idx_incoming_leads_tenant_id
    ON incoming_leads (tenant_id);
