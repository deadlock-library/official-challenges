# ${project.label}

# [Remove When Done]
TODO this briefing file should contain a **FUN** description written using the **Markdown** syntax.  
Remember that every Deadlock challenge should be both **entertaining** and **useful**. It helps to provide a fun context around what you are trying to achieve as contexts give **purpose**.

The briefing should be as exhaustive as possible to enable your candidates to succeed. It should not however give out too many details in **how** to succeed. The **hints** (two other mandatory files in this folder) will be helpful to guide your candidate towards success if the briefing was not enough.

The following lines provide an actual sample of briefing that we find satisfying.


# Locate Hacker

Last week, your old phone was stolen by some guy who started selling happiness pills to all your contacts.
Angry, you decided to call the Google support line and try to get a hold on your old account, but they decided to leave you to it. You decide to hack Google (because that's what we do), and manage to find an access to their PostgreSQL Database.

In the database, the users are stored in the table named **user**

Each user has multiple attributes (which are the table columns):  
`id, nickname,	name,	description,	email,	gender,	age, interested_in,	subscribe_date,	latitude,	longitude`

In particular, the latitude and longitude fields store the last known location of the user. This is your unique occasion to know where your phone is, and teach a lesson to the one who stole it from you.

Want to see data samples?
```
142	|	Tom41	|	Tom Prud'homme	|	I wouldn't recommend sex, drugs or insanity for everyone, but they've always worked for me.	|	Tom41@yahoo.com	|	male	|	30	|	female	|	2017-01-01	|	2.34377297643457094	|	48.9052836836143214	|
144	|	Dante53	|	Dante Shoaf	|	I remember that a wise friend of mine did usually say, "That which is everybody's business is nobody's business."	|	Dante53@gmail.com	|	male	|	18	|	female	|	2017-01-01	|	2.34924750243903757	|	48.8343300818878348	|
145	|	Jessie3	|	Jessie Soullard	|	Pour autant que les mathématiques se rapportent à la réalité, elles ne sont pas certaines.	|	Jessie3@free.fr	|	female	|	34	|	male	|	2017-01-01	|	2.32959218707125482	|	48.812956706278996	|
```

# What you need to do

What interests you is the last known location of your guy.
He changed your name but forgot to change your nickname (**Phil85**) and email (**Phil85@free.fr**).

In this challenge, your mission is to write the query which will return **ONLY** the latitude and longitude field of your account.

PS: Their database runs on the latest version of **PostgreSQL**

# [/Remove When Done]
