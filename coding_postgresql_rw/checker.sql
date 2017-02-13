-- check total count
SELECT COUNT(*)
FROM public."users";
-- check count for hotmail users (should be 0)
SELECT COUNT(*)
FROM public."users"
WHERE email LIKE '%@hotmail.com'
