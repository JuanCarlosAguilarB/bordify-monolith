estregia para migrar a mongodb

    cambiar todos los @Entity por @Document
    ----cambiar los repositories por mongoTemplate
    cambiar los repositorios por MongoRepository


- problemas con los campo UUID:
    - mongo no soporta UUID
    - mongo no soporta @Id @GeneratedValue
    - mongo no soporta @Id @GeneratedValue(strategy = GenerationType.AUTO)
    - mongo no soporta @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    - mongo no soporta @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    - mongo no soporta @Id @GeneratedValue(strategy = GenerationType.TABLE)

    alternatives:
    - cambiar el campo UUID por String
    - crear una implementacion del repositorio para intercepter los save y actualizar el campo UUID
        - https://www.baeldung.com/spring-data-mongodb-custom-repository
        - https://www.baeldung.com/spring-data-mongodb-custom-repository-implementation
        - https://stackoverflow.com/questions/48842631/how-to-implement-custom-repository-in-spring-data-mongodb
        - https://stackoverflow.com/questions/49050831/how-to-implement-custom-repository-in-spring-data-mongodb
        - https://www.baeldung.com/spring-data-mongodb-custom-repository-implementation


