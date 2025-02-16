# Identity Access Management
Exodus IAM Service is an open-source Identity and Access Management (IAM) solution designed to provide user authentication, authorization, and management.

## Key features
- User management (create, update, delete, and retrieve users)
- User authentication using a password

## Installation
### Docker
The project includes a `Dockerfile` that allows you to containerize the Exodus IAM Service for deployment in Docker environments.
The project currently depends on the [JSend library](https://github.com/Maros1077/exodus-jsend-network), that must be included within the source files.
### Database
All the data are stored using in a database using Hibernate.

Example guide:
- Run a db container - e.g., [MySQL](https://hub.docker.com/_/mysql)
- In the project, navigate to `src/main/resources/`
- Run `db_init.sql` file to create a database and a db user.
- Specify datasource parameters in `application.properties` file.
- Run `schema.sql` file to create schemas.
- Run `data.sql` file to populate tables with a basic data.

## Endpoints
- `/iam/v1/int/create` - creates a new identity
- `/iam/v1/int/update` - updated an existing identity
- `/iam/v1/int/retrieve` - retrieves an identity
- `/iam/v1/int/auth` - provides authentication - Work in progess

## Key concepts - TODO
### Application
An application is defined by a unique name and acts as a tenant.

### Identity
An identity is a set of tags and auth points.

### Tag
A tag a value, that can be used to uniquelly identify an identity within an application.
It has a type and a value. Each identity can have multiple tags (but only one of each type).

Predefined tags in Exodus IAM:
- IDID - mandatory tag, UUID for an identity
- NICK - nick, e.g., `MySuperNick77`
- EMAIL - email (currently not validated)

Tags in the Exodus IAM are defined in the `tag_type` table.

### Auth points
An Auth Point represents a method of authentication tied to an identity. Each identity can have multiple Auth Points. Auth points can be added and removed over time.

Currently supported Auth Points:
- Password -  password-based authentication. Values are stored using BCrypt hash function.

### Authentication
TODO

## Tests
To ensure that the IAM service works correctly, the project includes a Postman collection in the `/tests` folder.