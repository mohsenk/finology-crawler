# Finology Shopping Crawler Test Project

It's a test project to Finoligy company interview's.
The main purpose of this project is find all the products links and crawling each of them to get product information and store it into a sqlite database.

This project contains 3 main parts :

1. **Spider** : The spider is starting from one page and looking for another links that's we need to crawl ( only project pages ).
2. **Crawler** : The crawler's job is getting project links from Spider and going to fetch project information from each page.
3. **DataStore** : After fetching product information in Crawler service we should store it into a sqlite database and also we have to check the existence of product by its url.


## Notes
- I didn't use any frameworks like Spring-Boot or IoC frameworks by my own decision;
- It can be faster, but I've decided to keep it simple.

## How to Build & Run

This project generates a farJar artifact that you can run it easily like this :

1. Build Project via Gradle
```bash
./gradlew shadowJar
```

2. Set ENV config variables

```bash
export SPIDER_START_URL=https://magento-test.finology.com.my/breathe-easy-tank.html
export DATABASE_URL=jdbc:sqlite:products.db
```   

2. Execute jar file with Java 16 

```bash
cd build/libs
java -jar crawler-service.jar
```


## How to Test

```bash
./gradlew test
```