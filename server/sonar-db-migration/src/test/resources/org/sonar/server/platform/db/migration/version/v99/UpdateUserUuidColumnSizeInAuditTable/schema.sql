CREATE TABLE "AUDITS"(
                         "UUID" CHARACTER VARYING(40) NOT NULL,
                         "USER_UUID" CHARACTER VARYING(40) NOT NULL,
                         "USER_LOGIN" CHARACTER VARYING(255) NOT NULL,
                         "CATEGORY" CHARACTER VARYING(25) NOT NULL,
                         "OPERATION" CHARACTER VARYING(50) NOT NULL,
                         "NEW_VALUE" CHARACTER VARYING(4000),
                         "CREATED_AT" BIGINT NOT NULL,
                         "USER_TRIGGERED" BOOLEAN DEFAULT TRUE NOT NULL
);
ALTER TABLE "AUDITS" ADD CONSTRAINT "PK_AUDITS" PRIMARY KEY("UUID");
CREATE INDEX "AUDITS_CREATED_AT" ON "AUDITS"("CREATED_AT" NULLS FIRST);
