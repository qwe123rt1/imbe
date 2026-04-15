INSERT INTO users (
    created_at, updated_at, created_by, updated_by,
    full_name, email, phone, password_hash, is_active
) VALUES
    (NOW(), NOW(), 'seed', 'seed', 'Rohit Admin', 'admin@clinic.local', '9000000001', 'seed-password-hash', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Lead Manager', 'lead.manager@clinic.local', '9000000002', 'seed-password-hash', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Sales Executive', 'sales.executive@clinic.local', '9000000003', 'seed-password-hash', TRUE)
ON CONFLICT (email) DO NOTHING;

INSERT INTO departments (
    created_at, updated_at, created_by, updated_by,
    department_name, department_code, display_order, is_active
) VALUES
    (NOW(), NOW(), 'seed', 'seed', 'Sales', 'SALES', 1, TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Procurement', 'PROCUREMENT', 2, TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Operations', 'OPERATIONS', 3, TRUE)
ON CONFLICT (department_code) DO NOTHING;

INSERT INTO verticals (
    created_at, updated_at, created_by, updated_by,
    vertical_name, vertical_code, display_order, is_active
) VALUES
    (NOW(), NOW(), 'seed', 'seed', 'Fuel', 'FUEL', 1, TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Agri Biomass', 'AGRI_BIOMASS', 2, TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Minerals', 'MINERALS', 3, TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Industrial Materials', 'INDUSTRIAL_MATERIALS', 4, TRUE)
ON CONFLICT (vertical_code) DO NOTHING;

INSERT INTO lead_sources (
    created_at, updated_at, created_by, updated_by,
    source_name, source_code, is_active
) VALUES
    (NOW(), NOW(), 'seed', 'seed', 'WhatsApp', 'WHATSAPP', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Website', 'WEBSITE', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Referral', 'REFERRAL', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Phone Call', 'PHONE_CALL', TRUE)
ON CONFLICT (source_code) DO NOTHING;

INSERT INTO products (
    created_at, updated_at, created_by, updated_by,
    product_name, product_code, category_name, base_unit, description, is_active
) VALUES
    (NOW(), NOW(), 'seed', 'seed', 'Coal', 'COAL', 'Fuel', 'MT', 'Thermal coal for industrial boilers', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Pet Coke', 'PET_COKE', 'Fuel', 'MT', 'Petroleum coke for industrial use', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Biomass Pellets', 'BIOMASS_PELLETS', 'Agri Biomass', 'MT', 'Compressed biomass pellets', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Rice Husk', 'RICE_HUSK', 'Agri Biomass', 'MT', 'Rice husk biomass fuel', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Parali', 'PARALI', 'Agri Biomass', 'MT', 'Paddy straw biomass', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Mustard Husk', 'MUSTARD_HUSK', 'Agri Biomass', 'MT', 'Mustard crop residue biomass', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Wood Chips', 'WOOD_CHIPS', 'Agri Biomass', 'MT', 'Industrial wood chips', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Gypsum', 'GYPSUM', 'Minerals', 'MT', 'Gypsum for cement and construction', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Fly Ash', 'FLY_ASH', 'Industrial Materials', 'MT', 'Fly ash for cement and bricks', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Limestone', 'LIMESTONE', 'Minerals', 'MT', 'Limestone mineral supply', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Cement', 'CEMENT', 'Industrial Materials', 'BAG', 'Bagged cement supply', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Steel Scrap', 'STEEL_SCRAP', 'Industrial Materials', 'MT', 'Steel scrap for recycling', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Copper Scrap', 'COPPER_SCRAP', 'Industrial Materials', 'KG', 'Copper scrap for recycling', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Silica Sand', 'SILICA_SAND', 'Minerals', 'MT', 'Silica sand for industrial use', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Iron Ore', 'IRON_ORE', 'Minerals', 'MT', 'Iron ore fines and lumps', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Dolomite', 'DOLOMITE', 'Minerals', 'MT', 'Dolomite mineral supply', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Bentonite', 'BENTONITE', 'Minerals', 'MT', 'Bentonite clay mineral', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Furnace Oil', 'FURNACE_OIL', 'Fuel', 'KL', 'Industrial furnace oil', TRUE)
ON CONFLICT (product_code) DO NOTHING;

INSERT INTO companies (
    created_at, updated_at, created_by, updated_by,
    company_name, short_name, company_type, contact_person, phone, email,
    city, state, country, is_active
) VALUES
    (NOW(), NOW(), 'seed', 'seed', 'Shakti Coal Traders', 'Shakti Coal', 'SUPPLIER', 'Amit Sharma', '9100000001', 'amit@shakticoal.test', 'Nagpur', 'Maharashtra', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Eastern Fuel Supply Co', 'Eastern Fuel', 'SUPPLIER', 'Ravi Sinha', '9100000002', 'ravi@easternfuel.test', 'Dhanbad', 'Jharkhand', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Green Biomass Traders', 'Green Biomass', 'SUPPLIER', 'Karan Gill', '9100000003', 'karan@greenbiomass.test', 'Ludhiana', 'Punjab', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Punjab Agro Residue Hub', 'Punjab Agro', 'SUPPLIER', 'Harpreet Singh', '9100000004', 'harpreet@punjabagro.test', 'Bathinda', 'Punjab', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Rajasthan Mineral House', 'Rajasthan Mineral', 'SUPPLIER', 'Pooja Mehta', '9100000005', 'pooja@rajmineral.test', 'Udaipur', 'Rajasthan', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Gujarat Gypsum Supply', 'Gujarat Gypsum', 'SUPPLIER', 'Dev Patel', '9100000006', 'dev@gujarategypsum.test', 'Kutch', 'Gujarat', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Central Fly Ash Depot', 'Fly Ash Depot', 'SUPPLIER', 'Neeraj Verma', '9100000007', 'neeraj@flyashdepot.test', 'Korba', 'Chhattisgarh', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Vindhya Limestone Works', 'Vindhya Lime', 'SUPPLIER', 'Sanjay Tiwari', '9100000008', 'sanjay@vindhyalime.test', 'Satna', 'Madhya Pradesh', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Metro Cement Distributors', 'Metro Cement', 'SUPPLIER', 'Nilesh Shah', '9100000009', 'nilesh@metrocements.test', 'Ahmedabad', 'Gujarat', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'North Scrap Exchange', 'North Scrap', 'SUPPLIER', 'Iqbal Khan', '9100000010', 'iqbal@northscrap.test', 'Delhi', 'Delhi', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Western Metal Recyclers', 'Western Metal', 'SUPPLIER', 'Rakesh More', '9100000011', 'rakesh@westernmetal.test', 'Mumbai', 'Maharashtra', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Coastal Silica Minerals', 'Coastal Silica', 'SUPPLIER', 'Mohan Nair', '9100000012', 'mohan@coastalsilica.test', 'Mangaluru', 'Karnataka', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Odisha Ore Suppliers', 'Odisha Ore', 'SUPPLIER', 'Subhash Das', '9100000013', 'subhash@odishaore.test', 'Barbil', 'Odisha', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Malwa Biomass Supply', 'Malwa Biomass', 'SUPPLIER', 'Gurpreet Kaur', '9100000014', 'gurpreet@malwabiomass.test', 'Moga', 'Punjab', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Industrial Fuel Mart', 'Fuel Mart', 'SUPPLIER', 'Vikram Rao', '9100000015', 'vikram@fuelmart.test', 'Surat', 'Gujarat', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Apex Steel Mills', 'Apex Steel', 'BUYER', 'Anil Gupta', '9200000001', 'anil@apexsteel.test', 'Raipur', 'Chhattisgarh', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Bharat Cement Works', 'Bharat Cement', 'BUYER', 'Manish Jain', '9200000002', 'manish@bharatcement.test', 'Satna', 'Madhya Pradesh', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Prime Brick Industries', 'Prime Brick', 'BUYER', 'Suresh Yadav', '9200000003', 'suresh@primebrick.test', 'Kanpur', 'Uttar Pradesh', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Eco Power Boilers', 'Eco Power', 'BUYER', 'Meena Iyer', '9200000004', 'meena@ecopower.test', 'Pune', 'Maharashtra', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Sunrise Paper Mills', 'Sunrise Paper', 'BUYER', 'Deepak Bansal', '9200000005', 'deepak@sunrisepaper.test', 'Vapi', 'Gujarat', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Agni Energy Plant', 'Agni Energy', 'BUYER', 'Nitin Joshi', '9200000006', 'nitin@agnienergy.test', 'Bhopal', 'Madhya Pradesh', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Shree Lime Chemicals', 'Shree Lime', 'BUYER', 'Prakash Patel', '9200000007', 'prakash@shreelime.test', 'Vadodara', 'Gujarat', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Urban Builders Depot', 'Urban Builders', 'BUYER', 'Kunal Desai', '9200000008', 'kunal@urbanbuilders.test', 'Mumbai', 'Maharashtra', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Haryana Biomass Boilers', 'Haryana Biomass', 'BUYER', 'Sandeep Malik', '9200000009', 'sandeep@haryanabiomass.test', 'Panipat', 'Haryana', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Raj Alloy Foundry', 'Raj Alloy', 'BUYER', 'Ritesh Agarwal', '9200000010', 'ritesh@rajalloy.test', 'Jaipur', 'Rajasthan', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Coastal Cement Plant', 'Coastal Cement', 'BUYER', 'Joseph Dsouza', '9200000011', 'joseph@coastalcement.test', 'Mangaluru', 'Karnataka', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'East India Sponge Iron', 'East Sponge', 'BUYER', 'Alok Mishra', '9200000012', 'alok@eastsponge.test', 'Rourkela', 'Odisha', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'National Glass Works', 'National Glass', 'BUYER', 'Farhan Ali', '9200000013', 'farhan@nationalglass.test', 'Firozabad', 'Uttar Pradesh', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Delta Ceramic Industries', 'Delta Ceramic', 'BUYER', 'Jay Mehta', '9200000014', 'jay@deltaceramic.test', 'Morbi', 'Gujarat', 'India', TRUE),
    (NOW(), NOW(), 'seed', 'seed', 'Central Foundry Works', 'Central Foundry', 'BUYER', 'Omkar Kulkarni', '9200000015', 'omkar@centralfoundry.test', 'Indore', 'Madhya Pradesh', 'India', TRUE)
ON CONFLICT (company_name) DO NOTHING;

WITH listing_seed (
    company_name, product_code, side, min_quantity, max_quantity,
    unit_text, rate_hint, city, state, remarks
) AS (
    VALUES
        ('Shakti Coal Traders', 'COAL', 'SELLER', 50.00, 500.00, 'MT', 5200.00, 'Nagpur', 'Maharashtra', 'Steam coal ready stock'),
        ('Eastern Fuel Supply Co', 'COAL', 'SELLER', 100.00, 1200.00, 'MT', 4850.00, 'Dhanbad', 'Jharkhand', 'Bulk coal supply'),
        ('Industrial Fuel Mart', 'COAL', 'SELLER', 25.00, 250.00, 'MT', 5450.00, 'Surat', 'Gujarat', 'Industrial coal lots'),
        ('Eco Power Boilers', 'COAL', 'BUYER', 75.00, 400.00, 'MT', 5600.00, 'Pune', 'Maharashtra', 'Monthly boiler consumption'),
        ('Agni Energy Plant', 'COAL', 'BUYER', 200.00, 1500.00, 'MT', 5300.00, 'Bhopal', 'Madhya Pradesh', 'Power plant coal requirement'),
        ('Green Biomass Traders', 'BIOMASS_PELLETS', 'SELLER', 20.00, 300.00, 'MT', 7200.00, 'Ludhiana', 'Punjab', 'Pellet bags and bulk'),
        ('Malwa Biomass Supply', 'BIOMASS_PELLETS', 'SELLER', 30.00, 500.00, 'MT', 6950.00, 'Moga', 'Punjab', 'Industrial biomass pellets'),
        ('Haryana Biomass Boilers', 'BIOMASS_PELLETS', 'BUYER', 25.00, 250.00, 'MT', 7350.00, 'Panipat', 'Haryana', 'Biomass boiler buyer'),
        ('Punjab Agro Residue Hub', 'PARALI', 'SELLER', 50.00, 800.00, 'MT', 3100.00, 'Bathinda', 'Punjab', 'Baled parali available'),
        ('Malwa Biomass Supply', 'PARALI', 'SELLER', 75.00, 900.00, 'MT', 2950.00, 'Moga', 'Punjab', 'Parali for biomass fuel'),
        ('Haryana Biomass Boilers', 'PARALI', 'BUYER', 60.00, 700.00, 'MT', 3300.00, 'Panipat', 'Haryana', 'Parali purchase requirement'),
        ('Green Biomass Traders', 'RICE_HUSK', 'SELLER', 40.00, 600.00, 'MT', 4200.00, 'Ludhiana', 'Punjab', 'Rice husk stock'),
        ('Sunrise Paper Mills', 'RICE_HUSK', 'BUYER', 50.00, 450.00, 'MT', 4550.00, 'Vapi', 'Gujarat', 'Rice husk fuel buyer'),
        ('Malwa Biomass Supply', 'MUSTARD_HUSK', 'SELLER', 25.00, 300.00, 'MT', 3900.00, 'Moga', 'Punjab', 'Mustard husk supply'),
        ('Agni Energy Plant', 'MUSTARD_HUSK', 'BUYER', 25.00, 250.00, 'MT', 4150.00, 'Bhopal', 'Madhya Pradesh', 'Mustard husk buyer'),
        ('Green Biomass Traders', 'WOOD_CHIPS', 'SELLER', 30.00, 350.00, 'MT', 4700.00, 'Ludhiana', 'Punjab', 'Wood chips supply'),
        ('Eco Power Boilers', 'WOOD_CHIPS', 'BUYER', 35.00, 300.00, 'MT', 4950.00, 'Pune', 'Maharashtra', 'Wood chips boiler fuel'),
        ('Gujarat Gypsum Supply', 'GYPSUM', 'SELLER', 50.00, 1000.00, 'MT', 1450.00, 'Kutch', 'Gujarat', 'Natural gypsum supply'),
        ('Rajasthan Mineral House', 'GYPSUM', 'SELLER', 100.00, 1500.00, 'MT', 1380.00, 'Udaipur', 'Rajasthan', 'Gypsum loose material'),
        ('Bharat Cement Works', 'GYPSUM', 'BUYER', 100.00, 1200.00, 'MT', 1600.00, 'Satna', 'Madhya Pradesh', 'Gypsum for cement plant'),
        ('Central Fly Ash Depot', 'FLY_ASH', 'SELLER', 100.00, 2000.00, 'MT', 650.00, 'Korba', 'Chhattisgarh', 'Dry fly ash'),
        ('Prime Brick Industries', 'FLY_ASH', 'BUYER', 80.00, 1000.00, 'MT', 800.00, 'Kanpur', 'Uttar Pradesh', 'Fly ash for bricks'),
        ('Coastal Cement Plant', 'FLY_ASH', 'BUYER', 150.00, 1800.00, 'MT', 760.00, 'Mangaluru', 'Karnataka', 'Fly ash for cement'),
        ('Vindhya Limestone Works', 'LIMESTONE', 'SELLER', 100.00, 2000.00, 'MT', 900.00, 'Satna', 'Madhya Pradesh', 'Crushed limestone'),
        ('Rajasthan Mineral House', 'LIMESTONE', 'SELLER', 150.00, 2500.00, 'MT', 870.00, 'Udaipur', 'Rajasthan', 'Limestone bulk supply'),
        ('Bharat Cement Works', 'LIMESTONE', 'BUYER', 200.00, 2500.00, 'MT', 1020.00, 'Satna', 'Madhya Pradesh', 'Cement grade limestone buyer'),
        ('Metro Cement Distributors', 'CEMENT', 'SELLER', 1000.00, 20000.00, 'BAG', 335.00, 'Ahmedabad', 'Gujarat', 'OPC and PPC cement'),
        ('Urban Builders Depot', 'CEMENT', 'BUYER', 500.00, 10000.00, 'BAG', 360.00, 'Mumbai', 'Maharashtra', 'Construction cement buyer'),
        ('North Scrap Exchange', 'STEEL_SCRAP', 'SELLER', 10.00, 200.00, 'MT', 36500.00, 'Delhi', 'Delhi', 'Steel scrap mixed grade'),
        ('Apex Steel Mills', 'STEEL_SCRAP', 'BUYER', 25.00, 500.00, 'MT', 38200.00, 'Raipur', 'Chhattisgarh', 'Steel scrap mill buyer'),
        ('Raj Alloy Foundry', 'STEEL_SCRAP', 'BUYER', 10.00, 150.00, 'MT', 37600.00, 'Jaipur', 'Rajasthan', 'Foundry scrap requirement'),
        ('Western Metal Recyclers', 'COPPER_SCRAP', 'SELLER', 500.00, 10000.00, 'KG', 710.00, 'Mumbai', 'Maharashtra', 'Copper scrap lots'),
        ('Raj Alloy Foundry', 'COPPER_SCRAP', 'BUYER', 1000.00, 8000.00, 'KG', 735.00, 'Jaipur', 'Rajasthan', 'Copper scrap buyer'),
        ('Coastal Silica Minerals', 'SILICA_SAND', 'SELLER', 50.00, 800.00, 'MT', 1850.00, 'Mangaluru', 'Karnataka', 'Washed silica sand'),
        ('National Glass Works', 'SILICA_SAND', 'BUYER', 50.00, 700.00, 'MT', 2050.00, 'Firozabad', 'Uttar Pradesh', 'Glass grade silica sand'),
        ('Odisha Ore Suppliers', 'IRON_ORE', 'SELLER', 100.00, 2000.00, 'MT', 6200.00, 'Barbil', 'Odisha', 'Iron ore fines'),
        ('East India Sponge Iron', 'IRON_ORE', 'BUYER', 100.00, 1800.00, 'MT', 6550.00, 'Rourkela', 'Odisha', 'Sponge iron raw material')
)
INSERT INTO market_listings (
    created_at, updated_at, created_by, updated_by,
    company_id, product_id, side, min_quantity, max_quantity,
    unit_text, rate_hint, city, state, is_active, remarks
)
SELECT
    NOW(), NOW(), 'seed', 'seed',
    c.id, p.id, l.side, l.min_quantity, l.max_quantity,
    l.unit_text, l.rate_hint, l.city, l.state, TRUE, l.remarks
FROM listing_seed l
JOIN companies c ON c.company_name = l.company_name
JOIN products p ON p.product_code = l.product_code;

INSERT INTO whatsapp_incoming_messages (
    created_at, updated_at, created_by, updated_by,
    whatsapp_message_id, sender_phone, sender_name, message_text,
    received_at, processed_flag, parse_status, remarks
) VALUES
    (NOW(), NOW(), 'seed', 'seed', 'seed-msg-001', '9300000001', 'Test Buyer Coal', 'Need 100 MT coal from Nagpur to Pune', NOW(), FALSE, 'NOT_PROCESSED', 'Seed WhatsApp test message'),
    (NOW(), NOW(), 'seed', 'seed', 'seed-msg-002', '9300000002', 'Test Seller Parali', 'Have 200 MT parali available from Bathinda', NOW(), FALSE, 'NOT_PROCESSED', 'Seed WhatsApp test message'),
    (NOW(), NOW(), 'seed', 'seed', 'seed-msg-003', '9300000003', 'Test Buyer Gypsum', 'Required 300 MT gypsum in Satna', NOW(), FALSE, 'NOT_PROCESSED', 'Seed WhatsApp test message'),
    (NOW(), NOW(), 'seed', 'seed', 'seed-msg-004', '9300000004', 'Test Seller Scrap', 'Selling 50 MT steel scrap from Delhi', NOW(), FALSE, 'NOT_PROCESSED', 'Seed WhatsApp test message'),
    (NOW(), NOW(), 'seed', 'seed', 'seed-msg-005', '9300000005', 'Test Buyer Rice Husk', 'Looking for 80 MT rice husk to Vapi', NOW(), FALSE, 'NOT_PROCESSED', 'Seed WhatsApp test message')
ON CONFLICT (whatsapp_message_id) DO NOTHING;
