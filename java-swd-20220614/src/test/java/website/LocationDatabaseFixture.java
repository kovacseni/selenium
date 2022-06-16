package website;

import lombok.SneakyThrows;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class LocationDatabaseFixture {

    private JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public LocationDatabaseFixture() {
        MariaDbDataSource dataSource = new MariaDbDataSource("jdbc:mariadb://localhost/locations");
        dataSource.setUser("locations");
        dataSource.setPassword("locations");

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createLocation(String name, double latitude, double longitude) {
        jdbcTemplate.update("insert into location(name, lat, lon) values (?, ?, ?)",
                name, latitude, longitude);
    }

    public void deleteLocations() {
//        jdbcTemplate.update("delete from tag");
        jdbcTemplate.update("delete from location");
    }
}
