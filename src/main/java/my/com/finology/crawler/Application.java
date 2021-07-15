package my.com.finology.crawler;

import my.com.finology.crawler.workers.MainWorker;

public class Application {

    public static void main(String[] args) {
        var mainWorker = new MainWorker();
        mainWorker.start();
    }


}
