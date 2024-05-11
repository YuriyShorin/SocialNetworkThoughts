CREATE TABLE IF NOT EXISTS Posts_pictures
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    post_id UUID,
    url     TEXT,

    CONSTRAINT profiles_fk FOREIGN KEY (post_id)
        REFERENCES Posts (id)
);