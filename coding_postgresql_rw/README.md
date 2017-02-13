# What the sample contains
This sample contains a full implementation of a PostgreSQL challenge for write-operations.  

There are two kinds of Database challenges:
 * Those which only include "read" queries (**SELECT**)
 * Those which imply to change the database (**INSERT**, **UPDATE**, **DELETE**, etc...)

The sample is of the second kind (with schema changes).
If you only need to perform **SELECT** queries, please select the other appropriate sample.

The following workflow is executed by default:
1. All .sql files in the /services/db/entries folder are executed.
2. The **expected-query.sql** file is executed
3. The **checker.sql** file is executed and output is kept as a **reference**
4. The entries are reset (back to step 1.)
5. The **target script** from the candidate is executed
6. The **checker.sql** file is executed a second time and output is compared to the **reference**

# Condition of success
The challenge succeeds **only if** the two contents (**test** and **reference**) are equal.
That means that the **checker.sql** file is the only decision-maker! Please pay much attention when you implement it.

# Sample content

The sample challenge provided is about **removing old entries from a table**.

# Quick Start

All your sql source files should be copied to the **services/db/entries/** directory, and the **checker.sql** and **expected-query.sql** modified accordingly.

**NB**: Your sql files can contain multiple queries.

# Check-list
1. Remove existing files in the **services/db/entries** directory, and add your own. This will bootstrap the database content
2. Change the **expected-query.sql** file with your solution to the challenge (all operations allowed).
3. Change the **checker.sql** with only SELECT queries which should validate the accuracy of the expected queries.
4. Update the docs **briefing**, **hint1**, and **hint2**
5. Update the 4 mandatory test templates in the **test** folder
