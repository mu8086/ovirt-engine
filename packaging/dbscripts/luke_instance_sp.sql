-- get all LukeInstance
CREATE OR REPLACE FUNCTION GetAllFromLukeInstances (
    v_user_id UUID,
    v_is_filtered BOOLEAN
    )
RETURNS SETOF luke_instance STABLE AS $PROCEDURE$
BEGIN
    RETURN QUERY

    SELECT *
    FROM luke_instance
    WHERE (
            NOT v_is_filtered
            OR EXISTS (
                SELECT 1
                FROM user_cluster_permissions_view
                WHERE user_id = v_user_id
                )
            );
END;$PROCEDURE$
LANGUAGE plpgsql;

-- get LukeInstance by id
CREATE OR REPLACE FUNCTION GetLukeInstanceById (
    v_luke_instance_id UUID,
    v_user_id UUID,
    v_is_filtered BOOLEAN
    )
RETURNS SETOF luke_instance STABLE AS $PROCEDURE$
BEGIN
    RETURN QUERY

    SELECT *
    FROM luke_instance
    WHERE luke_instance_id = v_luke_instance_id
        AND (
            NOT v_is_filtered
            OR EXISTS (
                SELECT 1
                FROM user_cluster_permissions_view
                WHERE user_id = v_user_id
                    AND entity_id = v_luke_instance_id
                )
            );
END;$PROCEDURE$
LANGUAGE plpgsql;

-- Insert
CREATE OR REPLACE FUNCTION InsertLukeInstance (
    v_luke_instance_id UUID,
    v_status VARCHAR(40)
    )
RETURNS VOID AS $PROCEDURE$
BEGIN
    INSERT INTO luke_instance (
        luke_instance_id,
        status
        )
    VALUES (
        v_luke_instance_id,
        v_status
        );
END;$PROCEDURE$
LANGUAGE plpgsql;

-- Update
CREATE OR REPLACE FUNCTION UpdateLukeInstance (
    v_luke_instance_id UUID,
    v_status VARCHAR(40)
    )
RETURNS VOID AS $PROCEDURE$
BEGIN
    UPDATE luke_instance
    SET status = v_status
    WHERE luke_instance_id = v_luke_instance_id;
END;$PROCEDURE$
LANGUAGE plpgsql;

-- Delete
CREATE OR REPLACE FUNCTION DeleteLukeInstance (v_luke_instance_id UUID)
RETURNS VOID AS $PROCEDURE$
BEGIN
    DELETE
    FROM luke_instance
    WHERE luke_instance_id = v_luke_instance_id;
END;$PROCEDURE$
LANGUAGE plpgsql;
