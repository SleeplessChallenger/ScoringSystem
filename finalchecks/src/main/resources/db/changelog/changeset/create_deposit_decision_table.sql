CREATE TABLE DEPOSIT_DECISION (
    ID INTEGER NOT NULL UNIQUE,
    DEPOSIT_FINAL_DECISION VARCHAR(150) NOT NULL,
    DEPOSIT_SYSTEM_ID VARCHAR(150) NOT NULL,
    SENT VARCHAR(50) NOT NULL,
    DECISION_MADE_AT DATE NOT NULL,
    FLOW_UNIQUE_ID VARCHAR(150) NOT NULL UNIQUE,
    PRIMARY KEY(ID)
);

CREATE UNIQUE INDEX IDX_DEPOSIT_SYSTEM_ID ON DEPOSIT_DECISION(DEPOSIT_SYSTEM_ID);

COMMENT ON TABLE DEPOSIT_DECISION IS 'table for deposit final decision';