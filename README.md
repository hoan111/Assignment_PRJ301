# CSGO Server Rental Management (Beta)

A web page is created to manage game server for 5v5 match rental using Servlet, SQL Server & [Rest In Pawn Extension](https://github.com/ErikMinekus/sm-ripext)

## Description

How it work?
Because CSGO Server only support MySQL at this time so the game server can't insert match data to SQL Server. So the game server will communicate with SQL Server 
via some API endpoints in Servlet.
Basic features:
* Support match type: BO1, BO2, BO3, BO5.
* Auto kick player when no match is loaded to the game server.
* Auto update match score when the current match is live.
* Auto close match order when the match is not finished (Ex: All players disconnected when they were playing. So the match order should be set to 'Not finished' 
to prevent other player connect to the server).

## Getting Started

### Dependencies

* [GSON by Google](https://github.com/google/gson)
* [JDBC Driver for SQL Server](https://docs.microsoft.com/vi-vn/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-2017)
* [Java Standard Tag Library]()
* [Rest In Pawn Extension](https://github.com/ErikMinekus/sm-ripext)

### Installing & How to use
Will be update soon

