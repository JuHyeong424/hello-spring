package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
spring의 JdbcTemplate을 사용하여 db 접근 구현
JdbcTemplate는 Jdbc 기반 db 작업 단순화
 */

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    /*
    JdbcTemplate는 DataSource를 통해 초기화
    생정자에 DataSource 주입받아 JdbcTemplate 생성
    DataSource는 JDBC 연결을 생성, 관리하는 표준 인터페이스. db 연결 책임
    JdbcTemplate가 내부적으로 DataSource를 사용해 db 연결을 요청하므로, 개발자가 직접 Jdbc 연결 관리 필요 없음
     */
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*
    SimpleJdbcInsert를 사용하여 db에 데이터를 삽입하고, 자동 생성된 키 값을 반환받아 엔티티 객체에 설정하는 작업 수행
    SimpleJdbcInsert: 스프링 Jdbc에서 제공하는 유틸리티 클래스, INSERT 작업을 간단하게 처리
     */
    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); // 테이블 이름을 member로, db가 자동으로 생성해주는 키(primary key) column을 id로 지정. 자동 생성된 키 값을 반환받을 때 필요

        Map<String, Object> parameters = new HashMap<>(); // db에 삽입할 column과 value 매핑. 키는 컬럼이름, 값은 해당 컬럼에 삽입할 데이터
        parameters.put("name", member.getName()); // name 컬럼에 삽입할 값으로 member.getName() 설정

        /*
        executeAndReturnKey(): 지정된 파라미터 데이터를 사용해 INSERT 작업을 수행하고, 자동 생성된 키 값을 반환
        new MapSqlParameterSource(parameters): parameters를 SQL parameter로 변환. SimpleJdbcInsert가 내부적으로 SQL 쿼리를 실행할 때 사용
         */
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue()); // Number 타입을 long으로 변환하여 자동 생성된 키 값을 Member 객체의 id 필드에 설정
        return member;
    }

    /*
    주어진 id로 member 테이블에서 데이터 조회
    JdbcTemplate의 query 메서드로 매핑
    query 메서드는 SQL 쿼리를 실행하고, 결과를 RowMapper 등을 사용해 객체 리스트로 변환
    결과는 RowMapper를 통해 Mapper 객체로 매핑
     */
    @Override
    public Optional<Member> findById(Long id) {  // 조회된 회원 객체(Member)를 Optional로 감싸 결과가 없을 경우 비어있는 Optional을 반환하여 NullPointerException 방지
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id); // SQL SELECT를 실행해 ResultSet의 각 행에 대해 제공된 RowMapper를 호출하여 객체 생성. 매핑된 객체를 List에 담아 반환
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    /*
    RowMapper를 사용해 ResultSet의 각 행을 Member 객체로 변환
    RowMapper: db의 ResultSet을 원하는 객체로 변환하기 위한 인터페이스. JDBC를 단순화하고, 데이터 매핑 로직을 재사용 가능하게 설계
    ResultSet: column과 row으로 구성된 데이터를 다루는 Jdbc 기본 인터페이스. 원하는 객체로 변환하려면 수동으로 데이터를 읽어와 매핑
    mapRow 메서드에서 id와 name을 읽어 Member 객체의 필드에 설정
     */
    private RowMapper<Member> memberRowMapper() {
        return new RowMapper<Member>() { // RowMapper 인터페이스를 구현한 익명 클래스. mapRow 메서드를 오버라이드하여 각 행을 어떻게 매핑할지 정의
            // ResultSet rs: SQL query 결과 집합. int rowNum: 현재 매핑 중인 행의 번호
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getLong("id")); // ResultSet에서 id라는 column을 읽어와 Member의 id 필드 생성
                member.setName(rs.getString("name")); // ResultSet에서 name이라는 column을 읽어와 Member의 name 필드 생성
                return member; // JdbcTemplate의 query 메서드가 결과 리스트에 추가
            }
        };
    }
}
