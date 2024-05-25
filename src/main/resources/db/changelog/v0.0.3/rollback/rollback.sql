ALTER TABLE Profiles
    ADD COLUMN IF NOT EXISTS description TEXT;

ALTER TABLE Posts_images
    RENAME TO Posts_files;

ALTER TABLE Profiles_pictures
    RENAME TO Profiles_images;