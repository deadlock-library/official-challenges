-- check total count
SELECT COUNT(*)
FROM public."user";
-- check count for hotmail users (should be 0)
SELECT COUNT(*)
FROM public."user"
WHERE email LIKE '%@hotmail.com'
