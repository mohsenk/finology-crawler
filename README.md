# Finology Shopping Crawler Test Project

It's a test project for the Finoligy company interview.
The main purpose of this project is to find all the products links and crawling each of them to get product information and store it into an SQLite database.

For more information about this project you can read the [Project Definition](./defenition.md) document.

This project contains 3 main parts :

1. **Spider** : The spider is starting from one page and looking for other links that are we need to crawl ( only project pages ).
2. **Crawler** : The crawler's job is getting project links from Spider and going to fetch project information from each page.
3. **DataStore** : After fetching product information in Crawler service we should store it into an SQLite database and also we have to check the existence of the product by its URL.


## Notes
- I didn't use any frameworks like Spring-Boot or IoC frameworks by my own decision;
- It can be faster, but I've decided to keep it simple.
- I've been using Github Actions to Build, Test and Package.

## How to Build & Run

This project generates a far jar that you can run it easily like this.

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

On Fri, Jul 9, 2021 at 7:05 PM Mohsen Karimi <mohhsenk@gmail.com> wrote:
Hi Gobinath,
Thanks for your quick response.
Challenge accepted. ✌

Thanks in advance,
Mohsen Karimi.


On Fri, Jul 9, 2021 at 1:47 PM Gobinath Ganasan <gobinath.ganasan@finology.com.my> wrote:
Hi Mohsen Karimi,

Pleasure to receive your resume. We have reviewed it, and...

Congratulations on being selected to proceed with our recruitment process!

There are 3 stages to the recruitment process:
Stage 1: Technical Assessment  
Stage 2: 1st Interview (Technical)
Stage 3: 2nd Interview (Non-Technical)

Here is the Technical Assessment.

Please take your time to produce the best possible solutions. You have up to 7 days to revert back with your answers.

Please reply to this email to confirm the receipt of the assessment and that you are attempting them (or if you need more time).

Here you go:

Using the Java programming language, please write a web crawler to extract product information from this website: http://magento-test.finology.com.my/breathe-easy-tank.html

It should begin at the page mentioned above, and go on to find as many products on the site as you can. The product information gathered by your program must be stored into an sqlite database and also output to the console. The attributes of the product that you must gather are as follows:

1. Name of the Product
2. Price
3. The details or description of the product
4. Extra information about the product, as a delimited list

A sample of the output for one product would look like the following:

Name: Breathe-Easy Tank
Price: $34.00
Description: The Breathe Easy Tank is so soft, lightweight, and comfortable, you won't even know it's there -- until its high-tech Cocona® fabric starts wicking sweat away from your body to help you stay dry and focused. Layer it over your favorite sports bra and get moving. • Machine wash/dry. • Cocona® fabric.
Extra information: Style: Tank | Material: Cocona® performance fabric, Cotton | Pattern: Solid | Climate: Indoor, Warm

This is the basic functionality expected of your script but there are a few other things you must ensure.

1. You should take care to ensure that you don't list the same product more than once in your output.
2. The code must be well-structured and easy to understand

Beyond fulfilling the basic functionality requirements, you have the freedom to exhibit your other skills if you wish to. You may choose to cache the crawled pages so that you can regenerate the product db faster when you run the program more than once, you can choose a different method of formatting or sorting, or presenting your output. You may opt to use threads, you may write tests to ensure your program does the things expected of it, you may implement good logging, deployment or packaging skills with your program, etc.

--------------------

Good luck and hear from you soon!


Regards,

Gobinath Ganasan
Business Coordinator
+6011 1274 1742‬

Finology Sdn Bhd
52-1, Jalan Awan Hijau, Taman Oversea Union, 58200 Kuala Lumpur, Malaysia.
www.finology.com.my

PH: - | AL: -

DISCLAIMER: This email and its attachments may contain confidential or legally privileged information. It is intended solely for the use of the individual or entity to whom it is addressed. If you are not the intended recipient, you are hereby notified that disclosing, copying, distributing or taking any action in reliance on the contents of this information is strictly prohibited and may be unlawful. If you have received this communication in error, please notify the sender immediately by responding to this email and then delete it from your system.