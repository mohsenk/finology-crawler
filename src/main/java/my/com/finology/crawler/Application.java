package my.com.finology.crawler;

import com.google.gson.Gson;
import my.com.finology.crawler.workers.Crawler;
import my.com.finology.crawler.workers.Spider;
import my.com.finology.crawler.datastore.ProductRepository;
import my.com.finology.crawler.core.datastore.SqlConnectionInfo;
import my.com.finology.crawler.datastore.SqlLiteDataStore;
import my.com.finology.crawler.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.internal.StringUtil;

public class Application {

    static final Logger logger = LogManager.getLogger(Application.class.getSimpleName());

    public static void main(String[] args) {
        var app = new Application();
        app.start();
    }

    Crawler crawler;
    Spider spider;
    SqlLiteDataStore dataStore;

    void start() {
        var startUrl = System.getenv("SPIDER_START_URL"); // "https://magento-test.finology.com.my/breathe-easy-tank.html"
        var jdbcUrl = System.getenv("DATABASE_URL"); // "jdbc:sqlite:products.db"
        if (StringUtil.isBlank(startUrl)) {
            logger.error("SPIDER_START_URL env variable is empty or null , sample result : https://magento-test.finology.com.my/breathe-easy-tank.html");
            return;
        }

        if (StringUtil.isBlank(jdbcUrl) || !jdbcUrl.startsWith("jdbc:sqlite:") || !jdbcUrl.endsWith(".db")) {
            logger.error("DATABASE_URL env variable is empty or invalid , pattern : jdbc:sqlite:{DB_FILE_NAME}.db");
            return;
        }

        this.spider = getSpider(startUrl);
        this.crawler = getCrawler();
        this.dataStore = getDataStore(getProductRepository(getSqlConnection(jdbcUrl)));


        this.spider.setNewProductFoundEventListener(this::onNewProductFound);
        this.crawler.setNewProductFetchedEventListener(this::onNewProductFetched);


        this.spider.start();
        this.crawler.start();
    }

    void onNewProductFound(String url) {
        if (dataStore.exists(url)) {
            logger.info("Spider found a project but is already stored in database : {}", url);
            return;
        }
        logger.info("Spider found a new product at, going to crawl it : {}", url);
        crawler.addToQueue(url);
    }

    void onNewProductFetched(Product product) {
        var gson = new Gson().newBuilder().setPrettyPrinting().create();
        logger.info("I found a new project, I'm going to store it  : \r\n{}", gson.toJson(product));
        this.dataStore.save(product);
    }

    // IOC Beans ============================================== //

    public Crawler getCrawler() {
        return new Crawler();
    }

    public Spider getSpider(String startUrl) {
        return new Spider(startUrl);
    }

    public SqlLiteDataStore getDataStore(ProductRepository productRepository) {
        return new SqlLiteDataStore(productRepository);
    }

    public ProductRepository getProductRepository(SqlConnectionInfo sqlConnection) {
        return new ProductRepository(sqlConnection);
    }

    public SqlConnectionInfo getSqlConnection(String jdbcUrl) {
        return new SqlConnectionInfo(jdbcUrl);
    }
}
