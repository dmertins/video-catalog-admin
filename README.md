# video-catalog-admin
A **Video Catalog Management Service** designed with **Domain-Driven Design** (**DDD**) and **Clean Architecture** concepts.

## Requirements
- JDK 17

## Domain overview
The system domain is composed of the following models:
- _Video_: represents each binary video file available on the platform.
- _Cast Member_: represents each actor who performs in a video.
- _Genre_: represents the video stylistic or thematic category.
- _Category_: represents the category of each video.
 
## Architecture overview
The project structure follows a 3-layer pattern:
- domain
- application
- infrastructure
