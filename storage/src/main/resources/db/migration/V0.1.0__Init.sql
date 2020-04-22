CREATE TABLE asset (
    id uuid NOT NULL CONSTRAINT asset_pkey PRIMARY KEY,
    name character varying(255) NOT NULL CONSTRAINT asset_unique_name UNIQUE
);

CREATE TABLE data_type (
    id uuid NOT NULL CONSTRAINT data_type_pkey PRIMARY KEY,
    name character varying(255) NOT NULL CONSTRAINT data_type_unique_name UNIQUE,
    unit character varying(255) NOT NULL,
    description text NOT NULL
);

CREATE TABLE tag (
    id uuid NOT NULL CONSTRAINT tag_pkey PRIMARY KEY,
    name character varying(255) NOT NULL CONSTRAINT tag_unique_name UNIQUE,
    description text NOT NULL
);

CREATE TABLE tag_value (
    id uuid NOT NULL CONSTRAINT tag_value_pkey PRIMARY KEY,
    value character varying(255) NOT NULL,
    asset uuid NOT NULL REFERENCES tag(id) NOT DEFERRABLE,
    tag uuid NOT NULL REFERENCES asset(id) NOT DEFERRABLE
);

CREATE TABLE data (
    timestamp timestamp NOT NULL,
    value double precision NOT NULL,
    asset uuid NOT NULL REFERENCES asset(id) NOT DEFERRABLE,
    data_type uuid NOT NULL REFERENCES data_type(id) NOT DEFERRABLE
);

CREATE TABLE join__asset__data_type (
    asset uuid NOT NULL REFERENCES asset(id) NOT DEFERRABLE,
    data_type uuid NOT NULL REFERENCES data_type(id) NOT DEFERRABLE,
    CONSTRAINT join__asset__data_type_pkey PRIMARY KEY (asset, data_type)
);
