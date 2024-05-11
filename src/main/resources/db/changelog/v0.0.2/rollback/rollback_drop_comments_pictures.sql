CREATE TABLE IF NOT EXISTS Comments_pictures
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    comment_id UUID,
    url        TEXT,

    CONSTRAINT profiles_fk FOREIGN KEY (comment_id)
        REFERENCES Comments (id)
);