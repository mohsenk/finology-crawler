# Project Definition

Using the Java programming language, please write a web crawler to extract product information from this website: http://magento-test.finology.com.my/breathe-easy-tank.html


It should begin at the page mentioned above, and go on to find as many products on the site as you can. The product information gathered by your program must be stored into a sqlite database and also output to the console. The attributes of the product that you must gather are as follows:

1. Name of the Product
2. Price
3. The details or description of the product
4. Extra information about the product, as a delimited list

A sample of the output for one product would look like the following:

```
Name: Breathe-Easy Tank
Price: $34.00
Description: The Breathe Easy Tank is so soft, lightweight, and comfortable, you won't even know it's there -- until its high-tech Cocona® fabric starts wicking sweat away from your body to help you stay dry and focused. Layer it over your favorite sports bra and get moving. • Machine wash/dry. • Cocona® fabric.
Extra information: Style: Tank | Material: Cocona® performance fabric, Cotton | Pattern: Solid | Climate: Indoor, Warm
```

This is the basic functionality expected of your script but there are a few other things you must ensure.

1. You should take care to ensure that you don't list the same product more than once in your output.
2. The code must be well-structured and easy to understand

Beyond fulfilling the basic functionality requirements, you have the freedom to exhibit your other skills if you wish to. 
You may choose to cache the crawled pages so that you can regenerate the product db faster when you run the program more than once, you can choose a different method of formatting or sorting, or presenting your output. 
You may opt to use threads, you may write tests to ensure your program does the things expected of it, you may implement good logging, deployment or packaging skills with your program, etc.
