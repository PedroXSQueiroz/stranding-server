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
		
CREATE TABLE stranding_user_password(
	stranding_user_password_id uuid not null,
	hash varchar(256) not null,
	user_id uuid not null,
	creation_date timestamp not null
);

ALTER TABLE stranding_user_password
	ALTER COLUMN stranding_user_password_id
	  SET DEFAULT random_uuid();

ALTER TABLE stranding_user_password
	ADD CONSTRAINT Password_User
		FOREIGN KEY (user_id)
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

CREATE TABLE post_media(
	post_media_id uuid not null,
	post_id uuid not null,
	media_name varchar(256) not null,
	internal_id varchar(256) not null
);
		

ALTER TABLE post_media
	ALTER COLUMN post_media_id
		SET DEFAULT random_uuid();


ALTER TABLE post_media
	ADD CONSTRAINT Media_Post
		FOREIGN KEY (post_id)
		REFERENCES post(post_id);

