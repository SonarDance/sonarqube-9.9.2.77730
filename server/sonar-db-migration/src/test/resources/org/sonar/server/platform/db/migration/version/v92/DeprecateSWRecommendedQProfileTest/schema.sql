CREATE TABLE "RULES_PROFILES"(
    "UUID" VARCHAR(40) NOT NULL,
    "NAME" VARCHAR(100) NOT NULL,
    "LANGUAGE" VARCHAR(20),
    "IS_BUILT_IN" BOOLEAN NOT NULL,
    "RULES_UPDATED_AT" VARCHAR(100),
    "CREATED_AT" TIMESTAMP,
    "UPDATED_AT" TIMESTAMP
);
ALTER TABLE "RULES_PROFILES" ADD CONSTRAINT "PK_RULES_PROFILES" PRIMARY KEY("UUID");
