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
The project structure follows a 3-layered pattern, where the _domain_ layer is on the highest level, and each layer knows only the layers above it:
- _domain_
- _application_
- _infrastructure_

## Tactical Patterns overview
The project adopts the following patterns from DDD for structuring the _domain_ models:
- _Entities_: represent objects that are distinguished by their identities.
- _Value Objects_: represent objects that are distinguished by their attributes.
- _Aggregates_: represent _entities_ that are responsible for clustering other _entities_ and _value objects_.
