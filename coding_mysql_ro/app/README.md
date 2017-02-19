# The java checker for the challenge

## What it does
This app allows to check between an expected sql result and a sql query provided by the user.

This tutorial will allow you to insert entries in a temporary database, before it actually dumps the data into a sql file.
Then, you can use this file as an input to your mysql container.

## Howto

1. Build an image of your database manually: `cd ../ && docker build -t db-tmp-img .`  
2. Run a temporary container for your db `docker run -it -p "5432:5432" --name db-tmp db-tmp-img`  
3. Run the java generator application (src/main/java/Core.java). Don't forget to check the db params in the resources (src/main/resources/db.properties)
4. Export the resulting database using this command: `docker exec -ti db-tmp pg_dump --dbname=thedb --table=public.user --table=public.matches --file="/2-entries.sql" --data-only`
5. Copy the entries into the **entries** folder `docker cp db-tmp:/2-entries.sql entries/`

You're done!

If you want to export the schema AND the entries, we advise you do look at the **pg_dump** docs.
Options may look like `pg_dump --dbname=thedb --table=public.user --table=public.matches --file="/db.sql" --create --clean --if-exists`
