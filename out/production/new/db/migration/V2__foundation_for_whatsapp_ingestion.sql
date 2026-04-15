CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    full_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    password_hash VARCHAR(255),
    is_active BOOLEAN,
    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE companies (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    company_name VARCHAR(255),
    short_name VARCHAR(255),
    company_type VARCHAR(100),
    contact_person VARCHAR(255),
    phone VARCHAR(50),
    email VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    is_active BOOLEAN,
    CONSTRAINT uk_companies_company_name UNIQUE (company_name)
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    product_name VARCHAR(255),
    product_code VARCHAR(100),
    category_name VARCHAR(255),
    base_unit VARCHAR(50),
    description VARCHAR(255),
    is_active BOOLEAN,
    CONSTRAINT uk_products_product_code UNIQUE (product_code)
);

CREATE TABLE lead_sources (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    source_name VARCHAR(255),
    source_code VARCHAR(100),
    is_active BOOLEAN,
    CONSTRAINT uk_lead_sources_source_code UNIQUE (source_code)
);

CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    department_name VARCHAR(255),
    department_code VARCHAR(100),
    display_order INTEGER,
    is_active BOOLEAN,
    CONSTRAINT uk_departments_department_code UNIQUE (department_code)
);

CREATE TABLE verticals (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    vertical_name VARCHAR(255),
    vertical_code VARCHAR(100),
    display_order INTEGER,
    is_active BOOLEAN,
    CONSTRAINT uk_verticals_vertical_code UNIQUE (vertical_code)
);

CREATE TABLE whatsapp_incoming_messages (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    whatsapp_message_id VARCHAR(255),
    sender_phone VARCHAR(50),
    sender_name VARCHAR(255),
    message_text TEXT,
    received_at TIMESTAMP,
    processed_flag BOOLEAN,
    parse_status VARCHAR(50),
    parse_confidence INTEGER,
    extracted_side VARCHAR(50),
    extracted_product_text VARCHAR(255),
    extracted_quantity NUMERIC(18,2),
    extracted_unit VARCHAR(50),
    extracted_location_from VARCHAR(255),
    extracted_location_to VARCHAR(255),
    remarks VARCHAR(255),
    CONSTRAINT uk_whatsapp_incoming_messages_whatsapp_message_id UNIQUE (whatsapp_message_id)
);

CREATE TABLE incoming_leads (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    lead_no VARCHAR(100),
    source_id BIGINT,
    whatsapp_message_id BIGINT,
    lead_type VARCHAR(100),
    party_name VARCHAR(255),
    contact_person VARCHAR(255),
    phone VARCHAR(50),
    whatsapp_no VARCHAR(50),
    email VARCHAR(255),
    company_name VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    interested_side VARCHAR(50),
    raw_message TEXT,
    product_id BIGINT,
    product_text VARCHAR(255),
    quantity NUMERIC(18,2),
    unit_text VARCHAR(50),
    location_from VARCHAR(255),
    location_to VARCHAR(255),
    assigned_to BIGINT,
    department_id BIGINT,
    vertical_id BIGINT,
    lead_status VARCHAR(50),
    parse_confidence INTEGER,
    remarks VARCHAR(255),
    CONSTRAINT uk_incoming_leads_lead_no UNIQUE (lead_no),
    CONSTRAINT fk_incoming_leads_source_id FOREIGN KEY (source_id) REFERENCES lead_sources (id),
    CONSTRAINT fk_incoming_leads_whatsapp_message_id FOREIGN KEY (whatsapp_message_id) REFERENCES whatsapp_incoming_messages (id),
    CONSTRAINT fk_incoming_leads_product_id FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_incoming_leads_assigned_to FOREIGN KEY (assigned_to) REFERENCES users (id),
    CONSTRAINT fk_incoming_leads_department_id FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_incoming_leads_vertical_id FOREIGN KEY (vertical_id) REFERENCES verticals (id)
);

CREATE TABLE lead_activities (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    lead_id BIGINT NOT NULL,
    activity_type VARCHAR(50),
    notes TEXT,
    next_follow_up_at TIMESTAMP,
    done_by BIGINT,
    CONSTRAINT fk_lead_activities_lead_id FOREIGN KEY (lead_id) REFERENCES incoming_leads (id),
    CONSTRAINT fk_lead_activities_done_by FOREIGN KEY (done_by) REFERENCES users (id)
);

