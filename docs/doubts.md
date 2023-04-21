# Case (id_jogo, nr) on partida 
https://www.postgresql.org/docs/current/sql-createtable.html#SQL-CREATETABLE-EXCLUDE

`PRIMARY KEY (column constraint)`
`PRIMARY KEY ( column_name [, ... ] )` [ `INCLUDE ( column_name [, ...])` ] (table constraint)

The `PRIMARY KEY` constraint specifies that a column or columns of a table can contain only unique (non-duplicate), nonnull values. 
Only one primary key can be specified for a table, whether as a column constraint or a table constraint.

The primary key constraint should name a set of columns that is different from the set of columns named by any unique constraint defined for the same table. 
(Otherwise, the unique constraint is redundant and will be discarded.)

`PRIMARY KEY` enforces the same data constraints as a combination of `UNIQUE` and `NOT NULL`. 
However, identifying a set of columns as the primary key also provides metadata about the design of the schema, since a primary key implies that other tables can rely on this set of columns as a unique identifier for rows.

When placed on a partitioned table, `PRIMARY KEY` constraints share the restrictions previously described for UNIQUE constraints.

Adding a PRIMARY KEY constraint will automatically create a unique btree index on the column or group of columns used in the constraint.

The optional `INCLUDE` clause adds to that index one or more columns that are simply “payload”: uniqueness is not enforced on them, and the index cannot be searched on the basis of those columns. However they can be retrieved by an index-only scan. 
Note that although the constraint is not enforced on included columns, it still depends on them. 
Consequently, some operations on such columns (e.g., `DROP COLUMN`) can cause cascaded constraint and index deletion.

# What can a banned player do?

- [] Send a messages?
- [] Play a game (single player or multiplayer)?
- [] Create conversations?

# When a trigger is fired do we have access to the transaction level and if so how, should we manipulate it?

# How to generate the columns of the stats tables? Generate them with a trigger or with a function? Tried the `Generated Always As` and didn't work.