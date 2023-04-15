# Documentations

## createTable

This script creates a database table with the following structure:

### Domains
- `ALPHANUMERIC`: A domain with a maximum length of 10 characters, consisting letters and digits only.
- `URL`: A domain with a maximum length of 255 characters, representing a URL in the format of either HTTP or HTTPS.
- `EMAIL`: A domain with a maximum length of 254 characters, representing an email address.

### Tables
- `regiao`: Represents a region with a name field.
- `jogador`: Represents a player with fields including an auto-generated ID, username, email, player state (default: 'Ativo'), and region name.
- `jogo`: Represents a game with fields including an alphanumeric ID, name, and URL.
- `conversa`: Represents a conversation with an auto-generated ID and name.
- `jogador_estatistica`: Represents player statistics with fields including player ID, number of played matches, number of games played, and total points earned.
- `jogo_estatistica`: Represents game statistics with fields including game ID, number of matches played, number of players participated, and total points earned.
- `partida`: Represents a match with fields including an auto-incrementing serial number, start date, end date, game ID, and region name.
- `partida_normal`: Represents normal matches with fields including difficulty level, score, and match number.
- `partida_multijogador`: Represents multiplayer matches with fields including match state (default: 'Por iniciar'), score, and match number.
- `cracha`: Represents a badge with fields including name, image URL, point limit, and game ID.

Please note that some fields have constraints, such as unique values, check constraints, and foreign keys, which ensure data integrity in the database.

The script also includes some commented-out constants that can be used as references for defining maximum sizes or initial values for fields in the tables.

The tables are created using SQL statements and the script also includes some error handling, such as dropping tables if they already exist before creating them.

## insertValues

## createRestrictions

This script defines two triggers in a PostgreSQL database to enforce model restrictions for the "partida" and "mensagem" tables.

### Model Restriction 1: Partida must be played by jogadores of the same regiao
- Function Name: `checkJogadorPartidaRegiao()`
- Trigger Name: `checkJogadorPartidaRegiao`
- Description: This function is triggered before inserting a new row into the "joga" table (which represents players playing a match) and checks if the player belongs to the same region as the match they are trying to join. If not, it raises an exception indicating that the player does not belong to the region of the match.

### Model Restriction 2: Mensagem must be sent by a jogador of the conversa
- Function Name: `checkJogadorMensagemConversa()`
- Trigger Name: `checkJogadorMensagemConversa`
- Description: This function is triggered before inserting a new row into the "mensagem" table (which represents messages in a conversation) and checks if the player sending the message belongs to the conversation they are trying to send the message to. If not, it raises an exception indicating that the player does not belong to the conversation.

