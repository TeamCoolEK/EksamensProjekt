SET SQL_SAFE_UPDATES = 0;

UPDATE project     SET projectStatus      = 'I_gang'       WHERE projectStatus = 'In Progress';
UPDATE project     SET projectStatus      = 'Ikke_startet' WHERE projectStatus = 'Not Started';
UPDATE project     SET projectStatus      = 'Lukket'       WHERE projectStatus = 'Closed';

UPDATE subproject  SET subProjectStatus   = 'I_gang'       WHERE subProjectStatus = 'In Progress';
UPDATE subproject  SET subProjectStatus   = 'Ikke_startet' WHERE subProjectStatus = 'Not Started';
UPDATE subproject  SET subProjectStatus   = 'Lukket'       WHERE subProjectStatus = 'Closed';

UPDATE userstory   SET userStoryStatus    = 'I_gang'       WHERE userStoryStatus = 'In Progress';
UPDATE userstory   SET userStoryStatus    = 'Ikke_startet' WHERE userStoryStatus = 'Not Started';
UPDATE userstory   SET userStoryStatus    = 'Lukket'       WHERE userStoryStatus = 'Closed';

UPDATE task        SET taskStatus         = 'I_gang'       WHERE taskStatus = 'In Progress';
UPDATE task        SET taskStatus         = 'Ikke_startet' WHERE taskStatus = 'Not Started';
UPDATE task        SET taskStatus         = 'Lukket'       WHERE taskStatus = 'Closed';

SET SQL_SAFE_UPDATES = 1;