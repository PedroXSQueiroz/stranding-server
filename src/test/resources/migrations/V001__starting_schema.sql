CREATE TABLE stranding_user(
	stranding_user_id uuid not null,
	stranding_user_login varchar(256) not null,
	stranding_user_name varchar(256) not null
);

ALTER TABLE stranding_user
  ALTER COLUMN stranding_user_id
	  SET DEFAULT random_uuid();

CREATE TABLE stranding_user_x_friends(
	stranding_user_id uuid not null,
	friend_id uuid not null
);

ALTER TABLE stranding_user_x_friends
	ADD	CONSTRAINT User_x_friends_User
		FOREIGN KEY (stranding_user_id)
		REFERENCES stranding_user(stranding_user_id);

ALTER TABLE stranding_user_x_friends
	ADD	CONSTRAINT User_x_friends_Friends
		FOREIGN KEY (friend_id)
		REFERENCES stranding_user(stranding_user_id);

CREATE TABLE post(
	post_id uuid not null,
	post_content varchar(256) not null,
	stranding_user_id uuid not null,
	creation_date TIMESTAMP NOT NULL
);

ALTER TABLE post
	ALTER COLUMN post_id
		SET DEFAULT random_uuid();

ALTER TABLE post
	ADD CONSTRAINT Post_User
		FOREIGN KEY (stranding_user_id)
		REFERENCES stranding_user(stranding_user_id);
		
CREATE TABLE post_url_images(
	post_url_image_id uuid not null,
	post_id uuid not null,
	url varchar(256) NOT NULL
);

ALTER TABLE post_url_images
	ALTER COLUMN post_url_image_id
		SET DEFAULT random_uuid();


ALTER TABLE post_url_images
	ADD CONSTRAINT Image_Post
		FOREIGN KEY (post_id)
		REFERENCES post(post_id);


