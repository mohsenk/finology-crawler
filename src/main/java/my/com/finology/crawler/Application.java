package my.com.finology.crawler;

import my.com.finology.crawler.models.Product;
import my.com.finology.crawler.storage.ProductRepository;
import my.com.finology.crawler.core.Crawler;
import my.com.finology.crawler.workers.MainWorker;

public class Application {

	/*
	Connection connection = null;
		try
	{
		// create a database connection
		connection = DriverManager.getConnection("jdbc:sqlite:products.db");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);  // set timeout to 30 sec.

		statement.executeUpdate("drop table if exists person");
		statement.executeUpdate("create table person (id integer, name string)");
		statement.executeUpdate("insert into person values(1, 'leo')");
		statement.executeUpdate("insert into person values(2, 'yui')");
		ResultSet rs = statement.executeQuery("select * from person");
		while(rs.next())
		{
			// read the result set
			System.out.println("name = " + rs.getString("name"));
			System.out.println("id = " + rs.getInt("id"));
		}
	}
		catch(Exception e)
	{
		// if the error message is "out of memory",
		// it probably means no database file is found
		System.err.println(e.getMessage());
	}
		finally
	{
		try
		{
			if(connection != null)
				connection.close();
		}
		catch(SQLException e)
		{
			// connection close failed.
			System.err.println(e.getMessage());
		}
	 */

    public static void main(String[] args) {
//        var crawler = new Crawler();
//        var repository = new ProductRepository();
//        repository.create(new Product("https://digikala.com", "Test Test", "This is a description",15.0f,  null));
//        var product = repository.findById(1);
//        product.toString();

        var mainWorker = new MainWorker();
        mainWorker.start();
    }


}
