CREATE TABLE "PUSH_EVENTS"(
    "UUID" CHARACTER VARYING(40) NOT NULL,
    "PROJECT_UUID" CHARACTER VARYING(40) NOT NULL,
    "PAYLOAD" CHARACTER LARGE OBJECT NOT NULL,
    "CREATED_AT" BIGINT NOT NULL
);
ALTER TABLE "PUSH_EVENTS" ADD CONSTRAINT "PK_PUSH_EVENTS" PRIMARY KEY("UUID");
