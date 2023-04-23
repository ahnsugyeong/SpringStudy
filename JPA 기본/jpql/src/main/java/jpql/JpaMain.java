package jpql;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setAge(10);
//            em.persist(member);

//            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();
//            System.out.println("result = " + result.getUsername());


//            List resultList = em.createQuery("select m.username, m.age from Member m")
//                    .getResultList();
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//            System.out.println("username = " + result[0]);
//            System.out.println("age = " + result[1]);

//            List<MemberDto> resultList = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class)
//                    .getResultList();
//            MemberDto result = resultList.get(0);
//            System.out.println("username = " + result.getUsername());
//            System.out.println("age = " + result.getAge());

//            for (int i = 0; i < 100; i++) {
//                Team team = new Team();
//                team.setName("teamA");
//                em.persist(team);
//
//                Member member = new Member();
//                member.setUsername("member" + i);
//                member.setAge(i);
//                member.changeTeam(team);
//
//
//                em.persist(member);
//            }

//            String query = "select m from Member m, Team t where m.username = t.name";
//            List<Member> result = em.createQuery(query, Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//            System.out.println("result.size() = " + result.size());


//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member");
//            member.setAge(10);
//            member.changeTeam(team);
//            member.setType(MemberType.ADMIN);
//
//            em.persist(member);
//
//            em.flush();
//            em.clear();

            // 조인 대상 필터링
//            String query = "select m from Member m left join m.team t on t.name = 'teamA'";

            // 연관관계 없는 엔티티 외부 조인
//            String query = "select m from Member m left join Team t on m.username = t.name";
//            List<Member> result = em.createQuery(query, Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//            System.out.println("result.size() = " + result.size());

            // JPQL 타입 표현
//            String query = "select m.username, 'HELLO', TRUE From Member m " +
//                    "where m.type = :userType";
//            List<Object[]> result = em.createQuery(query)
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList();
//
//            for (Object[] objects : result) {
//                System.out.println("objects = " + objects[0]);
//                System.out.println("objects = " + objects[1]);
//                System.out.println("objects = " + objects[2]);
//            }

//            String query =
//                    "select " +
//                            "case when m.age <= 10 then '학생요금' " +
//                            "     when m.age >= 60 then '경로요금' " +
//                            "     else '일반요금' " +
//                            "end " +
//                            "from Member m";
//            String query = "select coalesce(m.username, '이름 없는 회원') from Member m";
//            String query = "select nullif(m.username, '관리자') from Member m";
//            List<String> result = em.createQuery(query, String.class).getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            // JPQL 기본 함수
//            String query = "select concat('a', 'b') From Member m";
//            String query ="select substring(m.username, 2, 3) From Member m";
//            String query = "select locate('de', 'abcdefg') From Member m";
//            String query = "select size(t.members) From Team t";

            // 사용자 정의 함수
            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setAge(10);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setAge(10);

            em.persist(member1);
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select function('group_concat', m.username) From Member m ";
            List<String> result = em.createQuery(query, String.class).getResultList();
            for (String s : result) {
                System.out.println("s = " + s);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
