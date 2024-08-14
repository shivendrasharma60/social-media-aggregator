-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Create Influencer Table
CREATE TABLE IF NOT EXISTS influencers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    uniquehash VARCHAR(255) NOT NULL
);

-- Create Social Media Profile Table
CREATE TABLE IF NOT EXISTS social_media_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    platform VARCHAR(255) NOT NULL,
    profile_url VARCHAR(255) NOT NULL,
    influencer_id BIGINT,
    FOREIGN KEY (influencer_id) REFERENCES influencers(id)
);

-- Create User-Influencer Mapping Table
CREATE TABLE IF NOT EXISTS user_influencer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    influencer_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (influencer_id) REFERENCES influencers(id)
);

-- Create Social Media Feed Table
CREATE TABLE IF NOT EXISTS social_media_feed (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    platform VARCHAR(255) NOT NULL,
    content TEXT,
    media_url VARCHAR(255),
    timestamp TIMESTAMP,
    author_name VARCHAR(255),
    author_profile_url VARCHAR(255),
    likes INT,
    shares INT,
    comments INT,
    social_media_profile_id BIGINT,
    FOREIGN KEY (social_media_profile_id) REFERENCES social_media_profiles(id)
);

-- Insert Users
INSERT INTO users (username, password) VALUES ('user1', 'password1');
INSERT INTO users (username, password) VALUES ('user2', 'password2');
INSERT INTO users (username, password) VALUES ('user3', 'password3');
INSERT INTO users (username, password) VALUES ('user4', 'password4');
INSERT INTO users (username, password) VALUES ('user5', 'password5');

-- Insert Influencers
INSERT INTO influencers (name, uniquehash) VALUES ('Narendra Modi', 'uniquehash1');
INSERT INTO influencers (name, uniquehash) VALUES ('Elon Musk', 'uniquehash2');
INSERT INTO influencers (name, uniquehash) VALUES ('Taylor Swift', 'uniquehash3');
INSERT INTO influencers (name, uniquehash) VALUES ('Cristiano Ronaldo', 'uniquehash4');
INSERT INTO influencers (name, uniquehash) VALUES ('Bill Gates', 'uniquehash5');

-- Insert Social Media Profiles
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Twitter', 'https://twitter.com/narendramodi', 1);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Instagram', 'https://instagram.com/narendramodi', 1);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Facebook', 'https://www.facebook.com/narendramodi', 1);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Twitter', 'https://twitter.com/elonmusk', 2);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Instagram', 'https://instagram.com/elonmusk', 2);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Facebook', 'https://www.facebook.com/elonmusk', 2);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Twitter', 'https://twitter.com/taylorswift13', 3);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Instagram', 'https://instagram.com/taylorswift', 3);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Facebook', 'https://www.facebook.com/TaylorSwift', 3);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Twitter', 'https://twitter.com/Cristiano', 4);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Instagram', 'https://instagram.com/cristiano', 4);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Facebook', 'https://www.facebook.com/Cristiano', 4);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Twitter', 'https://twitter.com/BillGates', 5);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Instagram', 'https://instagram.com/thisisbillgates', 5);
INSERT INTO social_media_profiles (platform, profile_url, influencer_id) VALUES ('Facebook', 'https://www.facebook.com/BillGates', 5);

-- Insert User-Influencer Mappings
INSERT INTO user_influencer (user_id, influencer_id) VALUES (1, 1);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (1, 2);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (1, 3);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (2, 4);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (2, 5);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (3, 1);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (3, 2);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (3, 3);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (4, 4);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (4, 5);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (5, 1);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (5, 2);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (5, 3);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (5, 4);
INSERT INTO user_influencer (user_id, influencer_id) VALUES (5, 5);

-- Insert Social Media Feeds
INSERT INTO social_media_feed (platform, content, media_url, timestamp, author_name, author_profile_url, likes, shares, comments, social_media_profile_id) VALUES 
('Twitter', 'Excited to address the nation today!', 'https://image.twitter.com/narendramodi.jpg', '2024-08-13T10:00:00', 'Narendra Modi', 'https://twitter.com/narendramodi', 200000, 50000, 10000, 1),
('Instagram', 'Here are some moments from today’s event.', 'https://image.instagram.com/narendramodi.jpg', '2024-08-13T11:00:00', 'Narendra Modi', 'https://instagram.com/narendramodi', 150000, 30000, 8000, 2),
('Facebook', 'Live from the event!', 'https://image.facebook.com/narendramodi.jpg', '2024-08-13T12:00:00', 'Narendra Modi', 'https://www.facebook.com/narendramodi', 250000, 70000, 12000, 3),
('Twitter', 'Just launched the next rocket to Mars!', 'https://image.twitter.com/elonmusk.jpg', '2024-08-13T10:30:00', 'Elon Musk', 'https://twitter.com/elonmusk', 300000, 100000, 50000, 4),
('Instagram', 'A glimpse of our new project.', 'https://image.instagram.com/elonmusk.jpg', '2024-08-13T11:30:00', 'Elon Musk', 'https://instagram.com/elonmusk', 250000, 60000, 40000, 5),
('Facebook', 'Exciting times ahead!', 'https://image.facebook.com/elonmusk.jpg', '2024-08-13T12:30:00', 'Elon Musk', 'https://www.facebook.com/elonmusk', 350000, 120000, 70000, 6),
('Twitter', 'New album dropping soon!', 'https://image.twitter.com/taylorswift.jpg', '2024-08-13T10:15:00', 'Taylor Swift', 'https://twitter.com/taylorswift13', 400000, 200000, 150000, 7),
('Instagram', 'Sneak peek of my upcoming tour.', 'https://image.instagram.com/taylorswift.jpg', '2024-08-13T11:15:00', 'Taylor Swift', 'https://instagram.com/taylorswift', 350000, 180000, 140000, 8),
('Facebook', 'Can’t wait to share more!', 'https://image.facebook.com/taylorswift.jpg', '2024-08-13T12:15:00', 'Taylor Swift', 'https://www.facebook.com/TaylorSwift', 450000, 220000, 160000, 9),
('Twitter', 'Ready for the big match tonight!', 'https://image.twitter.com/cristiano.jpg', '2024-08-13T10:45:00', 'Cristiano Ronaldo', 'https://twitter.com/Cristiano', 500000, 250000, 200000, 10),
('Instagram', 'Training hard for the next game.', 'https://image.instagram.com/cristiano.jpg', '2024-08-13T11:45:00', 'Cristiano Ronaldo', 'https://instagram.com/cristiano', 450000, 220000, 190000, 11),
('Facebook', 'Thanks for the support, everyone!', 'https://image.facebook.com/cristiano.jpg', '2024-08-13T12:45:00', 'Cristiano Ronaldo', 'https://www.facebook.com/Cristiano', 550000, 280000, 210000, 12),
('Twitter', 'Advancing our work on climate change.', 'https://image.twitter.com/billgates.jpg', '2024-08-13T10:20:00', 'Bill Gates', 'https://twitter.com/BillGates', 600000, 300000, 250000, 13),
('Instagram', 'A closer look at our latest initiative.', 'https://image.instagram.com/billgates.jpg', '2024-08-13T11:20:00', 'Bill Gates', 'https://instagram.com/thisisbillgates', 550000, 280000, 240000, 14),
('Facebook', 'Making strides in global health.', 'https://image.facebook.com/billgates.jpg', '2024-08-13T12:20:00', 'Bill Gates', 'https://www.facebook.com/BillGates', 650000, 320000, 270000, 15);
