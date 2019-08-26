
# [Product Shop](https://product-shop-workshop.herokuapp.com/home?title=Product+Shop)
##### Workshop for the course [Spring MVC Frameworks - Spring](https://softuni.bg/trainings/2295/java-mvc-frameworks-spring-february-2019/open#lesson-11017) at [Software University](https://softuni.bg/). Deployed to [Heroku](https://dashboard.heroku.com/).
------------
### Simple Product Shop project with the functionality:

- User functionality
- Admin and User roles managed through the application
- Category CRUD functionality
- Product CRUD functionality
- Orders
- Shopping Cart
------------
### Running on:
- Java **11**
- Spring Boot **2.1.6.RELEASE**

### Some of the technologies used:
- Spring MVC
- Spring Security
    - User roles
	- CSRF protection
	- Google Recaptcha
	- Remember-me functionality
- Hibernate as ORM
- Thymeleaf as a templating engine and fragments
- [PostgreSQL](https://www.postgresql.org/) as database
- [Dropbox](https://www.dropbox.com/) for cloud storage
- [Lombok](https://projectlombok.org/) as a boilerplate remover
- [Model Mapper](ModelMapper) for object mapping
- Some AJAX for dynamically loading content

### Testing
- JUnit as a testing framework
- Mockito for mocking
- Spring Security testing
- Spring MVC Mock integration testing using [Flyway Test Extensions](https://github.com/flyway/flyway-test-extensions) and [Zonkyio](https://github.com/zonkyio/embedded-database-spring-test) to embed the PostgreSQL database.

