# BasicSprintApp

# Project Setup
1. Clone repository
2. import project into Intellij as Maven Project
3. run the application
4. open http://localhost:8080 to access the home page

# How it works
- Model classes Book and Author hold the id, title/name, and book/author properties.
- The Repoitory interfaces extends JpaRepsoitories and provieds crud operations for Book and Author models respectively
- Controller classes handle CRUD operations
- Thymeleaf templates are used to render views for the html.
