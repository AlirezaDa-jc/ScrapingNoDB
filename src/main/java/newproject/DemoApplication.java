package newproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DemoApplication extends SpringBootServletInitializer {
//	public static void main(String[] args) {
//
//				CassandraConnector connector = new CassandraConnector();
//				connector.connect("127.0.0.1", null);
//				Session session = connector.getSession();
//
//				KeyspaceRepository sr = new KeyspaceRepository(session);
//				sr.createKeyspace("library", "SimpleStrategy", 1);
//				sr.useKeyspace("library");
//
//				BookRepository br = new BookRepository(session);
//				br.createTable();
//				br.alterTablebooks("publisher", "text");
//
//				br.createTableBooksByTitle();
//
//				Book book = new Book(UUIDs.timeBased(), "Effective Java", "Joshua Bloch", "Programming");
//				br.insertBookBatch(book);
//
//				br.selectAll().forEach(o -> LOG.info("Title in books: " + o.getTitle()));
//				br.selectAllBookByTitle().forEach(o -> LOG.info("Title in booksByTitle: " + o.getTitle()));
//
//				br.deletebookByTitle("Effective Java");
//				br.deleteTable("books");
//				br.deleteTable("booksByTitle");
//
//				sr.deleteKeyspace("library");
//
//				connector.close();
//			}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
//	@Bean
//	public DataSource getDataSource() {
//		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//		dataSourceBuilder.url("jdbc:sqlserver://127.0.0.1:1433;database=Quera2;user=Hiru;password=pwd;encrypt=true;trustServerCertificate=true;loginTimeout=30;");
//		return dataSourceBuilder.build();
//	}
//	@Bean
//	public JdbcTemplate jdbcTemplate(){
//		return new JdbcTemplate(getDataSource());
//	}
//	@Bean
//	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
//		return new NamedParameterJdbcTemplate(getDataSource());
//	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DemoApplication.class);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public SpringTemplateEngine springTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		return templateEngine;
	}

	@Bean
	public ClassLoaderTemplateResolver htmlTemplateResolver() {
		ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
		emailTemplateResolver.setPrefix("/templates/");
		emailTemplateResolver.setSuffix(".html");
		emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
		emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
		return emailTemplateResolver;
	}
}
