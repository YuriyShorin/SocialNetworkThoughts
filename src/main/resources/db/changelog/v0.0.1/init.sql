CREATE TABLE IF NOT EXISTS Users
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username TEXT UNIQUE NOT NULL,
    password TEXT        NOT NULL,
    role     TEXT        NOT NULL
);

CREATE TABLE IF NOT EXISTS Profiles
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     UUID,
    nickname    TEXT NOT NULL,
    status      TEXT,
    description TEXT,
    subscribes  BIGINT           DEFAULT 0,
    subscribers BIGINT           DEFAULT 0,
    created_at  TIMESTAMPTZ      DEFAULT now(),

    CONSTRAINT users_fk FOREIGN KEY (user_id)
        REFERENCES Users (id)
);

CREATE TABLE IF NOT EXISTS Profiles_pictures
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    profile_id UUID,
    url       TEXT,

    CONSTRAINT profiles_fk FOREIGN KEY (profile_id)
        REFERENCES Profiles (id)
);

CREATE TABLE IF NOT EXISTS Subscriptions
(
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    profile_id              UUID,
    subscription_profile_id UUID,

    CONSTRAINT profiles_fk FOREIGN KEY (profile_id)
        REFERENCES Profiles (id)
);

CREATE TABLE IF NOT EXISTS Posts
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    profile_id UUID,
    theme      TEXT,
    content    TEXT,
    likes      BIGINT           DEFAULT 0,
    reposts    BIGINT           DEFAULT 0,
    comments   BIGINT           DEFAULT 0,
    views      BIGINT           DEFAULT 0,
    is_repost  BOOLEAN          DEFAULT FALSE,
    author_id  UUID,
    created_at TIMESTAMPTZ      DEFAULT now(),
    edited_at  TIMESTAMPTZ      DEFAULT now(),

    CONSTRAINT profiles_fk FOREIGN KEY (profile_id)
        REFERENCES Profiles (id),

    CONSTRAINT author_fk FOREIGN KEY (author_id)
        REFERENCES Profiles (id)
);

CREATE TABLE IF NOT EXISTS Posts_pictures
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    post_id UUID,
    url     TEXT,

    CONSTRAINT profiles_fk FOREIGN KEY (post_id)
        REFERENCES Posts (id)
);

CREATE TABLE IF NOT EXISTS Posts_videos
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    post_id UUID,
    url     TEXT,

    CONSTRAINT profiles_fk FOREIGN KEY (post_id)
        REFERENCES Posts (id)
);

CREATE TABLE IF NOT EXISTS Posts_files
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    post_id UUID,
    url     TEXT,

    CONSTRAINT profiles_fk FOREIGN KEY (post_id)
        REFERENCES Posts (id)
);

CREATE TABLE IF NOT EXISTS Likes
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    profile_id UUID,
    post_id    UUID,

    CONSTRAINT profiles_fk FOREIGN KEY (profile_id)
        REFERENCES Profiles (id),

    CONSTRAINT posts_fk FOREIGN KEY (post_id)
        REFERENCES Posts (id)
);

CREATE TABLE IF NOT EXISTS Comments
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    profile_id UUID,
    post_id    UUID,
    content    TEXT,
    likes      BIGINT           DEFAULT 0,
    created_at TIMESTAMPTZ      DEFAULT now(),
    edited_at  TIMESTAMPTZ      DEFAULT now(),

    CONSTRAINT profiles_fk FOREIGN KEY (profile_id)
        REFERENCES Profiles (id),

    CONSTRAINT posts_fk FOREIGN KEY (post_id)
        REFERENCES Posts (id)
);

CREATE TABLE IF NOT EXISTS Comments_pictures
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    comment_id UUID,
    url        TEXT,

    CONSTRAINT profiles_fk FOREIGN KEY (comment_id)
        REFERENCES Comments (id)
);

CREATE TABLE IF NOT EXISTS Comments_videos
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    comment_id UUID,
    url        TEXT,

    CONSTRAINT profiles_fk FOREIGN KEY (comment_id)
        REFERENCES Comments (id)
);

CREATE TABLE IF NOT EXISTS Comments_files
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    comment_id UUID,
    url        TEXT,

    CONSTRAINT profiles_fk FOREIGN KEY (comment_id)
        REFERENCES Comments (id)
);


CREATE TABLE IF NOT EXISTS Comments_likes
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    profile_id UUID,
    comment_id UUID,

    CONSTRAINT profiles_fk FOREIGN KEY (profile_id)
        REFERENCES Profiles (id),

    CONSTRAINT comments_fk FOREIGN KEY (comment_id)
        REFERENCES Comments (id)
);

CREATE TABLE IF NOT EXISTS Views
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    profile_id UUID,
    post_id    UUID,

    CONSTRAINT profiles_fk FOREIGN KEY (profile_id)
        REFERENCES Profiles (id),

    CONSTRAINT posts_fk FOREIGN KEY (post_id)
        REFERENCES Posts (id)
);
