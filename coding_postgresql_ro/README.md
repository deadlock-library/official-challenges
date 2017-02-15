# What the sample contains
This sample contains a full implementation of a PostgreSQL challenge for select-operations (read only).  

There are two kinds of Database challenges:
 * Those which only include "read" queries (**SELECT**)
 * Those which imply to change the database (**INSERT**, **UPDATE**, **DELETE**, etc...)

The sample is of the first kind (select statements only).
If you need to perform **UPDATE**, **INSERT** or **DELETE** queries, please select the other appropriate sample.

The following workflow is executed by default:
1. All .sql files in the /services/db/entries folder are executed.
2. The **expected-query.sql** file is executed and output is kept as a **reference**
3. The **target script** from the candidate is executed and output is compared to the **reference**

# Condition of success
The challenge succeeds **only if** the two contents (**test** and **reference**) are equal.
That means that the **expected-query.sql** output should be exactly the same (order, rows, columns) as the **test**! Please pay much attention when you implement it.

# Sample content

The sample challenge provided is about **removing old entries from a table**.

# Quick Start

All your sql source files should be copied to the **services/db/entries/** directory, and **expected-query.sql** modified accordingly.

**NB**: Your sql file can contain multiple queries.

# Check-list
1. Remove existing files in the **services/db/entries** directory, and add your own. This will bootstrap the database content
2. Change the **expected-query.sql** file with your solution (only SELECT queries) to the challenge.
3. Update the docs **briefing**, **hint1**, and **hint2**
4. Update the 4 mandatory test templates in the **test** folder
