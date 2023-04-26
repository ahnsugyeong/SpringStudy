package jpql;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

//            String query = "select m From Team t join t.members m";

//            String query = "select m From Member m";
//
//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
//                // 1 -> A (SQL)
//                // 2 -> A (1차 캐시)
//                // 3 -> B (SQL)
//                // 회원 100명 -> N + 1
//            }

//            String query = "select m From Member m join fetch m.team";

//            String query = "select distinct t From Team t join fetch t.members";
//
//            List<Team> result = em.createQuery(query, Team.class)
//                    .getResultList();
//
//            for (Team team : result) {
//                System.out.println("team = " + team.getName() + ", " + team.getMembers().size());
//
//            }
//
//
//            String query = "select t From Team t";
//            List<Team> result = em.createQuery(query, Team.class)
//                    .setFirstResult(0)
//                    .setMaxResults(2)
//                    .getResultList();
//            for (Team team : result) {
//                System.out.println("team = " + team.getName() + "|members = "+ team.getMembers().size());
//                for (Member member : team.getMembers()) {
//                    System.out.println("-> member = " + member);
//                }
//            }
//
//            // entity 직접 사용
//            String query = "select m From Member m where m = :member";
//            Member findMember = em.createQuery(query, Member.class)
//                    .setParameter("member", member1)
//                    .getSingleResult();
//            System.out.println("findMember = " + findMember);
//
//            // entity id 사용
//            String query2 = "select m From Member m where m.id = :memberId";
//            Member findMember2 = em.createQuery(query2, Member.class)
//                    .setParameter("memberId", member1.getId())
//                    .getSingleResult();
//            System.out.println("findMember2 = " + findMember2);

            // Named query
            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "member1")
                    .getResultList();
            for (Member member : resultList) {
                System.out.println("member = " + member);
            }


            // 벌크 연산 -> flush 자동 호출. DB에만 반영, 영속성 컨텍트스에 반영 X
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            em.clear(); // 영속성 컨텍스트 초기화
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());

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