CREATE TABLE lead_assignments (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    lead_id BIGINT NOT NULL,
    assigned_to BIGINT NOT NULL,
    assigned_by BIGINT,
    assigned_at TIMESTAMP,
    remarks VARCHAR(255),
    CONSTRAINT fk_lead_assignments_lead_id FOREIGN KEY (lead_id) REFERENCES incoming_leads (id),
    CONSTRAINT fk_lead_assignments_assigned_to FOREIGN KEY (assigned_to) REFERENCES users (id),
    CONSTRAINT fk_lead_assignments_assigned_by FOREIGN KEY (assigned_by) REFERENCES users (id)
);

CREATE TABLE trade_requirements (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    requirement_no VARCHAR(100),
    lead_id BIGINT,
    requirement_type VARCHAR(50),
    buyer_or_seller VARCHAR(50),
    company_id BIGINT,
    product_id BIGINT,
    product_text VARCHAR(255),
    quantity NUMERIC(18,2),
    unit_text VARCHAR(50),
    quality_spec VARCHAR(255),
    target_price NUMERIC(18,2),
    source_location VARCHAR(255),
    destination_location VARCHAR(255),
    payment_terms VARCHAR(255),
    urgency_level VARCHAR(100),
    requirement_status VARCHAR(50),
    department_id BIGINT,
    vertical_id BIGINT,
    remarks VARCHAR(255),
    CONSTRAINT uk_trade_requirements_requirement_no UNIQUE (requirement_no),
    CONSTRAINT fk_trade_requirements_lead_id FOREIGN KEY (lead_id) REFERENCES incoming_leads (id),
    CONSTRAINT fk_trade_requirements_company_id FOREIGN KEY (company_id) REFERENCES companies (id),
    CONSTRAINT fk_trade_requirements_product_id FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_trade_requirements_department_id FOREIGN KEY (department_id) REFERENCES departments (id),
    CONSTRAINT fk_trade_requirements_vertical_id FOREIGN KEY (vertical_id) REFERENCES verticals (id)
);

CREATE TABLE requirement_matches (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    requirement_id BIGINT NOT NULL,
    matched_company_id BIGINT NOT NULL,
    match_side VARCHAR(50),
    match_score INTEGER,
    priority_rank INTEGER,
    shortlisted_flag BOOLEAN,
    remarks VARCHAR(255),
    CONSTRAINT fk_requirement_matches_requirement_id FOREIGN KEY (requirement_id) REFERENCES trade_requirements (id),
    CONSTRAINT fk_requirement_matches_matched_company_id FOREIGN KEY (matched_company_id) REFERENCES companies (id)
);

CREATE INDEX idx_incoming_leads_lead_status ON incoming_leads (lead_status);
CREATE INDEX idx_incoming_leads_assigned_to ON incoming_leads (assigned_to);
CREATE INDEX idx_incoming_leads_product_id ON incoming_leads (product_id);
CREATE INDEX idx_incoming_leads_whatsapp_message_id ON incoming_leads (whatsapp_message_id);
CREATE INDEX idx_incoming_leads_parse_confidence ON incoming_leads (parse_confidence);
CREATE INDEX idx_whatsapp_incoming_messages_sender_phone ON whatsapp_incoming_messages (sender_phone);
CREATE INDEX idx_whatsapp_incoming_messages_parse_status ON whatsapp_incoming_messages (parse_status);
CREATE INDEX idx_whatsapp_incoming_messages_received_at ON whatsapp_incoming_messages (received_at);
CREATE INDEX idx_trade_requirements_requirement_status ON trade_requirements (requirement_status);
CREATE INDEX idx_trade_requirements_product_id ON trade_requirements (product_id);
CREATE INDEX idx_requirement_matches_requirement_id ON requirement_matches (requirement_id);
CREATE INDEX idx_requirement_matches_priority_rank ON requirement_matches (priority_rank);
CREATE INDEX idx_requirement_matches_shortlisted_flag ON requirement_matches (shortlisted_flag);
