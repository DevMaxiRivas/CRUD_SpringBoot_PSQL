package dev.mycrud.repositories;

import dev.mycrud.model.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

//    dependencia para facil insertacion en la db
    private final SimpleJdbcInsert insert;

    private final BookMapper mapper = new BookMapper();

    private final String table = "books";

    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
//        debemos especificar para que tabla se instanciara el simpleJdbcInsert ademas de su primarykey
        this.insert = new SimpleJdbcInsert(dataSource).withTableName(table).usingGeneratedKeyColumns("id");
    }

//  inyectamos la dependencia

    public List<Book> getAllBooks(){
        String sql = "select * from " + table;
        return jdbcTemplate.query(sql, mapper);
    }

    public long createBook(Book newBook) {
        return insert.executeAndReturnKey(
                new MapSqlParameterSource("name", newBook.name)
        ).longValue();
    }

    //    definimos una clase ya que necesitamos mapear las tuplas de la tabla
    private static class BookMapper implements RowMapper<Book>{
//    alt + enter para que se defina lo solicitado en la interfaz
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");

        return new Book(id, name);
    }
}



}
