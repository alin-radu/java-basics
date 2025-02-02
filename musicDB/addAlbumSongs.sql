USE `music`;
DROP procedure IF EXISTS `addAlbum`;

USE `music`;
DROP procedure IF EXISTS `music`.`addAlbum`;

DELIMITER $$
USE `music`$$
CREATE DEFINER=`devuser`@`localhost` PROCEDURE `addAlbum`(artistName TEXT, albumName TEXT, songTitles JSON)
BEGIN
	DECLARE val_artist_id INT DEFAULT NULL;
    DECLARE val_album_id INT DEFAULT NULL;
    DECLARE i INT DEFAULT 0;
    DECLARE num_items INT;
    DECLARE song_title VARCHAR(255);
    SET num_items = JSON_LENGTH(songTitles);
    
	SELECT artist_id INTO val_artist_id FROM artists
		WHERE  artist_name = artistName;

	START TRANSACTION;
	IF val_artist_id IS NULL THEN
        -- Insert a new order
        INSERT INTO artists (artist_name) VALUES (artistName);
        -- Get artist_id of last artist inserted
		SELECT LAST_INSERT_ID() INTO val_artist_id;
    END IF;

	SELECT album_id INTO val_album_id FROM albums
		WHERE album_name = albumName AND artist_id = val_artist_id;
        
    IF val_album_id IS NULL THEN
		-- Insert a new album
		INSERT INTO albums (artist_id, album_name) VALUES (val_artist_id, albumName);
		-- Get album_id of last artist inserted
		SELECT LAST_INSERT_ID() INTO val_album_id;
        
        -- Loop through the JSON Song Titles Array
		WHILE i < num_items DO
			-- JSON functions extract the right element, and unquote it
			SET song_title = JSON_UNQUOTE(JSON_EXTRACT(songTitles, CONCAT('$[', i, ']')));	
            -- Insert a new song, track number is assigned here.
			INSERT INTO songs (album_id, track_number, song_title)
				VALUES (val_album_id, i + 1, song_title); 
			SET i = i + 1;
            
		END WHILE;
	END IF;    
    COMMIT;
END$$

DELIMITER ;
